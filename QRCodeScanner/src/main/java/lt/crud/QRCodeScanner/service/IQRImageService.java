package lt.crud.QRCodeScanner.service;

import lt.crud.QRCodeScanner.model.QRImage;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.Path;
import java.util.Optional;

public interface IQRImageService {
    void init();

    void store(MultipartFile file);

    Path directory();

}
