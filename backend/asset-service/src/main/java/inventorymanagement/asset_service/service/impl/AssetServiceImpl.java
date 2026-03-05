package inventorymanagement.asset_service.service.impl;

import inventorymanagement.asset_service.entity.Asset;
import inventorymanagement.asset_service.repository.AssetRepository;
import inventorymanagement.asset_service.service.AssetService;
import inventorymanagement.asset_service.client.WarehouseClient;
import inventorymanagement.asset_service.exception.AssetNotFoundException;
import inventorymanagement.asset_service.exception.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class AssetServiceImpl implements AssetService {

    @Autowired
    private AssetRepository assetRepository;
    @Autowired
    private WarehouseClient warehouseClient;

    @Override
    public Asset addAsset(Asset asset) {
//        try {
//            warehouseClient.getWarehouseById(asset.getWarehouseId());
//        } catch (Exception e) {
//            throw new ValidationException("Validation Failed: Warehouse ID " + asset.getWarehouseId() + " not found.");
//        }

        asset.setCreatedBy("Randil Gimantha"); // In production, get this from JWT context
        asset.setCreatedDate(LocalDate.now());
        asset.setDeleted(false);
        asset.setStatus("ACTIVE");
        asset.setAssetTag(asset.getAssetTag().toUpperCase());
        return assetRepository.save(asset);
    }

    @Override
    public List<Asset> getAssetsByWarehouse(Long warehouseId) {

        return assetRepository.findByWarehouseIdAndIsDeletedFalse(warehouseId);
    }

    @Override
    public Asset updateAsset(Long id, Asset details) {
        Asset asset = assetRepository.findById(id)
                .orElseThrow(() -> new AssetNotFoundException("Update Failed: No asset found with ID " + id));

        asset.setName(details.getName());
        asset.setAssetTag(details.getAssetTag().toUpperCase());
        asset.setStatus(details.getStatus());
        asset.setWarranty(details.getWarranty());
        asset.setWarehouseId(details.getWarehouseId());
        asset.setModifiedBy("System_User");
        asset.setModifiedDate(LocalDate.now());
        asset.setModifiedBy("System_Admin");
        asset.setModifiedDate(LocalDate.now());

        return assetRepository.save(asset);
    }

    @Override
    public void deleteAsset(Long id) {
        Asset asset = assetRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Asset not found with id: " + id));

        asset.setDeleted(true);
        assetRepository.save(asset);

    }

    @Override
    public List<Asset> getAllAssets() {
        return assetRepository.findByIsDeletedFalse();
    }
}