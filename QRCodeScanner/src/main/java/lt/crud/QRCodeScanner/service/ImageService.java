package lt.crud.QRCodeScanner.service;

import lt.crud.QRCodeScanner.model.Beneficiary;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.zip.DataFormatException;

public interface ImageService {

    void store(MultipartFile file);

    List<Beneficiary> getContent() throws IOException, DataFormatException;

    MultipartFile getImageFromUrl(String baseUrl) throws IOException;

    byte[] compressBytes(byte[] data) throws IOException;

}
