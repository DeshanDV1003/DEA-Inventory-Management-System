import React, { useEffect, useState, useCallback, useMemo } from "react";
import Sidebar from "../components/Sidebar.jsx";
import {
  getAllGrns,
  createGrn,
  cancelGrn,
  getAllCompanies,
  getAllWarehouses,
  getAllSuppliers,
  getAllProducts,
  getAllPurchaseOrders
} from "../services/api";

import "./Products.css";
import "./Company.css";
import "./GRN.css";

const emptyForm = {
  companyId: "",
  warehouseId: "",
  supplierId: "",
  poNumber: "",
  date: new Date().toISOString().split('T')[0],
  items: []
};

export default function GRN() {
  const [grns, setGrns] = useState([]);
  const [masterData, setMasterData] = useState({
    companies: [],
    warehouses: [],
    suppliers: [],
    products: [],
    purchaseOrders: []
  });

  // UI States
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const [query, setQuery] = useState("");
  const [showModal, setShowModal] = useState(false);
  const [expandedId, setExpandedId] = useState(null);
  const [saving, setSaving] = useState(false);

  // Form States
  const [form, setForm] = useState(emptyForm);
  const [currentItem, setCurrentItem] = useState({
    productId: "",
    requestedQty: "",
    receivedQty: "",
    unitPrice: "",
    discountAmount: 0
  });

  const [filteredWarehouses, setFilteredWarehouses] = useState([]);

  const fetchData = useCallback(async () => {
    try {
      setLoading(true);
      const [grnRes, compRes, whRes, supRes, prodRes, poRes] = await Promise.all([
        getAllGrns().catch(() => ({ data: [] })),
        getAllCompanies().catch(() => ({ data: [] })),
        getAllWarehouses().catch(() => ({ data: [] })),
        getAllSuppliers().catch(() => ({ data: [] })),
        getAllProducts().catch(() => ({ data: [] })),
        getAllPurchaseOrders().catch(() => ({ data: [] }))
      ]);

      setGrns(grnRes.data || []);
      setMasterData({
        companies: compRes.data || [],
        warehouses: whRes.data || [],
        suppliers: supRes.data || [],
        products: prodRes.data || [],
        purchaseOrders: poRes.data || []
      });
    } catch (err) {
      setError("Failed to sync GRN records.");
    } finally {
      setLoading(false);
    }
  }, []);

  useEffect(() => { fetchData(); }, [fetchData]);

  // --- Logic ---
  const handleCompanyChange = (id) => {
    setForm({ ...form, companyId: id, warehouseId: "" });
    setFilteredWarehouses(masterData.warehouses.filter(w => String(w.companyId) === String(id)));
  };

  const addItemToList = () => {
    if (!currentItem.productId || !currentItem.receivedQty || !currentItem.requestedQty || !currentItem.unitPrice) return;
    setForm(prev => ({ ...prev, items: [...prev.items, { ...currentItem }] }));
    setCurrentItem({ productId: "", requestedQty: "", receivedQty: "", unitPrice: "", discountAmount: 0 });
  };

  const handleCreate = async (e) => {
    e.preventDefault();
    if (form.items.length === 0) return alert("Add at least one item.");
    setSaving(true);
    try {
      const username = localStorage.getItem("username") || "admin";
      await createGrn({ ...form, createdBy: username });
      setShowModal(false);
      setForm(emptyForm);
      fetchData();
    } catch {
      setError("Failed to create GRN. Please check stock service connectivity.");
    } finally {
      setSaving(false);
    }
  };

  const handleCancel = async (grnNumber) => {
    if (!window.confirm("Are you sure you want to cancel this GRN?")) return;
    try {
      const username = localStorage.getItem("username") || "User";
      await cancelGrn(grnNumber, username);
      fetchData();
    } catch { alert("Failed to cancel GRN."); }
  };

  // --- Helpers ---
  const getName = (list, id) => list.find(i => String(i.id) === String(id))?.name || `ID: ${id}`;

  const filteredGrns = useMemo(() => {
    const q = query.toLowerCase();
    return grns.filter(g => g.grnNumber?.toLowerCase().includes(q) || g.poNumber?.toLowerCase().includes(q));
  }, [grns, query]);

  if (loading) return <div className="products-root"><Sidebar /><div className="loader"></div></div>;

  return (
    <div className="products-root">
      <Sidebar />
      <main className="main-content">
        <header className="page-header">
          <div><p className="page-label">PROCUREMENT</p><h1 className="page-title">GRN Records</h1></div>
          <button className="add-btn" onClick={() => setShowModal(true)}>+ Create GRN</button>
        </header>

        <div className="company-search-row">
          <div className="company-search-box">
            <span className="company-search-ic">⌕</span>
            <input value={query} onChange={e => setQuery(e.target.value)} placeholder="Search GRN or PO Number..." />
          </div>
        </div>

        <div className="company-table-wrap">
          <table className="company-table">
            <thead>
              <tr>
                <th>GRN #</th>
                <th>PO Reference</th>
                <th>Warehouse</th>
                <th>Supplier</th>
                <th>Status</th>
                <th>Net Total</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              {filteredGrns.map((g) => (
                <React.Fragment key={g.grnNumber}>
                  <tr onClick={() => setExpandedId(expandedId === g.grnNumber ? null : g.grnNumber)} style={{ cursor: 'pointer' }}>
                    <td className="cell-name">#{g.grnNumber}</td>
                    <td>#{g.poNumber}</td>
                    <td>{getName(masterData.warehouses, g.warehouseId)}</td>
                    <td>{getName(masterData.suppliers, g.supplierId)}</td>
                    <td><span className={`badge ${g.status === 'RECEIVED' ? 'badge-active' : 'badge-inactive'}`}>{g.status}</span></td>
                    <td style={{ fontWeight: 'bold' }}>${Number(g.totalNetAmount).toFixed(2)}</td>
                    <td className="actions">
                      {g.status !== 'CANCELLED' && (
                        <button className="icon-btn danger" onClick={(e) => { e.stopPropagation(); handleCancel(g.grnNumber); }} title="Cancel GRN">✕</button>
                      )}
                      <button className="icon-btn" style={{ marginLeft: '10px' }}>{expandedId === g.grnNumber ? "▴" : "▾"}</button>
                    </td>
                  </tr>

                  {expandedId === g.grnNumber && (
                    <tr style={{ background: '#09090b' }}>
                      <td colSpan="7" style={{ padding: '1.5rem', borderBottom: '1px solid #1e1e24' }}>
                        <p className="page-label" style={{ color: '#a78bfa', marginBottom: '10px' }}>RECEIVED ITEMS</p>
                        <table style={{ width: '100%', borderCollapse: 'collapse' }}>
                          <thead>
                            <tr style={{ textAlign: 'left', borderBottom: '1px solid #27272a' }}>
                              <th style={{ padding: '8px', color: '#71717a', fontSize: '0.7rem' }}>Product</th>
                              <th style={{ padding: '8px', color: '#71717a', fontSize: '0.7rem' }}>Req. Qty</th>
                              <th style={{ padding: '8px', color: '#71717a', fontSize: '0.7rem' }}>Rec. Qty</th>
                              <th style={{ padding: '8px', color: '#71717a', fontSize: '0.7rem', textAlign: 'right' }}>Unit Price</th>
                            </tr>
                          </thead>
                          <tbody>
                            {g.items?.map((item, idx) => (
                              <tr key={idx} style={{ borderBottom: '1px solid #1e1e24' }}>
                                <td style={{ padding: '8px' }}>{getName(masterData.products, item.productId)}</td>
                                <td style={{ padding: '8px', color: '#71717a' }}>{item.requestedQty}</td>
                                <td style={{ padding: '8px', color: '#4ade80', fontWeight: 'bold' }}>{item.receivedQty}</td>
                                <td style={{ padding: '8px', textAlign: 'right' }}>${item.unitPrice}</td>
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

      {/* --- CREATE MODAL --- */}
      {showModal && (
        <div className="modal-overlay">
          <div className="modal company-modal" style={{ maxWidth: '800px' }}>
            <div className="modal-header"><h2>New Goods Received Note</h2><button className="modal-close" onClick={() => setShowModal(false)}>✕</button></div>
            <form className="modal-form" onSubmit={handleCreate}>
              <div className="company-form-grid">
                <div className="company-field">
                  <label>COMPANY</label>
                  <select required value={form.companyId} onChange={e => handleCompanyChange(e.target.value)}>
                    <option value="">Select Company</option>
                    {masterData.companies.map(c => <option key={c.id} value={c.id}>{c.name}</option>)}
                  </select>
                </div>
                <div className="company-field">
                  <label>PO REFERENCE</label>
                  <select required value={form.poNumber} onChange={e => setForm({ ...form, poNumber: e.target.value })}>
                    <option value="">Select Purchase Order</option>
                    {masterData.purchaseOrders
                      .filter(po => String(po.companyId) === String(form.companyId))
                      .map(po => <option key={po.id} value={po.poNumber}>#{po.poNumber}</option>)
                    }
                  </select>
                </div>
                <div className="company-field">
                  <label>RECEIVING WAREHOUSE</label>
                  <select required value={form.warehouseId} onChange={e => setForm({ ...form, warehouseId: e.target.value })} disabled={!form.companyId}>
                    <option value="">Select Warehouse</option>
                    {filteredWarehouses.map(w => <option key={w.id} value={w.id}>{w.name}</option>)}
                  </select>
                </div>
                <div className="company-field">
                  <label>SUPPLIER</label>
                  <select required value={form.supplierId} onChange={e => setForm({ ...form, supplierId: e.target.value })}>
                    <option value="">Select Supplier</option>
                    {masterData.suppliers.map(s => <option key={s.id} value={s.id}>{s.name}</option>)}
                  </select>
                </div>
              </div>

              <div style={{ marginTop: '20px', padding: '15px', background: '#111115', borderRadius: '12px', border: '1px solid #1e1e24' }}>
                <div style={{ display: 'grid', gridTemplateColumns: '2fr 1fr 1fr 1fr auto', gap: '10px', marginBottom: '15px' }}>
                  <select value={currentItem.productId} onChange={e => setCurrentItem({ ...currentItem, productId: e.target.value })}>
                    <option value="">Select Product</option>
                    {masterData.products.map(p => <option key={p.id} value={p.id}>{p.name}</option>)}
                  </select>
                  <input type="number" placeholder="Req Qty" value={currentItem.requestedQty} onChange={e => setCurrentItem({ ...currentItem, requestedQty: e.target.value })} />
                  <input type="number" placeholder="Rec Qty" value={currentItem.receivedQty} onChange={e => setCurrentItem({ ...currentItem, receivedQty: e.target.value })} />
                  <input type="number" placeholder="Price" value={currentItem.unitPrice} onChange={e => setCurrentItem({ ...currentItem, unitPrice: e.target.value })} />
                  <button type="button" className="add-btn" style={{ marginTop: 0, padding: '0 15px', background: '#a78bfa', color: '#000' }} onClick={addItemToList}>Add</button>
                </div>
                <div style={{ maxHeight: '150px', overflowY: 'auto' }}>
                  {form.items.map((item, idx) => (
                    <div key={idx} style={{ display: 'flex', justifyContent: 'space-between', padding: '8px', borderBottom: '1px solid #1e1e24', fontSize: '0.8rem' }}>
                      <span>{getName(masterData.products, item.productId)} — Rec: <strong>{item.receivedQty}</strong> @ ${item.unitPrice}</span>
                      <button type="button" style={{ color: '#f87171', background: 'none', border: 'none' }} onClick={() => setForm({ ...form, items: form.items.filter((_, i) => i !== idx) })}>✕</button>
                    </div>
                  ))}
                </div>
              </div>

              <div className="modal-actions" style={{ marginTop: '2rem' }}>
                <button type="button" className="company-btn-light" onClick={() => setShowModal(false)}>Cancel</button>
                <button type="submit" className="company-submit-btn" disabled={saving || form.items.length === 0}>Save GRN</button>
              </div>
            </form>
          </div>
        </div>
      )}
    
  </div>
  );
}