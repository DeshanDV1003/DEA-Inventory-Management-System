import React, { useEffect, useMemo, useState, useCallback } from "react";
import { useNavigate } from "react-router-dom";
import { 
    getAllPurchaseOrders, 
    deletePurchaseOrder, 
    updatePurchaseOrderStatus, // Used for inline update
    createPurchaseOrder,
    updatePurchaseOrder,
    getAllCompanies,
    getAllWarehouses,
    getAllSuppliers,
    getAllProducts
} from "../../services/api";
import Sidebar from "../../components/Sidebar.jsx";
import "../Products.css";
import "../Company.css";
import "./PurchaseOrder.css";

export default function PurchaseOrderList() {
    const navigate = useNavigate();

    // --- DATA STATES ---
    const [orders, setOrders] = useState([]);
    const [masterData, setMasterData] = useState({
        companies: [], warehouses: [], suppliers: [], products: []
    });

    // --- UI STATES ---
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState("");
    const [query, setQuery] = useState("");
    const [expandedId, setExpandedId] = useState(null);
    const [deleteId, setDeleteId] = useState(null);

    // --- MODAL & FORM STATES ---
    const [showModal, setShowModal] = useState(false);
    const [saving, setSaving] = useState(false);
    const [editingId, setEditingId] = useState(null);
    const [formData, setFormData] = useState({
        poNumber: "", companyId: "", supplierId: "", warehouseId: "",
        items: [{ productId: "", quantity: 1 }]
    });

    const [filteredWarehouses, setFilteredWarehouses] = useState([]);
    const [filteredSuppliers, setFilteredSuppliers] = useState([]);

    const fetchData = useCallback(async () => {
        try {
            setLoading(true);
            const [po, c, w, s, p] = await Promise.all([
                getAllPurchaseOrders(),
                getAllCompanies(),
                getAllWarehouses(),
                getAllSuppliers(),
                getAllProducts()
            ]);

            setOrders(po.data || []);
            setMasterData({
                companies: c.data || [],
                warehouses: w.data || [],
                suppliers: s.data || [],
                products: p.data || []
            });
        } catch (err) {
            setError("Failed to sync data from services.");
        } finally {
            setLoading(false);
        }
    }, []);

    useEffect(() => { fetchData(); }, [fetchData]);

    // --- LOGIC: INLINE STATUS CHANGE ---
    const handleStatusUpdate = async (id, newStatus) => {
        try {
            // This calls your PATCH http://localhost:8062/api/v1/purchase-orders/{id}/status
            await updatePurchaseOrderStatus(id, newStatus);
            fetchData(); // Refresh the table to show the new status
        } catch (err) {
            setError("Failed to update status. Check backend connection.");
        }
    };

    // --- LOGIC: DYNAMIC FILTERING ---
    const handleCompanyChange = (companyId) => {
        const id = parseInt(companyId);
        setFormData(prev => ({ ...prev, companyId: id, warehouseId: "", supplierId: "" }));
        setFilteredWarehouses(masterData.warehouses.filter(w => String(w.companyId) === String(id)));
        setFilteredSuppliers(masterData.suppliers.filter(s => String(s.companyId) === String(id)));
    };

    const confirmDelete = async () => {
        try {
            await deletePurchaseOrder(deleteId);
            setDeleteId(null);
            fetchData();
        } catch { setError("Failed to delete order."); }
    };

    const onFormSubmit = async (e) => {
        e.preventDefault();
        setSaving(true);
        try {
            if (editingId) await updatePurchaseOrder(editingId, formData);
            else await createPurchaseOrder(formData);
            setShowModal(false);
            fetchData();
        } catch { setError("Error saving order. PO Number must be unique."); }
        finally { setSaving(false); }
    };

    // --- LOOKUP HELPERS (With String Casting Fix) ---
    const getName = (list, id) => {
        if (!id) return "—";
        const found = list.find(item => String(item.id) === String(id));
        return found ? found.name : `ID: ${id}`;
    };

    const filteredOrders = useMemo(() => {
        const q = query.trim().toLowerCase();
        if (!q) return orders;
        return orders.filter(o => o.poNumber.toString().includes(q));
    }, [orders, query]);

    return (
        <div className="products-root">
            <Sidebar />
            <main className="main-content">
                <header className="page-header">
                    <div>
                        <p className="page-label">PROCUREMENT</p>
                        <h1 className="page-title">Purchase Orders</h1>
                    </div>
                    <button className="add-btn" onClick={() => {
                        setEditingId(null);
                        setFormData({ poNumber: "", companyId: "", supplierId: "", warehouseId: "", items: [{ productId: "", quantity: 1 }] });
                        setShowModal(true);
                    }}>+ New Order</button>
                </header>

                {error && <div className="alert-error">! {error} <button onClick={() => setError("")}>✕</button></div>}

                <div className="company-search-row">
                    <div className="company-search-box">
                        <span className="company-search-ic">⌕</span>
                        <input value={query} onChange={e => setQuery(e.target.value)} placeholder="Search PO Number..." />
                    </div>
                </div>

                <div className="company-table-wrap">
                    <table className="company-table">
                        <thead>
                            <tr>
                                <th>PO #</th>
                                <th>Company</th>
                                <th>Supplier</th>
                                <th>Warehouse</th>
                                <th>Status</th>
                                <th className="actions-col">Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            {filteredOrders.map((o) => (
                                <React.Fragment key={o.id}>
                                    <tr onClick={() => setExpandedId(expandedId === o.id ? null : o.id)} style={{ cursor: 'pointer' }}>
                                        <td className="cell-name">#{o.poNumber}</td>
                                        <td>{getName(masterData.companies, o.companyId)}</td>
                                        <td>{getName(masterData.suppliers, o.supplierId)}</td>
                                        <td>{getName(masterData.warehouses, o.warehouseId)}</td>
                                        <td>
                                            {/* Status Dropdown */}
                                            <select 
                                                className="status-select" 
                                                value={o.status} 
                                                onClick={e => e.stopPropagation()} // Prevent expansion when clicking select
                                                onChange={e => handleStatusUpdate(o.id, e.target.value)}
                                            >
                                                <option value="CREATED">CREATED</option>
                                                <option value="APPROVED">APPROVED</option>
                                                <option value="SHIPPED">SHIPPED</option>
                                                <option value="CANCELLED">CANCELLED</option>
                                            </select>
                                        </td>
                                        <td className="actions">
                                            <button className="icon-btn" onClick={(e) => { e.stopPropagation(); 
                                                setEditingId(o.id);
                                                setFormData(o);
                                                handleCompanyChange(o.companyId);
                                                setShowModal(true);
                                            }}>✎</button>
                                            <button className="icon-btn danger" onClick={(e) => { e.stopPropagation(); setDeleteId(o.id); }}>✕</button>
                                        </td>
                                    </tr>
                                    {expandedId === o.id && (
                                        <tr>
                                            <td colSpan="6" style={{ background: '#111115', padding: '1.5rem' }}>
                                                <div className="page-label" style={{marginBottom: '10px'}}>Items in this order</div>
                                                <table style={{ width: '100%', borderCollapse: 'collapse' }}>
                                                    <thead>
                                                        <tr style={{ textAlign: 'left', borderBottom: '1px solid #27272a' }}>
                                                            <th style={{ padding: '8px', color: '#71717a', fontSize: '0.7rem' }}>PRODUCT</th>
                                                            <th style={{ padding: '8px', color: '#71717a', fontSize: '0.7rem' }}>QTY</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        {o.items?.map((item, idx) => (
                                                            <tr key={idx} style={{ borderBottom: '1px solid #1e1e24' }}>
                                                                <td style={{ padding: '8px' }}>{getName(masterData.products, item.productId)}</td>
                                                                <td style={{ padding: '8px' }}>{item.quantity} Units</td>
                                                            </tr>
                                                        ))}
                                                    </tbody>
                                                </table>
                                            </td>
                                        </tr>
                                    )}
                                </React.Fragment>
                            ))}
                        </tbody>
                    </table>
                </div>
            </main>

            {/* --- CREATE / EDIT MODAL --- */}
            {showModal && (
                <div className="modal-overlay">
                    <div className="modal company-modal" style={{maxWidth: '750px'}}>
                        <div className="modal-header">
                            <h2>{editingId ? `Edit Order #${formData.poNumber}` : "New Purchase Order"}</h2>
                            <button className="modal-close" onClick={() => setShowModal(false)}>✕</button>
                        </div>
                        <form className="modal-form" onSubmit={onFormSubmit}>
                            <div className="company-form-grid">
                                <div className="company-field">
                                    <label>PO NUMBER</label>
                                    <input type="number" required value={formData.poNumber} onChange={e => setFormData({...formData, poNumber: e.target.value})} />
                                </div>
                                <div className="company-field">
                                    <label>COMPANY</label>
                                    <select required value={formData.companyId} onChange={e => handleCompanyChange(e.target.value)}>
                                        <option value="">Select Company</option>
                                        {masterData.companies.map(c => <option key={c.id} value={c.id}>{c.name}</option>)}
                                    </select>
                                </div>
                                <div className="company-field">
                                    <label>SUPPLIER (Filtered)</label>
                                    <select required value={formData.supplierId} onChange={e => setFormData({...formData, supplierId: e.target.value})} disabled={!formData.companyId}>
                                        <option value="">{formData.companyId ? "Select Supplier" : "Select Company First"}</option>
                                        {filteredSuppliers.map(s => <option key={s.id} value={s.id}>{s.name}</option>)}
                                    </select>
                                </div>
                                <div className="company-field">
                                    <label>WAREHOUSE (Filtered)</label>
                                    <select required value={formData.warehouseId} onChange={e => setFormData({...formData, warehouseId: e.target.value})} disabled={!formData.companyId}>
                                        <option value="">{formData.companyId ? "Select Warehouse" : "Select Company First"}</option>
                                        {filteredWarehouses.map(w => <option key={w.id} value={w.id}>{w.name}</option>)}
                                    </select>
                                </div>
                            </div>

                            <div className="page-label" style={{marginTop: '20px', marginBottom: '10px'}}>Add Products</div>
                            {formData.items.map((item, index) => (
                                <div key={index} style={{display: 'flex', gap: '10px', marginBottom: '10px'}}>
                                    <select style={{flex: 3}} required value={item.productId} onChange={e => {
                                        const newItems = [...formData.items];
                                        newItems[index].productId = parseInt(e.target.value);
                                        setFormData({...formData, items: newItems});
                                    }}>
                                        <option value="">Select Product</option>
                                        {masterData.products.map(p => <option key={p.id} value={p.id}>{p.name}</option>)}
                                    </select>
                                    <input style={{flex: 1}} type="number" min="1" value={item.quantity} onChange={e => {
                                        const newItems = [...formData.items];
                                        newItems[index].quantity = parseInt(e.target.value);
                                        setFormData({...formData, items: newItems});
                                    }} />
                                    {formData.items.length > 1 && <button type="button" className="icon-btn danger" onClick={() => setFormData({...formData, items: formData.items.filter((_, i) => i !== index)})}>✕</button>}
                                </div>
                            ))}
                            
                            {/* Updated Visibility Button */}
                            <button 
                                type="button" 
                                className="btn-add" 
                                style={{
                                    marginTop: '20px', 
                                    border: '1px dashed #a78bfa', 
                                    color: '#a78bfa', 
                                    background: 'rgba(167, 139, 250, 0.1)',
                                    width: '100%',
                                    padding: '12px'
                                }} 
                                onClick={() => setFormData({...formData, items: [...formData.items, { productId: "", quantity: 1 }]})}
                            >
                                + Add Another Product Item
                            </button>

                            <div className="modal-actions" style={{marginTop: '20px'}}>
                                <button type="button" className="company-btn-light" onClick={() => setShowModal(false)}>Cancel</button>
                                <button type="submit" className="company-submit-btn" disabled={saving}>{saving ? "Saving..." : "Save Order"}</button>
                            </div>
                        </form>
                    </div>
                </div>
            )}
        </div>
    );
}