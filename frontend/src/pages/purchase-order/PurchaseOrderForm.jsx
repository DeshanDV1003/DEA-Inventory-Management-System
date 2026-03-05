import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import Sidebar from "../../components/Sidebar.jsx";
import { purchaseOrderService } from '../../services/purchaseOrderService';
import { getAllProducts, getAllCompanies, getAllSuppliers, getAllWarehouses } from '../../services/api';

// --- DUMMY DATA FOR TESTING (FALLBACKS) ---
const FALLBACK_DATA = {
    companies: [
        { id: 1, name: "Global Logistics Corp" },
        { id: 2, name: "Tech Manufacturing Ltd" }
    ],
    suppliers: [
        { id: 101, name: "Primary Steel Inc", companyId: 1 },
        { id: 102, name: "Parts & Tools Co", companyId: 1 },
        { id: 103, name: "Silicon Valley Chips", companyId: 2 }
    ],
    warehouses: [
        { id: 50, name: "North-East Hub", companyId: 1 },
        { id: 51, name: "Main Export Port", companyId: 1 },
        { id: 60, name: "Tech Cleanroom A", companyId: 2 }
    ],
    products: [
        { id: 501, name: "Industrial Girders" },
        { id: 502, name: "Microchips V8" }
    ]
};

const PurchaseOrderForm = ({ mode }) => {
    const { id } = useParams();
    const navigate = useNavigate();

    const [formData, setFormData] = useState({
        companyId: '',
        supplierId: '',
        warehouseId: '',
        poNumber: '',
        items: [{ productId: '', quantity: 1 }]
    });

    // MASTER LISTS (All data from API)
    const [masterLists, setMasterLists] = useState({
        companies: [],
        suppliers: [],
        warehouses: [],
        products: []
    });

    // FILTERED LISTS (Only what the user should see based on company selection)
    const [filteredSuppliers, setFilteredSuppliers] = useState([]);
    const [filteredWarehouses, setFilteredWarehouses] = useState([]);

    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);

    useEffect(() => {
        loadInitialData();
    }, [id, mode]);

    // const loadInitialData = async () => {
    //     setLoading(true);
    //     try {
    //         const [c, s, w, p] = await Promise.all([
    //             getAllCompanies().catch(() => ({ data: FALLBACK_DATA.companies })),
    //             getAllSuppliers().catch(() => ({ data: FALLBACK_DATA.suppliers })),
    //             getAllWarehouses().catch(() => ({ data: FALLBACK_DATA.warehouses })),
    //             getAllProducts().catch(() => ({ data: FALLBACK_DATA.products }))
    //         ]);

    //         setMasterLists({
    //             companies: c.data,
    //             suppliers: s.data,
    //             warehouses: w.data,
    //             products: p.data
    //         });

    //         if (mode === 'edit' && id) {
    //             const res = await purchaseOrderService.getById(id);
    //             setFormData(res.data);
    //             // Trigger filtering for edit mode
    //             filterDependentData(res.data.companyId, s.data, w.data);
    //         }
    //     } catch (err) {
    //         setError("Serious system error. Could not load data.");
    //     } finally {
    //         setLoading(false);
    //     }
    // };

    // --- Filtering Logic ---
    
    const loadInitialData = async () => {
    setLoading(true);
    try {
        const [c, s, w, p] = await Promise.all([
            getAllCompanies().catch(() => ({ data: FALLBACK_DATA.companies })),
            getAllSuppliers().catch(() => ({ data: FALLBACK_DATA.suppliers })),
            getAllWarehouses().catch(() => ({ data: FALLBACK_DATA.warehouses })),
            getAllProducts().catch(() => ({ data: FALLBACK_DATA.products }))
        ]);

        // ADD LOGS TO SEE WHAT IS ACTUALLY COMING BACK
        console.log("Companies received:", c.data);

        setMasterLists({
            // Use logical OR to ensure it's always an array
            companies: Array.isArray(c.data) ? c.data : [],
            suppliers: Array.isArray(s.data) ? s.data : [],
            warehouses: Array.isArray(w.data) ? w.data : [],
            products: Array.isArray(p.data) ? p.data : []
        });

        // ... rest of your code
    } catch (err) {
        console.error("Initialization error:", err);
        setError("Failed to load data.");
    } finally {
        setLoading(false);
    }
};
    
    const filterDependentData = (companyId, suppliers, warehouses) => {
        const selectedId = parseInt(companyId);
        setFilteredSuppliers(suppliers.filter(s => s.companyId === selectedId));
        setFilteredWarehouses(warehouses.filter(w => w.companyId === selectedId));
    };

    const handleCompanyChange = (e) => {
        const companyId = e.target.value;
        // 1. Update form
        setFormData({ 
            ...formData, 
            companyId: companyId,
            supplierId: '', // Reset these so user doesn't pick wrong combos
            warehouseId: '' 
        });
        // 2. Filter lists
        filterDependentData(companyId, masterLists.suppliers, masterLists.warehouses);
    };

    // --- Dynamic Item Logic ---
    const handleItemChange = (index, field, value) => {
        const newItems = [...formData.items];
        newItems[index][field] = value;
        setFormData({ ...formData, items: newItems });
    };

    const addItem = () => setFormData({ ...formData, items: [...formData.items, { productId: '', quantity: 1 }] });
    const removeItem = (index) => setFormData({ ...formData, items: formData.items.filter((_, i) => i !== index) });

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            if (mode === 'create') await purchaseOrderService.create(formData);
            else await purchaseOrderService.update(id, formData);
            navigate('/purchase-order/list');
        } catch (err) {
            setError("Failed to save order. Check database constraints.");
        }
    };

    return (
        <div className="products-root">
            <Sidebar />
            <div className="modal-overlay">
                <div className="modal" style={{ maxWidth: '700px', width: '90%' }}>
                    <header className="modal-header">
                        <h2>{mode === 'edit' ? `Edit Order #${formData.poNumber}` : 'Create Purchase Order'}</h2>
                    </header>

                    <form className="modal-form" onSubmit={handleSubmit}>
                        {/* Header Row 1 */}
                        <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '1.5rem' }}>
                            <div className="field-group">
                                <label>PO NUMBER</label>
                                <input type="number" value={formData.poNumber} onChange={e => setFormData({...formData, poNumber: e.target.value})} required />
                            </div>

                            <div className="field-group">
                                <label>COMPANY</label>
                                <select value={formData.companyId} onChange={handleCompanyChange} required>
                                    <option value="">Select Company</option>
                                    {masterLists.companies.map(c => <option key={c.id} value={c.id}>{c.name}</option>)}
                                </select>
                            </div>
                        </div>

                        {/* Header Row 2: Filtered Dropdowns */}
                        <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '1.5rem' }}>
                            <div className="field-group">
                                <label>SUPPLIER (Filtered)</label>
                                <select 
                                    value={formData.supplierId} 
                                    onChange={e => setFormData({...formData, supplierId: e.target.value})} 
                                    required 
                                    disabled={!formData.companyId}
                                >
                                    <option value="">{formData.companyId ? "Select Supplier" : "Select Company First"}</option>
                                    {filteredSuppliers.map(s => <option key={s.id} value={s.id}>{s.name}</option>)}
                                </select>
                            </div>

                            <div className="field-group">
                                <label>WAREHOUSE (Filtered)</label>
                                <select 
                                    value={formData.warehouseId} 
                                    onChange={e => setFormData({...formData, warehouseId: e.target.value})} 
                                    required
                                    disabled={!formData.companyId}
                                >
                                    <option value="">{formData.companyId ? "Select Warehouse" : "Select Company First"}</option>
                                    {filteredWarehouses.map(w => <option key={w.id} value={w.id}>{w.name}</option>)}
                                </select>
                            </div>
                        </div>

                        <hr style={{ borderColor: '#1e1e24', margin: '1rem 0' }} />

                        {/* Items Section */}
                        <label>ORDER ITEMS</label>
                        {formData.items.map((item, index) => (
                            <div key={index} style={{ display: 'flex', gap: '10px', marginBottom: '10px' }}>
                                <select style={{ flex: 3 }} value={item.productId} onChange={e => handleItemChange(index, 'productId', e.target.value)} required>
                                    <option value="">Select Product</option>
                                    {masterLists.products.map(p => <option key={p.id} value={p.id}>{p.name}</option>)}
                                </select>
                                <input style={{ flex: 1 }} type="number" min="1" value={item.quantity} onChange={e => handleItemChange(index, 'quantity', e.target.value)} required />
                                {formData.items.length > 1 && <button type="button" className="del-btn" onClick={() => removeItem(index)}>Remove</button>}
                            </div>
                        ))}

                        <button type="button" className="btn-add" onClick={addItem}>+ Add Item</button>

                        <div className="modal-actions">
                            <button type="button" className="cancel-btn" onClick={() => navigate('/purchase-order/list')}>Cancel</button>
                            <button type="submit" className="submit-btn">Save Order</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    );
};

export default PurchaseOrderForm;