import axios from "axios";

const USER_SERVICE = import.meta.env.VITE_USER_SERVICE || "http://localhost:8071";
const PRODUCT_SERVICE = import.meta.env.VITE_PRODUCT_SERVICE || "http://localhost:8081";
const COMPANY_SERVICE = import.meta.env.VITE_COMPANY_SERVICE || "http://localhost:8071";

const authHeader = () => ({
  headers: { Authorization: `Bearer ${localStorage.getItem("token")}` },
});

export const loginUser = (credentials) =>
  axios.post(`${USER_SERVICE}/api/auth/login`, credentials);

export const registerUser = (data) =>
  axios.post(`${USER_SERVICE}/api/auth/register`, data);

export const getAllProducts = () =>
  axios.get(`${PRODUCT_SERVICE}/api/products`, authHeader());

export const addProduct = (product) =>
  axios.post(`${PRODUCT_SERVICE}/api/products/addProduct`, product, authHeader());

export const deleteProduct = (id) =>
  axios.delete(`${PRODUCT_SERVICE}/api/products/deleteProduct`, {
    ...authHeader(),
    data: id,
  });

const COMPANY_BASE = `${COMPANY_SERVICE}/api/companies`;

export const getAllCompanies = () =>
  axios.get(COMPANY_BASE, authHeader());

export const addCompany = (data) =>
  axios.post(`${COMPANY_BASE}/addCompany`, data, authHeader());

export const updateCompany = (id, data) =>
  axios.put(`${COMPANY_BASE}/updateCompany/${id}`, data, authHeader());

export const deleteCompany = (id) =>
  axios.delete(`${COMPANY_BASE}/deleteCompany/${id}`, authHeader());
