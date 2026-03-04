package inventorymanagement.warehouse_service.service.impl;

import inventorymanagement.warehouse_service.dto.WarehouseDTO;
import inventorymanagement.warehouse_service.entity.Warehouse;
import inventorymanagement.warehouse_service.repository.WarehouseRepository;
import inventorymanagement.warehouse_service.service.WarehouseService;
import inventorymanagement.warehouse_service.config.MyAPIConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WarehouseServiceImpl implements WarehouseService {

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private MyAPIConfig apiConfig;

    @Override
    public WarehouseDTO createWarehouse(WarehouseDTO warehouseDTO) {
        Warehouse entity = dtoToEntity(warehouseDTO);
        entity.setCreatedDate(LocalDateTime.now());
        Warehouse saved = warehouseRepository.save(entity);
        return entityToDto(saved);
    }

    @Override
    public List<WarehouseDTO> getAllWarehouses() {
        return warehouseRepository.findAll()
                .stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public WarehouseDTO getWarehouseById(Long id) {
        Warehouse w = warehouseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Warehouse not found"));
        return entityToDto(w);
    }

    @Override
    public WarehouseDTO updateWarehouse(Long id, WarehouseDTO warehouseDTO) {
        Warehouse existing = warehouseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Warehouse not found"));
        existing.setCompanyId(warehouseDTO.getCompanyId());
        existing.setName(warehouseDTO.getName());
        existing.setPhone(warehouseDTO.getPhone());
        existing.setEmail(warehouseDTO.getEmail());
        existing.setAddress(warehouseDTO.getAddress());
        existing.setStatus(warehouseDTO.getStatus());
        existing.setUpdatedBy(warehouseDTO.getUpdatedBy());
        existing.setUpdatedDate(LocalDateTime.now());
        Warehouse updated = warehouseRepository.save(existing);
        return entityToDto(updated);
    }

    @Override
    public void deleteWarehouse(Long id) {
        Warehouse existing = warehouseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Warehouse not found"));
        warehouseRepository.delete(existing);
    }

    // cross-service calls
    @Override
    public Object getPurchaseOrdersByWarehouse(Long id) {
        String url = apiConfig.getPoServiceUrl()+"/api/v1/purchase-orders/warehouse/"+id;
        return restTemplate.getForObject(url, Object.class);
    }

    @Override
    public Object getStockTransfersByWarehouse(Long id) {
        String url = apiConfig.getStockTransferServiceUrl()+"/api/v1/stock-transfers/warehouse/"+id;
        return restTemplate.getForObject(url, Object.class);
    }

    @Override
    public Object getGRNsByWarehouse(Long id) {
        String url = apiConfig.getGrnServiceUrl()+"/api/v1/grns/warehouse/"+id;
        return restTemplate.getForObject(url, Object.class);
    }

    @Override
    public Object getStockByWarehouse(Long id) {
        String url = apiConfig.getStockServiceUrl()+"/api/v1/stocks/warehouse/"+id;
        return restTemplate.getForObject(url, Object.class);
    }

    @Override
    public Object getAllCompanies() {
        String url = apiConfig.getCompanyServiceUrl()+"/api/v1/companies";
        try {
            return restTemplate.getForObject(url, Object.class);
        } catch (Exception e) {
            return List.of();
        }
    }

    private Warehouse dtoToEntity(WarehouseDTO dto) {
        Warehouse e = new Warehouse();
        e.setId(dto.getId());
        e.setCompanyId(dto.getCompanyId());
        e.setName(dto.getName());
        e.setPhone(dto.getPhone());
        e.setEmail(dto.getEmail());
        e.setAddress(dto.getAddress());
        e.setStatus(dto.getStatus());
        e.setCreatedBy(dto.getCreatedBy());
        e.setCreatedDate(dto.getCreatedDate());
        e.setUpdatedBy(dto.getUpdatedBy());
        e.setUpdatedDate(dto.getUpdatedDate());
        return e;
    }

    private WarehouseDTO entityToDto(Warehouse e) {
        WarehouseDTO d = new WarehouseDTO();
        d.setId(e.getId());
        d.setCompanyId(e.getCompanyId());
        d.setName(e.getName());
        d.setPhone(e.getPhone());
        d.setEmail(e.getEmail());
        d.setAddress(e.getAddress());
        d.setStatus(e.getStatus());
        d.setCreatedBy(e.getCreatedBy());
        d.setCreatedDate(e.getCreatedDate());
        d.setUpdatedBy(e.getUpdatedBy());
        d.setUpdatedDate(e.getUpdatedDate());
        return d;
    }
}