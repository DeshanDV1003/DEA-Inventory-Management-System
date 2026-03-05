package inventorymanagement.company_service.controller;

import inventorymanagement.company_service.dto.AddCompanyRequestDto;
import inventorymanagement.company_service.entity.Company;
import inventorymanagement.company_service.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/companies")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @GetMapping
    public List<Company> getAllCompanies() {
        return companyService.findAllCompanies();
    }

    @PostMapping(value = "addCompany")
    public String insert(@RequestBody AddCompanyRequestDto request) {
        companyService.saveCompany(request);
        return "Company added successfully";
    }

    @DeleteMapping("deleteCompany/{id}")
    public String delete(@PathVariable Integer id) {
        companyService.deleteCompany(id);
        return "The company with id: " + id + " has been deleted";
    }

    @GetMapping("getcompany")
    public Company getCompany(@RequestParam Integer id)
    {
        return companyService.findCompanyById(id);
    }

    @PutMapping("updateCompany/{id}")
    public String update(@PathVariable Integer id, @RequestBody AddCompanyRequestDto request) {

        companyService.updateCompany(id, request);
        return "Company updated successfully";
    }
}
