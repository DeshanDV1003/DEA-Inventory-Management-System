import React, { useEffect, useState, useCallback, useMemo } from "react";
import Sidebar from "../components/Sidebar.jsx";
import {
  getAllStocks,
  createStock,
  updateStock,
  deleteStock,
  getAllCompanies,
  getAllWarehouses,
  getAllProducts
} from "../services/api";

import "./Products.css";
import "./Company.css";

const emptyForm = {
  companyId: "",
  warehouseId: "",
  productId: "",
  quantity: 0,
  maxStockLevel: 0,
  minStockLevel: 0,
  reOrderLevel: 0,
};

export default function Stock() {
  // --- Data States ---
  const [stocks, setStocks] = useState([]);
  const [masterData, setMasterData] = useState({
    companies: [],
    warehouses: [],
    products: []
  });

  // --- UI States ---
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const [query, setQuery] = useState("");
  const [showModal, setShowModal] = useState(false);
  const [editingId, setEditingId] = useState(null);
  const [form, setForm] = useState(emptyForm);
  const [deleteId, setDeleteId] = useState(null);
  const [saving, setSaving] = useState(false);

  // Filtered dropdowns for the form
  const [filteredWarehouses, setFilteredWarehouses] = useState([]);

  const fetchData = useCallback(async () => {
    try {
      setLoading(true);
      const [stockRes, compRes, whRes, prodRes] = await Promise.all([
        getAllStocks(),
        getAllCompanies(),
        getAllWarehouses(),
        getAllProducts()
      ]);

      // Note: check if your API returns res.data or res.data.data
      setStocks(stockRes.data.data || stockRes.data || []);
      setMasterData({
        companies: compRes.data || [],
        warehouses: whRes.data || [],
        products: prodRes.data || []
      });
    } catch (err) {
      setError("Failed to sync stock data.");
    } finally {
      setLoading(false);
    }
  }, []);

  useEffect(() => { fetchData(); }, [fetchData]);

  // --- Cascading Logic ---
  const handleCompanyChange = (id) => {
    const compId = String(id);
    setForm({ ...form, companyId: compId, warehouseId: "" });
    setFilteredWarehouses(masterData.warehouses.filter(w => String(w.companyId) === compId));
  };

  // --- Handlers ---
  const openEdit = (s) => {
    setEditingId(s.stockId);
    setForm(s);
    setFilteredWarehouses(masterData.warehouses.filter(w => String(w.companyId) === String(s.companyId)));
    setShowModal(true);
  };

  const onFormSubmit = async (e) => {
    e.preventDefault();
    setSaving(true);
    try {
      if (editingId) await updateStock(editingId, form);
      else await createStock(form);
      setShowModal(false);
      fetchData();
    } catch {
      setError("Error saving stock record.");
    } finally {
      setSaving(false);
    }
  };

  const confirmDelete = async () => {
    try {
      await deleteStock(deleteId);
      setDeleteId(null);
      fetchData();
    } catch {
      setError("Delete failed.");
    }
  };

  // Helper for Table Names
  const getName = (list, id) => list.find(i => String(i.id) === String(id))?.name || `ID: ${id}`;

  const filteredStocks = useMemo(() => {
    const q = query.toLowerCase();
    return stocks.filter(s => getName(masterData.products, s.productId).toLowerCase().includes(q));
  }, [stocks, query, masterData.products]);

  return (
    <div className="products-root">
      <Sidebar />
      <main className="main-content">
        <header className="page-header">
          <div>
            <p className="page-label">INVENTORY</p>
            <h1 className="page-title">Stock Levels</h1>
          </div>
          <button className="add-btn" onClick={() => { setEditingId(null); setForm(emptyForm); setShowModal(true); }}>
            + Add Stock
          </button>
        </header>

        {error && <div className="alert-error">! {error} <button onClick={() => setError("")}>✕</button></div>}

        <div className="company-search-row">
            <div className="company-search-box">
                <span className="company-search-ic">⌕</span>
                <input value={query} onChange={e => setQuery(e.target.value)} placeholder="Search by product name..." />
            </div>
        </div>

        <div className="company-table-wrap">
          <table className="company-table">
            <thead>
              <tr>
                <th>Product</th>
                <th>Warehouse</th>
                <th>Company</th>
                <th>Qty</th>
                <th>Min/Max</th>
                <th>Status</th>
                <th className="actions-col">Actions</th>
              </tr>
            </thead>
            <tbody>
              {filteredStocks.map((s) => (
                <tr key={s.stockId}>
                  <td className="cell-name">{getName(masterData.products, s.productId)}</td>
                  <td>{getName(masterData.warehouses, s.warehouseId)}</td>
                  <td>{getName(masterData.companies, s.companyId)}</td>
                  <td style={{fontWeight: '700', color: s.quantity <= s.reOrderLevel ? '#f87171' : '#fff'}}>
                    {s.quantity}
                  </td>
                  <td style={{fontSize: '0.7rem', color: '#71717a'}}>
                    {s.minStockLevel} / {s.maxStockLevel}
                  </td>
                  <td>
                    {s.quantity <= s.reOrderLevel ? 
                        <span className="badge badge-inactive">LOW STOCK</span> : 
                        <span className="badge badge-active">IN STOCK</span>
                    }
                  </td>
                  <td className="actions">
                    <button className="icon-btn" onClick={() => openEdit(s)}>✎</button>
                    <button className="icon-btn danger" onClick={() => setDeleteId(s.stockId)}>✕</button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </main>

      {/* --- Add/Edit Modal --- */}
      {showModal && (
        <div className="modal-overlay">
          <div className="modal company-modal" style={{maxWidth: '650px'}}>
            <div className="modal-header">
              <h2>{editingId ? "Adjust Stock" : "Manual Stock Entry"}</h2>
              <button className="modal-close" onClick={() => setShowModal(false)}>✕</button>
            </div>
            <form className="modal-form" onSubmit={onFormSubmit}>
              <div className="company-form-grid">
                <div className="company-field">
                  <label>COMPANY</label>
                  <select required value={form.companyId} onChange={e => handleCompanyChange(e.target.value)}>
                    <option value="">Select Company</option>
                    {masterData.companies.map(c => <option key={c.id} value={c.id}>{c.name}</option>)}
                  </select>
                </div>

                <div className="company-field">
                  <label>WAREHOUSE</label>
                  <select required value={form.warehouseId} onChange={e => setForm({...form, warehouseId: e.target.value})} disabled={!form.companyId}>
                    <option value="">Select Warehouse</option>
                    {filteredWarehouses.map(w => <option key={w.id} value={w.id}>{w.name}</option>)}
                  </select>
                </div>

                <div className="company-field company-field-full">
                  <label>PRODUCT</label>
                  <select required value={form.productId} onChange={e => setForm({...form, productId: e.target.value})}>
                    <option value="">Select Product</option>
                    {masterData.products.map(p => <option key={p.id} value={p.id}>{p.name}</option>)}
                  </select>
                </div>

                <div className="company-field">
                  <label>CURRENT QUANTITY</label>
                  <input type="number" required value={form.quantity} onChange={e => setForm({...form, quantity: e.target.value})} />
                </div>

                <div className="company-field">
                  <label>REORDER LEVEL</label>
                  <input type="number" value={form.reOrderLevel} onChange={e => setForm({...form, reOrderLevel: e.target.value})} />
                </div>

                <div className="company-field">
                  <label>MIN LEVEL</label>
                  <input type="number" value={form.minStockLevel} onChange={e => setForm({...form, minStockLevel: e.target.value})} />
                </div>

                <div className="company-field">
                  <label>MAX LEVEL</label>
                  <input type="number" value={form.maxStockLevel} onChange={e => setForm({...form, maxStockLevel: e.target.value})} />
                </div>
              </div>

              <div className="modal-actions">
                <button type="button" className="company-btn-light" onClick={() => setShowModal(false)}>Cancel</button>
                <button type="submit" className="company-submit-btn" disabled={saving}>{saving ? "Saving..." : "Save Stock"}</button>
              </div>
            </form>
          </div>
        </div>
      )}

      {/* Delete Confirmation */}
      {deleteId && (
        <div className="modal-overlay" onClick={() => setDeleteId(null)}>
          <div className="modal confirm-modal">
            <h2>Remove Stock Entry?</h2>
            <p>This will delete the tracking record for this item.</p>
            <div className="modal-actions">
              <button className="cancel-btn" onClick={() => setDeleteId(null)}>Cancel</button>
              <button className="delete-confirm-btn" onClick={confirmDelete}>Delete</button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
}