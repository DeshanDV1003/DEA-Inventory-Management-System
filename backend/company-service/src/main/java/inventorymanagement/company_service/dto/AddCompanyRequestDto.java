package inventorymanagement.company_service.dto;

public class AddCompanyRequestDto {

    private String companyRegNumber;
    private String name;
    private String logoPath;
    private String address;
    private String phone;
    private String email;
    private String status;

    public AddCompanyRequestDto() {
    }

    public AddCompanyRequestDto(String companyRegNumber, String name, String logoPath,
                                String address, String phone, String email, String status) {
        this.companyRegNumber = companyRegNumber;
        this.name = name;
        this.logoPath = logoPath;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.status = status;
    }

    public String getCompanyRegNumber() {
        return companyRegNumber;
    }
    public void setCompanyRegNumber(String companyRegNumber) {
        this.companyRegNumber = companyRegNumber;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getLogoPath() {
        return logoPath;
    }

    public void setLogoPath(String logoPath) {
        this.logoPath = logoPath;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}