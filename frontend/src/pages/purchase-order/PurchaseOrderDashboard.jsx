import React, { useEffect, useState, useCallback } from 'react';
import { useNavigate } from 'react-router-dom';
import { purchaseOrderService } from '../../services/purchaseOrderService';
import Sidebar from "../../components/Sidebar.jsx";

// Helper component for the stylish mini bar charts
const MiniChart = ({ colorClass, heights }) => (
    <div className={`mini-chart ${colorClass}`}>
        {heights.map((h, i) => (
            <div key={i} className={`bar ${h > 60 ? 'active' : ''}`} style={{ height: `${h}%` }}></div>
        ))}
    </div>
);

const PurchaseOrderDashboard = () => {
    const navigate = useNavigate();
    const [stats, setStats] = useState({ total: 324, pending: 213, shipped: 123, cancelled: 41 });
    const [recentOrders, setRecentOrders] = useState([]);

    const loadData = useCallback(async () => {
        try {
            const res = await purchaseOrderService.getAll();
            if (res.data && res.data.length > 0) {
                const d = res.data;
                setStats({
                    total: d.length,
                    pending: d.filter(o => o.status === 'CREATED').length,
                    shipped: d.filter(o => o.status === 'SHIPPED').length,
                    cancelled: d.filter(o => o.status === 'CANCELLED').length
                });
                setRecentOrders(d.slice(-5).reverse());
            }
        } catch (e) { console.error("API offline, showing style preview"); }
    }, []);

    useEffect(() => { loadData(); }, [loadData]);

    return (
        <div className="products-root">
            <Sidebar />
            <main className="main-content">
                <header className="page-header">
                    <div>
                        <p className="page-label">PURCHASES</p>
                        <h1 className="page-title">All Purchase Orders</h1>
                    </div>
                    <button className="add-btn" onClick={() => navigate('/purchase-order/create')}>+ Add New</button>
                </header>

                <div className="dashboard-grid">
                    {/* Card 1: Total Orders */}
                    <div className="stat-card" onClick={() => navigate('/purchase-order/list')}>
                        <div className="stat-header">
                            <span className="page-label">Total Purchase Orders</span>
                            <span className="trend-badge up">↑ 12%</span>
                        </div>
                        <div className="stat-main">
                            <h2 className="stat-value">{stats.total}</h2>
                            <MiniChart colorClass="purple-bars" heights={[40, 70, 45, 90, 65, 80]} />
                        </div>
                        <p className="card-footer-text">vs last week</p>
                    </div>

                    {/* Card 2: New/Pending */}
                    <div className="stat-card">
                        <div className="stat-header">
                            <span className="page-label">New Purchase Orders</span>
                            <span className="trend-badge up">↑ 8%</span>
                        </div>
                        <div className="stat-main">
                            <h2 className="stat-value">{stats.pending}</h2>
                            <MiniChart colorClass="blue-bars" heights={[30, 40, 80, 50, 90, 70]} />
                        </div>
                        <p className="card-footer-text">Awaiting Approval</p>
                    </div>

                    {/* Card 3: Returns/Shipped */}
                    <div className="stat-card">
                        <div className="stat-header">
                            <span className="page-label">Returns / Shipped</span>
                            <span className="trend-badge down">↓ 2%</span>
                        </div>
                        <div className="stat-main">
                            <h2 className="stat-value">{stats.shipped}</h2>
                            <MiniChart colorClass="gold-bars" heights={[90, 60, 40, 30, 50, 45]} />
                        </div>
                        <p className="card-footer-text">In Transit</p>
                    </div>

                    {/* Card 4: Cancelled */}
                    <div className="stat-card">
                        <div className="stat-header">
                            <span className="page-label">Today Cancelled</span>
                            <span className="trend-badge down">↑ 14%</span>
                        </div>
                        <div className="stat-main">
                            <h2 className="stat-value" style={{color: '#f87171'}}>{stats.cancelled}</h2>
                            <MiniChart colorClass="red-bars" heights={[20, 30, 20, 40, 80, 100]} />
                        </div>
                        <p className="card-footer-text">Voided Records</p>
                    </div>
                </div>

                {/* Table Section */}
                <div className="product-table-wrap" style={{marginTop: '1rem'}}>
                    <div className="section-header" style={{display: 'flex', justifyContent: 'space-between', padding: '1.2rem', borderBottom: '1px solid #1e1e24'}}>
                        <h3 className="page-label">RECENT TRANSACTIONS</h3>
                        <button className="see-more-link" onClick={() => navigate('/purchase-order/list')}>See more →</button>
                    </div>
                    <table className="product-table">
                        <thead>
                        <tr>
                            <th>PO #</th>
                            <th>WAREHOUSE</th>
                            <th>STATUS</th>
                            <th></th>
                        </tr>
                        </thead>
                        <tbody>
                        {recentOrders.length > 0 ? recentOrders.map(order => (
                            <tr key={order.id}>
                                <td className="product-name">#{order.poNumber}</td>
                                <td className="sku">{order.warehouseId}</td>
                                <td><span className="badge badge-active">{order.status}</span></td>
                                <td className="actions"><button className="del-btn" onClick={() => navigate('/purchase-order/list')}>View</button></td>
                            </tr>
                        )) : (
                            <tr><td colSpan="4" style={{textAlign: 'center', padding: '2rem', color: '#52525b'}}>No recent data available.</td></tr>
                        )}
                        </tbody>
                    </table>
                </div>
            </main>
        </div>
    );
};

export default PurchaseOrderDashboard;