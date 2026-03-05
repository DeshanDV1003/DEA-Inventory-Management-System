package inventorymanagement.stock_service.constants;

public class AppConstants {
        private AppConstants() {}

        // Success messages
        public static final String STOCK_CREATED     = "Stock record created successfully.";
        public static final String STOCK_UPDATED     = "Stock record updated successfully.";
        public static final String STOCK_DELETED     = "Stock record deleted successfully.";
        public static final String STOCK_FETCHED     = "Stock record fetched successfully.";
        public static final String STOCK_LIST_FETCHED = "Stock records fetched successfully.";

        // Error messages
        public static final String STOCK_NOT_FOUND        = "Stock record not found with ID: ";
        public static final String STOCK_DUPLICATE        = "Stock already exists for this product in the given warehouse and company.";
        public static final String COMPANY_NOT_FOUND      = "Company not found with ID: ";
        public static final String WAREHOUSE_NOT_FOUND    = "Warehouse not found with ID: ";
        public static final String PRODUCT_NOT_FOUND      = "Product not found with ID: ";
        public static final String INVALID_STOCK_LEVELS   = "minStockLevel must be less than maxStockLevel and reOrderLevel must be between them.";
    }

