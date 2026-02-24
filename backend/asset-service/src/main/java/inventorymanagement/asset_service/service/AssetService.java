package inventorymanagement.asset_service.service;

import inventorymanagement.asset_service.entity.Asset;
import java.util.List;

public interface AssetService {
    Asset addAsset(Asset asset);
    Asset updateAsset(Long id, Asset asset);
    List<Asset> getAllAssets();
    List<Asset> getAssetsByWarehouse(Long warehouseId);
    void deleteAsset(Long id);
}