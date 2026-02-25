import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { getAllProducts, addProduct, deleteProduct } from "../services/api";
import "./Products.css";

const Products = () => {
  const navigate = useNavigate();
  const username = localStorage.getItem("username") || "User";

  const [products, setProducts] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const [showModal, setShowModal] = useState(false);
  const [form, setForm] = useState({ name: "", price: "", sku: "" });
  const [saving, setSaving] = useState(false);
  const [deleteId, setDeleteId] = useState(null);

  const fetchProducts = async () => {
    try {
      setLoading(true);
      const res = await getAllProducts();
      setProducts(res.data);
    } catch (err) {
      if (err.response?.status === 401 || err.response?.status === 403) {
        handleLogout();
      } else {
        setError("Failed to load products.");
      }
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => { fetchProducts(); }, []);

  const handleLogout = () => {
    localStorage.removeItem("token");
    localStorage.removeItem("username");
    navigate("/login");
  };

  const handleAdd = async (e) => {
    e.preventDefault();
    setSaving(true);
    try {
      await addProduct({
        name: form.name,
        price: parseFloat(form.price),
        sku: form.sku,
      });
      setShowModal(false);
      setForm({ name: "", price: "", sku: "" });
      fetchProducts();
    } catch {
      setError("Failed to add product.");
    } finally {
      setSaving(false);
    }
  };

  const handleDelete = async (id) => {
    try {
      await deleteProduct(id);
      setDeleteId(null);
      fetchProducts();
    } catch {
      setError("Failed to delete product.");
    }
  };

  return (
    <div className="products-root">
      {/* Sidebar */}
      <aside className="sidebar">
        <div className="sidebar-brand">
          <span className="brand-icon">⬡</span>
          <span>INVNTRY</span>
        </div>
        <nav className="sidebar-nav">
          <a className="nav-item active">
            <span className="nav-icon">☰</span> Products
          </a>
          <a className="nav-item">
            <span className="nav-icon">⫙</span> Warehouse
          </a>
          <a className="nav-item">
            <span className="nav-icon">☷</span> Suppliers
          </a>
          <a className="nav-item">
            <span className="nav-icon">⊞</span> Reports
          </a>
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

      {/* Main */}
      <main className="main-content">
        <header className="page-header">
          <div>
            <p className="page-label">INVENTORY</p>
            <h1 className="page-title">Products</h1>
          </div>
          <button className="add-btn" onClick={() => setShowModal(true)}>
            + Add Product
          </button>
        </header>

        {error && (
          <div className="alert-error">
            ⚠ {error}
            <button onClick={() => setError("")}>✕</button>
          </div>
        )}

        {loading ? (
          <div className="loading-state">
            <div className="loader" />
            <p>Loading products...</p>
          </div>
        ) : products.length === 0 ? (
          <div className="empty-state">
            <span className="empty-icon">☰</span>
            <p>No products yet. Add your first one.</p>
          </div>
        ) : (
          <div className="product-table-wrap">
            <table className="product-table">
              <thead>
                <tr>
                  <th>#</th>
                  <th>Name</th>
                  <th>SKU</th>
                  <th>Price</th>
                  <th>Status</th>
                  <th></th>
                </tr>
              </thead>
              <tbody>
                {products.map((p, i) => (
                  <tr key={p.id}>
                    <td className="row-num">{String(i + 1).padStart(2, "0")}</td>
                    <td className="product-name">{p.name}</td>
                    <td className="sku">{p.sku || "—"}</td>
                    <td className="price">
                      {p.price != null ? `$${Number(p.price).toFixed(2)}` : "—"}
                    </td>
                    <td>
                      <span className={`badge ${p.status === 1 ? "badge-active" : "badge-inactive"}`}>
                        {p.status === 1 ? "Active" : "Inactive"}
                      </span>
                    </td>
                    <td className="actions">
                      <button
                        className="del-btn"
                        onClick={() => setDeleteId(p.id)}
                      >
                        Delete
                      </button>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        )}
      </main>

      {/* Add Product Modal */}
      {showModal && (
        <div className="modal-overlay" onClick={() => setShowModal(false)}>
          <div className="modal" onClick={(e) => e.stopPropagation()}>
            <div className="modal-header">
              <h2>New Product</h2>
              <button className="modal-close" onClick={() => setShowModal(false)}>✕</button>
            </div>
            <form onSubmit={handleAdd} className="modal-form">
              <div className="field-group">
                <label>PRODUCT NAME</label>
                <input
                  required
                  placeholder="e.g. Wireless Keyboard"
                  value={form.name}
                  onChange={(e) => setForm({ ...form, name: e.target.value })}
                />
              </div>
              <div className="field-group">
                <label>PRICE (USD)</label>
                <input
                  type="number"
                  step="0.01"
                  min="0"
                  required
                  placeholder="0.00"
                  value={form.price}
                  onChange={(e) => setForm({ ...form, price: e.target.value })}
                />
              </div>
              <div className="field-group">
                <label>SKU</label>
                <input
                  placeholder="e.g. WKB-001"
                  value={form.sku}
                  onChange={(e) => setForm({ ...form, sku: e.target.value })}
                />
              </div>
              <div className="modal-actions">
                <button type="button" className="cancel-btn" onClick={() => setShowModal(false)}>
                  Cancel
                </button>
                <button type="submit" className="submit-btn" disabled={saving}>
                  {saving ? <span className="spinner" /> : "Add Product"}
                </button>
              </div>
            </form>
          </div>
        </div>
      )}

      {/* Delete Confirm Modal */}
      {deleteId && (
        <div className="modal-overlay" onClick={() => setDeleteId(null)}>
          <div className="modal confirm-modal" onClick={(e) => e.stopPropagation()}>
            <h2>Delete Product?</h2>
            <p>This action cannot be undone.</p>
            <div className="modal-actions">
              <button className="cancel-btn" onClick={() => setDeleteId(null)}>Cancel</button>
              <button className="delete-confirm-btn" onClick={() => handleDelete(deleteId)}>
                Delete
              </button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
};

export default Products;
