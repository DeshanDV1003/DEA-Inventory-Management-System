/**
 * PurchaseOrderList Component
 *
 * Features:
 * - Fallback Logic: Uses DUMMY_ORDERS if the backend service is unreachable.
 * - Expandable Detail: Click a row to see individual product line items.
 * - Inline Status: Rapid status updates via dropdown.
 *
 * Logic:
 * - useEffect triggers loadOrders on mount.
 * - If API returns empty or throws error, state is set to dummy values for testing.
 */
import React, {useCallback, useEffect, useState} from 'react';
import { purchaseOrderService } from '../../services/purchaseOrderService';
import { useNavigate } from 'react-router-dom';
import Sidebar from "../../components/Sidebar.jsx";

// TEST DATA: Used when Backend is empty or disconnected
const DUMMY_ORDERS = [
    {
        id: 1,
        poNumber: 5001,
        warehouseId: "MAIN-WH-COLOMBO",
        status: "CREATED",
        date: "2026-03-04T10:00:00",
        items: [
            { productId: 101, quantity: 50 },
            { productId: 102, quantity: 50 },
            { productId: 103, quantity: 50 },
            { productId: 104, quantity: 50 },
            { productId: 105, quantity: 12 }
        ]
    },
    {
        id: 2,
        poNumber: 5002,
        warehouseId: "KANDY-LOGISTICS-HUB",
        status: "APPROVED",
        date: "2026-03-04T11:30:00",
        items: [
            { productId: 202, quantity: 100 }
        ]
    },
    {
        id: 3,
        poNumber: 5002,
        warehouseId: "KANDY-LOGISTICS-HUB",
        status: "APPROVED",
        date: "2026-03-04T11:30:00",
        items: [
            { productId: 202, quantity: 100 }
        ]
    },
    {
        id: 4,
        poNumber: 5002,
        warehouseId: "KANDY-LOGISTICS-HUB",
        status: "REJECTED",
        date: "2026-03-04T11:30:00",
        items: [
            { productId: 202, quantity: 100 }
        ]
    }
];

const PurchaseOrderList = () => {
    const [orders, setOrders] = useState([]);
    const [expandedId, setExpandedId] = useState(null);
    const [loading, setLoading] = useState(true);
    const navigate = useNavigate();

    // Using useCallback to prevent "cascading renders" warning
    const loadOrders = useCallback(async () => {
        setLoading(true);
        try {
            const response = await purchaseOrderService.getAll();
            if (response.data && response.data.length > 0) {
                setOrders(response.data);
            } else {
                setOrders(DUMMY_ORDERS);
            }
        } catch (error) {
            console.error("Connection failed. Showing dummy data.", error);
            setOrders(DUMMY_ORDERS);
        } finally {
            setLoading(false);
        }
    }, []);

    useEffect(() => {
        // Handling the promise to fix "Promise returned is ignored"
        loadOrders().catch(console.error);
    }, [loadOrders]);

    const handleStatusChange = async (id, newStatus) => {
        try {
            await purchaseOrderService.updateStatus(id, newStatus);
            await loadOrders(); // Correctly awaiting the async call
        } catch (err) {
            console.error("Status update failed", err);
            alert("Failed to update status on server.");
        }
    };

    const toggleExpand = (id) => {
        setExpandedId(expandedId === id ? null : id);
    };

    return (
        <div className="products-root">
            <Sidebar />
            <main className="main-content">
                <header className="page-header">
                    <div>
                        <p className="page-label">LOGISTICS MODULE</p>
                        <h1 className="page-title">Purchase Order Records</h1>
                    </div>
                    <button className="add-btn" onClick={() => navigate('/purchase-order/create')}>
                        + New Order
                    </button>
                </header>

                <div className="product-table-wrap">
                    <table className="product-table">
                        <thead>
                        <tr>
                            <th>#</th>
                            <th>PO NUMBER</th>
                            <th>WAREHOUSE</th>
                            <th>DATE</th>
                            <th>STATUS</th>
                            <th className="actions">ACTIONS</th>
                        </tr>
                        </thead>
                        <tbody>
                        {orders.map((order, index) => (
                            <React.Fragment key={order.id}>
                                <tr onClick={() => toggleExpand(order.id)} style={{ cursor: 'pointer' }}>
                                    <td className="row-num">{String(index + 1).padStart(2, "0")}</td>
                                    <td className="product-name">#{order.poNumber}</td>
                                    <td className="sku">{order.warehouseId}</td>
                                    <td>{new Date(order.date).toLocaleDateString()}</td>
                                    <td>
                                        <select
                                            className="status-select"
                                            value={order.status}
                                            onClick={(e) => e.stopPropagation()}
                                            onChange={(e) => handleStatusChange(order.id, e.target.value)}
                                        >
                                            <option value="CREATED">Created</option>
                                            <option value="APPROVED">Approved</option>
                                            <option value="SHIPPED">Shipped</option>
                                            <option value="CANCELLED">Cancelled</option>
                                        </select>
                                    </td>
                                    <td className="actions">
                                        <button className="cancel-btn" style={{padding: '0.3rem 0.6rem', marginRight: '5px'}} onClick={(e) => {e.stopPropagation(); navigate(`/purchase-order/edit/${order.id}`)}}>Edit</button>
                                        <button className="del-btn" onClick={(e) => {e.stopPropagation(); navigate(`/purchase-order/delete/${order.id}`)}}>Delete</button>
                                    </td>
                                </tr>

                                {/* Expandable Detail View */}
                                {expandedId === order.id && (
                                    <tr>
                                        <td colSpan="6" style={{ background: '#16161d', padding: '1.5rem', borderBottom: '1px solid #1e1e24' }}>
                                            <p className="page-label" style={{ marginBottom: '1rem' }}>ORDER LINE ITEMS</p>
                                            <table style={{ width: '100%' }}>
                                                <thead>
                                                <tr style={{ textAlign: 'left', borderBottom: '1px solid #27272a' }}>
                                                    <th style={{ color: '#71717a', paddingBottom: '0.5rem' }}>PRODUCT ID</th>
                                                    <th style={{ color: '#71717a', paddingBottom: '0.5rem' }}>QUANTITY</th>
                                                </tr>
                                                </thead>
                                                <tbody>
                                                {order.items.map((item, i) => (
                                                    <tr key={i}>
                                                        <td style={{ padding: '0.5rem 0' }}>📦 Item #{item.productId}</td>
                                                        <td>{item.quantity} units</td>
                                                    </tr>
                                                ))}
                                                </tbody>
                                            </table>
                                        </td>
                                    </tr>
                                )}
                            </React.Fragment>
                        ))}
                        </tbody>
                    </table>
                </div>
            </main>
        </div>
    );
};

export default PurchaseOrderList;