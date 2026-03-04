import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import Login from "./pages/Login";
import Register from "./pages/Register";
import Products from "./pages/Products";

import PurchaseOrderDashboard from "./pages/purchase-order/PurchaseOrderDashboard";
import PurchaseOrderList from "./pages/purchase-order/PurchaseOrderList";
import PurchaseOrderForm from "./pages/purchase-order/PurchaseOrderForm";
import DeletePurchaseOrder from "./pages/purchase-order/DeletePurchaseOrder";

import ProtectedRoute from "./components/ProtectedRoute";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        {/* Default redirect */}
        <Route path="/" element={<Navigate to="/products" replace />} />

        {/* Public routes */}
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />

        {/* Protected route – redirects to /login if no token */}
        <Route
          path="/products"
          element={
            <ProtectedRoute>
              <Products />
            </ProtectedRoute>
          }
        />

          {/* Protected Purchase Order Routes */}
          <Route path="/purchase-order" element={<ProtectedRoute><PurchaseOrderDashboard /></ProtectedRoute>} />
          <Route path="/purchase-order/list" element={<ProtectedRoute><PurchaseOrderList /></ProtectedRoute>} />
          <Route path="/purchase-order/create" element={<ProtectedRoute><PurchaseOrderForm mode="create" /></ProtectedRoute>} />
          <Route path="/purchase-order/edit/:id" element={<ProtectedRoute><PurchaseOrderForm mode="edit" /></ProtectedRoute>} />

          <Route path="/purchase-order/delete/:id" element={<ProtectedRoute><DeletePurchaseOrder /></ProtectedRoute>} />
          {/* Catch all */}
        <Route path="*" element={<Navigate to="/products" replace />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
