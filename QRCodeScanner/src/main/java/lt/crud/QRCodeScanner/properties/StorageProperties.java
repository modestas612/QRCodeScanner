package lt.crud.QRCodeScanner.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("storage")
@Getter
@Setter
public class StorageProperties {

    private String location = "QR-codes";
}
