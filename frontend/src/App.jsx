import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import Login from "./pages/Login";
import Register from "./pages/Register";
import Products from "./pages/Products";
import Warehouse from "./pages/Warehouse"; 
import Suppliers from "./pages/Suppliers"; 
import Stock from "./pages/Stock"; 
import PurchaseOrderList from "./pages/purchase-order/PurchaseOrderList";
import StockTransfer from "./pages/StockTransfer";
import ProtectedRoute from "./components/ProtectedRoute";
import Company from "./pages/Company";
import Maintenance from "./pages/Maintenance";
import Assets from "./pages/Assets";
import GRN from "./pages/GRN";
import Dashboard from "./pages/Dashboard"; // Import the new Dashboard

function App() {
  return (
    <BrowserRouter>
      <Routes>
        {/* Public routes */}
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />

        {/* Protected Dashboard - The Main Entrance */}
        <Route path="/" element={<ProtectedRoute><Dashboard /></ProtectedRoute>} />

        {/* Protected: Core Modules */}
        <Route path="/companies" element={<ProtectedRoute><Company /></ProtectedRoute>} />
        <Route path="/warehouse" element={<ProtectedRoute><Warehouse /></ProtectedRoute>} />
        <Route path="/suppliers" element={<ProtectedRoute><Suppliers /></ProtectedRoute>} />
        <Route path="/products" element={<ProtectedRoute><Products /></ProtectedRoute>} />
        <Route path="/maintenance" element={<ProtectedRoute><Maintenance /></ProtectedRoute>} />
        <Route path="/stock" element={<ProtectedRoute><Stock /></ProtectedRoute>} />
        <Route path="/stock-transfers" element={<ProtectedRoute><StockTransfer /></ProtectedRoute>} />
        <Route path="/assets" element={<ProtectedRoute><Assets /></ProtectedRoute>} />
        <Route path="/grn" element={<ProtectedRoute><GRN /></ProtectedRoute>} />

        {/* Purchase Order Redirect */}
        <Route path="/purchase-order" element={<Navigate to="/purchase-order/list" replace />} />
        <Route path="/purchase-order/list" element={<ProtectedRoute><PurchaseOrderList /></ProtectedRoute>} />

        {/* Catch all - redirect to dashboard if logged in, else login */}
        <Route path="*" element={<Navigate to="/" replace />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;