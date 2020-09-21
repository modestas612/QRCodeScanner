package lt.crud.QRCodeScanner;

import lt.crud.QRCodeScanner.properties.StorageProperties;
import lt.crud.QRCodeScanner.service.QRImageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class QrCodeScannerApplication {

	public static void main(String[] args) {
		SpringApplication.run(QrCodeScannerApplication.class, args);
	}
}
