import { useState, useEffect } from 'react'
import { Plus, Trash2, Edit2, Package, ShoppingCart, CheckCircle2, Search } from 'lucide-react'

function WarehousePage() {
    const [warehouses, setWarehouses] = useState([])
    const [companies, setCompanies] = useState([])
    const [loading, setLoading] = useState(true)
    const [searchTerm, setSearchTerm] = useState('')
    const [showForm, setShowForm] = useState(false)
    const [selectedWarehouse, setSelectedWarehouse] = useState(null)

    const [formData, setFormData] = useState({
        name: '',
        companyId: '',
        phone: '',
        email: '',
        address: '',
        status: 'Active'
    })

    useEffect(() => {
        fetchWarehouses()
        fetchCompanies()
    }, [])

    const fetchWarehouses = async () => {
        setLoading(true)
        try {
            const response = await fetch('/api/v1/warehouses')
            const data = await response.json()
            setWarehouses(data)
        } catch (error) {
            console.error('Error fetching warehouses:', error)
        } finally {
            setLoading(false)
        }
    }

    const fetchCompanies = async () => {
        try {
            const response = await fetch('/api/v1/warehouses/companies')
            const data = await response.json()
            setCompanies(data)
            const userCompanyId = window.USER_COMPANY_ID || ''
            if (userCompanyId) {
                setFormData(prev => ({ ...prev, companyId: userCompanyId }))
            }
        } catch (error) {
            console.error('Error fetching companies:', error)
        }
    }

    const companyMap = companies.reduce((acc, c) => {
        acc[c.id] = c.name
        return acc
    }, {})

    const handleInputChange = (e) => {
        const { name, value } = e.target
        setFormData(prev => ({ ...prev, [name]: value }))
    }

    const handleSubmit = async (e) => {
        e.preventDefault()
        const method = selectedWarehouse ? 'PUT' : 'POST'
        const url = selectedWarehouse ? `/api/v1/warehouses/${selectedWarehouse.id}` : '/api/v1/warehouses'

        try {
            const response = await fetch(url, {
                method,
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(formData)
            })
            if (response.ok) {
                fetchWarehouses()
                resetForm()
            }
        } catch (error) {
            console.error('Error saving warehouse:', error)
        }
    }

    const handleEdit = (warehouse) => {
        setSelectedWarehouse(warehouse)
        setFormData({
            name: warehouse.name,
            companyId: warehouse.companyId,
            phone: warehouse.phone,
            email: warehouse.email,
            address: warehouse.address,
            status: warehouse.status
        })
        setShowForm(true)
    }

    const handleDelete = async (id) => {
        if (window.confirm('Are you sure you want to delete this warehouse?')) {
            try {
                await fetch(`/api/v1/warehouses/${id}`, { method: 'DELETE' })
                fetchWarehouses()
            } catch (error) {
                console.error('Error deleting warehouse:', error)
            }
        }
    }

    const resetForm = () => {
        setShowForm(false)
        setSelectedWarehouse(null)
        setFormData({
            name: '',
            companyId: '',
            phone: '',
            email: '',
            address: '',
            status: 'Active'
        })
    }

    const filtered = warehouses.filter(w =>
        w.name.toLowerCase().includes(searchTerm.toLowerCase()) ||
        w.email.toLowerCase().includes(searchTerm.toLowerCase()) ||
        (companyMap[w.companyId] || '').toLowerCase().includes(searchTerm.toLowerCase())
    )

    return (
        <div className="container animate-fade-in">
            <header style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '3rem' }}>
                <div>
                    <h1 style={{ fontSize: '2.5rem', marginBottom: '0.5rem' }}>Warehouse Portal</h1>
                    <p style={{ color: 'var(--text-muted)' }}>Keep your storage locations under control</p>
                </div>
                <button className="btn btn-primary" onClick={() => setShowForm(true)}>
                    <Plus size={20} /> Add New Warehouse
                </button>
            </header>

            {showForm && (
                <div className="glass-card" style={{ marginBottom: '3rem' }}>
                    <h2 style={{ marginBottom: '1.5rem' }}>{selectedWarehouse ? 'Edit Warehouse' : 'New Warehouse Registration'}</h2>
                    <form onSubmit={handleSubmit}>
                        <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fit, minmax(250px, 1fr))', gap: '1.5rem' }}>
                            <div className="form-group">
                                <label>Warehouse Name</label>
                                <input type="text" name="name" className="form-input" value={formData.name} onChange={handleInputChange} required />
                            </div>
                            <div className="form-group">
                                <label>Company</label>
                                <select name="companyId" className="form-input" value={formData.companyId} onChange={handleInputChange} required>
                                    <option value="">Select a company</option>
                                    {companies.map(c => (
                                        <option key={c.id} value={c.id}>{c.name}</option>
                                    ))}
                                </select>
                            </div>
                            <div className="form-group">
                                <label>Company ID (Reference)</label>
                                <input type="text" className="form-input" value={formData.companyId} readOnly />
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
                            <label>Address</label>
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
                            placeholder="Search warehouses..."
                            style={{ paddingLeft: '2.5rem' }}
                            value={searchTerm}
                            onChange={(e) => setSearchTerm(e.target.value)}
                        />
                    </div>
                    <div style={{ fontSize: '0.875rem', color: 'var(--text-muted)' }}>
                        Showing {filtered.length} of {warehouses.length} results
                    </div>
                </div>

                {loading ? (
                    <p>Loading...</p>
                ) : (
                    <table className="table">
                        <thead>
                            <tr>
                                <th>Name</th>
                                <th>Company</th>
                                <th>Phone</th>
                                <th>Email</th>
                                <th>Status</th>
                                <th style={{ width: '120px' }}>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            {filtered.map(w => (
                                <tr key={w.id}>
                                    <td>{w.name}</td>
                                    <td>{companyMap[w.companyId]}</td>
                                    <td>{w.phone}</td>
                                    <td>{w.email}</td>
                                    <td>{w.status}</td>
                                    <td>
                                        <Edit2 size={16} className="icon-button" onClick={() => handleEdit(w)} />
                                        <Trash2 size={16} className="icon-button" onClick={() => handleDelete(w.id)} />
                                    </td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                )}
            </div>
        </div>
    )
}

export default WarehousePage
