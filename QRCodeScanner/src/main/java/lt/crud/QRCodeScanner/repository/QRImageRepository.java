package lt.crud.QRCodeScanner.repository;

import lt.crud.QRCodeScanner.model.QRImage;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QRImageRepository extends CrudRepository<QRImage, Long> {
}
