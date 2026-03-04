import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import Login from "./pages/Login";
import Register from "./pages/Register";
import Products from "./pages/Products";
import ProtectedRoute from "./components/ProtectedRoute";
import Company from "./pages/Company";



function App() {
  return (
    <BrowserRouter>
      <Routes>

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

        <Route
          path="/companies"
          element={<Company />}
        />

        <Route
          path="/company"
          element={<Navigate to="/companies" replace />}
        />

        {/* Catch all */}
        <Route path="*" element={<Navigate to="/companies" replace />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
