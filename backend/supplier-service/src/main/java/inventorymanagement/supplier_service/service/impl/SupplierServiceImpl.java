package inventorymanagement.supplier_service.service.impl;

import inventorymanagement.supplier_service.config.MyAPIConfig;
import inventorymanagement.supplier_service.dto.SupplierDTO;
import inventorymanagement.supplier_service.entity.Supplier;
import inventorymanagement.supplier_service.exception.ResourceNotFoundException;
import inventorymanagement.supplier_service.repository.SupplierRepository;
import inventorymanagement.supplier_service.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SupplierServiceImpl implements SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private MyAPIConfig apiConfig;

    @Override
    public SupplierDTO createSupplier(SupplierDTO dto) {
        Supplier entity = mapToEntity(dto);
        entity.setCreatedDate(LocalDateTime.now());
        Supplier saved = supplierRepository.save(entity);
        return mapToDTO(saved);
    }

    @Override
    public List<SupplierDTO> getAllSuppliers() {
        return supplierRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public SupplierDTO getSupplierById(Long id) {
        Supplier entity = supplierRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Supplier not found with id: " + id));
        return mapToDTO(entity);
    }

    @Override
    public SupplierDTO updateSupplier(Long id, SupplierDTO dto) {
        Supplier entity = supplierRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Supplier not found with id: " + id));

        entity.setCompanyId(dto.getCompanyId());
        entity.setName(dto.getName());
        entity.setPhone(dto.getPhone());
        entity.setEmail(dto.getEmail());
        entity.setAddress(dto.getAddress());
        entity.setStatus(dto.getStatus());
        entity.setUpdatedBy(dto.getUpdatedBy());
        entity.setUpdatedDate(LocalDateTime.now());

        Supplier updated = supplierRepository.save(entity);
        return mapToDTO(updated);
    }

    @Override
    public void deleteSupplier(Long id) {
        if (!supplierRepository.existsById(id)) {
            throw new ResourceNotFoundException("Supplier not found with id: " + id);
        }
        supplierRepository.deleteById(id);
    }

    @Override
    public Object getProductsBySupplier(Long supplierId) {
        String url = apiConfig.getProductServiceUrl() + "/api/v1/products/supplier/" + supplierId;
        return restTemplate.getForObject(url, Object.class);
    }

    @Override
    public Object getPurchaseOrdersBySupplier(Long supplierId) {
        String url = apiConfig.getPoServiceUrl() + "/api/v1/purchase-orders/supplier/" + supplierId;
        return restTemplate.getForObject(url, Object.class);
    }

    @Override
    public Object approveGRN(Long supplierId, Long grnId) {
        String url = apiConfig.getGrnServiceUrl() + "/api/v1/grn/" + grnId + "/approve?supplierId=" + supplierId;
        restTemplate.put(url, null);
        return "GRN Approved successfully";
    }

    @Override
    public Object getAllCompanies() {
        String url = apiConfig.getCompanyServiceUrl() + "/api/v1/companies";
        try {
            return restTemplate.getForObject(url, Object.class);
        } catch (Exception e) {
            return List.of();
        }
    }

    private Supplier mapToEntity(SupplierDTO dto) {
        Supplier entity = new Supplier();
        entity.setId(dto.getId());
        entity.setCompanyId(dto.getCompanyId());
        entity.setName(dto.getName());
        entity.setPhone(dto.getPhone());
        entity.setEmail(dto.getEmail());
        entity.setAddress(dto.getAddress());
        entity.setStatus(dto.getStatus());
        entity.setCreatedBy(dto.getCreatedBy());
        entity.setCreatedDate(dto.getCreatedDate());
        entity.setUpdatedBy(dto.getUpdatedBy());
        entity.setUpdatedDate(dto.getUpdatedDate());
        return entity;
    }

    private SupplierDTO mapToDTO(Supplier entity) {
        SupplierDTO dto = new SupplierDTO();
        dto.setId(entity.getId());
        dto.setCompanyId(entity.getCompanyId());
        dto.setName(entity.getName());
        dto.setPhone(entity.getPhone());
        dto.setEmail(entity.getEmail());
        dto.setAddress(entity.getAddress());
        dto.setStatus(entity.getStatus());
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setUpdatedBy(entity.getUpdatedBy());
        dto.setUpdatedDate(entity.getUpdatedDate());
        return dto;
    }
}
