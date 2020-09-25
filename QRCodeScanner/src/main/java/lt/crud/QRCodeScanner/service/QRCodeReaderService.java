package lt.crud.QRCodeScanner.service;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lt.crud.QRCodeScanner.model.Beneficiary;
import lt.crud.QRCodeScanner.model.QRImage;
import lt.crud.QRCodeScanner.repository.BeneficiaryRepository;
import lt.crud.QRCodeScanner.repository.QRImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class QRCodeReaderService implements ReaderService {

    @Autowired
    private ImageService QRImageService;
    @Autowired
    private QRImageRepository QRImageRepository;
    @Autowired
    private BeneficiaryRepository beneficiaryRepository;

    @Override
    public void decodeQRCode(MultipartFile file, String name) throws IOException {
        QRImage imageInfo = new QRImage();
        imageInfo.setFilename(name);
        imageInfo.setUrl(file.getOriginalFilename());
        imageInfo.setPicByte(QRImageService.compressBytes(file.getBytes()));

        String scannedText = scanQRCode(file.getInputStream());
        List<String> QRTextList = Arrays.asList(scannedText.split(" ; "));

        Beneficiary beneficiary = new Beneficiary();
        beneficiary.setUniqCode(QRTextList.get(0));
        beneficiary.setName(QRTextList.get(1));
        beneficiary.setImage(imageInfo);

        QRImageRepository.save(imageInfo);
        beneficiaryRepository.save(beneficiary);
        QRImageService.store(file);
    }


    public String scanQRCode(InputStream file) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(file);
        LuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

        try {
            Result result = new MultiFormatReader().decode(bitmap);
            return result.getText();
        } catch (NotFoundException e) {
            System.out.println("There is no QR code in the image");
            return null;
        }
    }
}
