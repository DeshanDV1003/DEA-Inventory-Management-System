/**
 * Sidebar Component
 *
 * Purpose:
 * - Provides global navigation for the Inventory system.
 * - Uses NavLink for automatic "active" styling.
 *
 * Navigation Items:
 * - Products: Link to Product Management.
 * - Purchase Orders: Link to the PO Dashboard.
 */
import React from 'react';
import { NavLink, useNavigate } from 'react-router-dom';

const Sidebar = () => {
    const navigate = useNavigate();
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
                <NavLink to="/products" className={({ isActive }) => isActive ? "nav-item active" : "nav-item"}>
                    <span className="nav-icon">☰</span> Products
                </NavLink>

                {/* Main Purchase Order Module Link */}
                <NavLink to="/purchase-order" className={({ isActive }) => isActive ? "nav-item active" : "nav-item"}>
                    <span className="nav-icon">📦</span> Purchase Orders
                </NavLink>

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