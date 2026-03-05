package inventorymanagement.company_service.repository;

import inventorymanagement.company_service.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Integer> {

    Company findCompanyById(Integer id);

}