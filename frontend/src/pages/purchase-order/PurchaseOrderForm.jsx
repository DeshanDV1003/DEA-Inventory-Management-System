/**
 * PurchaseOrderForm Component
 *
 * This component provides a unified interface for both creating and editing
 * Purchase Orders in the Inventory Management System.
 *
 * Purpose:
 * - Create Mode: Initializes an empty form with one item row.
 * - Edit Mode: Fetches existing data from the backend to pre-populate the form.
 * - Dynamic Form Logic: Allows users to add or remove multiple product line items.
 * - Data Integration: Aggregates information from Product, Supplier, and Company
 *   microservices to populate dropdown menus.
 *
 * Logic Workflow:
 * 1. On mount: Fetches all dropdown data from external service ports (8082, 8083, 8084).
 * 2. If 'edit': Fetches the specific PO record from port 8081.
 * 3. Submission: Validates local state and sends a structured JSON payload
 *    (Header + List of Items) to the PO Service.
 *
 * Variables:
 * - id: The URL parameter used to identify the record in Edit mode.
 * - formData: State object containing companyId, supplierId, warehouseId, poNumber, and items list.
 * - dropdowns: State object containing lists for Companies, Suppliers, and Products.
 */

import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import axios from 'axios';
import { purchaseOrderService } from '../../services/purchaseOrderService';

const PurchaseOrderForm = ({ mode }) => {
    const { id } = useParams();
    const navigate = useNavigate();

    // Helper to include JWT token in cross-service axios calls
    const authHeader = () => ({
        headers: { Authorization: `Bearer ${localStorage.getItem("token")}` },
    });

    // Form State
    const [formData, setFormData] = useState({
        companyId: '',
        supplierId: '',
        warehouseId: '',
        poNumber: '',
        items: [{ productId: '', quantity: 1 }]
    });

    // Lists for dropdown selections
    const [dropdowns, setDropdowns] = useState({
        companies: [],
        suppliers: [],
        products: []
    });

    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);

    useEffect(() => {
        loadInitialData();
    }, [id, mode]);

    const loadInitialData = async () => {
        setLoading(true);
        try {
            // 1. Load Dropdowns from other Microservices
            const [c, s, p] = await Promise.all([
                axios.get('http://localhost:8084/api/v1/companies', authHeader()),
                axios.get('http://localhost:8083/api/v1/suppliers', authHeader()),
                axios.get('http://localhost:8082/api/v1/products', authHeader())
            ]);

            setDropdowns({
                companies: c.data,
                suppliers: s.data,
                products: p.data
            });

            // 2. Load existing data if in Edit Mode
            if (mode === 'edit' && id) {
                const res = await purchaseOrderService.getById(id);
                setFormData(res.data);
            }
        } catch (err) {
            setError("Failed to initialize form. Please check if all services are running.");
            console.error(err);
        } finally {
            setLoading(false);
        }
    };

    // --- Dynamic Item Logic ---

    const handleItemChange = (index, field, value) => {
        const newItems = [...formData.items];
        newItems[index][field] = value;
        setFormData({ ...formData, items: newItems });
    };

    const addItem = () => {
        setFormData({
            ...formData,
            items: [...formData.items, { productId: '', quantity: 1 }]
        });
    };

    const removeItem = (index) => {
        const newItems = formData.items.filter((_, i) => i !== index);
        setFormData({ ...formData, items: newItems });
    };

    // --- Form Submission ---

    const handleSubmit = async (e) => {
        e.preventDefault();
        setLoading(true);
        try {
            if (mode === 'create') {
                await purchaseOrderService.create(formData);
            } else {
                await purchaseOrderService.update(id, formData);
            }
            navigate('/purchase-order/list');
        } catch (err) {
            setError(err.response?.data?.message || "An error occurred while saving.");
        } finally {
            setLoading(false);
        }
    };

    if (loading && mode === 'edit') return <div className="loader"></div>;

    return (
        <div className="modal-overlay">
            <div className="modal" style={{ maxWidth: '700px', width: '90%' }}>
                <header className="modal-header">
                    <h2>{mode === 'edit' ? `Edit Order #${formData.poNumber}` : 'Create Purchase Order'}</h2>
                    <button className="modal-close" onClick={() => navigate('/purchase-order/list')}>&times;</button>
                </header>

                {error && <div className="alert-error">{error} <button onClick={() => setError(null)}>&times;</button></div>}

                <form className="modal-form" onSubmit={handleSubmit}>

                    {/* Header Details */}
                    <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '1.5rem' }}>
                        <div className="field-group">
                            <label>PO NUMBER</label>
                            <input
                                type="number"
                                value={formData.poNumber}
                                onChange={e => setFormData({...formData, poNumber: e.target.value})}
                                required
                                placeholder="e.g. 5001"
                            />
                        </div>

                        <div className="field-group">
                            <label>DESTINATION WAREHOUSE</label>
                            <input
                                type="text"
                                value={formData.warehouseId}
                                onChange={e => setFormData({...formData, warehouseId: e.target.value})}
                                required
                                placeholder="Warehouse Name/ID"
                            />
                        </div>
                    </div>

                    <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '1.5rem' }}>
                        <div className="field-group">
                            <label>COMPANY</label>
                            <select
                                value={formData.companyId}
                                onChange={e => setFormData({...formData, companyId: e.target.value})}
                                required
                            >
                                <option value="">Select Company</option>
                                {dropdowns.companies.map(c => <option key={c.id} value={c.id}>{c.name}</option>)}
                            </select>
                        </div>

                        <div className="field-group">
                            <label>SUPPLIER</label>
                            <select
                                value={formData.supplierId}
                                onChange={e => setFormData({...formData, supplierId: e.target.value})}
                                required
                            >
                                <option value="">Select Supplier</option>
                                {dropdowns.suppliers.map(s => <option key={s.id} value={s.id}>{s.name}</option>)}
                            </select>
                        </div>
                    </div>

                    <hr style={{ borderColor: '#1e1e24', margin: '1rem 0' }} />

                    {/* Dynamic Items List */}
                    <label style={{ fontSize: '0.65rem', letterSpacing: '0.15em', color: '#71717a' }}>ORDER ITEMS</label>
                    <div style={{ maxHeight: '250px', overflowY: 'auto', paddingRight: '5px' }}>
                        {formData.items.map((item, index) => (
                            <div key={index} className="item-row" style={{ display: 'flex', gap: '10px', marginBottom: '10px' }}>
                                <div style={{ flex: 2 }}>
                                    <select
                                        value={item.productId}
                                        onChange={e => handleItemChange(index, 'productId', e.target.value)}
                                        required
                                    >
                                        <option value="">Select Product</option>
                                        {dropdowns.products.map(p => <option key={p.id} value={p.id}>{p.name}</option>)}
                                    </select>
                                </div>
                                <div style={{ flex: 1 }}>
                                    <input
                                        type="number"
                                        min="1"
                                        value={item.quantity}
                                        onChange={e => handleItemChange(index, 'quantity', e.target.value)}
                                        required
                                    />
                                </div>
                                {formData.items.length > 1 && (
                                    <button type="button" className="del-btn" onClick={() => removeItem(index)}>Remove</button>
                                )}
                            </div>
                        ))}
                    </div>

                    <button type="button" className="btn-add" onClick={addItem}>
                        + Add Another Item
                    </button>

                    <div className="modal-actions">
                        <button type="button" className="cancel-btn" onClick={() => navigate('/purchase-order/list')}>
                            Cancel
                        </button>
                        <button type="submit" className="submit-btn" disabled={loading}>
                            {loading ? <span className="spinner"></span> : (mode === 'edit' ? 'Update Order' : 'Create Order')}
                        </button>
                    </div>
                </form>
            </div>
        </div>
    );
};

export default PurchaseOrderForm;