package lt.crud.QRCodeScanner.repository;

import lt.crud.QRCodeScanner.model.Beneficiary;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BeneficiaryRepository extends CrudRepository<Beneficiary, Long> {
}
