package inventorymanagement.stock_service.dto;

public class CompanyResponseDTO {
    private int companyId;
    private String companyName;
    private boolean active;

    public CompanyResponseDTO() {}

    public int getCompanyId() { return companyId; }
    public void setCompanyId(int companyId) { this.companyId = companyId; }
    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
}