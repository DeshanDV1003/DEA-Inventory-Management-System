import React, { useState, useEffect } from 'react';
import { Package, Trash2, ShieldCheck, PlusCircle, Warehouse, Edit } from 'lucide-react';
import axios from 'axios';
import AssetModal from './components/AssetModal';

const api = axios.create({ baseURL: 'http://localhost:8081/api/v1' });

function App() {
  const [assets, setAssets] = useState([]);
  const [loading, setLoading] = useState(true);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [searchWh, setSearchWh] = useState('');
  const [selectedAsset, setSelectedAsset] = useState(null);

  useEffect(() => { fetchAssets(); }, []);

  const fetchAssets = async () => {
    try {
      const res = await api.get('/assets/all');
      setAssets(res.data);
      setLoading(false);
    } catch (err) { console.error("Backend offline?", err); }
  };

  const handleEdit = (asset) => {
    setSelectedAsset(asset);
    setIsModalOpen(true);
  };

  const handleSoftDelete = async (id) => {
    if (confirm("Archive this asset (Soft Delete)?")) {
      await api.delete(`/assets/delete/${id}`);
      fetchAssets();
    }
  };


  const filteredAssets = assets.filter(asset => {
    const matchesWarehouse = !searchWh || (asset.warehouseId && asset.warehouseId.toString().includes(searchWh));
    const matchesName = asset.name?.toLowerCase().includes(searchWh.toLowerCase());
    return matchesWarehouse || matchesName;
  });

  return (
    <div className="min-h-screen bg-slate-50 p-8 font-sans">
      <div className="max-w-6xl mx-auto">
        <header className="flex justify-between items-center mb-12 bg-white p-8 rounded-3xl shadow-sm border border-slate-100">
          <div>
            <h1 className="text-3xl font-black text-slate-900 flex items-center gap-3">
              <ShieldCheck className="text-sky-600" size={32} /> Asset Management
            </h1>
            <p className="text-slate-400 font-medium mt-1 uppercase text-xs tracking-widest">Enterprise Inventory Microservice</p>
          </div>
          <button
            onClick={() => { setSelectedAsset(null); setIsModalOpen(true); }}
            className="bg-sky-600 hover:bg-sky-700 text-white px-6 py-3 rounded-2xl font-bold transition-all flex items-center gap-2 shadow-lg shadow-sky-100"
          >
            <PlusCircle size={20}/> Register New Asset
          </button>
        </header>


        <div className="mb-6 flex gap-4">
          <div className="relative flex-1">
            <Warehouse className="absolute left-4 top-1/2 -translate-y-1/2 text-slate-400" size={20} />
            <input
              type="text"
              placeholder="Search by name or Warehouse ID..."
              className="w-full pl-12 pr-4 py-4 rounded-2xl border border-slate-200 outline-none focus:ring-4 focus:ring-sky-500/10 transition-all font-semibold"
              value={searchWh}
              onChange={(e) => setSearchWh(e.target.value)}
            />
          </div>
        </div>

        <div className="grid gap-6">
          {filteredAssets.map(asset => (
            <div key={asset.id} className="bg-white p-6 rounded-3xl border border-slate-100 flex justify-between items-center hover:scale-[1.01] transition-transform shadow-sm">
              <div className="flex gap-5 items-center">
                <div className="bg-sky-50 p-4 rounded-2xl text-sky-600"><Package size={28} /></div>
                <div>
                  <h3 className="text-lg font-bold text-slate-800">{asset.name}</h3>
                  <div className="flex gap-2 items-center mt-1">
                    <code className="text-xs font-mono bg-slate-100 text-slate-500 px-2 py-0.5 rounded">{asset.assetTag}</code>
                    <span className="text-[10px] text-slate-400 font-bold uppercase tracking-tighter">Warranty: {asset.warranty}</span>
                  </div>
                </div>
              </div>

              <div className="flex items-center gap-6">
                <div className="text-right">
                  <span className="text-[10px] text-slate-300 font-bold uppercase block">Org Hierarchy</span>
                  <div className="text-xs font-bold text-slate-500">
                    C:{asset.companyId} | D:{asset.departmentId} | W:{asset.warehouseId}
                  </div>
                </div>

                <span className="bg-emerald-100 text-emerald-700 px-3 py-1 rounded-full font-bold text-xs">{asset.status}</span>

                <div className="flex items-center gap-2">
                  <button onClick={() => handleEdit(asset)} className="p-3 text-slate-300 hover:text-sky-500 transition-colors">
                    <Edit size={22} />
                  </button>
                  <button onClick={() => handleSoftDelete(asset.id)} className="p-3 text-slate-300 hover:text-red-500 transition-colors">
                    <Trash2 size={22} />
                  </button>
                </div>
              </div>
            </div>
          ))}

          {filteredAssets.length === 0 && !loading && (
            <div className="text-center py-20 text-slate-400 font-medium border-2 border-dashed rounded-3xl">
              No assets found matching "{searchWh}".
            </div>
          )}
        </div>
      </div>

      <AssetModal
        isOpen={isModalOpen}
        initialData={selectedAsset}
        onClose={() => { setIsModalOpen(false); setSelectedAsset(null); }}
        onRefresh={fetchAssets}
      />
    </div>
  );
}

export default App;