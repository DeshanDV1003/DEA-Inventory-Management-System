import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import Login from "./pages/Login";
import Register from "./pages/Register";
import Products from "./pages/Products";
import Warehouse from "./pages/Warehouse"; 
import Suppliers from "./pages/Suppliers"; 

import PurchaseOrderList from "./pages/purchase-order/PurchaseOrderList";

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

        <Route path="/warehouse" element={<ProtectedRoute><Warehouse /></ProtectedRoute>} />

        <Route path="/suppliers" element={<ProtectedRoute><Suppliers /></ProtectedRoute>} />

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
        {/* Purchase Order Module */}
        <Route path="/purchase-order" element={<Navigate to="/purchase-order/list" replace />} />
        
        <Route path="/purchase-order/list" element={<ProtectedRoute><PurchaseOrderList /></ProtectedRoute>} />


        {/* Catch all - redirect to login */}
        <Route path="*" element={<Navigate to="/login" replace />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
