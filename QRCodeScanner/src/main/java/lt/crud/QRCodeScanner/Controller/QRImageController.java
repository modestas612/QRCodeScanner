package lt.crud.QRCodeScanner.Controller;

import lt.crud.QRCodeScanner.exception.StorageException;
import lt.crud.QRCodeScanner.model.Beneficiary;
import lt.crud.QRCodeScanner.model.QRImage;
import lt.crud.QRCodeScanner.repository.BeneficiaryRepository;
import lt.crud.QRCodeScanner.repository.QRImageRepository;
import lt.crud.QRCodeScanner.service.IQRImageService;
import lt.crud.QRCodeScanner.service.QRCodeReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class QRImageController {

    @Autowired
    private IQRImageService QRImageService;
    @Autowired
    private QRImageRepository QRImageRepository;
    @Autowired
    private QRCodeReaderService QRCodeReaderService;
    @Autowired
    private BeneficiaryRepository beneficiaryRepository;

    @GetMapping("allQR")
    ResponseEntity<List<Beneficiary>> displayAllQRCodes(Model model) throws IOException {

        List<Beneficiary> beneficiarys = new ArrayList<>();
        Iterable<Beneficiary> beneficiaryInfo = beneficiaryRepository.findAll();

        for (Beneficiary beneficiary : beneficiaryInfo) {
            beneficiarys.add(beneficiary);
        }

        return ResponseEntity.status(HttpStatus.OK).body(beneficiarys);
    }

    @PostMapping("upload")
    ResponseEntity<StorageException> uploadAndDecodeQRCode(@RequestParam("file") MultipartFile file,
                                                           @RequestParam("name") String name) {
        QRImageService.init();
        try {
            QRImage imageInfo = new QRImage();
            imageInfo.setFilename(name);
            imageInfo.setUrl(file.getOriginalFilename());

            String decodedText = QRCodeReaderService.decodeQRCode(file.getInputStream());
            List<String> QRTextList = Arrays.asList(decodedText.split(" ; "));

            Beneficiary beneficiary = new Beneficiary();
            beneficiary.setUniqCode(QRTextList.get(0));
            beneficiary.setName(QRTextList.get(1));
            beneficiary.setImage(imageInfo);

            QRImageRepository.save(imageInfo);
            beneficiaryRepository.save(beneficiary);
            QRImageService.store(file);

            return ResponseEntity.status(HttpStatus.OK).body(new StorageException("File uploaded successfully!"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new StorageException("Failed to upload file"));
        }
    }
}
