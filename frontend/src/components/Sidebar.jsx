/**
 * Sidebar Component
 *
 * Purpose:
 * - Provides global navigation for the Inventory system.
 * - Features an expandable sub-menu for "Purchase Orders".
 * - Uses NavLink for automatic "active" styling.
 *
 * State Variables:
 * - poOpen (boolean): Controls the visibility of the Purchase Order sub-menu.
 */
import React, { useState, useEffect } from 'react';
import { NavLink, useNavigate, useLocation } from 'react-router-dom';

const Sidebar = () => {
    const navigate = useNavigate();
    const location = useLocation();
    const username = localStorage.getItem("username") || "User";

    // Menu toggle state
    const [poOpen, setPoOpen] = useState(false);

    // Automatically keep the menu open if we are on a purchase-order sub-page
    useEffect(() => {
        if (location.pathname.startsWith('/purchase-order')) {
            setPoOpen(true);
        }
    }, [location]);

    const handleLogout = () => {
        localStorage.removeItem("token");
        localStorage.removeItem("username");
        navigate("/login");
    };

    return (
        <aside className="sidebar">
            <div className="sidebar-brand">
                <span className="brand-icon">⬡</span>
                <span>INVNTRY</span>
            </div>

            <nav className="sidebar-nav">
                <NavLink to="/products" className={({ isActive }) => isActive ? "nav-item active" : "nav-item"}>
                    <span className="nav-icon">☰</span> Products
                </NavLink>

                {/* Expandable Purchase Order Section */}
                <div>
                    <div
                        className={`nav-item ${location.pathname.startsWith('/purchase-order') ? 'active' : ''}`}
                        onClick={() => setPoOpen(!poOpen)}
                        style={{ cursor: 'pointer', justifyContent: 'space-between' }}
                    >
                        <div style={{ display: 'flex', alignItems: 'center', gap: '0.6rem' }}>
                            <span className="nav-icon">📦</span> Purchase Orders
                        </div>
                        <span style={{ fontSize: '0.7rem', transform: poOpen ? 'rotate(180deg)' : 'rotate(0deg)', transition: '0.2s' }}>▼</span>
                    </div>

                    {poOpen && (
                        <div className="sidebar-submenu" style={{ paddingLeft: '1.5rem', display: 'flex', flexDirection: 'column', gap: '0.25rem', marginTop: '0.25rem' }}>
                            <NavLink to="/purchase-order" end className={({ isActive }) => isActive ? "nav-item active" : "nav-item"}>
                                ⬩ Overview
                            </NavLink>
                            <NavLink to="/purchase-order/list" className={({ isActive }) => isActive ? "nav-item active" : "nav-item"}>
                                ⬩ Order List
                            </NavLink>
                            <NavLink to="/purchase-order/create" className={({ isActive }) => isActive ? "nav-item active" : "nav-item"}>
                                ⬩ New Order
                            </NavLink>
                        </div>
                    )}
                </div>

                <NavLink to="/warehouse" className="nav-item">
                    <span className="nav-icon">⫙</span> Warehouse
                </NavLink>
                <NavLink to="/suppliers" className="nav-item">
                    <span className="nav-icon">☷</span> Suppliers
                </NavLink>
                <NavLink to="/reports" className="nav-item">
                    <span className="nav-icon">⊞</span> Reports
                </NavLink>
            </nav>

            <div className="sidebar-footer">
                <div className="user-pill">
                    <div className="user-avatar">{username[0].toUpperCase()}</div>
                    <span>{username}</span>
                </div>
                <button className="logout-btn" onClick={handleLogout}>
                    Sign out →
                </button>
            </div>
        </aside>
    );
};

export default Sidebar;