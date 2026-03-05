import { useEffect, useMemo, useState } from "react";
import { NavLink, useNavigate } from "react-router-dom";
import { getAllCompanies, addCompany, updateCompany, deleteCompany } from "../services/api";
import "./Products.css";
import "./Company.css";

const emptyForm = {
  companyRegNumber: "",
  name: "",
  logoPath: "",
  address: "",
  phone: "",
  email: "",
  status: "ACTIVE",
};

export default function Company() {
  const navigate = useNavigate();
  const username = localStorage.getItem("username") || "User";

  const [companies, setCompanies] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  const [query, setQuery] = useState("");
  const [showModal, setShowModal] = useState(false);
  const [saving, setSaving] = useState(false);

  const [editing, setEditing] = useState(null); // company object
  const [form, setForm] = useState(emptyForm);

  const [deleteId, setDeleteId] = useState(null);

  const handleLogout = () => {
    localStorage.removeItem("token");
    localStorage.removeItem("username");
    navigate("/login");
  };

  const fetchCompanies = async () => {
    try {
      setLoading(true);
      const res = await getAllCompanies();
      setCompanies(res.data);
    } catch (err) {
      setError("Failed to load companies.");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchCompanies();
  }, []);

  const filtered = useMemo(() => {
    const q = query.trim().toLowerCase();
    if (!q) return companies;
    return companies.filter((c) => {
      const name = (c.name || "").toLowerCase();
      const addr = (c.address || "").toLowerCase();
      const reg = (c.companyRegNumber || "").toLowerCase();
      const email = (c.email || "").toLowerCase();
      return (
        name.includes(q) ||
        addr.includes(q) ||
        reg.includes(q) ||
        email.includes(q)
      );
    });
  }, [companies, query]);

  const openAdd = () => {
    setEditing(null);
    setForm(emptyForm);
    setShowModal(true);
  };

  const openEdit = (c) => {
    setEditing(c);
    setForm({
      companyRegNumber: c.companyRegNumber || "",
      name: c.name || "",
      logoPath: c.logoPath || "",
      address: c.address || "",
      phone: c.phone || "",
      email: c.email || "",
      status: c.status || "ACTIVE",
    });
    setShowModal(true);
  };

  const closeModal = () => {
    if (saving) return;
    setShowModal(false);
    setEditing(null);
    setForm(emptyForm);
  };

  const onSubmit = async (e) => {
    e.preventDefault();
    setError("");
    setSaving(true);

    try {
      if (editing) {
        await updateCompany(editing.id, form);
      } else {
        await addCompany(form);
      }
      closeModal();
      fetchCompanies();
    } catch {
      setError(editing ? "Failed to update company." : "Failed to add company.");
    } finally {
      setSaving(false);
    }
  };

  const confirmDelete = async () => {
    try {
      await deleteCompany(deleteId);
      setDeleteId(null);
      fetchCompanies();
    } catch {
      setError("Failed to delete company.");
    }
  };

  const badgeClass = (status) => {
    const s = (status || "").toUpperCase();
    if (s === "ACTIVE") return "badge badge-active";
    return "badge badge-inactive";
  };

  const formatDate = (iso) => {
    if (!iso) return "—";
    // handles "2026-03-02T07:39:00" etc.
    const d = new Date(iso);
    if (isNaN(d.getTime())) return "—";
    return d.toLocaleDateString();
  };

  return (
    <div className="products-root">
      <aside className="sidebar">
        <div className="sidebar-brand">
          <span className="brand-icon">⬡</span>
          <span>INVNTRY</span>
        </div>
        <nav className="sidebar-nav">
          <NavLink to="/products" className={({ isActive }) => `nav-item${isActive ? " active" : ""}`}>
            <span className="nav-icon">☰</span> Products
          </NavLink>
          <NavLink to="/companies" className={({ isActive }) => `nav-item${isActive ? " active" : ""}`}>
            <span className="nav-icon">▦</span> Companies
          </NavLink>
          <span className="nav-item">
            <span className="nav-icon">⫙</span> Warehouse
          </span>
          <span className="nav-item">
            <span className="nav-icon">☷</span> Suppliers
          </span>
          <span className="nav-item">
            <span className="nav-icon">⊞</span> Reports
          </span>
        </nav>
        <div className="sidebar-footer">
          <div className="user-pill">
            <div className="user-avatar">{username[0].toUpperCase()}</div>
            <span>{username}</span>
          </div>
          <button className="logout-btn" onClick={handleLogout}>
            Sign out →
          </button>
        </div>
      </aside>

      <main className="main-content">
        <header className="page-header">
          <div>
            <p className="page-label">INVENTORY</p>
            <h1 className="page-title">Companies</h1>
          </div>
          <button className="add-btn" onClick={openAdd}>
            + Add Company
          </button>
        </header>

        {error && (
          <div className="alert-error">
            ! {error}
            <button onClick={() => setError("")}>✕</button>
          </div>
        )}

        <div className="company-search-row">
          <div className="company-search-box">
            <span className="company-search-ic">⌕</span>
            <input
              value={query}
              onChange={(e) => setQuery(e.target.value)}
              placeholder="Search companies"
            />
          </div>
        </div>

        {loading ? (
          <div className="loading-state">
            <div className="loader" />
            <p>Loading companies...</p>
          </div>
        ) : filtered.length === 0 ? (
          <div className="empty-state">
            <span className="empty-icon">▦</span>
            <p>No companies found.</p>
          </div>
        ) : (
          <div className="company-table-wrap">
            <table className="company-table">
              <thead>
                <tr>
                  <th>Name</th>
                  <th>Address</th>
                  <th>Status</th>
                  <th>Updated Date</th>
                  <th className="actions-col">Actions</th>
                </tr>
              </thead>
              <tbody>
                {filtered.map((c) => (
                  <tr key={c.id}>
                    <td className="cell-name">{c.name}</td>
                    <td>{c.address || "—"}</td>
                    <td>
                      <span className={badgeClass(c.status)}>
                        {(c.status || "—").toUpperCase()}
                      </span>
                    </td>
                    <td>{formatDate(c.updatedDate || c.createdDate)}</td>
                    <td className="actions">
                      <button className="icon-btn" title="Edit" onClick={() => openEdit(c)}>
                        ✎
                      </button>
                      <button className="icon-btn danger" title="Delete" onClick={() => setDeleteId(c.id)}>
                        ✕
                      </button>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        )}
      </main>

      {/* Add / Edit Modal */}
      {showModal && (
        <div className="modal-overlay" onClick={closeModal}>
          <div className="modal company-modal" onClick={(e) => e.stopPropagation()}>
            <div className="modal-header">
              <h2>{editing ? "Update Company" : "New Company"}</h2>
              <button className="modal-close" onClick={closeModal}>✕</button>
            </div>

            <form className="modal-form" onSubmit={onSubmit}>
              <div className="company-form-grid">
                <div className="company-field">
                  <label>REG NUMBER</label>
                  <input
                    value={form.companyRegNumber}
                    onChange={(e) => setForm({ ...form, companyRegNumber: e.target.value })}
                    placeholder="e.g. CR001"
                  />
                </div>

                <div className="company-field">
                  <label>COMPANY NAME</label>
                  <input
                    required
                    value={form.name}
                    onChange={(e) => setForm({ ...form, name: e.target.value })}
                    placeholder="e.g. TechCorp"
                  />
                </div>

                <div className="company-field">
                  <label>ADDRESS</label>
                  <input
                    value={form.address}
                    onChange={(e) => setForm({ ...form, address: e.target.value })}
                    placeholder="e.g. Seaview Street"
                  />
                </div>

                <div className="company-field">
                  <label>STATUS</label>
                  <select
                    value={form.status}
                    onChange={(e) => setForm({ ...form, status: e.target.value })}
                  >
                    <option value="ACTIVE">ACTIVE</option>
                    <option value="INACTIVE">INACTIVE</option>
                  </select>
                </div>

                <div className="company-field">
                  <label>PHONE</label>
                  <input
                    value={form.phone}
                    onChange={(e) => setForm({ ...form, phone: e.target.value })}
                    placeholder="e.g. 0771234567"
                  />
                </div>

                <div className="company-field">
                  <label>EMAIL</label>
                  <input
                    type="email"
                    value={form.email}
                    onChange={(e) => setForm({ ...form, email: e.target.value })}
                    placeholder="e.g. abc@mail.com"
                  />
                </div>

                <div className="company-field company-field-full">
                  <label>LOGO PATH</label>
                  <input
                    value={form.logoPath}
                    onChange={(e) => setForm({ ...form, logoPath: e.target.value })}
                    placeholder="/logos/company.png"
                  />
                </div>
              </div>

              <div className="modal-actions">
                <button type="button" className="company-btn-light" onClick={closeModal}>
                  Cancel
                </button>
                <button type="submit" className="company-submit-btn" disabled={saving}>
                  {saving ? "Saving..." : editing ? "Update Company" : "Add Company"}
                </button>
              </div>
            </form>
          </div>
        </div>
      )}

      {/* Delete Confirm */}
      {deleteId && (
        <div className="modal-overlay" onClick={() => setDeleteId(null)}>
          <div className="modal confirm-modal" onClick={(e) => e.stopPropagation()}>
            <h2>Delete Company?</h2>
            <p>This action cannot be undone.</p>
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