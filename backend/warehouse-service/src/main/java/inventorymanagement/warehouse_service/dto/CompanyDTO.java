package inventorymanagement.warehouse_service.dto;

public class CompanyDTO {
    private Long id;
    private String name;

    public CompanyDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}