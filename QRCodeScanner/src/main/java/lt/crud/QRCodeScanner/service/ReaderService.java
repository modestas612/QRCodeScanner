package lt.crud.QRCodeScanner.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

public interface ReaderService {

    void decodeQRCode(MultipartFile file, String name)throws IOException;
}
