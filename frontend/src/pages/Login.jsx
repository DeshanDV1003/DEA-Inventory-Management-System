import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { loginUser } from "../services/api";
import "./Login.css";

const Login = () => {
  const navigate = useNavigate();
  const [form, setForm] = useState({ username: "", password: "" });
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false);

  const handleChange = (e) =>
    setForm({ ...form, [e.target.name]: e.target.value });

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError("");
    setLoading(true);
    try {
      const res = await loginUser(form);
      localStorage.setItem("token", res.data.token);
      localStorage.setItem("username", form.username);
      navigate("/");
    } catch {
      setError("Invalid username or password.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="login-root">
      <div className="login-left">
        <div className="login-brand">
          <span className="brand-icon">⬡</span>
          <span className="brand-name">INVNTRY</span>
        </div>
        <div className="login-tagline">
          <h1>Manage stock.<br />Track products.<br />Stay in control.</h1>
        </div>
        <div className="login-decoration">
          <div className="deco-circle c1" />
          <div className="deco-circle c2" />
          <div className="deco-circle c3" />
        </div>
      </div>

      <div className="login-right">
        <div className="login-card">
          <p className="login-subtitle">INVENTORY MANAGEMENT SYSTEM</p>
          <h2 className="login-title">Welcome back</h2>

          <form onSubmit={handleSubmit} className="login-form">
            <div className="field-group">
              <label>USERNAME</label>
              <input
                name="username"
                value={form.username}
                onChange={handleChange}
                placeholder="Enter your username"
                required
                autoComplete="off"
              />
            </div>

            <div className="field-group">
              <label>PASSWORD</label>
              <input
                name="password"
                type="password"
                value={form.password}
                onChange={handleChange}
                placeholder="Enter your password"
                required
              />
            </div>

            {error && <p className="error-msg">⚠ {error}</p>}

            <button type="submit" className="login-btn" disabled={loading}>
              {loading ? <span className="spinner" /> : "SIGN IN →"}
            </button>
          </form>

          <div className="login-footer">
            <p>Admin Inventory Management System</p>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Login;
