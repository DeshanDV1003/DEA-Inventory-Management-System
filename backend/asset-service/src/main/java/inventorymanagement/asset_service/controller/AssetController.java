package inventorymanagement.asset_service.controller;

import inventorymanagement.asset_service.entity.Asset;
import inventorymanagement.asset_service.service.AssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/assets")
public class AssetController {

    @Autowired
    private AssetService assetService;

    @PostMapping("/add")
    public Asset createAsset(@RequestBody Asset asset) {
        return assetService.addAsset(asset);
    }

    @GetMapping("/warehouse/{warehouseId}")
    public List<Asset> getByWarehouse(@PathVariable Long warehouseId) {
        return assetService.getAssetsByWarehouse(warehouseId);
    }

    @GetMapping("/all")
    public List<Asset> getAll() {
        return assetService.getAllAssets();
    }
}