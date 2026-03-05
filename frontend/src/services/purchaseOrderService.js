import axios from "axios"; // 1. Import axios directly

// const PO_API_URL = 'http://localhost:8081/api/v1/purchase-orders';

const PO_API_URL = 'http://localhost:8062/api/v1/purchase-orders';

/**
 * Helper: authHeader
 * Since api.js does not export its authHeader function,
 * we define it here to ensure our requests include the JWT token.
 */
const authHeader = () => ({
    headers: { Authorization: `Bearer ${localStorage.getItem("token")}` },
});

export const purchaseOrderService = {
    // 2. Use axios directly with the authHeader
    getAll: () => axios.get(PO_API_URL, authHeader()),

    getById: (id) => axios.get(`${PO_API_URL}/${id}`, authHeader()),

    create: (data) => axios.post(PO_API_URL, data, authHeader()),

    update: (id, data) => axios.put(`${PO_API_URL}/${id}`, data, authHeader()),

    // Note: status is sent in the body as per your Backend @RequestBody Map
    updateStatus: (id, status) =>
        axios.patch(`${PO_API_URL}/${id}/status`, { status }, authHeader()),

    delete: (id) => axios.delete(`${PO_API_URL}/${id}`, authHeader()),
};