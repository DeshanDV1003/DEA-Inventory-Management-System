import axios from "axios";

const USER_SERVICE = import.meta.env.VITE_USER_SERVICE || "http://localhost:8071";
const PRODUCT_SERVICE = import.meta.env.VITE_PRODUCT_SERVICE || "http://localhost:8081";

const PO_SERVICE = import.meta.env.VITE_PO_SERVICE || "http://localhost:8061";

// ── Auth ─────────────────────────────────────────────────────────────────────
export const loginUser = (credentials) =>
  axios.post(`${USER_SERVICE}/api/auth/login`, credentials);

export const registerUser = (data) =>
  axios.post(`${USER_SERVICE}/api/auth/register`, data);

// ── Products ─────────────────────────────────────────────────────────────────
const authHeader = () => ({
  headers: { Authorization: `Bearer ${localStorage.getItem("token")}` },
});

export const getAllProducts = () =>
  axios.get(`${PRODUCT_SERVICE}/api/products`, authHeader());

export const addProduct = (product) =>
  axios.post(`${PRODUCT_SERVICE}/api/products/addProduct`, product, authHeader());

export const deleteProduct = (id) =>
  axios.delete(`${PRODUCT_SERVICE}/api/products/deleteProduct`, {
    ...authHeader(),
    data: id,
  });


/* ─────────────────────────────────────────────────────────────
   PURCHASE ORDER APIs
───────────────────────────────────────────────────────────── */

const PO_API_URL = `${PO_SERVICE}/api/v1/purchase-orders`;

/* ─────────────────────────────────────────────────────────────
   PURCHASE ORDER APIs
───────────────────────────────────────────────────────────── */


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
