package lt.crud.QRCodeScanner.Controller;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lt.crud.QRCodeScanner.exception.StorageException;
import lt.crud.QRCodeScanner.model.Beneficiary;
import lt.crud.QRCodeScanner.model.QRImage;
import lt.crud.QRCodeScanner.repository.BeneficiaryRepository;
import lt.crud.QRCodeScanner.repository.QRImageRepository;
import lt.crud.QRCodeScanner.service.ImageService;
import lt.crud.QRCodeScanner.service.QRCodeReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.zip.DataFormatException;

@RestController
@RequestMapping("/api/")
@AllArgsConstructor
@NoArgsConstructor
public class QRImageController {

    @Autowired
    private ImageService QRImageService;
    @Autowired
    private QRCodeReaderService QRCodeReaderService;

    @GetMapping("allQR")
    ResponseEntity<List<Beneficiary>> getContent() throws IOException, DataFormatException {
        List<Beneficiary> beneficiaries = QRImageService.getContent();
        return ResponseEntity.status(HttpStatus.OK).body(beneficiaries);
    }

    @PostMapping("upload")
    ResponseEntity<StorageException> decodeQRCode(@RequestParam("file") MultipartFile file,
                                                  @RequestParam("name") String name) {
        try {
            QRCodeReaderService.decodeQRCode(file, name);
            return ResponseEntity.status(HttpStatus.OK).body(new StorageException("File uploaded successfully!"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new StorageException("Failed to upload file"));
        }
    }

    @PostMapping("imageUrl")
    ResponseEntity<StorageException> getImageUrl(@RequestParam("url") String url) {
        try {
            MultipartFile file = QRImageService.getImageFromUrl(url);
            QRCodeReaderService.decodeQRCode(file, file.getName());
            return ResponseEntity.status(HttpStatus.OK).body(new StorageException("File uploaded successfully!"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new StorageException("Failed to upload file"));
        }
    }

}
