package lt.crud.QRCodeScanner.service;

import java.io.IOException;
import java.io.InputStream;

public interface IORCodeReaderService {

    String decodeQRCode(InputStream file) throws IOException;
}
