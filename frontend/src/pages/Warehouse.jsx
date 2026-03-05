/**
 * Warehouse Page
 *
 * Purpose:
 * - Displays all warehouses in a table.
 * - Supports Add, Edit (inline modal), and Delete operations.
 * - Filter dropdown to narrow list by Company ID.
 *
 * API Calls (via api.js):
 * - getAllWarehouses()
 * - createWarehouse(data)
 * - updateWarehouse(id, data)
 * - deleteWarehouse(id)
 */

import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import {
  getAllWarehouses,
  createWarehouse,
  updateWarehouse,
  deleteWarehouse,
} from "../services/api";
import Sidebar from "../components/Sidebar";
import "./Warehouse.css";

const EMPTY_FORM = {
  name: "",
  companyId: "",
  email: "",
  phone: "",
  status: "Active",
  address: "",
};

const Warehouse = () => {
  const navigate = useNavigate();
  const username = localStorage.getItem("username") || "User";

  const [warehouses, setWarehouses]   = useState([]);
  const [loading, setLoading]         = useState(true);
  const [error, setError]             = useState("");

  // Modal state: "add" | "edit" | null
  const [modalMode, setModalMode]     = useState(null);
  const [editTarget, setEditTarget]   = useState(null);
  const [form, setForm]               = useState(EMPTY_FORM);
  const [saving, setSaving]           = useState(false);

  // Delete confirm
  const [deleteId, setDeleteId]       = useState(null);

  // Filter by company
  const [filterCompany, setFilterCompany] = useState("");

  /* ── fetch ── */
  const fetchWarehouses = async () => {
    try {
      setLoading(true);
      const res = await getAllWarehouses();
      setWarehouses(res.data);
    } catch (err) {
      if (err.response?.status === 401 || err.response?.status === 403) {
        localStorage.removeItem("token");
        localStorage.removeItem("username");
        navigate("/login");
      } else {
        setError("Failed to load warehouses.");
      }
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => { fetchWarehouses(); }, []);

  /* ── derived: unique company IDs for filter dropdown ── */
  const companyIds = [...new Set(warehouses.map((w) => w.companyId).filter(Boolean))].sort((a,b)=>a-b);

  const displayed = filterCompany
      ? warehouses.filter((w) => String(w.companyId) === filterCompany)
      : warehouses;

  /* ── open modals ── */
  const openAdd = () => {
    setForm(EMPTY_FORM);
    setEditTarget(null);
    setModalMode("add");
  };

  const openEdit = (warehouse) => {
    setEditTarget(warehouse);
    setForm({
      name:      warehouse.name      || "",
      companyId: warehouse.companyId != null ? String(warehouse.companyId) : "",
      email:     warehouse.email     || "",
      phone:     warehouse.phone     || "",
      status:    warehouse.status    || "Active",
      address:   warehouse.address   || "",
    });
    setModalMode("edit");
  };

  const closeModal = () => { setModalMode(null); setEditTarget(null); };

  /* ── submit (add or edit) ── */
  const handleSubmit = async (e) => {
    e.preventDefault();
    setSaving(true);
    try {
      const payload = {
        name:      form.name,
        companyId: parseInt(form.companyId),
        email:     form.email     || null,
        phone:     form.phone     || null,
        status:    form.status,
        address:   form.address   || null,
      };

      if (modalMode === "add") {
        await createWarehouse(payload);
      } else {
        await updateWarehouse(editTarget.id, payload);
      }

      closeModal();
      fetchWarehouses();
    } catch {
      setError(modalMode === "add" ? "Failed to add warehouse." : "Failed to update warehouse.");
    } finally {
      setSaving(false);
    }
  };

  /* ── delete ── */
  const handleDelete = async () => {
    try {
      await deleteWarehouse(deleteId);
      setDeleteId(null);
      fetchWarehouses();
    } catch {
      setError("Failed to delete warehouse.");
      setDeleteId(null);
    }
  };

  /* ── badge helper ── */
  const statusBadge = (status) => {
    const s = (status || "").toLowerCase();
    if (s === "active")   return "badge badge-active";
    if (s === "inactive") return "badge badge-inactive";
    return "badge badge-other";
  };

  /* ── render ── */
  return (
      <div className="warehouse-root">
        <Sidebar />

        <main className="main-content">

          {/* Header */}
          <header className="page-header">
            <div>
              <p className="page-label">INVENTORY</p>
              <h1 className="page-title">Warehouses</h1>
            </div>
            <button className="add-btn" onClick={openAdd}>+ Add Warehouse</button>
          </header>

          {/* Error */}
          {error && (
              <div className="alert-error">
                ⚠ {error}
                <button onClick={() => setError("")}>✕</button>
              </div>
          )}

          {/* Filter bar */}
          {!loading && warehouses.length > 0 && (
              <div className="filter-bar">
                <span className="filter-label">FILTER BY COMPANY</span>
                <select
                    className="filter-select"
                    value={filterCompany}
                    onChange={(e) => setFilterCompany(e.target.value)}
                >
                  <option value="">All companies</option>
                  {companyIds.map((id) => (
                      <option key={id} value={String(id)}>Company {id}</option>
                  ))}
                </select>
                {filterCompany && (
                    <button className="filter-clear-btn" onClick={() => setFilterCompany("")}>
                      ✕ Clear
                    </button>
                )}
                <span className="filter-count">
              {displayed.length} of {warehouses.length} warehouses
            </span>
              </div>
          )}

          {/* Content */}
          {loading ? (
              <div className="loading-state">
                <div className="loader" />
                <p>Loading warehouses...</p>
              </div>
          ) : displayed.length === 0 ? (
              <div className="empty-state">
                <span className="empty-icon">⫙</span>
                <p>{filterCompany ? "No warehouses for this company." : "No warehouses yet. Add your first one."}</p>
              </div>
          ) : (
              <div className="warehouse-table-wrap">
                <table className="warehouse-table">
                  <thead>
                  <tr>
                    <th>#</th>
                    <th>Name</th>
                    <th>Company ID</th>
                    <th>Email</th>
                    <th>Phone</th>
                    <th>Address</th>
                    <th>Status</th>
                    <th></th>
                  </tr>
                  </thead>
                  <tbody>
                  {displayed.map((w, i) => (
                      <tr key={w.id}>
                        <td className="row-num">{String(i + 1).padStart(2, "0")}</td>
                        <td className="warehouse-name">{w.name}</td>
                        <td>{w.companyId || "—"}</td>
                        <td className="mono">{w.email || "—"}</td>
                        <td className="mono">{w.phone || "—"}</td>
                        <td>{w.address || "—"}</td>
                        <td>
                          <span className={statusBadge(w.status)}>{w.status || "—"}</span>
                        </td>
                        <td className="actions">
                          <button className="action-btn edit" onClick={() => openEdit(w)}>Edit</button>
                          <button className="action-btn del"  onClick={() => setDeleteId(w.id)}>Delete</button>
                        </td>
                      </tr>
                  ))}
                  </tbody>
                </table>
              </div>
          )}
        </main>

        {/* ── Add / Edit Modal ── */}
        {modalMode && (
            <div className="modal-overlay" onClick={closeModal}>
              <div className="modal" onClick={(e) => e.stopPropagation()}>
                <div className="modal-header">
                  <h2>{modalMode === "add" ? "New Warehouse" : "Edit Warehouse"}</h2>
                  <button className="modal-close" onClick={closeModal}>✕</button>
                </div>
                <form onSubmit={handleSubmit} className="modal-form">
                  <div className="field-group">
                    <label>WAREHOUSE NAME</label>
                    <input
                        required
                        placeholder="e.g. Main Distribution Centre"
                        value={form.name}
                        onChange={(e) => setForm({ ...form, name: e.target.value })}
                    />
                  </div>

                  <div className="field-row">
                    <div className="field-group">
                      <label>COMPANY ID</label>
                      <input
                          type="number"
                          required
                          placeholder="e.g. 1"
                          value={form.companyId}
                          onChange={(e) => setForm({ ...form, companyId: e.target.value })}
                      />
                    </div>
                    <div className="field-group">
                      <label>STATUS</label>
                      <select
                          value={form.status}
                          onChange={(e) => setForm({ ...form, status: e.target.value })}
                      >
                        <option value="Active">Active</option>
                        <option value="Inactive">Inactive</option>
                      </select>
                    </div>
                  </div>

                  <div className="field-row">
                    <div className="field-group">
                      <label>EMAIL</label>
                      <input
                          type="email"
                          placeholder="e.g. warehouse@company.com"
                          value={form.email}
                          onChange={(e) => setForm({ ...form, email: e.target.value })}
                      />
                    </div>
                    <div className="field-group">
                      <label>PHONE</label>
                      <input
                          placeholder="e.g. +94 77 123 4567"
                          value={form.phone}
                          onChange={(e) => setForm({ ...form, phone: e.target.value })}
                      />
                    </div>
                  </div>

                  <div className="field-group">
                    <label>ADDRESS</label>
                    <input
                        placeholder="e.g. 42 Industrial Zone, Negombo"
                        value={form.address}
                        onChange={(e) => setForm({ ...form, address: e.target.value })}
                    />
                  </div>

                  <div className="modal-actions">
                    <button type="button" className="cancel-btn" onClick={closeModal}>Cancel</button>
                    <button type="submit" className="submit-btn" disabled={saving}>
                      {saving
                          ? <span className="spinner" />
                          : modalMode === "add" ? "Add Warehouse" : "Save Changes"}
                    </button>
                  </div>
                </form>
              </div>
            </div>
        )}

        {/* ── Delete Confirm Modal ── */}
        {deleteId && (
            <div className="modal-overlay" onClick={() => setDeleteId(null)}>
              <div className="modal confirm-modal" onClick={(e) => e.stopPropagation()}>
                <h2>Delete Warehouse?</h2>
                <p>This action cannot be undone. Any linked services (PO, Stock, GRN) may be affected.</p>
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

export default Warehouse;
