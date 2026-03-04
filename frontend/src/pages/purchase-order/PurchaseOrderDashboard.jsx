/**
 * PurchaseOrderDashboard Component
 *
 * Purpose:
 * - Acts as the main landing page for the Purchase Order module.
 * - Displays "Quick Stats" (Total orders, Pending Approvals).
 * - Provides a "Recent Activity" feed.
 *
 * State Variables:
 * - stats (Object): Aggregated data from the list of orders.
 */
import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { purchaseOrderService } from '../../services/purchaseOrderService';

const PurchaseOrderDashboard = () => {
    const navigate = useNavigate();
    const [stats, setStats] = useState({ total: 0, pending: 0, shipped: 0 });

    useEffect(() => {
        purchaseOrderService.getAll().then(res => {
            const data = res.data;
            setStats({
                total: data.length,
                pending: data.filter(o => o.status === 'CREATED').length,
                shipped: data.filter(o => o.status === 'SHIPPED').length
            });
        });
    }, []);

    return (
        <div className="products-root">
            <main className="main-content">
                <header className="page-header">
                    <div>
                        <p className="page-label">LOGISTICS HUB</p>
                        <h1 className="page-title">Management Overview</h1>
                    </div>
                </header>

                {/* Stat Cards */}
                <div className="dashboard-grid">
                    <div className="stat-card" onClick={() => navigate('/purchase-order/list')}>
                        <span className="page-label">TOTAL ORDERS</span>
                        <h2 className="price">{stats.total}</h2>
                        <p className="sku">Click to view all</p>
                    </div>
                    <div className="stat-card">
                        <span className="page-label">WAITING APPROVAL</span>
                        <h2 style={{color: '#fbbf24'}}>{stats.pending}</h2>
                        <p className="sku">Needs attention</p>
                    </div>
                    <div className="stat-card">
                        <span className="page-label">SHIPPED THIS MONTH</span>
                        <h2 style={{color: '#4ade80'}}>{stats.shipped}</h2>
                        <p className="sku">In transit</p>
                    </div>
                </div>

                {/* Module Quick Actions */}
                <section style={{marginTop: '3rem'}}>
                    <h3 className="page-label">QUICK ACTIONS</h3>
                    <div className="action-row" style={{display: 'flex', gap: '1rem', marginTop: '1rem'}}>
                        <button className="add-btn" onClick={() => navigate('/purchase-order/create')}>+ Create New Purchase Order</button>
                        <button className="cancel-btn" onClick={() => navigate('/purchase-order/list')}>View Audit Logs</button>
                    </div>
                </section>
            </main>
        </div>
    );
};

export default PurchaseOrderDashboard;