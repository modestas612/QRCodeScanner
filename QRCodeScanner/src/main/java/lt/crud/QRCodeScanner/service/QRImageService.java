package lt.crud.QRCodeScanner.service;

import lt.crud.QRCodeScanner.exception.StorageException;
import lt.crud.QRCodeScanner.exception.StorageFileNotFoundException;
import lt.crud.QRCodeScanner.model.QRImage;
import lt.crud.QRCodeScanner.properties.StorageProperties;
import lt.crud.QRCodeScanner.repository.QRImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Service
public class QRImageService implements IQRImageService {

    private Path rootLocation;

    @Autowired
    public QRImageService(StorageProperties properties){
        this.rootLocation = Paths.get(properties.getLocation());
    }
    @Autowired
    private QRImageRepository qrImageRepository;

    @Override
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

    @Override
    public Path directory() {
        return rootLocation;
    }
}
