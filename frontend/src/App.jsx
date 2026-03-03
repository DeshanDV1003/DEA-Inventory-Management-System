import { useState } from 'react'
import Supplier from './Supplier'
import Warehouse from './Warehouse'

function App() {
    const [view, setView] = useState('suppliers') // 'suppliers' or 'warehouses'

    return (
        <div>
            <nav style={{ padding: '1rem', background: '#fafafa', borderBottom: '1px solid #ddd' }}>
                <button onClick={() => setView('suppliers')} style={{ marginRight: '1rem' }}>Suppliers</button>
                <button onClick={() => setView('warehouses')}>Warehouses</button>
            </nav>
            {view === 'suppliers' ? <Supplier /> : <Warehouse />}
        </div>
    )
}
                </div>

                <table className="data-table">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Supplier Name</th>
                            <th>Company</th>
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
                                <td>{companyMap[supplier.companyId] || supplier.companyId}</td>
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
