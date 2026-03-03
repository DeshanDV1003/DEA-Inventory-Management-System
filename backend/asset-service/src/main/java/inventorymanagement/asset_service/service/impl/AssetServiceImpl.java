package inventorymanagement.asset_service.service.impl;

import inventorymanagement.asset_service.entity.Asset;
import inventorymanagement.asset_service.repository.AssetRepository;
import inventorymanagement.asset_service.service.AssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class AssetServiceImpl implements AssetService {

    @Autowired
    private AssetRepository assetRepository;

    @Override
    public Asset addAsset(Asset asset) {

        asset.setStatus("ACTIVE");
        asset.setCreatedDate(LocalDate.now());
        return assetRepository.save(asset);
    }

    @Override
    public List<Asset> getAssetsByWarehouse(Long warehouseId) {

        return assetRepository.findByWarehouseIdAndIsDeletedFalse(warehouseId);
    }

    @Override
    public Asset updateAsset(Long id, Asset details) {
        Asset asset = assetRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Asset not found with id: " + id));

        asset.setName(details.getName());
        asset.setStatus(details.getStatus());
        asset.setWarranty(details.getWarranty());
        asset.setWarehouseId(details.getWarehouseId());

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