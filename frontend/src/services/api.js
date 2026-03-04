import axios from "axios";

const USER_SERVICE = import.meta.env.VITE_USER_SERVICE || "http://localhost:8072";
const PRODUCT_SERVICE = import.meta.env.VITE_PRODUCT_SERVICE || "http://localhost:8082";

const PO_SERVICE = import.meta.env.VITE_PO_SERVICE || "http://localhost:8062";
const COMPANY_SERVICE = import.meta.env.VITE_COMPANY_SERVICE || "http://localhost:8022";

// ── Auth ─────────────────────────────────────────────────────────────────────
export const loginUser = (credentials) =>
  axios.post(`${USER_SERVICE}/api/v1/auth/login`, credentials);

export const registerUser = (data) =>
  axios.post(`${USER_SERVICE}/api/v1/auth/register`, data);

// ── Products ─────────────────────────────────────────────────────────────────
const authHeader = () => ({
  headers: { Authorization: `Bearer ${localStorage.getItem("token")}` },
});

export const getAllProducts = () =>
  axios.get(`${PRODUCT_SERVICE}/api/v1/products`, authHeader());

export const addProduct = (product) =>
  axios.post(`${PRODUCT_SERVICE}/api/v1/products/addProduct`, product, authHeader());

export const deleteProduct = (id) =>
  axios.delete(`${PRODUCT_SERVICE}/api/v1/products/deleteProduct`, {
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

// ── Companies ─────────────────────────────────────────────────────────────────
export const getAllCompanies = () =>
    axios.get(`${COMPANY_SERVICE}/api/v1/companies`, authHeader());

export const addCompany = (data) =>
    axios.post(`${COMPANY_SERVICE}/api/v1/companies/addCompany`, data, authHeader());

export const updateCompany = (id, data) =>
    axios.put(`${COMPANY_SERVICE}/api/v1/companies/updateCompany/${id}`, data, authHeader());

export const deleteCompany = (id) =>
    axios.delete(`${COMPANY_SERVICE}/api/v1/companies/deleteCompany/${id}`, authHeader());
