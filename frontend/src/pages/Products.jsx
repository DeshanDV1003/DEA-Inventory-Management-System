import React, { useState, useEffect, useCallback, useMemo } from "react";
import { useNavigate } from "react-router-dom";
import { 
    getAllProducts, 
    addProduct, 
    deleteProduct, 
    getAllCompanies, 
    getAllWarehouses, 
    getAllSuppliers 
} from "../services/api";
import BarcodeScanner from "../components/BarcodeScanner";
import Sidebar from "../components/Sidebar";
import "./Products.css";

const emptyForm = {
    name: "",
    price: "",
    sku: "",
    warehouseId: "",
    companyId: "",
    supplierId: "",
    imgPath: "",
    status: "Active",
};

const Products = () => {
    const navigate = useNavigate();
    const username = localStorage.getItem("username") || "User";

    // Data States
    const [products, setProducts] = useState([]);
    const [masterData, setMasterData] = useState({
        companies: [],
        warehouses: [],
        suppliers: []
    });

    // UI States
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState("");
    const [showModal, setShowModal] = useState(false);
    const [saving, setSaving] = useState(false);
    const [deleteId, setDeleteId] = useState(null);
    const [showScanner, setShowScanner] = useState(false);
    const [form, setForm] = useState(emptyForm);

    const fetchData = async () => {
        try {
            setLoading(true);
            // Fetch Products + All Reference Data
            const [prodRes, compRes, whRes, supRes] = await Promise.all([
                getAllProducts(),
                getAllCompanies(),
                getAllWarehouses(),
                getAllSuppliers()
            ]);

            setProducts(prodRes.data);
            setMasterData({
                companies: compRes.data,
                warehouses: whRes.data,
                suppliers: supRes.data
            });
        } catch (err) {
            setError("Failed to load inventory data.");
            console.error(err);
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchData();
    }, []);

    const handleBarcodeScan = useCallback((barcode) => {
        setForm((prev) => ({ ...prev, sku: barcode }));
        setShowScanner(false);
    }, []);

    const handleAdd = async (e) => {
        e.preventDefault();
        setSaving(true);
        try {
            await addProduct({
                ...form,
                sku: parseInt(form.sku),
                warehouseId: parseInt(form.warehouseId),
                companyId: parseInt(form.companyId),
                supplierId: parseInt(form.supplierId),
                createdBy: username,
                modifiedBy: username,
            });
            setShowModal(false);
            setForm(emptyForm);
            fetchData();
        } catch {
            setError("Failed to add product. Check if SKU is unique.");
        } finally {
            setSaving(false);
        }
    };

    const handleDelete = async () => {
        try {
            await deleteProduct(deleteId);
            setDeleteId(null);
            fetchData();
        } catch {
            setError("Failed to delete product.");
        }
    };

    // Helper to find names for the table display
    const getName = (list, id) => list.find(item => item.id === parseInt(id))?.name || "—";

    return (
        <div className="products-root">
            <Sidebar />

            <main className="main-content">
                <header className="page-header">
                    <div>
                        <p className="page-label">INVENTORY</p>
                        <h1 className="page-title">Product Catalog</h1>
                    </div>
                    <button className="add-btn" onClick={() => setShowModal(true)}>
                        + Add Product
                    </button>
                </header>

                {error && <div className="alert-error">⚠ {error} <button onClick={() => setError("")}>✕</button></div>}

                {loading ? (
                    <div className="loading-state"><div className="loader" /><p>Syncing product data...</p></div>
                ) : products.length === 0 ? (
                    <div className="empty-state"><span className="empty-icon">📦</span><p>No products found.</p></div>
                ) : (
                    <div className="product-table-wrap">
                        <table className="product-table">
                            <thead>
                                <tr>
                                    <th>#</th>
                                    <th>Product Name</th>
                                    <th>SKU</th>
                                    <th>Price</th>
                                    <th>Warehouse</th>
                                    <th>Supplier</th>
                                    <th>Status</th>
                                    <th className="actions-col">Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                {products.map((p, i) => (
                                    <tr key={p.id}>
                                        <td className="row-num">{String(i + 1).padStart(2, "0")}</td>
                                        <td className="product-name">{p.name}</td>
                                        <td className="sku">{p.sku}</td>
                                        <td className="price">${Number(p.price).toFixed(2)}</td>
                                        <td>{getName(masterData.warehouses, p.warehouseId)}</td>
                                        <td>{getName(masterData.suppliers, p.supplierId)}</td>
                                        <td>
                                            <span className={`badge ${p.status === "Active" ? "badge-active" : "badge-inactive"}`}>
                                                {p.status}
                                            </span>
                                        </td>
                                        <td className="actions">
                                            <button className="icon-btn danger" onClick={() => setDeleteId(p.id)}>✕</button>
                                        </td>
                                    </tr>
                                ))}
                            </tbody>
                        </table>
                    </div>
                )}
            </main>

            {/* Add Product Modal */}
            {showModal && (
                <div className="modal-overlay">
                    <div className="modal company-modal">
                        <div className="modal-header">
                            <h2>New Product</h2>
                            <button className="modal-close" onClick={() => setShowModal(false)}>✕</button>
                        </div>
                        <form onSubmit={handleAdd} className="modal-form">
                            <div className="company-form-grid">
                                <div className="company-field company-field-full">
                                    <label>PRODUCT NAME</label>
                                    <input required placeholder="Item name" value={form.name} onChange={e => setForm({...form, name: e.target.value})} />
                                </div>

                                <div className="company-field">
                                    <label>SKU (BARCODE)</label>
                                    <div style={{ display: 'flex', gap: '5px' }}>
                                        <input type="number" required value={form.sku} onChange={e => setForm({...form, sku: e.target.value})} />
                                        <button type="button" className="scan-btn" onClick={() => setShowScanner(true)}>Scan</button>
                                    </div>
                                </div>

                                <div className="company-field">
                                    <label>PRICE (USD)</label>
                                    <input type="number" step="0.01" required value={form.price} onChange={e => setForm({...form, price: e.target.value})} />
                                </div>

                                <div className="company-field">
                                    <label>COMPANY</label>
                                    <select required value={form.companyId} onChange={e => setForm({...form, companyId: e.target.value})}>
                                        <option value="">Select Company</option>
                                        {masterData.companies.map(c => <option key={c.id} value={c.id}>{c.name}</option>)}
                                    </select>
                                </div>

                                <div className="company-field">
                                    <label>WAREHOUSE</label>
                                    <select required value={form.warehouseId} onChange={e => setForm({...form, warehouseId: e.target.value})}>
                                        <option value="">Select Location</option>
                                        {masterData.warehouses.map(w => <option key={w.id} value={w.id}>{w.name}</option>)}
                                    </select>
                                </div>

                                <div className="company-field">
                                    <label>SUPPLIER</label>
                                    <select required value={form.supplierId} onChange={e => setForm({...form, supplierId: e.target.value})}>
                                        <option value="">Select Supplier</option>
                                        {masterData.suppliers.map(s => <option key={s.id} value={s.id}>{s.name}</option>)}
                                    </select>
                                </div>

                                <div className="company-field">
                                    <label>STATUS</label>
                                    <select value={form.status} onChange={e => setForm({...form, status: e.target.value})}>
                                        <option value="Active">Active</option>
                                        <option value="Inactive">Inactive</option>
                                    </select>
                                </div>
                            </div>

                            <div className="modal-actions">
                                <button type="button" className="company-btn-light" onClick={() => setShowModal(false)}>Cancel</button>
                                <button type="submit" className="company-submit-btn" disabled={saving}>
                                    {saving ? "Saving..." : "Add Product"}
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
            )}

            {/* Barcode Scanner Overlay */}
            {showScanner && (
                <BarcodeScanner onScan={handleBarcodeScan} onClose={() => setShowScanner(false)} />
            )}

            {/* Delete Modal */}
            {deleteId && (
                <div className="modal-overlay" onClick={() => setDeleteId(null)}>
                    <div className="modal confirm-modal" onClick={(e) => e.stopPropagation()}>
                        <h2>Delete Product?</h2>
                        <p>This will remove the item from the catalog.</p>
                        <div className="modal-actions">
                            <button className="cancel-btn" onClick={() => setDeleteId(null)}>Cancel</button>
                            <button className="delete-confirm-btn" onClick={handleDelete}>Delete</button>
                        </div>
                    </div>
                </div>
            )}
        </div>
    );
};

export default Products;