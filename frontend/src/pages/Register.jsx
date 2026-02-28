import { useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import { registerUser } from "../services/api";
import "./Register.css";

const Register = () => {
  const navigate = useNavigate();
  const [form, setForm] = useState({
    username: "",
    password: "",
    fullName: "",
    designation: "",
    email: "",
    phone: "",
    userType: "",
    companyId: "",
    warehouseId: "",
  });
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false);

  const handleChange = (e) =>
    setForm({ ...form, [e.target.name]: e.target.value });

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError("");
    setLoading(true);
    try {
      const payload = {
        ...form,
        companyId: form.companyId ? Number(form.companyId) : null,
        warehouseId: form.warehouseId ? Number(form.warehouseId) : null,
      };
      await registerUser(payload);
      navigate("/login");
    } catch (err) {
      setError(
        err.response?.data?.message || "Registration failed. Please try again."
      );
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="register-root">
      <div className="register-left">
        <div className="register-brand">
          <span className="brand-icon">⬡</span>
          <span className="brand-name">INVNTRY</span>
        </div>
        <div className="register-tagline">
          <h1>
            Join the team.
            <br />
            Start managing
            <br />
            inventory today.
          </h1>
        </div>
        <div className="register-decoration">
          <div className="deco-circle c1" />
          <div className="deco-circle c2" />
          <div className="deco-circle c3" />
        </div>
      </div>

      <div className="register-right">
        <div className="register-card">
          <p className="register-subtitle">INVENTORY MANAGEMENT SYSTEM</p>
          <h2 className="register-title">Create an account</h2>

          <form onSubmit={handleSubmit} className="register-form">
            <div className="register-row">
              <div className="field-group">
                <label>FULL NAME</label>
                <input
                  name="fullName"
                  value={form.fullName}
                  onChange={handleChange}
                  placeholder="John Doe"
                  required
                />
              </div>
              <div className="field-group">
                <label>USERNAME</label>
                <input
                  name="username"
                  value={form.username}
                  onChange={handleChange}
                  placeholder="johndoe"
                  required
                  autoComplete="off"
                />
              </div>
            </div>

            <div className="register-row">
              <div className="field-group">
                <label>EMAIL</label>
                <input
                  name="email"
                  type="email"
                  value={form.email}
                  onChange={handleChange}
                  placeholder="john@example.com"
                  required
                />
              </div>
              <div className="field-group">
                <label>PHONE</label>
                <input
                  name="phone"
                  value={form.phone}
                  onChange={handleChange}
                  placeholder="+1 234 567 890"
                />
              </div>
            </div>

            <div className="register-row">
              <div className="field-group">
                <label>DESIGNATION</label>
                <input
                  name="designation"
                  value={form.designation}
                  onChange={handleChange}
                  placeholder="Manager"
                />
              </div>
              <div className="field-group">
                <label>USER TYPE</label>
                <input
                  name="userType"
                  value={form.userType}
                  onChange={handleChange}
                  placeholder="admin / staff"
                />
              </div>
            </div>

            <div className="register-row">
              <div className="field-group">
                <label>COMPANY ID</label>
                <input
                  name="companyId"
                  type="number"
                  value={form.companyId}
                  onChange={handleChange}
                  placeholder="1"
                />
              </div>
              <div className="field-group">
                <label>WAREHOUSE ID</label>
                <input
                  name="warehouseId"
                  type="number"
                  value={form.warehouseId}
                  onChange={handleChange}
                  placeholder="1"
                />
              </div>
            </div>

            <div className="field-group">
              <label>PASSWORD</label>
              <input
                name="password"
                type="password"
                value={form.password}
                onChange={handleChange}
                placeholder="Create a strong password"
                required
              />
            </div>

            {error && <p className="error-msg">{error}</p>}

            <button type="submit" className="register-btn" disabled={loading}>
              {loading ? <span className="spinner" /> : "CREATE ACCOUNT →"}
            </button>
          </form>

          <p className="register-footer">
            Already have an account? <Link to="/login">Sign in</Link>
          </p>
        </div>
      </div>
    </div>
  );
};

export default Register;
