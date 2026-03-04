import { useState, useEffect, useCallback } from "react";
import { useNavigate } from "react-router-dom";
import { getAllProducts, addProduct, deleteProduct } from "../services/api";
import BarcodeScanner from "../components/BarcodeScanner";
import Sidebar from "../components/Sidebar";
import "./Products.css";

const Products = () => {
  const navigate = useNavigate();
  const username = localStorage.getItem("username") || "User";

  const [products, setProducts] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const [showModal, setShowModal] = useState(false);
  const [form, setForm] = useState({
    name: "",
    price: "",
    sku: "",
    warehouseId: "",
    companyId: "",
    supplierId: "",
    imgPath: "",
    status: "Active",
  });
  const [saving, setSaving] = useState(false);
  const [deleteId, setDeleteId] = useState(null);
  const [showScanner, setShowScanner] = useState(false);

  const handleBarcodeScan = useCallback((barcode) => {
    setForm((prev) => ({ ...prev, sku: barcode }));
    setShowScanner(false);
  }, []);

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
        price: form.price,
        sku: parseInt(form.sku),
        warehouseId: parseInt(form.warehouseId),
        companyId: parseInt(form.companyId),
        supplierId: parseInt(form.supplierId),
        imgPath: form.imgPath,
        status: form.status,
        createdBy: username,
        modifiedBy: username,
      });
      setShowModal(false);
      setForm({
        name: "",
        price: "",
        sku: "",
        warehouseId: "",
        companyId: "",
        supplierId: "",
        imgPath: "",
        status: "Active",
      });
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
      <Sidebar />

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
                  <th>Warehouse</th>
                  <th>Supplier</th>
                  <th>Status</th>
                  <th>Created By</th>
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
                    <td>{p.warehouseId || "—"}</td>
                    <td>{p.supplierId || "—"}</td>
                    <td>
                      <span className={`badge ${p.status === "Active" ? "badge-active" : "badge-inactive"}`}>
                        {p.status || "—"}
                      </span>
                    </td>
                    <td>{p.createdBy || "—"}</td>
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
              <div className="field-row">
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
                  <div className="sku-input-row">
                    <input
                      type="number"
                      required
                      placeholder="e.g. 1001"
                      value={form.sku}
                      onChange={(e) => setForm({ ...form, sku: e.target.value })}
                    />
                    <button
                      type="button"
                      className="scan-btn"
                      onClick={() => setShowScanner(true)}
                    >
                      Scan
                    </button>
                  </div>
                </div>
              </div>
              <div className="field-row">
                <div className="field-group">
                  <label>WAREHOUSE ID</label>
                  <input
                    type="number"
                    required
                    placeholder="e.g. 1"
                    value={form.warehouseId}
                    onChange={(e) => setForm({ ...form, warehouseId: e.target.value })}
                  />
                </div>
                <div className="field-group">
                  <label>COMPANY ID</label>
                  <input
                    type="number"
                    required
                    placeholder="e.g. 1"
                    value={form.companyId}
                    onChange={(e) => setForm({ ...form, companyId: e.target.value })}
                  />
                </div>
              </div>
              <div className="field-row">
                <div className="field-group">
                  <label>SUPPLIER ID</label>
                  <input
                    type="number"
                    required
                    placeholder="e.g. 1"
                    value={form.supplierId}
                    onChange={(e) => setForm({ ...form, supplierId: e.target.value })}
                  />
                </div>
                <div className="field-group">
                  <label>STATUS</label>
                  <select
                    value={form.status}
                    onChange={(e) => setForm({ ...form, status: e.target.value })}
                  >
                    <option value="Active">Active</option>
                    <option value="Inactive">Inactive</option>
                  </select>
                </div>
              </div>
              <div className="field-group">
                <label>IMAGE PATH</label>
                <input
                  placeholder="e.g. /images/product.png"
                  value={form.imgPath}
                  onChange={(e) => setForm({ ...form, imgPath: e.target.value })}
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

      {/* Barcode Scanner */}
      {showScanner && (
        <BarcodeScanner
          onScan={handleBarcodeScan}
          onClose={() => setShowScanner(false)}
        />
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
