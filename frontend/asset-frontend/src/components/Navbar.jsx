import React from 'react';
import { ShieldCheck, User, LogOut } from 'lucide-react';

const Navbar = () => (
  <nav className="bg-white border-b border-slate-100 px-8 py-4 flex justify-between items-center shadow-sm sticky top-0 z-40">
    <div className="flex items-center gap-3">
      <div className="bg-sky-600 p-2 rounded-xl text-white">
        <ShieldCheck size={24} />
      </div>
      <div>
        <span className="text-xl font-black text-slate-900 tracking-tight">AssetManager</span>
        <div className="h-1 w-full bg-sky-600 rounded-full scale-x-0 group-hover:scale-x-100 transition-transform"></div>
      </div>
    </div>
    <div className="flex items-center gap-6">
      <div className="flex items-center gap-2 text-slate-500 font-semibold text-sm hover:text-sky-600 cursor-pointer transition-colors">
        <User size={18}/> <span>Randil Welikala</span>
      </div>
      <button className="text-slate-300 hover:text-red-500 transition-all p-2 rounded-xl hover:bg-red-50">
        <LogOut size={20}/>
      </button>
    </div>
  </nav>
);

export default Navbar;