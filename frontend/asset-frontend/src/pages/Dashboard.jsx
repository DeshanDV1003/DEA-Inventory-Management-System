import React, { useState, useEffect } from 'react';
import api from '../api/api';
import { Package, Trash2, Filter, Plus, LayoutGrid, CheckCircle, Database } from 'lucide-react';

const Dashboard = () => {
  const [assets, setAssets] = useState([]);
  const [warehouseFilter, setWarehouseFilter] = useState('');
  const [loading, setLoading] = useState(true);

  useEffect(() => { fetchAssets(); }, [warehouseFilter]);

  const fetchAssets = async () => {
    try {
      const url = warehouseFilter ? `/assets/warehouse/${warehouseFilter}` : '/assets/all';
      const res = await api.get(url);
      setAssets(res.data);
      setLoading(false);
    } catch (err) { console.error("Sync Error", err); }
  };

  const handleSoftDelete = async (id) => {
    if (confirm("Move asset to maintenance archive? (Soft Delete)")) {
      await api.delete(`/assets/delete/${id}`); //
      fetchAssets();
    }
  };

  return (
    <div className="min-h-screen bg-slate-50/50 p-4 md:p-8 animate-in fade-in duration-700">
      <div className="max-w-7xl mx-auto">

        <div className="flex flex-col md:flex-row justify-between items-start md:items-center mb-10 gap-4">
          <div>
            <h1 className="text-4xl font-black text-slate-900 tracking-tight flex items-center gap-3">
              <div className="bg-sky-600 p-2 rounded-xl text-white shadow-lg shadow-sky-200"><Package size={28}/></div>
              Asset Manager
            </h1>
            <p className="text-slate-500 font-medium mt-1">Enterprise Inventory & Lifecycle Tracking</p>
          </div>
          <button className="group bg-sky-600 hover:bg-sky-700 text-white px-6 py-3.5 rounded-2xl font-bold transition-all flex items-center gap-2 shadow-xl shadow-sky-100 hover:-translate-y-0.5">
            <Plus size={20} className="group-hover:rotate-90 transition-transform duration-300"/> New Registration
          </button>
        </div>


        <div className="grid grid-cols-1 md:grid-cols-3 gap-6 mb-8">
          <div className="bg-white p-6 rounded-3xl border border-slate-100 shadow-sm flex items-center gap-4">
            <div className="bg-emerald-100 text-emerald-600 p-4 rounded-2xl"><CheckCircle size={24}/></div>
            <div><p className="text-sm text-slate-400 font-bold uppercase">Active Assets</p><h3 className="text-2xl font-black text-slate-800">{assets.length}</h3></div>
          </div>
          <div className="md:col-span-2 bg-white p-4 rounded-3xl border border-slate-100 shadow-sm flex items-center gap-4">
            <Filter className="text-slate-300 ml-2" size={24}/>
            <input
              type="number"
              placeholder="Search by Warehouse ID (e.g., 1)..."
              className="w-full bg-transparent outline-none text-lg font-semibold text-slate-600 placeholder:text-slate-300"
              onChange={(e) => setWarehouseFilter(e.target.value)}
            />
          </div>
        </div>


        <div className="bg-white rounded-[2rem] shadow-sm border border-slate-100 overflow-hidden">
          <table className="w-full text-left">
            <thead className="bg-slate-50/50 border-b border-slate-100">
              <tr>
                <th className="px-8 py-5 text-xs font-black text-slate-400 uppercase tracking-widest">Asset Details</th>
                <th className="px-8 py-5 text-xs font-black text-slate-400 uppercase tracking-widest">Status</th>
                <th className="px-8 py-5 text-xs font-black text-slate-400 uppercase tracking-widest">Location</th>
                <th className="px-8 py-5 text-xs font-black text-slate-400 uppercase tracking-widest text-right">Actions</th>
              </tr>
            </thead>
            <tbody className="divide-y divide-slate-50">
              {assets.map((asset) => (
                <tr key={asset.id} className="hover:bg-slate-50/30 transition-all group">
                  <td className="px-8 py-6">
                    <div className="font-bold text-slate-800 text-lg group-hover:text-sky-600 transition-colors">{asset.name}</div>
                    <div className="text-xs font-mono bg-slate-100 text-slate-500 px-2 py-0.5 rounded inline-block mt-1">{asset.assetTag}</div>
                  </td>
                  <td className="px-8 py-6">
                    <span className="bg-emerald-50 text-emerald-700 px-3 py-1 rounded-full text-[10px] font-black uppercase tracking-tighter border border-emerald-100">
                      {asset.status}
                    </span>
                  </td>
                  <td className="px-8 py-6">
                    <div className="flex items-center gap-2 text-slate-600 font-bold italic">
                      <Database size={16} className="text-slate-300" /> Wh: {asset.warehouseId}
                    </div>
                  </td>
                  <td className="px-8 py-6 text-right">
                    <button onClick={() => handleSoftDelete(asset.id)} className="p-3 text-slate-300 hover:text-red-500 hover:bg-red-50 rounded-2xl transition-all">
                      <Trash2 size={22}/>
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
          {assets.length === 0 && !loading && (
            <div className="py-24 text-center">
              <div className="bg-slate-50 w-20 h-20 rounded-full flex items-center justify-center mx-auto mb-4 text-slate-200"><LayoutGrid size={40}/></div>
              <p className="text-slate-400 font-bold uppercase tracking-widest text-xs">No Assets Registered</p>
            </div>
          )}
        </div>
      </div>
    </div>
  );
};

export default Dashboard;