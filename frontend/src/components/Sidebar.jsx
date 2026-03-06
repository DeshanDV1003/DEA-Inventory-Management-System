import React, { useState, useEffect } from 'react';
import { NavLink, useNavigate, useLocation } from 'react-router-dom';

const Sidebar = () => {
    const navigate = useNavigate();
    const location = useLocation();
    const username = localStorage.getItem("username") || "User";

    // Menu toggle state for Purchase Orders
    const [poOpen, setPoOpen] = useState(false);

    // Automatically keep the PO menu open if we are on a purchase-order sub-page
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
                {/* 1. Products */}
                <NavLink to="/products" className={({ isActive }) => isActive ? "nav-item active" : "nav-item"}>
                    <span className="nav-icon">☰</span> Products
                </NavLink>

                {/* 2. Companies (Newly Added) */}
                <NavLink to="/companies" className={({ isActive }) => isActive ? "nav-item active" : "nav-item"}>
                    <span className="nav-icon">▦</span> Companies
                </NavLink>

                {/* 3. Warehouse */}
                <NavLink to="/warehouse" className={({ isActive }) => isActive ? "nav-item active" : "nav-item"}>
                    <span className="nav-icon">⫙</span> Warehouse
                </NavLink>

                {/* 4. Suppliers */}
                <NavLink to="/suppliers" className={({ isActive }) => isActive ? "nav-item active" : "nav-item"}>
                    <span className="nav-icon">☷</span> Suppliers
                </NavLink>

                {/* 5. Expandable Purchase Order Section */}
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

                {/* 6. Reports */}
                <NavLink to="/reports" className={({ isActive }) => isActive ? "nav-item active" : "nav-item"}>
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