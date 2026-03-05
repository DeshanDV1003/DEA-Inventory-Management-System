package inventorymanagement.company_service.service.impl;

import inventorymanagement.company_service.dto.AddCompanyRequestDto;
import inventorymanagement.company_service.entity.Company;
import inventorymanagement.company_service.repository.CompanyRepository;
import inventorymanagement.company_service.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    public List<Company> findAllCompanies() {
        return companyRepository.findAll();
    }

    public void saveCompany(AddCompanyRequestDto request) {

        Company company = new Company();

        company.setCompanyRegNumber(request.getCompanyRegNumber());
        company.setName(request.getName());
        company.setAddress(request.getAddress());
        company.setEmail(request.getEmail());
        company.setPhone(request.getPhone());
        company.setStatus(request.getStatus());

        company.setCreatedDate(LocalDateTime.now());
        company.setCreatedBy("SYSTEM");

        companyRepository.save(company);
    }

    public void deleteCompany(Integer id) {
        companyRepository.deleteById(id);
    }

    public Company findCompanyById(Integer id) {
        return companyRepository.findById(id).orElse(null);
    }
    public void updateCompany(Integer id, AddCompanyRequestDto request) {

        Company company = companyRepository.findById(id).orElse(null);

        if (company != null) {
            company.setCompanyRegNumber(request.getCompanyRegNumber());
            company.setName(request.getName());
            company.setAddress(request.getAddress());
            company.setEmail(request.getEmail());
            company.setPhone(request.getPhone());
            company.setStatus(request.getStatus());

            company.setUpdatedDate(LocalDateTime.now());
            company.setUpdatedBy("SYSTEM");

            companyRepository.save(company);
        }
    }
}