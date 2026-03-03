import { useState, useEffect } from 'react'
import { Plus, Trash2, Edit2, Package, ShoppingCart, CheckCircle2, Search } from 'lucide-react'

function App() {
    const [suppliers, setSuppliers] = useState([])
    const [loading, setLoading] = useState(true)
    const [searchTerm, setSearchTerm] = useState('')
    const [showForm, setShowForm] = useState(false)
    const [selectedSupplier, setSelectedSupplier] = useState(null)

    // Form State
    const [formData, setFormData] = useState({
        name: '',
        companyId: '',
        phone: '',
        email: '',
        address: '',
        status: 'Active'
    })

    useEffect(() => {
        fetchSuppliers()
    }, [])

    const fetchSuppliers = async () => {
        setLoading(true)
        try {
            const response = await fetch('/api/suppliers')
            const data = await response.json()
            setSuppliers(data)
        } catch (error) {
            console.error('Error fetching suppliers:', error)
        } finally {
            setLoading(false)
        }
    }

    const handleInputChange = (e) => {
        const { name, value } = e.target
        setFormData(prev => ({ ...prev, [name]: value }))
    }

    const handleSubmit = async (e) => {
        e.preventDefault()
        const method = selectedSupplier ? 'PUT' : 'POST'
        const url = selectedSupplier ? `/api/suppliers/${selectedSupplier.id}` : '/api/suppliers'

        try {
            const response = await fetch(url, {
                method,
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(formData)
            })
            if (response.ok) {
                fetchSuppliers()
                resetForm()
            }
        } catch (error) {
            console.error('Error saving supplier:', error)
        }
    }

    const handleEdit = (supplier) => {
        setSelectedSupplier(supplier)
        setFormData({
            name: supplier.name,
            companyId: supplier.companyId,
            phone: supplier.phone,
            email: supplier.email,
            address: supplier.address,
            status: supplier.status
        })
        setShowForm(true)
    }

    const handleDelete = async (id) => {
        if (window.confirm('Are you sure you want to delete this supplier?')) {
            try {
                await fetch(`/api/suppliers/${id}`, { method: 'DELETE' })
                fetchSuppliers()
            } catch (error) {
                console.error('Error deleting supplier:', error)
            }
        }
    }

    const resetForm = () => {
        setShowForm(false)
        setSelectedSupplier(null)
        setFormData({
            name: '',
            companyId: '',
            phone: '',
            email: '',
            address: '',
            status: 'Active'
        })
    }

    const filteredSuppliers = suppliers.filter(s =>
        s.name.toLowerCase().includes(searchTerm.toLowerCase()) ||
        s.email.toLowerCase().includes(searchTerm.toLowerCase())
    )

    return (
        <div className="container animate-fade-in">
            <header style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '3rem' }}>
                <div>
                    <h1 style={{ fontSize: '2.5rem', marginBottom: '0.5rem' }}>Supplier Portal</h1>
                    <p style={{ color: 'var(--text-muted)' }}>Manage your global supply chain and service interactions</p>
                </div>
                <button className="btn btn-primary" onClick={() => setShowForm(true)}>
                    <Plus size={20} /> Add New Supplier
                </button>
            </header>

            {showForm && (
                <div className="glass-card" style={{ marginBottom: '3rem' }}>
                    <h2 style={{ marginBottom: '1.5rem' }}>{selectedSupplier ? 'Edit Supplier' : 'New Supplier Registration'}</h2>
                    <form onSubmit={handleSubmit}>
                        <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fit, minmax(250px, 1fr))', gap: '1.5rem' }}>
                            <div className="form-group">
                                <label>Company Name</label>
                                <input type="text" name="name" className="form-input" value={formData.name} onChange={handleInputChange} required />
                            </div>
                            <div className="form-group">
                                <label>Company ID (Reference)</label>
                                <input type="number" name="companyId" className="form-input" value={formData.companyId} onChange={handleInputChange} required />
                            </div>
                            <div className="form-group">
                                <label>Phone Number</label>
                                <input type="text" name="phone" className="form-input" value={formData.phone} onChange={handleInputChange} />
                            </div>
                            <div className="form-group">
                                <label>Email Address</label>
                                <input type="email" name="email" className="form-input" value={formData.email} onChange={handleInputChange} />
                            </div>
                        </div>
                        <div className="form-group">
                            <label>Office Address</label>
                            <input type="text" name="address" className="form-input" value={formData.address} onChange={handleInputChange} />
                        </div>
                        <div style={{ display: 'flex', gap: '1rem', justifyContent: 'flex-end', marginTop: '1rem' }}>
                            <button type="button" className="btn btn-outline" onClick={resetForm}>Cancel</button>
                            <button type="submit" className="btn btn-primary">Save Changes</button>
                        </div>
                    </form>
                </div>
            )}

            <div className="glass-card">
                <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '2rem' }}>
                    <div style={{ position: 'relative', width: '300px' }}>
                        <Search size={18} style={{ position: 'absolute', left: '12px', top: '50%', transform: 'translateY(-50%)', color: 'var(--text-muted)' }} />
                        <input
                            type="text"
                            className="form-input"
                            placeholder="Search suppliers..."
                            style={{ paddingLeft: '2.5rem' }}
                            value={searchTerm}
                            onChange={(e) => setSearchTerm(e.target.value)}
                        />
                    </div>
                    <div style={{ fontSize: '0.875rem', color: 'var(--text-muted)' }}>
                        Showing {filteredSuppliers.length} of {suppliers.length} results
                    </div>
                </div>

                <table className="data-table">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Supplier Name</th>
                            <th>Contact Info</th>
                            <th>Status</th>
                            <th>Actions</th>
                            <th>Interactions</th>
                        </tr>
                    </thead>
                    <tbody>
                        {filteredSuppliers.map(supplier => (
                            <tr key={supplier.id}>
                                <td>#{supplier.id}</td>
                                <td style={{ fontWeight: '600' }}>{supplier.name}</td>
                                <td>
                                    <div>{supplier.email}</div>
                                    <div style={{ color: 'var(--text-muted)', fontSize: '0.75rem' }}>{supplier.phone}</div>
                                </td>
                                <td>
                                    <span className="badge badge-success">Active</span>
                                </td>
                                <td>
                                    <div style={{ display: 'flex', gap: '1rem' }}>
                                        <Edit2 size={18} style={{ cursor: 'pointer' }} onClick={() => handleEdit(supplier)} />
                                        <Trash2 size={18} style={{ cursor: 'pointer', color: 'var(--error)' }} onClick={() => handleDelete(supplier.id)} />
                                    </div>
                                </td>
                                <td>
                                    <div style={{ display: 'flex', gap: '0.75rem' }}>
                                        <button className="btn btn-outline" title="View Products" style={{ padding: '0.5rem' }}>
                                            <Package size={16} />
                                        </button>
                                        <button className="btn btn-outline" title="Check POs" style={{ padding: '0.5rem' }}>
                                            <ShoppingCart size={16} />
                                        </button>
                                        <button className="btn btn-outline" title="Approve GRN" style={{ padding: '0.5rem', color: 'var(--success)' }}>
                                            <CheckCircle2 size={16} />
                                        </button>
                                    </div>
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table>
                {loading && <div style={{ textAlign: 'center', padding: '2rem' }}>Loading suppliers...</div>}
                {!loading && filteredSuppliers.length === 0 && (
                    <div style={{ textAlign: 'center', padding: '3rem', color: 'var(--text-muted)' }}>
                        No suppliers found matching your search.
                    </div>
                )}
            </div>
        </div>
    )
}

export default App
