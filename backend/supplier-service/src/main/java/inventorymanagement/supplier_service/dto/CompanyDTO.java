package inventorymanagement.supplier_service.dto;

public class CompanyDTO {
    private Long id;
    private String name;

    public CompanyDTO() {}

    public CompanyDTO(Long id, String name) {
        this.id = id;
        this.name = name;
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