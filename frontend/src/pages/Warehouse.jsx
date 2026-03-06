import React, { useEffect, useMemo, useState } from "react";
import Sidebar from "../components/Sidebar.jsx";
import { 
    getAllWarehouses, 
    createWarehouse, 
    updateWarehouse, 
    deleteWarehouse, 
    getAllCompanies 
} from "../services/api";

const emptyForm = {
    name: "",
    address: "",
    email: "",
    phone: "",
    status: "ACTIVE",
    companyId: ""
};

export default function Warehouse() {
    const [warehouses, setWarehouses] = useState([]);
    const [companies, setCompanies] = useState([]); // Needed for the dropdown
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState("");
    const [query, setQuery] = useState("");
    const [showModal, setShowModal] = useState(false);
    const [saving, setSaving] = useState(false);
    const [editing, setEditing] = useState(null);
    const [form, setForm] = useState(emptyForm);
    const [deleteId, setDeleteId] = useState(null);

    const fetchData = async () => {
        try {
            setLoading(true);
            const [whRes, compRes] = await Promise.all([
                getAllWarehouses(),
                getAllCompanies()
            ]);
            setWarehouses(whRes.data);
            setCompanies(compRes.data);
        } catch (err) {
            setError("Failed to load data. Please ensure services are running.");
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchData();
    }, []);

    const filtered = useMemo(() => {
        const q = query.toLowerCase();
        return warehouses.filter(w => 
            w.name?.toLowerCase().includes(q) || 
            w.address?.toLowerCase().includes(q)
        );
    }, [warehouses, query]);

    const openEdit = (w) => {
        setEditing(w);
        setForm({ ...w, companyId: w.companyId || "" });
        setShowModal(true);
    };

    const onSubmit = async (e) => {
        e.preventDefault();
        setSaving(true);
        try {
            if (editing) await updateWarehouse(editing.id, form);
            else await createWarehouse(form);
            setShowModal(false);
            fetchData();
        } catch (err) {
            setError("Error saving warehouse.");
        } finally {
            setSaving(false);
        }
    };

    const confirmDelete = async () => {
        try {
            await deleteWarehouse(deleteId);
            setDeleteId(null);
            fetchData();
        } catch {
            setError("Failed to delete warehouse.");
        }
    };

    return (
        <div className="products-root">
            <Sidebar />
            <main className="main-content">
                <header className="page-header">
                    <div>
                        <p className="page-label">LOGISTICS</p>
                        <h1 className="page-title">Warehouses</h1>
                    </div>
                    <button className="add-btn" onClick={() => { setForm(emptyForm); setEditing(null); setShowModal(true); }}>
                        + Add Warehouse
                    </button>
                </header>

                <div className="company-search-row">
                    <div className="company-search-box">
                        <span className="company-search-ic">⌕</span>
                        <input value={query} onChange={(e) => setQuery(e.target.value)} placeholder="Search warehouses..." />
                    </div>
                </div>

                <div className="company-table-wrap">
                    <table className="company-table">
                        <thead>
                            <tr>
                                <th>Name</th>
                                <th>Location</th>
                                <th>Company</th>
                                <th>Status</th>
                                <th className="actions-col">Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            {filtered.map((w) => (
                                <tr key={w.id}>
                                    <td className="cell-name">{w.name}</td>
                                    <td>{w.address}</td>
                                    <td>{companies.find(c => c.id === w.companyId)?.name || "N/A"}</td>
                                    <td><span className={`badge ${w.status === 'ACTIVE' ? 'badge-active' : 'badge-inactive'}`}>{w.status}</span></td>
                                    <td className="actions">
                                        <button className="icon-btn" onClick={() => openEdit(w)}>✎</button>
                                        <button className="icon-btn danger" onClick={() => setDeleteId(w.id)}>✕</button>
                                    </td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                </div>
            </main>

            {showModal && (
                <div className="modal-overlay">
                    <div className="modal company-modal">
                        <div className="modal-header">
                            <h2>{editing ? "Edit Warehouse" : "New Warehouse"}</h2>
                            <button className="modal-close" onClick={() => setShowModal(false)}>✕</button>
                        </div>
                        <form className="modal-form" onSubmit={onSubmit}>
                            <div className="company-form-grid">
                                <div className="company-field">
                                    <label>NAME</label>
                                    <input required value={form.name} onChange={e => setForm({...form, name: e.target.value})} />
                                </div>
                                <div className="company-field">
                                    <label>COMPANY</label>
                                    <select required value={form.companyId} onChange={e => setForm({...form, companyId: e.target.value})}>
                                        <option value="">Select Parent Company</option>
                                        {companies.map(c => <option key={c.id} value={c.id}>{c.name}</option>)}
                                    </select>
                                </div>
                                <div className="company-field company-field-full">
                                    <label>ADDRESS</label>
                                    <input value={form.address} onChange={e => setForm({...form, address: e.target.value})} />
                                </div>
                            </div>
                            <div className="modal-actions">
                                <button type="button" className="company-btn-light" onClick={() => setShowModal(false)}>Cancel</button>
                                <button type="submit" className="company-submit-btn">{saving ? "Saving..." : "Save"}</button>
                            </div>
                        </form>
                    </div>
                </div>
            )}
            
            {deleteId && (
                <div className="modal-overlay" onClick={() => setDeleteId(null)}>
                    <div className="modal confirm-modal">
                        <h2>Delete Warehouse?</h2>
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