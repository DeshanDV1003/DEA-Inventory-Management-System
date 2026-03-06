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

            <nav className="sidebar-nav" style={{ overflowY: 'auto', paddingRight: '5px' }}>
                <NavLink to="/" className={({ isActive }) => isActive ? "nav-item active" : "nav-item"}>
                    <span className="nav-icon">🏠</span> Dashboard
                </NavLink>
                {/* --- INVENTORY CORE --- */}
                <div className="nav-group-label">INVENTORY</div>
                <NavLink to="/products" className={({ isActive }) => isActive ? "nav-item active" : "nav-item"}>
                    <span className="nav-icon">☰</span> Products
                </NavLink>
                <NavLink to="/stock" className={({ isActive }) => isActive ? "nav-item active" : "nav-item"}>
                    <span className="nav-icon">📉</span> Stock levels
                </NavLink>
                <NavLink to="/stock-transfers" className={({ isActive }) => isActive ? "nav-item active" : "nav-item"}>
                    <span className="nav-icon">🔄</span> Transfers
                </NavLink> 

                {/* --- PROCUREMENT --- */}
                <div className="nav-group-label" style={{ marginTop: '1.5rem' }}>PROCUREMENT</div>
                <NavLink to="/suppliers" className={({ isActive }) => isActive ? "nav-item active" : "nav-item"}>
                    <span className="nav-icon">☷</span> Suppliers
                </NavLink>
                <NavLink to="/purchase-order/list" className={({ isActive }) => (isActive || location.pathname.startsWith('/purchase-order')) ? "nav-item active" : "nav-item"}>
                    <span className="nav-icon">📦</span> Purchase Orders
                </NavLink>
                <NavLink to="/grn" className={({ isActive }) => isActive ? "nav-item active" : "nav-item"}>
                    <span className="nav-icon">📜</span> GRN Records
                </NavLink>

                {/* --- INFRASTRUCTURE --- */}
                <div className="nav-group-label" style={{ marginTop: '1.5rem' }}>INFRASTRUCTURE</div>
                <NavLink to="/companies" className={({ isActive }) => isActive ? "nav-item active" : "nav-item"}>
                    <span className="nav-icon">▦</span> Companies
                </NavLink>
                <NavLink to="/warehouse" className={({ isActive }) => isActive ? "nav-item active" : "nav-item"}>
                    <span className="nav-icon">⫙</span> Warehouse
                </NavLink>
                <NavLink to="/assets" className={({ isActive }) => isActive ? "nav-item active" : "nav-item"}>
                    <span className="nav-icon">🛠️</span> Assets
                </NavLink>

                {/* --- SYSTEM --- */}
                <div className="nav-group-label" style={{ marginTop: '1.5rem' }}>SYSTEM</div>
                <NavLink to="/maintenance" className={({ isActive }) => isActive ? "nav-item active" : "nav-item"}>
                    <span className="nav-icon">⚙️</span> Maintenance
                </NavLink>
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