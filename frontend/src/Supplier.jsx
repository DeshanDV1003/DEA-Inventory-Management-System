import { useState, useEffect } from 'react'
import { Plus, Trash2, Edit2, Package, ShoppingCart, CheckCircle2, Search } from 'lucide-react'

function Supplier() {
    const [suppliers, setSuppliers] = useState([])
    const [companies, setCompanies] = useState([])                       // available companies for dropdown
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
        fetchCompanies()
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

    const fetchCompanies = async () => {
        try {
            const response = await fetch('/api/suppliers/companies')
            const data = await response.json()
            setCompanies(data)
            // if user is already scoped to a company (e.g. after login), default it
            const userCompanyId = window.USER_COMPANY_ID || ''
            if (userCompanyId) {
                setFormData(prev => ({ ...prev, companyId: userCompanyId }))
            }
        } catch (error) {
            console.error('Error fetching companies:', error)
        }
    }

    // map of id->name for lookup when rendering rows
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
        s.email.toLowerCase().includes(searchTerm.toLowerCase()) ||
        (companyMap[s.companyId] || '').toLowerCase().includes(searchTerm.toLowerCase())
    )

    return (
        <div className="container animate-fade-in">
            {/* existing supplier UI markup unchanged */}
        </div>
    )
}

export default Supplier;