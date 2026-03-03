import { useState, useEffect } from 'react'
import { Plus, Trash2, Edit2, Package, ShoppingCart, CheckCircle2, Search } from 'lucide-react'

function Warehouse() {
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
            const response = await fetch('/api/warehouses')
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
            const response = await fetch('/api/warehouses/companies')
            const data = await response.json()
            setCompanies(data)
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
        const url = selectedWarehouse ? `/api/warehouses/${selectedWarehouse.id}` : '/api/warehouses'

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
                await fetch(`/api/warehouses/${id}`, { method: 'DELETE' })
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
                    <p style={{ color: 'var(--text-muted)' }}>Manage warehouse locations and inventory links</p>
                </div>
                <button className="btn btn-primary" onClick={() => setShowForm(true)}>
                    <Plus size={20} /> Add New Warehouse
                </button>
            </header>
            {/* rest of UI same as supplier page with variable names switched */}
        </div>
    )
}

export default Warehouse;