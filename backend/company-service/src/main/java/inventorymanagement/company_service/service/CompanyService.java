package inventorymanagement.company_service.service;

import inventorymanagement.company_service.dto.AddCompanyRequestDto;
import inventorymanagement.company_service.entity.Company;

import java.util.List;

public interface CompanyService {

    List<Company> findAllCompanies();

    void saveCompany(AddCompanyRequestDto request);

    void deleteCompany(Integer id);

    Company findCompanyById(Integer id);

    void updateCompany(Integer id, AddCompanyRequestDto request);
}