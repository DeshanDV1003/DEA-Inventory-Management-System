import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import Login from "./pages/Login";
import Register from "./pages/Register";
import Products from "./pages/Products";

import PurchaseOrderDashboard from "./pages/purchase-order/PurchaseOrderDashboard";
import PurchaseOrderList from "./pages/purchase-order/PurchaseOrderList";
import PurchaseOrderForm from "./pages/purchase-order/PurchaseOrderForm";
import DeletePurchaseOrder from "./pages/purchase-order/DeletePurchaseOrder";

import Warehouse from "./pages/Warehouse";

import ProtectedRoute from "./components/ProtectedRoute";
import Company from "./pages/Company";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        {/* Public routes */}
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />

        {/* Protected: Company selection (after login) */}
        <Route
          path="/companies"
          element={
            <ProtectedRoute>
              <Company />
            </ProtectedRoute>
          }
        />
        <Route path="/company" element={<Navigate to="/companies" replace />} />

        {/* Protected: Products */}
        <Route
          path="/products"
          element={
            <ProtectedRoute>
              <Products />
            </ProtectedRoute>
          }
        />

        {/* Protected: Purchase Order Routes */}
        <Route path="/purchase-order" element={<ProtectedRoute><PurchaseOrderDashboard /></ProtectedRoute>} />
        <Route path="/purchase-order/list" element={<ProtectedRoute><PurchaseOrderList /></ProtectedRoute>} />
        <Route path="/purchase-order/create" element={<ProtectedRoute><PurchaseOrderForm mode="create" /></ProtectedRoute>} />
        <Route path="/purchase-order/edit/:id" element={<ProtectedRoute><PurchaseOrderForm mode="edit" /></ProtectedRoute>} />
        <Route path="/purchase-order/delete/:id" element={<ProtectedRoute><DeletePurchaseOrder /></ProtectedRoute>} />

        {/* Protected: Warehouse */}
          <Route path="/warehouse" element={<ProtectedRoute><Warehouse /></ProtectedRoute>} />

        {/* Catch all - redirect to login */}
        <Route path="*" element={<Navigate to="/login" replace />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
