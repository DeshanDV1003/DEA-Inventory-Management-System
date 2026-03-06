import React, { useEffect, useState, useCallback } from "react";
import Sidebar from "../components/Sidebar";
import TopHeader from "../components/TopHeader";
import { 
    getAllProducts, getAllStocks, getAllSuppliers, 
    getAllPurchaseOrders, getAllGrns, getAllAssets, getAllTransfers 
} from "../services/api";
import "./Dashboard.css";

import { PieChart, Pie, Cell, BarChart, Bar, XAxis, YAxis, Tooltip, ResponsiveContainer } from "recharts";

export default function Dashboard() {
  const username = localStorage.getItem("username") || "User";
  const [stats, setStats] = useState({
    products: 0, stock: 0, suppliers: 0, purchaseOrders: 0, grn: 0, assets: 0, transfers: 0
  });
  const [loading, setLoading] = useState(true);

  const fetchStats = useCallback(async () => {
    try {
      const [p, s, sup, po, g, a, t] = await Promise.all([
        getAllProducts().catch(() => ({ data: [] })),
        getAllStocks().catch(() => ({ data: { data: [] } })), // Handles your .data.data structure
        getAllSuppliers().catch(() => ({ data: [] })),
        getAllPurchaseOrders().catch(() => ({ data: [] })),
        getAllGrns().catch(() => ({ data: [] })),
        getAllAssets().catch(() => ({ data: [] })),
        getAllTransfers().catch(() => ({ data: [] }))
      ]);

      setStats({
        products: p.data?.length || 0,
        stock: s.data?.data?.length || s.data?.length || 0,
        suppliers: sup.data?.length || 0,
        purchaseOrders: po.data?.length || 0,
        grn: g.data?.length || 0,
        assets: a.data?.length || 0,
        transfers: t.data?.length || 0
      });
    } catch (err) {
      console.error("Dashboard error", err);
    } finally {
      setLoading(false);
    }
  }, []);

  useEffect(() => { fetchStats(); }, [fetchStats]);

  const pieData = [
    { name: "Products", value: stats.products },
    { name: "Stock", value: stats.stock },
    { name: "Assets", value: stats.assets }
  ];

  const barData = [
    { name: "Orders", value: stats.purchaseOrders },
    { name: "GRNs", value: stats.grn },
    { name: "Transfers", value: stats.transfers }
  ];

  return (
    <div className="dashboard-root">
      <Sidebar />
      <main className="dashboard-main">
        <TopHeader />
        
        <h2 className="welcome">Hi {username} 👋 <span style={{fontSize: '0.9rem', color: '#71717a', fontWeight: '400'}}>Here is your inventory summary.</span></h2>

        <div className="cards">
          <div className="card">Products <span>{stats.products}</span></div>
          <div className="card">Stock Items <span>{stats.stock}</span></div>
          <div className="card">Suppliers <span>{stats.suppliers}</span></div>
          <div className="card">Purchase Orders <span>{stats.purchaseOrders}</span></div>
          <div className="card">GRN Records <span>{stats.grn}</span></div>
          <div className="card">Active Assets <span>{stats.assets}</span></div>
          <div className="card">Transfers <span>{stats.transfers}</span></div>
        </div>

        <div className="charts">
          <div className="chart">
            <h3 className="page-label" style={{marginBottom: '20px'}}>Inventory Mix</h3>
            <PieChart width={350} height={250}>
              <Pie data={pieData} dataKey="value" outerRadius={80} innerRadius={60} paddingAngle={5}>
                <Cell fill="#a78bfa" />
                <Cell fill="#22c55e" />
                <Cell fill="#3b82f6" />
              </Pie>
              <Tooltip contentStyle={{background: '#18181b', border: '1px solid #27272a'}} />
            </PieChart>
          </div>

          <div className="chart" style={{flex: 1}}>
            <h3 className="page-label" style={{marginBottom: '20px'}}>Operations Volume</h3>
            <ResponsiveContainer width="100%" height={250}>
                <BarChart data={barData}>
                <XAxis dataKey="name" stroke="#52525b" />
                <YAxis stroke="#52525b" />
                <Tooltip cursor={{fill: 'rgba(255,255,255,0.05)'}} contentStyle={{background: '#18181b', border: '1px solid #27272a'}} />
                <Bar dataKey="value" fill="#a78bfa" radius={[4, 4, 0, 0]} barSize={40} />
                </BarChart>
            </ResponsiveContainer>
          </div>
        </div>
      </main>
    </div>
  );
}