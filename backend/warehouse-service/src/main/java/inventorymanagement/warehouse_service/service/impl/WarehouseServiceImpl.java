package inventorymanagement.warehouse_service.service.impl;

import inventorymanagement.warehouse_service.dto.WarehouseDTO;
import inventorymanagement.warehouse_service.entity.Warehouse;
import inventorymanagement.warehouse_service.repository.WarehouseRepository;
import inventorymanagement.warehouse_service.service.WarehouseService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WarehouseServiceImpl implements WarehouseService {

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public WarehouseDTO createWarehouse(WarehouseDTO warehouseDTO) {
        Warehouse entity = dtoToEntity(warehouseDTO);
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
        BeanUtils.copyProperties(warehouseDTO, existing, "id");
        Warehouse updated = warehouseRepository.save(existing);
        return entityToDto(updated);
    }

    @Override
    public void deleteWarehouse(Long id) {
        warehouseRepository.deleteById(id);
    }

    // cross-service calls - patterns similar to supplier service
    @Override
    public Object getPurchaseOrdersByWarehouse(Long id) {
        String url = "http://purchase-order-service/api/purchase-orders/warehouse/" + id;
        return restTemplate.getForObject(url, Object.class);
    }

    @Override
    public Object getStockTransfersByWarehouse(Long id) {
        String url = "http://stock-transfer-service/api/stock-transfers/warehouse/" + id;
        return restTemplate.getForObject(url, Object.class);
    }

    @Override
    public Object getGRNsByWarehouse(Long id) {
        String url = "http://grn-service/api/grns/warehouse/" + id;
        return restTemplate.getForObject(url, Object.class);
    }

    @Override
    public Object getStockByWarehouse(Long id) {
        String url = "http://stock-service/api/stocks/warehouse/" + id;
        return restTemplate.getForObject(url, Object.class);
    }

    private Warehouse dtoToEntity(WarehouseDTO dto) {
        Warehouse e = new Warehouse();
        BeanUtils.copyProperties(dto, e);
        return e;
    }

    private WarehouseDTO entityToDto(Warehouse e) {
        WarehouseDTO d = new WarehouseDTO();
        BeanUtils.copyProperties(e, d);
        return d;
    }
}