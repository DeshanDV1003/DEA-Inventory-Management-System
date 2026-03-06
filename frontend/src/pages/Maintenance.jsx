import React, { useEffect, useState, useCallback, useMemo } from "react";
import Sidebar from "../components/Sidebar.jsx";
import {
  getAllMaintenances,
  addMaintenance,
  updateMaintenance,
  deleteMaintenance,
  getAllCompanies,
  getAllWarehouses,
  getAllAssets
} from "../services/api";

import "./Products.css";
import "./Company.css";

const emptyForm = {
  maintenanceNumber: "",
  companyId: "",
  warehouseId: "",
  assetId: "",
  date: "",
  cost: "",
  description: "",
  statusId: "PENDING" // Default status
};

export default function Maintenance() {
  const [maintenances, setMaintenances] = useState([]);
  const [masterData, setMasterData] = useState({
    companies: [],
    warehouses: [],
    assets: []
  });

  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const [showModal, setShowModal] = useState(false);
  const [editingId, setEditingId] = useState(null);
  const [form, setForm] = useState(emptyForm);
  const [deleteId, setDeleteId] = useState(null);

  // Cascading Filter States
  const [filteredWarehouses, setFilteredWarehouses] = useState([]);
  const [filteredAssets, setFilteredAssets] = useState([]);

//   const fetchData = useCallback(async () => {
//     try {
//       setLoading(true);
//       const [mRes, cRes, wRes, aRes] = await Promise.all([
//         getAllMaintenances(),
//         getAllCompanies(),
//         getAllWarehouses(),
//         getAllAssets()
//       ]);
//       setMaintenances(mRes.data || []);
//       setMasterData({
//         companies: cRes.data || [],
//         warehouses: wRes.data || [],
//         assets: aRes.data || []
//       });
//     } catch (err) {
//       setError("Failed to load maintenance records or reference data.");
//     } finally {
//       setLoading(false);
//     }
//   }, []);

  const fetchData = useCallback(async () => {
    try {
        setLoading(true);
        
        // We wrap each call in a .catch so one failure doesn't kill the whole page
        const [mRes, cRes, wRes, aRes] = await Promise.all([
        getAllMaintenances().catch(err => { console.error("Maint fail"); return { data: [] }; }),
        getAllCompanies().catch(err => { console.error("Comp fail"); return { data: [] }; }),
        getAllWarehouses().catch(err => { console.error("WH fail"); return { data: [] }; }),
        getAllAssets().catch(err => { console.error("Asset fail"); return { data: [] }; })
        ]);

        setMaintenances(mRes.data || []);
        setMasterData({
        companies: cRes.data || [],
        warehouses: wRes.data || [],
        assets: aRes.data || []
        });
    } catch (err) {
        setError("A critical error occurred while fetching data.");
    } finally {
        setLoading(false);
    }
    }, []);

  useEffect(() => { fetchData(); }, [fetchData]);

  // --- Cascading Logic ---
  const handleCompanyChange = (id) => {
    const companyId = String(id);
    setForm({ ...form, companyId, warehouseId: "", assetId: "" });
    setFilteredWarehouses(masterData.warehouses.filter(w => String(w.companyId) === companyId));
    setFilteredAssets([]);
  };

  const handleWarehouseChange = (id) => {
    const warehouseId = String(id);
    setForm({ ...form, warehouseId, assetId: "" });
    setFilteredAssets(masterData.assets.filter(a => String(a.warehouseId) === warehouseId));
  };

  // --- CRUD Handlers ---
  const openEdit = (m) => {
    setEditingId(m.id);
    setForm(m);
    // Pre-populate filtered lists for existing data
    setFilteredWarehouses(masterData.warehouses.filter(w => String(w.companyId) === String(m.companyId)));
    setFilteredAssets(masterData.assets.filter(a => String(a.warehouseId) === String(m.warehouseId)));
    setShowModal(true);
  };

  const onSubmit = async (e) => {
    e.preventDefault();
    try {
      if (editingId) await updateMaintenance(editingId, form);
      else await addMaintenance(form);
      setShowModal(false);
      fetchData();
    } catch {
      setError("Failed to save record.");
    }
  };

  const confirmDelete = async () => {
    try {
      await deleteMaintenance(deleteId);
      setDeleteId(null);
      fetchData();
    } catch { setError("Delete failed."); }
  };

  // Helper for Table Names
  const getName = (list, id) => list.find(i => String(i.id) === String(id))?.name || `ID: ${id}`;

  return (
    <div className="products-root">
      <Sidebar />
      <main className="main-content">
        <header className="page-header">
          <div>
            <p className="page-label">OPERATIONS</p>
            <h1 className="page-title">Maintenance Registry</h1>
          </div>
          <button className="add-btn" onClick={() => { setEditingId(null); setForm(emptyForm); setShowModal(true); }}>
            + Add Record
          </button>
        </header>

        {error && <div className="alert-error">! {error} <button onClick={() => setError("")}>✕</button></div>}

        <div className="company-table-wrap">
          <table className="company-table">
            <thead>
              <tr>
                <th>MNT #</th>
                <th>Company</th>
                <th>Warehouse</th>
                <th>Asset</th>
                <th>Date</th>
                <th>Cost</th>
                <th>Status</th>
                <th className="actions-col">Actions</th>
              </tr>
            </thead>
            <tbody>
              {maintenances.map((m) => (
                <tr key={m.id}>
                  <td className="cell-name">#{m.maintenanceNumber}</td>
                  <td>{getName(masterData.companies, m.companyId)}</td>
                  <td>{getName(masterData.warehouses, m.warehouseId)}</td>
                  <td>{getName(masterData.assets, m.assetId)}</td>
                  <td>{new Date(m.date).toLocaleDateString()}</td>
                  <td style={{ color: '#4ade80', fontWeight: '600' }}>${m.cost}</td>
                  <td><span className="badge-active">{m.statusId}</span></td>
                  <td className="actions">
                    <button className="icon-btn" onClick={() => openEdit(m)}>✎</button>
                    <button className="icon-btn danger" onClick={() => setDeleteId(m.id)}>✕</button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </main>

      {showModal && (
        <div className="modal-overlay">
          <div className="modal company-modal" style={{ maxWidth: '750px' }}>
            <div className="modal-header">
              <h2>{editingId ? "Update Record" : "New Maintenance Entry"}</h2>
              <button className="modal-close" onClick={() => setShowModal(false)}>✕</button>
            </div>
            <form className="modal-form" onSubmit={onSubmit}>
              <div className="company-form-grid">
                <div className="company-field">
                  <label>MAINTENANCE NUMBER</label>
                  <input required value={form.maintenanceNumber} onChange={e => setForm({ ...form, maintenanceNumber: e.target.value })} placeholder="e.g. MNT-101" />
                </div>

                <div className="company-field">
                  <label>COMPANY</label>
                  <select required value={form.companyId} onChange={e => handleCompanyChange(e.target.value)}>
                    <option value="">Select Company</option>
                    {masterData.companies.map(c => <option key={c.id} value={c.id}>{c.name}</option>)}
                  </select>
                </div>

                <div className="company-field">
                  <label>WAREHOUSE</label>
                  <select required value={form.warehouseId} onChange={e => handleWarehouseChange(e.target.value)} disabled={!form.companyId}>
                    <option value="">{form.companyId ? "Select Warehouse" : "Choose Company First"}</option>
                    {filteredWarehouses.map(w => <option key={w.id} value={w.id}>{w.name}</option>)}
                  </select>
                </div>

                <div className="company-field">
                  <label>TARGET ASSET</label>
                  <select required value={form.assetId} onChange={e => setForm({ ...form, assetId: e.target.value })} disabled={!form.warehouseId}>
                    <option value="">{form.warehouseId ? "Select Asset" : "Choose Warehouse First"}</option>
                    {filteredAssets.map(a => <option key={a.id} value={a.id}>{a.name}</option>)}
                  </select>
                </div>

                <div className="company-field">
                  <label>SCHEDULED DATE</label>
                  <input type="datetime-local" required value={form.date} onChange={e => setForm({ ...form, date: e.target.value })} />
                </div>

                <div className="company-field">
                  <label>TOTAL COST ($)</label>
                  <input type="number" step="0.01" required value={form.cost} onChange={e => setForm({ ...form, cost: e.target.value })} />
                </div>

                <div className="company-field">
                  <label>CURRENT STATUS</label>
                  <select value={form.statusId} onChange={e => setForm({ ...form, statusId: e.target.value })}>
                    <option value="PENDING">PENDING</option>
                    <option value="IN_PROGRESS">IN PROGRESS</option>
                    <option value="COMPLETED">COMPLETED</option>
                    <option value="CANCELLED">CANCELLED</option>
                  </select>
                </div>
              </div>

              <div className="company-field company-field-full" style={{ marginTop: '1rem' }}>
                <label>WORK DESCRIPTION</label>
                <textarea value={form.description} onChange={e => setForm({ ...form, description: e.target.value })} placeholder="Details of the maintenance work..." style={{ minHeight: '80px' }} />
              </div>

              <div className="modal-actions">
                <button type="button" className="company-btn-light" onClick={() => setShowModal(false)}>Cancel</button>
                <button type="submit" className="company-submit-btn">Save Record</button>
              </div>
            </form>
          </div>
        </div>
      )}

      {deleteId && (
        <div className="modal-overlay" onClick={() => setDeleteId(null)}>
          <div className="modal confirm-modal">
            <h2>Delete Maintenance Record?</h2>
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