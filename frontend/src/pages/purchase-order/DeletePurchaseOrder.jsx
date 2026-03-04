/**
 * DeletePurchaseOrder Component
 *
 * A comprehensive form used to generate a new Purchase Order with multiple line items.
 *
 * Purpose:
 * - Aggregates data from multiple microservices (Company, Supplier, Warehouse, Product)
 *   to provide dropdown selections.
 * - Manages a dynamic list of order items (add/remove functionality).
 * - Submits a complex JSON object containing both header and item data to the PO Service.
 *
 * State Variables:
 * - formData (Object): Holds the header data and an array of items.
 * - companies/suppliers/warehouses/products (Arrays): Store data fetched from external services
 *   to populate dropdown menus.
 *
 * Key Logic:
 * - handleItemChange: Updates specific product IDs or quantities within the items array.
 * - fetchDropdowns: Executes parallel API calls to external ports (8082, 8083, etc.)
 *   on component mount.
 *
 * API Interaction:
 * - POST /api/v1/purchase-orders (Submit)
 * - External GETs: Fetches from Product, Supplier, and Warehouse microservices.
 */

import React, { useState, useEffect } from 'react';
import { purchaseOrderService } from '../../services/purchaseOrderService';
// import api from '../../services/api';
import { useNavigate } from 'react-router-dom';

const DeletePurchaseOrder = () => {
    const navigate = useNavigate();

    // Initializing form with one empty item row
    const [formData, setFormData] = useState({
        companyId: '',
        supplierId: '',
        warehouseId: '',
        poNumber: '',
        items: [{ productId: '', quantity: 1 }]
    });

    const [companies, setCompanies] = useState([]);
    const [suppliers, setSuppliers] = useState([]);
    const [warehouses, setWarehouses] = useState([]);
    const [products, setProducts] = useState([]);

    useEffect(() => {
        const fetchDropdowns = async () => {
            try {
                // Fetching from other microservices ports
                const resComp = await api.get('http://localhost:8084/api/v1/companies');
                const resSupp = await api.get('http://localhost:8083/api/v1/suppliers');
                const resWh = await api.get('http://localhost:8085/api/v1/warehouses');
                const resProd = await api.get('http://localhost:8082/api/v1/products');

                setCompanies(resComp.data);
                setSuppliers(resSupp.data);
                setWarehouses(resWh.data);
                setProducts(resProd.data);
            } catch (err) {
                console.error("Failed to load dropdown data. Check if other services are up.");
            }
        };
        fetchDropdowns();
    }, []);

    const handleItemChange = (index, field, value) => {
        const newItems = [...formData.items];
        newItems[index][field] = value;
        setFormData({ ...formData, items: newItems });
    };

    const addItem = () => {
        setFormData({ ...formData, items: [...formData.items, { productId: '', quantity: 1 }] });
    };

    const removeItem = (index) => {
        const newItems = formData.items.filter((_, i) => i !== index);
        setFormData({ ...formData, items: newItems });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            await purchaseOrderService.create(formData);
            alert("Order Created Successfully!");
            navigate('/purchase-orders');
        } catch (error) {
            alert("Error: Ensure all fields are valid and PO number is unique.");
        }
    };

    return (
        <div className="form-container">
            <h3>Create New Purchase Order</h3>
            <form onSubmit={handleSubmit}>

                <div className="form-group">
                    <label>Company</label>
                    <select required value={formData.companyId} onChange={e => setFormData({...formData, companyId: e.target.value})}>
                        <option value="">-- Select Company --</option>
                        {companies.map(c => <option key={c.id} value={c.id}>{c.name}</option>)}
                    </select>
                </div>

                <div className="form-group">
                    <label>Supplier</label>
                    <select required value={formData.supplierId} onChange={e => setFormData({...formData, supplierId: e.target.value})}>
                        <option value="">-- Select Supplier --</option>
                        {suppliers.map(s => <option key={s.id} value={s.id}>{s.name}</option>)}
                    </select>
                </div>

                <div className="form-group">
                    <label>Warehouse</label>
                    <select required value={formData.warehouseId} onChange={e => setFormData({...formData, warehouseId: e.target.value})}>
                        <option value="">-- Select Warehouse --</option>
                        {warehouses.map(w => <option key={w.id} value={w.name}>{w.name}</option>)}
                    </select>
                </div>

                <div className="form-group">
                    <label>PO Number</label>
                    <input type="number" required value={formData.poNumber} onChange={e => setFormData({...formData, poNumber: e.target.value})} />
                </div>

                <hr />
                <h4>Order Items</h4>
                {formData.items.map((item, index) => (
                    <div key={index} className="item-row">
                        <select required value={item.productId} onChange={e => handleItemChange(index, 'productId', e.target.value)}>
                            <option value="">Select Product</option>
                            {products.map(p => <option key={p.id} value={p.id}>{p.name}</option>)}
                        </select>

                        <input type="number" min="1" placeholder="Qty" value={item.quantity} onChange={e => handleItemChange(index, 'quantity', e.target.value)} />

                        {formData.items.length > 1 && (
                            <button type="button" onClick={() => removeItem(index)}>Remove</button>
                        )}
                    </div>
                ))}

                <button type="button" className="btn-add" onClick={addItem}>+ Add Item</button>
                <hr />
                <div className="actions">
                    <button type="button" onClick={() => navigate('/purchase-orders')}>Cancel</button>
                    <button type="submit" className="btn-submit">Save Order</button>
                </div>
            </form>
        </div>
    );
};

export default DeletePurchaseOrder;