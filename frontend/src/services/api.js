import axios from "axios";

const USER_SERVICE = import.meta.env.VITE_USER_SERVICE || "http://localhost:8072";
const PRODUCT_SERVICE = import.meta.env.VITE_PRODUCT_SERVICE || "http://localhost:8082";

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
