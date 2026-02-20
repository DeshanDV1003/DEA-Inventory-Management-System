package inventorymanagement.user_service.dto;

public class RegisterRequest {
    private String username;
    private String password;
    private String fullName;
    private String designation;
    private String email;
    private String phone;
    private String userType;
    private Long companyId;
    private Long warehouseId;

    public RegisterRequest() {
    }

    public RegisterRequest(String username, String password, String fullName, String designation, String email, String phone, String userType, Long companyId, Long warehouseId) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.designation = designation;
        this.email = email;
        this.phone = phone;
        this.userType = userType;
        this.companyId = companyId;
        this.warehouseId = warehouseId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }
}
