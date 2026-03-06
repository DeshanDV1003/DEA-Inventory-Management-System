import React, { useEffect, useState, useCallback, useMemo } from "react";
import Sidebar from "../components/Sidebar.jsx";
import {
  getAllAssets,
  addAsset,
  updateAsset,
  deleteAsset,
  getAllCompanies,
  getAllWarehouses
} from "../services/api";

import "./Products.css";
import "./Company.css";

const emptyForm = {
  name: "",
  company_id: "",
  warehouse_id: "",
  location: "",
  asset_tag: "",
  purchase_value: "",
  purchase_date: "",
  status: "ACTIVE"
};

export default function Assets() {
  // --- Data States ---
  const [assets, setAssets] = useState([]);
  const [masterData, setMasterData] = useState({
    companies: [],
    warehouses: []
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

  // Cascading Filter
  const [filteredWarehouses, setFilteredWarehouses] = useState([]);

  const fetchData = useCallback(async () => {
    try {
      setLoading(true);
      // Fetch Assets + Metadata in parallel
      const [assetRes, compRes, whRes] = await Promise.all([
        getAllAssets().catch(() => ({ data: [] })),
        getAllCompanies().catch(() => ({ data: [] })),
        getAllWarehouses().catch(() => ({ data: [] }))
      ]);

      setAssets(assetRes.data || []);
      setMasterData({
        companies: compRes.data || [],
        warehouses: whRes.data || []
      });
    } catch (err) {
      setError("Failed to sync asset registry.");
    } finally {
      setLoading(false);
    }
  }, []);

  useEffect(() => { fetchData(); }, [fetchData]);

  // --- Cascading Logic ---
  const handleCompanyChange = (id) => {
    const compId = String(id);
    setForm({ ...form, company_id: compId, warehouse_id: "" });
    setFilteredWarehouses(masterData.warehouses.filter(w => String(w.companyId) === compId));
  };

  // --- CRUD Handlers ---
  const openEdit = (a) => {
    setEditingId(a.id);
    setForm(a);
    // Pre-filter warehouses for the selected company
    setFilteredWarehouses(masterData.warehouses.filter(w => String(w.companyId) === String(a.company_id)));
    setShowModal(true);
  };

  const onFormSubmit = async (e) => {
    e.preventDefault();
    setSaving(true);
    try {
      if (editingId) await updateAsset(editingId, form);
      else await addAsset(form);
      setShowModal(false);
      fetchData();
    } catch {
      setError("Error saving asset. Check if Asset Tag is unique.");
    } finally {
      setSaving(false);
    }
  };

  const confirmDelete = async () => {
    try {
      await deleteAsset(deleteId);
      setDeleteId(null);
      fetchData();
    } catch {
      setError("Delete failed. Asset might be linked to maintenance records.");
    }
  };

  // Helper for Table Names
  const getName = (list, id) => list.find(i => String(i.id) === String(id))?.name || `ID: ${id}`;

  const filteredAssets = useMemo(() => {
    const q = query.toLowerCase();
    return assets.filter(a => 
        a.name?.toLowerCase().includes(q) || 
        a.asset_tag?.toLowerCase().includes(q)
    );
  }, [assets, query]);

  if (loading) return <div className="products-root"><Sidebar /><div className="loader"></div></div>;

  return (
    <div className="products-root">
      <Sidebar />
      <main className="main-content">
        <header className="page-header">
          <div>
            <p className="page-label">INFRASTRUCTURE</p>
            <h1 className="page-title">Asset Registry</h1>
          </div>
          <button className="add-btn" onClick={() => { setEditingId(null); setForm(emptyForm); setShowModal(true); }}>
            + Add Asset
          </button>
        </header>

        {error && <div className="alert-error">! {error} <button onClick={() => setError("")}>✕</button></div>}

        <div className="company-search-row">
            <div className="company-search-box">
                <span className="company-search-ic">⌕</span>
                <input value={query} onChange={e => setQuery(e.target.value)} placeholder="Search by name or tag..." />
            </div>
        </div>

        <div className="company-table-wrap">
          <table className="company-table">
            <thead>
              <tr>
                <th>Asset Tag</th>
                <th>Name</th>
                <th>Company</th>
                <th>Warehouse</th>
                <th>Value</th>
                <th>Status</th>
                <th className="actions-col">Actions</th>
              </tr>
            </thead>
            <tbody>
              {filteredAssets.map((a) => (
                <tr key={a.id}>
                  <td className="cell-name" style={{color: '#a78bfa'}}>#{a.asset_tag}</td>
                  <td style={{fontWeight: '600'}}>{a.name}</td>
                  <td>{getName(masterData.companies, a.company_id)}</td>
                  <td>{getName(masterData.warehouses, a.warehouse_id)}</td>
                  <td>${Number(a.purchase_value).toLocaleString()}</td>
                  <td>
                    <span className={`badge ${a.status === 'ACTIVE' ? 'badge-active' : 'badge-inactive'}`}>
                        {a.status}
                    </span>
                  </td>
                  <td className="actions">
                    <button className="icon-btn" onClick={() => openEdit(a)}>✎</button>
                    <button className="icon-btn danger" onClick={() => setDeleteId(a.id)}>✕</button>
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
          <div className="modal company-modal" style={{maxWidth: '700px'}}>
            <div className="modal-header">
              <h2>{editingId ? "Update Asset" : "New Asset Registry"}</h2>
              <button className="modal-close" onClick={() => setShowModal(false)}>✕</button>
            </div>
            <form className="modal-form" onSubmit={onFormSubmit}>
              <div className="company-form-grid">
                <div className="company-field">
                  <label>ASSET NAME</label>
                  <input required value={form.name} onChange={e => setForm({...form, name: e.target.value})} placeholder="e.g. Forklift Truck" />
                </div>

                <div className="company-field">
                    <label>ASSET TAG / SN</label>
                    <input required value={form.asset_tag} onChange={e => setForm({...form, asset_tag: e.target.value})} placeholder="e.g. AST-9901" />
                </div>

                <div className="company-field">
                  <label>OWNING COMPANY</label>
                  <select required value={form.company_id} onChange={e => handleCompanyChange(e.target.value)}>
                    <option value="">Select Company</option>
                    {masterData.companies.map(c => <option key={c.id} value={c.id}>{c.name}</option>)}
                  </select>
                </div>

                <div className="company-field">
                  <label>WAREHOUSE LOCATION</label>
                  <select required value={form.warehouse_id} onChange={e => setForm({...form, warehouse_id: e.target.value})} disabled={!form.company_id}>
                    <option value="">Select Warehouse</option>
                    {filteredWarehouses.map(w => <option key={w.id} value={w.id}>{w.name}</option>)}
                  </select>
                </div>

                <div className="company-field">
                  <label>PURCHASE VALUE ($)</label>
                  <input type="number" step="0.01" value={form.purchase_value} onChange={e => setForm({...form, purchase_value: e.target.value})} />
                </div>

                <div className="company-field">
                  <label>PURCHASE DATE</label>
                  <input type="date" value={form.purchase_date} onChange={e => setForm({...form, purchase_date: e.target.value})} />
                </div>

                <div className="company-field">
                  <label>PHYSICAL LOCATION (INTERNAL)</label>
                  <input value={form.location} onChange={e => setForm({...form, location: e.target.value})} placeholder="e.g. Section B-4" />
                </div>

                <div className="company-field">
                  <label>STATUS</label>
                  <select value={form.status} onChange={e => setForm({...form, status: e.target.value})}>
                    <option value="ACTIVE">ACTIVE</option>
                    <option value="MAINTENANCE">UNDER MAINTENANCE</option>
                    <option value="RETIRED">RETIRED</option>
                  </select>
                </div>
              </div>

              <div className="modal-actions" style={{marginTop: '2rem'}}>
                <button type="button" className="company-btn-light" onClick={() => setShowModal(false)}>Cancel</button>
                <button type="submit" className="company-submit-btn" disabled={saving}>{saving ? "Saving..." : "Save Asset"}</button>
              </div>
            </form>
          </div>
        </div>
      )}

      {/* Delete Confirmation */}
      {deleteId && (
        <div className="modal-overlay" onClick={() => setDeleteId(null)}>
          <div className="modal confirm-modal">
            <h2>Delete Asset?</h2>
            <p>This will permanently remove the asset from the registry.</p>
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