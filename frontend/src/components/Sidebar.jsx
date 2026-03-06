import React from 'react';
import { NavLink, useNavigate, useLocation } from 'react-router-dom';

const Sidebar = () => {
    const navigate = useNavigate();
    const location = useLocation();
    const username = localStorage.getItem("username") || "User";

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

                {/* 2. Companies */}
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

                {/* 5. Purchase Orders - Now a single direct link to the List/Manager page */}
                <NavLink 
                    to="/purchase-order/list" 
                    className={({ isActive }) => (isActive || location.pathname.startsWith('/purchase-order')) ? "nav-item active" : "nav-item"}
                >
                    <span className="nav-icon">📦</span> Purchase Orders
                </NavLink>

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