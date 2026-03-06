import React, { useEffect, useMemo, useState } from "react";
import Sidebar from "../components/Sidebar.jsx";
import { 
    getAllSuppliers, 
    createSupplier, 
    updateSupplier, 
    deleteSupplier, 
    getAllCompanies 
} from "../services/api";

const emptyForm = {
    name: "",
    phone: "",
    email: "",
    address: "",
    status: "ACTIVE",
    companyId: ""
};

export default function Suppliers() {
    const [suppliers, setSuppliers] = useState([]);
    const [companies, setCompanies] = useState([]);
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
            const [supRes, compRes] = await Promise.all([
                getAllSuppliers(),
                getAllCompanies()
            ]);
            setSuppliers(supRes.data);
            setCompanies(compRes.data);
        } catch (err) {
            setError("Failed to load suppliers.");
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchData();
    }, []);

    const filtered = useMemo(() => {
        const q = query.toLowerCase();
        return suppliers.filter(s => s.name?.toLowerCase().includes(q) || s.email?.toLowerCase().includes(q));
    }, [suppliers, query]);

    const onSubmit = async (e) => {
        e.preventDefault();
        setSaving(true);
        try {
            if (editing) await updateSupplier(editing.id, form);
            else await createSupplier(form);
            setShowModal(false);
            fetchData();
        } catch (err) {
            setError("Error saving supplier.");
        } finally {
            setSaving(false);
        }
    };

    return (
        <div className="products-root">
            <Sidebar />
            <main className="main-content">
                <header className="page-header">
                    <div>
                        <p className="page-label">PROCUREMENT</p>
                        <h1 className="page-title">Suppliers</h1>
                    </div>
                    <button className="add-btn" onClick={() => { setForm(emptyForm); setEditing(null); setShowModal(true); }}>
                        + Add Supplier
                    </button>
                </header>

                <div className="company-search-row">
                    <div className="company-search-box">
                        <span className="company-search-ic">⌕</span>
                        <input value={query} onChange={(e) => setQuery(e.target.value)} placeholder="Search suppliers..." />
                    </div>
                </div>

                <div className="company-table-wrap">
                    <table className="company-table">
                        <thead>
                            <tr>
                                <th>Supplier Name</th>
                                <th>Contact</th>
                                <th>Parent Company</th>
                                <th>Status</th>
                                <th className="actions-col">Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            {filtered.map((s) => (
                                <tr key={s.id}>
                                    <td className="cell-name">{s.name}</td>
                                    <td>
                                        <div style={{fontSize: '0.85rem'}}>{s.email}</div>
                                        <div style={{fontSize: '0.75rem', color: '#71717a'}}>{s.phone}</div>
                                    </td>
                                    <td>{companies.find(c => c.id === s.companyId)?.name || "General"}</td>
                                    <td><span className={`badge ${s.status === 'ACTIVE' ? 'badge-active' : 'badge-inactive'}`}>{s.status}</span></td>
                                    <td className="actions">
                                        <button className="icon-btn" onClick={() => { setEditing(s); setForm(s); setShowModal(true); }}>✎</button>
                                        <button className="icon-btn danger" onClick={() => setDeleteId(s.id)}>✕</button>
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
                            <h2>{editing ? "Edit Supplier" : "New Supplier"}</h2>
                            <button className="modal-close" onClick={() => setShowModal(false)}>✕</button>
                        </div>
                        <form className="modal-form" onSubmit={onSubmit}>
                            <div className="company-form-grid">
                                <div className="company-field">
                                    <label>SUPPLIER NAME</label>
                                    <input required value={form.name} onChange={e => setForm({...form, name: e.target.value})} />
                                </div>
                                <div className="company-field">
                                    <label>LINKED COMPANY</label>
                                    <select required value={form.companyId} onChange={e => setForm({...form, companyId: e.target.value})}>
                                        <option value="">Select Company</option>
                                        {companies.map(c => <option key={c.id} value={c.id}>{c.name}</option>)}
                                    </select>
                                </div>
                                <div className="company-field">
                                    <label>EMAIL</label>
                                    <input type="email" value={form.email} onChange={e => setForm({...form, email: e.target.value})} />
                                </div>
                                <div className="company-field">
                                    <label>PHONE</label>
                                    <input value={form.phone} onChange={e => setForm({...form, phone: e.target.value})} />
                                </div>
                            </div>
                            <div className="modal-actions">
                                <button type="button" className="company-btn-light" onClick={() => setShowModal(false)}>Cancel</button>
                                <button type="submit" className="company-submit-btn">Save Supplier</button>
                            </div>
                        </form>
                    </div>
                </div>
            )}
        </div>
    );
}