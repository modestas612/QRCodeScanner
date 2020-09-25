package lt.crud.QRCodeScanner.properties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("storage")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StorageProperties {

    private String location = "QR-codes";
}
