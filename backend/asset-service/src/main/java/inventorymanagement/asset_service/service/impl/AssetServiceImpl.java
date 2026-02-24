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
        return assetRepository.findByWarehouseId(warehouseId);
    }

    @Override
    public Asset updateAsset(Long id, Asset details) {
        Asset asset = assetRepository.findById(id).orElseThrow();
        asset.setName(details.getName());
        asset.setAssetTag(details.getAssetTag());
        return assetRepository.save(asset);
    }

    @Override
    public void deleteAsset(Long id) {
        Asset asset = assetRepository.findById(id).orElseThrow();
        asset.setStatus("DELETED");
        assetRepository.save(asset);
    }

    @Override
    public List<Asset> getAllAssets() {
        return assetRepository.findAll();
    }
}