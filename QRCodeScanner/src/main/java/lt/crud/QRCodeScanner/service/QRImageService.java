package lt.crud.QRCodeScanner.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lt.crud.QRCodeScanner.exception.StorageException;
import lt.crud.QRCodeScanner.model.Beneficiary;
import lt.crud.QRCodeScanner.model.QRImage;
import lt.crud.QRCodeScanner.repository.BeneficiaryRepository;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

@Service
@NoArgsConstructor
@AllArgsConstructor
public class QRImageService implements ImageService {

    private Path rootLocation;

    @Autowired
    private BeneficiaryRepository beneficiaryRepository;

    public void init() {
        File directory = new File(rootLocation.toString());
        if (!directory.exists()) {
            directory.mkdir();
        }
    }

    @Override
    public void store(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file " + file.getOriginalFilename());
            }
            Files.copy(file.getInputStream(), this.rootLocation.resolve(file.getOriginalFilename()));
        } catch (IOException e) {
            throw new StorageException("Failed to store file " + file.getOriginalFilename(), e);
        }
    }

    public Path directory() {
        return rootLocation;
    }

    @Override
    public List<Beneficiary> getContent() throws IOException, DataFormatException {
        List<Beneficiary> beneficiaries = new ArrayList<>();
        Iterable<Beneficiary> beneficiaryInfo = beneficiaryRepository.findAll();

        for (Beneficiary beneficiary : beneficiaryInfo) {
            QRImage image = beneficiary.getImage();
            image.setPicByte(decompressBytes(image.getPicByte()));
            beneficiaries.add(beneficiary);
        }
        return beneficiaries;
    }

    @Override
    public MultipartFile getImageFromUrl(String baseUrl) throws IOException {

        URL url = new URL(baseUrl);
        File file = new File(String.format("%s.%s", "QR", RandomStringUtils.randomAlphanumeric(4)));
        FileUtils.copyURLToFile(url, file);

        InputStream stream =  new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile(file.getName(), file.getName(), MediaType.IMAGE_PNG_VALUE, stream);

        return multipartFile;
    }

    @Override
    public byte[] compressBytes(byte[] data) throws IOException {
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        deflater.finish();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        outputStream.close();
        return outputStream.toByteArray();
    }

    public static byte[] decompressBytes(byte[] data) throws IOException, DataFormatException {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];

        while (!inflater.finished()) {
            int count = inflater.inflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        outputStream.close();

        return outputStream.toByteArray();
    }

}
