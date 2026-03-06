import React, { useEffect, useState, useCallback, useMemo } from "react";
import Sidebar from "../components/Sidebar.jsx";
import {
  getAllTransfers,
  createTransfer,
  getTransferDetails, 
  getAllWarehouses,
  getAllProducts
} from "../services/api";

import "./Products.css";
import "./Company.css";
import "./StockTransfer.css";

const emptyForm = {
  fromWarehouseId: "",
  toWarehouseId: "",
  remark: "",
  items: []
};

export default function StockTransfer() {
  const [transfers, setTransfers] = useState([]); // Initialized as array to prevent .filter crash
  const [masterData, setMasterData] = useState({ warehouses: [], products: [] });
  
  // UI States
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const [query, setQuery] = useState("");
  const [showModal, setShowModal] = useState(false);
  const [expandedId, setExpandedId] = useState(null);
  const [saving, setSaving] = useState(false);
  const [deleteId, setDeleteId] = useState(null);

  // Form States
  const [form, setForm] = useState(emptyForm);
  const [currentItem, setCurrentItem] = useState({ productId: "", qty: 1 });

  const fetchData = useCallback(async () => {
    try {
      setLoading(true);
      const [transRes, whRes, prodRes] = await Promise.all([
        getAllTransfers().catch(() => ({ data: [] })),
        getAllWarehouses().catch(() => ({ data: [] })),
        getAllProducts().catch(() => ({ data: [] }))
      ]);

      // FIX: Ensure transfers is ALWAYS an array (handles res.data vs res.data.data)
      const transferArray = Array.isArray(transRes.data) 
        ? transRes.data 
        : (transRes.data?.data || []);

      setTransfers(transferArray);
      
      setMasterData({
        warehouses: Array.isArray(whRes.data) ? whRes.data : (whRes.data?.data || []),
        products: Array.isArray(prodRes.data) ? prodRes.data : (prodRes.data?.data || [])
      });
    } catch (err) {
      setError("Critical error connecting to services.");
    } finally {
      setLoading(false);
    }
  }, []);

  useEffect(() => {
    fetchData();
  }, [fetchData]);

  // --- LOGIC: Fetch items only when a row is expanded ---
  const handleToggleExpand = async (transferNo) => {
    if (expandedId === transferNo) {
      setExpandedId(null);
      return;
    }

    setExpandedId(transferNo);

    // If items aren't already loaded in the state for this specific transfer, fetch them
    const target = transfers.find(t => t.transferNo === transferNo);
    if (target && (!target.items || target.items.length === 0)) {
      try {
        const detailRes = await getTransferDetails(transferNo);
        const itemData = detailRes.data?.data || detailRes.data || [];
        
        setTransfers(prev => prev.map(t => 
          t.transferNo === transferNo ? { ...t, items: itemData } : t
        ));
      } catch (err) {
        console.error("Could not load transfer details");
      }
    }
  };

  // --- Helpers ---
  const getName = (list, id) => {
    if (!list || !id) return "—";
    const found = list.find(i => String(i.id) === String(id));
    return found ? found.name : `ID: ${id}`;
  };

  const addItemToList = () => {
    if (!currentItem.productId || !currentItem.qty) return;
    setForm(prev => ({
      ...prev,
      items: [...prev.items, { ...currentItem }]
    }));
    setCurrentItem({ productId: "", qty: 1 });
  };

  const handleCreate = async (e) => {
    e.preventDefault();
    if (form.items.length === 0) {
        alert("Please add at least one item to the transfer list.");
        return;
    }
    setSaving(true);
    try {
      await createTransfer(form);
      setShowModal(false);
      setForm(emptyForm);
      fetchData();
    } catch {
      setError("Transfer failed. Please check warehouse stock availability.");
    } finally {
      setSaving(false);
    }
  };

  // FIX: Added safety check Array.isArray(transfers) to prevent crash
  const filteredTransfers = useMemo(() => {
    const q = query.toLowerCase();
    const dataToFilter = Array.isArray(transfers) ? transfers : [];
    return dataToFilter.filter(t => (t.transferNo || "").toLowerCase().includes(q));
  }, [transfers, query]);

  if (loading) return <div className="products-root"><Sidebar /><div className="loader"></div></div>;

  return (
    <div className="products-root">
      <Sidebar />

      <main className="main-content">
        <header className="page-header">
          <div>
            <p className="page-label">LOGISTICS</p>
            <h1 className="page-title">Stock Transfers</h1>
          </div>
          <button className="add-btn" onClick={() => { setForm(emptyForm); setShowModal(true); }}>
            + New Transfer
          </button>
        </header>

        {error && <div className="alert-error">! {error} <button onClick={() => setError("")}>✕</button></div>}

        <div className="company-search-row">
            <div className="company-search-box">
                <span className="company-search-ic">⌕</span>
                <input value={query} onChange={e => setQuery(e.target.value)} placeholder="Search Transfer No..." />
            </div>
        </div>

        <div className="company-table-wrap">
          <table className="company-table">
            <thead>
              <tr>
                <th>Transfer No</th>
                <th>From Warehouse</th>
                <th>To Warehouse</th>
                <th>Status</th>
                <th className="actions-col">Actions</th>
              </tr>
            </thead>
            <tbody>
              {filteredTransfers.map((t) => (
                <React.Fragment key={t.transferNo || Math.random()}>
                  <tr onClick={() => handleToggleExpand(t.transferNo)} style={{cursor: 'pointer'}}>
                    <td className="cell-name">#{t.transferNo}</td>
                    <td>{getName(masterData.warehouses, t.fromWarehouseId)}</td>
                    <td>{getName(masterData.warehouses, t.toWarehouseId)}</td>
                    <td><span className="badge badge-active">{t.status || 'PENDING'}</span></td>
                    <td className="actions">
                      <button className="icon-btn" onClick={(e) => { e.stopPropagation(); /* logic */ }}>✎</button>
                      <button className="icon-btn danger" onClick={(e) => { e.stopPropagation(); setDeleteId(t.transferNo); }}>✕</button>
                      <button className="icon-btn" style={{marginLeft: '10px'}}>{expandedId === t.transferNo ? "▴" : "▾"}</button>
                    </td>
                  </tr>

                  {/* Expanded Items List */}
                  {expandedId === t.transferNo && (
                    <tr style={{background: '#09090b'}}>
                      <td colSpan="5" style={{padding: '1.5rem', borderBottom: '1px solid #1e1e24'}}>
                        <div className="page-label" style={{marginBottom: '10px', color: '#a78bfa'}}>Transferred Items</div>
                        <table style={{width: '100%', borderCollapse: 'collapse'}}>
                           <thead>
                             <tr style={{textAlign: 'left', borderBottom: '1px solid #27272a'}}>
                               <th style={{padding: '8px', color: '#71717a', fontSize: '0.7rem'}}>Product Name</th>
                               <th style={{padding: '8px', color: '#71717a', fontSize: '0.7rem', textAlign: 'right'}}>Quantity</th>
                             </tr>
                           </thead>
                           <tbody>
                             {t.items && t.items.length > 0 ? t.items.map((item, idx) => (
                               <tr key={idx} style={{borderBottom: '1px solid #1e1e24'}}>
                                 <td style={{padding: '8px', color: '#fff'}}>{getName(masterData.products, item.productId)}</td>
                                 <td style={{padding: '8px', color: '#fff', textAlign: 'right'}}>{item.qty || item.quantity} Units</td>
                               </tr>
                             )) : (
                               <tr><td colSpan="2" style={{padding: '10px', color: '#52525b', textAlign: 'center'}}>No items found for this record.</td></tr>
                             )}
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
          <div className="modal company-modal" style={{maxWidth: '750px'}}>
            <div className="modal-header">
              <h2>New Stock Transfer</h2>
              <button className="modal-close" onClick={() => setShowModal(false)}>✕</button>
            </div>
            
            <form className="modal-form" onSubmit={handleCreate}>
              <div className="company-form-grid">
                <div className="company-field">
                  <label>SOURCE WAREHOUSE</label>
                  <select required value={form.fromWarehouseId} onChange={e => setForm({...form, fromWarehouseId: e.target.value})}>
                    <option value="">Select Origin</option>
                    {masterData.warehouses.map(w => <option key={w.id} value={w.id}>{w.name}</option>)}
                  </select>
                </div>

                <div className="company-field">
                  <label>DESTINATION WAREHOUSE</label>
                  <select required value={form.toWarehouseId} onChange={e => setForm({...form, toWarehouseId: e.target.value})}>
                    <option value="">Select Destination</option>
                    {masterData.warehouses
                        .filter(w => String(w.id) !== String(form.fromWarehouseId))
                        .map(w => <option key={w.id} value={w.id}>{w.name}</option>)
                    }
                  </select>
                </div>
              </div>

              <div className="company-field company-field-full" style={{marginTop: '10px'}}>
                <label>REMARK</label>
                <textarea style={{minHeight: '60px'}} value={form.remark} onChange={e => setForm({...form, remark: e.target.value})} placeholder="Reason for transfer..." />
              </div>

              {/* Items Builder Box */}
              <div style={{marginTop: '20px', padding: '15px', background: '#111115', borderRadius: '12px', border: '1px solid #1e1e24'}}>
                <h4 className="page-label" style={{marginBottom: '15px'}}>Add Items to Transfer</h4>
                <div style={{display: 'flex', gap: '10px', marginBottom: '15px'}}>
                   <select style={{flex: 3, background: '#09090b', border: '1px solid #27272a', color: 'white', borderRadius: '6px'}} value={currentItem.productId} onChange={e => setCurrentItem({...currentItem, productId: e.target.value})}>
                      <option value="">Select Product</option>
                      {masterData.products.map(p => <option key={p.id} value={p.id}>{p.name}</option>)}
                   </select>
                   <input style={{flex: 1, background: '#09090b', border: '1px solid #27272a', color: 'white', padding: '0 10px', borderRadius: '6px'}} type="number" value={currentItem.qty} onChange={e => setCurrentItem({...currentItem, qty: e.target.value})} />
                   <button type="button" className="add-btn" style={{marginTop: 0, padding: '0 15px', background: '#a78bfa', color: '#000', fontWeight: 'bold'}} onClick={addItemToList}>Add</button>
                </div>

                <div style={{minHeight: '50px', border: '1px solid #27272a', borderRadius: '8px', padding: '10px', background: '#09090b'}}>
                  {form.items.length === 0 ? (
                    <p style={{color: '#52525b', fontSize: '0.8rem', textAlign: 'center', margin: '10px 0'}}>No items added yet.</p>
                  ) : (
                    <ul style={{listStyle: 'none', padding: 0, margin: 0}}>
                      {form.items.map((item, idx) => (
                        <li key={idx} style={{display: 'flex', justifyContent: 'space-between', padding: '8px', borderBottom: '1px solid #1e1e24', fontSize: '0.85rem', color: '#fff'}}>
                          <span>{getName(masterData.products, item.productId)} — <strong>{item.qty} units</strong></span>
                          <button type="button" style={{color: '#f87171', background: 'none', border: 'none', cursor: 'pointer'}} onClick={() => setForm({...form, items: form.items.filter((_, i) => i !== idx)})}>✕ Remove</button>
                        </li>
                      ))}
                    </ul>
                  )}
                </div>
              </div>

              <div className="modal-actions" style={{marginTop: '2rem'}}>
                <button type="button" className="company-btn-light" onClick={() => setShowModal(false)}>Cancel</button>
                <button type="submit" className="company-submit-btn" disabled={saving || form.items.length === 0}>
                  {saving ? "Processing..." : "Confirm Transfer"}
                </button>
              </div>
            </form>
          </div>
        </div>
      )}

      {/* --- DELETE CONFIRM MODAL --- */}
      {deleteId && (
        <div className="modal-overlay" onClick={() => setDeleteId(null)}>
          <div className="modal confirm-modal">
            <h2>Delete Transfer #{deleteId}?</h2>
            <p>This will remove the transfer record. Confirm with administration.</p>
            <div className="modal-actions">
              <button className="cancel-btn" onClick={() => setDeleteId(null)}>Cancel</button>
              <button className="delete-confirm-btn" onClick={() => { /* add delete call here */ setDeleteId(null); fetchData(); }}>Confirm</button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
}