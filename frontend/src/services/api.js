import axios from "axios";

const USER_SERVICE = import.meta.env.VITE_USER_SERVICE || "http://16.16.127.75:8072";
const PRODUCT_SERVICE = import.meta.env.VITE_PRODUCT_SERVICE || "http://16.16.127.75:8082";
const PO_SERVICE = import.meta.env.VITE_PO_SERVICE || "http://16.16.127.75:8062";
const COMPANY_SERVICE = import.meta.env.VITE_COMPANY_SERVICE || "http://16.16.127.75:8022";
const STOCK_SERVICE = import.meta.env.VITE_STOCK_SERVICE || "http://16.16.127.75:8042";
const STOCK_TRANSFER_SERVICE = import.meta.env.VITE_STOCK_TRANSFER_SERVICE || "http://16.16.127.75:8052";
const ASSET_SERVICE = import.meta.env.VITE_ASSET_SERVICE || "http://16.16.127.75:8012";
const GRN_SERVICE = import.meta.env.VITE_GRN_SERVICE || "http://16.16.127.75:8032";
const WAREHOUSE_SERVICE = import.meta.env.VITE_WAREHOUSE_SERVICE || "http://16.16.127.75:9012";
const SUPPLIER_SERVICE = import.meta.env.VITE_SUPPLIER_SERVICE || "http://16.16.127.75:9022";
const MAINTENANCE_SERVICE = import.meta.env.VITE_MAINTENANCE_SERVICE || "http://16.16.127.75:8092";


// ── Auth ─────────────────────────────────────────────────────────────────────
export const loginUser = (credentials) =>
  axios.post(`${USER_SERVICE}/api/v1/auth/login`, credentials);

export const registerUser = (data) =>
  axios.post(`${USER_SERVICE}/api/v1/auth/register`, data);




// ── Users ─────────────────────────────────────────────────────────────────────
const authHeader = () => ({
  headers: { Authorization: `Bearer ${localStorage.getItem("token")}` },
});

export const getAllUsers = () =>
    axios.get(`${USER_SERVICE}/api/v1/users`, authHeader());

export const addUser = (data) =>
    axios.post(`${USER_SERVICE}/api/v1/users/addUser`, data, authHeader());

export const deleteUser = (id) =>
    axios.delete(`${USER_SERVICE}/api/v1/users/deleteUser/${id}`, authHeader());

export const validateToken = () =>
    axios.get(`${USER_SERVICE}/api/v1/auth/validate`, authHeader());





// ── Products ─────────────────────────────────────────────────────────────────
export const getAllProducts = () =>
  axios.get(`${PRODUCT_SERVICE}/api/v1/products`, authHeader());

export const addProduct = (product) =>
  axios.post(`${PRODUCT_SERVICE}/api/v1/products/addProduct`, product, authHeader());

export const updateProduct = (id, product) =>
  axios.put(`${PRODUCT_SERVICE}/api/v1/products/updateProduct/${id}`, product, authHeader());

export const deleteProduct = (id) =>
  axios.delete(`${PRODUCT_SERVICE}/api/v1/products/deleteProduct/${id}`, authHeader());





// ── PURCHASE ORDER  ─────────────────────────────────────────────────────────────────

export const getAllPurchaseOrders = () =>
    axios.get(`${PO_SERVICE}/api/v1/purchase-orders`, authHeader());

export const getPurchaseOrderById = (id) =>
    axios.get(
        `${PO_SERVICE}/api/v1/purchase-orders/${id}`,
        authHeader()
    );

export const createPurchaseOrder = (data) =>
    axios.post(
        `${PO_SERVICE}/api/v1/purchase-orders`,
        data,
        authHeader()
    );

export const updatePurchaseOrder = (id, data) =>
    axios.put(
        `${PO_SERVICE}/api/v1/purchase-orders/${id}`,
        data,
        authHeader()
    );

export const updatePurchaseOrderStatus = (id, status) =>
    axios.patch(
        `${PO_SERVICE}/api/v1/purchase-orders/${id}/status`,
        { status },
        authHeader()
    );

export const deletePurchaseOrder = (id) =>
    axios.delete(
        `${PO_SERVICE}/api/v1/purchase-orders/${id}`,
        authHeader()
    );




// ── Companies ─────────────────────────────────────────────────────────────────
export const getAllCompanies = () =>
    axios.get(`${COMPANY_SERVICE}/api/v1/companies`, authHeader());

export const addCompany = (data) =>
    axios.post(`${COMPANY_SERVICE}/api/v1/companies/addCompany`, data, authHeader());

export const updateCompany = (id, data) =>
    axios.put(`${COMPANY_SERVICE}/api/v1/companies/updateCompany/${id}`, data, authHeader());

export const deleteCompany = (id) =>
    axios.delete(`${COMPANY_SERVICE}/api/v1/companies/deleteCompany/${id}`, authHeader());

export const getCompany = (id) =>
    axios.get(`${COMPANY_SERVICE}/api/v1/companies/getcompany`, { ...authHeader(), params: { id } });




// ── Stocks ───────────────────────────────────────────────────────────────────
export const getAllStocks = () =>
    axios.get(`${STOCK_SERVICE}/api/v1/stocks`, authHeader());

export const getStockById = (stockId) =>
    axios.get(`${STOCK_SERVICE}/api/v1/stocks/${stockId}`, authHeader());

export const getStocksByCompany = (companyId) =>
    axios.get(`${STOCK_SERVICE}/api/v1/stocks/company/${companyId}`, authHeader());

export const getStocksByWarehouse = (warehouseId) =>
    axios.get(`${STOCK_SERVICE}/api/v1/stocks/warehouse/${warehouseId}`, authHeader());

export const getStocksByProduct = (productId) =>
    axios.get(`${STOCK_SERVICE}/api/v1/stocks/product/${productId}`, authHeader());

export const getStocksByCompanyAndWarehouse = (companyId, warehouseId) =>
    axios.get(`${STOCK_SERVICE}/api/v1/stocks/company/${companyId}/warehouse/${warehouseId}`, authHeader());

export const getLowStockByCompany = (companyId, threshold) =>
    axios.get(`${STOCK_SERVICE}/api/v1/stocks/company/${companyId}/low`, {
        ...authHeader(),
        params: { threshold },
    });

export const createStock = (data) =>
    axios.post(`${STOCK_SERVICE}/api/v1/stocks`, data, authHeader());

export const updateStock = (stockId, data) =>
    axios.put(`${STOCK_SERVICE}/api/v1/stocks/${stockId}`, data, authHeader());

export const deleteStock = (stockId) =>
    axios.delete(`${STOCK_SERVICE}/api/v1/stocks/${stockId}`, authHeader());





// ── Stock Transfers ──────────────────────────────────────────────────────────
export const getAllTransfers = () =>
    axios.get(`${STOCK_TRANSFER_SERVICE}/api/v1/transfers`, authHeader());

export const getTransferByNo = (transferNo) =>
    axios.get(`${STOCK_TRANSFER_SERVICE}/api/v1/transfers/${transferNo}`, authHeader());

export const getTransferDetails = (transferNo) =>
    axios.get(`${STOCK_TRANSFER_SERVICE}/api/v1/transfers/${transferNo}/details`, authHeader());

export const createTransfer = (data) =>
    axios.post(`${STOCK_TRANSFER_SERVICE}/api/v1/transfers`, data, authHeader());





// ── Assets ───────────────────────────────────────────────────────────────────
export const getAllAssets = () =>
    axios.get(`${ASSET_SERVICE}/api/v1/assets/all`, authHeader());

export const getAssetsByWarehouse = (warehouseId) =>
    axios.get(`${ASSET_SERVICE}/api/v1/assets/warehouse/${warehouseId}`, authHeader());

export const addAsset = (data) =>
    axios.post(`${ASSET_SERVICE}/api/v1/assets/add`, data, authHeader());

export const updateAsset = (id, data) =>
    axios.put(`${ASSET_SERVICE}/api/v1/assets/update/${id}`, data, authHeader());

export const deleteAsset = (id) =>
    axios.delete(`${ASSET_SERVICE}/api/v1/assets/delete/${id}`, authHeader());





// ── GRN (Goods Received Notes) ───────────────────────────────────────────────
// export const createGrn = (data) =>
//     axios.post(`${GRN_SERVICE}/api/v1/grns`, data, authHeader());

// export const getGrnByNumber = (grnNumber) =>
//     axios.get(`${GRN_SERVICE}/api/v1/grns/${grnNumber}`, authHeader());

// export const filterGrns = (params) =>
//     axios.get(`${GRN_SERVICE}/api/v1/grns`, { ...authHeader(), params });

// export const updateGrn = (grnNumber, data) =>
//     axios.put(`${GRN_SERVICE}/api/v1/grns/${grnNumber}`, data, authHeader());

// export const cancelGrn = (grnNumber, cancelledBy) =>
//     axios.put(`${GRN_SERVICE}/api/v1/grns/${grnNumber}/cancel`, null, {
//         ...authHeader(),
//         params: { cancelledBy },
//     });

export const getAllGrns = () =>
    axios.get(`${GRN_SERVICE}/api/v1/grns`, authHeader());

export const createGrn = (data) =>
    axios.post(`${GRN_SERVICE}/api/v1/grns`, data, authHeader());

export const getGrnByNumber = (grnNumber) =>
    axios.get(`${GRN_SERVICE}/api/v1/grns/${grnNumber}`, authHeader());

export const updateGrn = (grnNumber, data) =>
    axios.put(`${GRN_SERVICE}/api/v1/grns/${grnNumber}`, data, authHeader());

export const cancelGrn = (grnNumber, cancelledBy) =>
    axios.put(`${GRN_SERVICE}/api/v1/grns/${grnNumber}/cancel`, null, {
        ...authHeader(),
        params: { cancelledBy },
    });





// ── Warehouses ───────────────────────────────────────────────────────────────
export const getAllWarehouses = () =>
    axios.get(`${WAREHOUSE_SERVICE}/api/v1/warehouses`, authHeader());

export const getWarehouseById = (id) =>
    axios.get(`${WAREHOUSE_SERVICE}/api/v1/warehouses/${id}`, authHeader());

export const getWarehousesByCompany = (companyId) =>
    axios.get(`${WAREHOUSE_SERVICE}/api/v1/warehouses/company/${companyId}`, authHeader());

export const createWarehouse = (data) =>
    axios.post(`${WAREHOUSE_SERVICE}/api/v1/warehouses`, data, authHeader());

export const updateWarehouse = (id, data) =>
    axios.put(`${WAREHOUSE_SERVICE}/api/v1/warehouses/${id}`, data, authHeader());

export const deleteWarehouse = (id) =>
    axios.delete(`${WAREHOUSE_SERVICE}/api/v1/warehouses/${id}`, authHeader());





// ── Suppliers ────────────────────────────────────────────────────────────────
export const getAllSuppliers = () =>
    axios.get(`${SUPPLIER_SERVICE}/api/v1/suppliers`, authHeader());

export const getSupplierById = (id) =>
    axios.get(`${SUPPLIER_SERVICE}/api/v1/suppliers/${id}`, authHeader());

export const createSupplier = (data) =>
    axios.post(`${SUPPLIER_SERVICE}/api/v1/suppliers`, data, authHeader());

export const updateSupplier = (id, data) =>
    axios.put(`${SUPPLIER_SERVICE}/api/v1/suppliers/${id}`, data, authHeader());

export const deleteSupplier = (id) =>
    axios.delete(`${SUPPLIER_SERVICE}/api/v1/suppliers/${id}`, authHeader());

export const getProductsBySupplier = (id) =>
    axios.get(`${SUPPLIER_SERVICE}/api/v1/suppliers/${id}/products`, authHeader());

export const getPurchaseOrdersBySupplier = (id) =>
    axios.get(`${SUPPLIER_SERVICE}/api/v1/suppliers/${id}/purchase-orders`, authHeader());

export const approveSupplierGrn = (id, grnId) =>
    axios.put(`${SUPPLIER_SERVICE}/api/v1/suppliers/${id}/grn/${grnId}/approve`, null, authHeader());

export const getSupplierCompanies = () =>
    axios.get(`${SUPPLIER_SERVICE}/api/v1/suppliers/companies`, authHeader());


// ── Maintenance ──────────────────────────────────────────────────────────────
export const getAllMaintenances = () =>
    axios.get(`${MAINTENANCE_SERVICE}/api/v1/maintenances`, authHeader());

export const getMaintenanceById = (id) =>
    axios.get(`${MAINTENANCE_SERVICE}/api/v1/maintenances/${id}`, authHeader());

export const addMaintenance = (data) =>
    axios.post(`${MAINTENANCE_SERVICE}/api/v1/maintenances`, data, authHeader());

export const updateMaintenance = (id, data) =>
    axios.put(`${MAINTENANCE_SERVICE}/api/v1/maintenances/${id}`, data, authHeader());

export const deleteMaintenance = (id) =>
    axios.delete(`${MAINTENANCE_SERVICE}/api/v1/maintenances/${id}`, authHeader());