package inventorymanagement.product_service.dto;

public class AddProductRequestDto {
    private Integer warehouseId;
    private Integer companyId;
    private Integer supplierId;
    private String name;
    private Integer sku;
    private String price;
    private String imgPath;
    private String status;
    private String createdBy;
    private String modifiedBy;

    public AddProductRequestDto() {
    }

    public AddProductRequestDto(Integer warehouseId, Integer companyId, Integer supplierId, String name, Integer sku, String price, String imgPath, String status, String createdBy, String modifiedBy) {
        this.warehouseId = warehouseId;
        this.companyId = companyId;
        this.supplierId = supplierId;
        this.name = name;
        this.sku = sku;
        this.price = price;
        this.imgPath = imgPath;
        this.status = status;
        this.createdBy = createdBy;
        this.modifiedBy = modifiedBy;
    }

    public Integer getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Integer warehouseId) {
        this.warehouseId = warehouseId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSku() {
        return sku;
    }

    public void setSku(Integer sku) {
        this.sku = sku;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }
}
