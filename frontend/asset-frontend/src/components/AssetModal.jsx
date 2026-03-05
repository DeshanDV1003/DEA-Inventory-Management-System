import React, { useState, useEffect } from 'react';
import { X, Save, AlertCircle, Building2, Layers, Warehouse, Shield } from 'lucide-react';
import api from '../api/api';

const AssetModal = ({ isOpen, onClose, onRefresh, initialData }) => { // Add initialData prop
  const [formData, setFormData] = useState({ name: '', assetTag: '', companyId: '', departmentId: '', warehouseId: '', warranty: '' });
  const [error, setError] = useState("");

  useEffect(() => {
      if (initialData) {
        setFormData(initialData);
      } else {
        setFormData({ name: '', assetTag: '', companyId: '', departmentId: '', warehouseId: '', warranty: '' });
      }
    }, [initialData, isOpen]);

  const handleSubmit = async (e) => {
      e.preventDefault();
      try {
        if (initialData) {

          await api.put(`/assets/update/${initialData.id}`, formData);
        } else {

          await api.post('/assets/add', formData);
        }
        onRefresh();
        onClose();
      } catch (err) {
        setError(err.response?.data?.message || "Operation failed.");
      }
    };

  if (!isOpen) return null;

  return (
    <div className="fixed inset-0 bg-slate-900/60 backdrop-blur-sm flex items-center justify-center p-4 z-50 animate-in fade-in duration-300">
      <div className="bg-white rounded-[2rem] shadow-2xl w-full max-w-lg overflow-hidden border border-slate-100">
        <div className="p-6 border-b flex justify-between items-center bg-slate-50">
          <h2 className="text-xl font-black text-slate-800 uppercase tracking-tight">Register New Asset</h2>
          <button onClick={onClose} className="p-2 hover:bg-slate-200 rounded-full transition-colors"><X size={20}/></button>
        </div>

        <form onSubmit={handleSubmit} className="p-8 space-y-5">
          {error && (
            <div className="bg-red-50 text-red-600 p-4 rounded-xl flex gap-3 text-xs font-bold border border-red-100 italic">
              <AlertCircle size={18}/> {error}
            </div>
          )}

          <div className="space-y-4">
            <div>
              <label className="text-[10px] font-bold text-slate-400 uppercase ml-1 tracking-widest">General Info</label>
              <input type="text" placeholder="Asset Name (e.g., Dell Monitor)" className="w-full p-4 rounded-xl border border-slate-200 focus:ring-2 focus:ring-sky-500 outline-none transition-all font-semibold"
                value={formData.name} onChange={e => setFormData({...formData, name: e.target.value})} required />
            </div>

            <div className="grid grid-cols-2 gap-4">
              <input type="text" placeholder="Asset Tag" className="p-4 rounded-xl border border-slate-200 font-mono text-sm outline-none"
                value={formData.assetTag} onChange={e => setFormData({...formData, assetTag: e.target.value})} required />
              <input type="text" placeholder="Warranty (e.g. 12m)" className="p-4 rounded-xl border border-slate-200 outline-none font-semibold"
                value={formData.warranty} onChange={e => setFormData({...formData, warranty: e.target.value})} required />
            </div>
          </div>

          <label className="text-[10px] font-bold text-slate-400 uppercase ml-1 tracking-widest">Organizational Hierarchy</label>
          <div className="grid grid-cols-3 gap-3">
             <div className="space-y-1">
               <div className="flex items-center gap-1 text-[9px] font-black text-slate-500 uppercase ml-1"><Building2 size={10}/> Company</div>
               <input type="number" placeholder="ID" className="w-full p-4 rounded-xl border border-slate-200 text-center font-bold"
                 value={formData.companyId} onChange={e => setFormData({...formData, companyId: e.target.value})} required />
             </div>
             <div className="space-y-1">
               <div className="flex items-center gap-1 text-[9px] font-black text-slate-500 uppercase ml-1"><Layers size={10}/> Dept</div>
               <input type="number" placeholder="ID" className="w-full p-4 rounded-xl border border-slate-200 text-center font-bold"
                 value={formData.departmentId} onChange={e => setFormData({...formData, departmentId: e.target.value})} required />
             </div>
             <div className="space-y-1">
               <div className="flex items-center gap-1 text-[9px] font-black text-slate-500 uppercase ml-1"><Warehouse size={10}/> Whse</div>
               <input type="number" placeholder="ID" className="w-full p-4 rounded-xl border border-slate-200 text-center font-bold"
                 value={formData.warehouseId} onChange={e => setFormData({...formData, warehouseId: e.target.value})} required />
             </div>
          </div>

          <button type="submit" className="w-full bg-sky-600 text-white py-5 rounded-2xl font-black text-lg hover:bg-sky-700 shadow-lg shadow-sky-100 transition-all flex items-center justify-center gap-3 active:scale-95 mt-2">
            <Save size={22}/> Save to Inventory
          </button>
        </form>
      </div>
    </div>
  );
};

export default AssetModal;