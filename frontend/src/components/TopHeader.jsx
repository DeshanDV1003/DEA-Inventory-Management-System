import React from "react";
import "./TopHeader.css";

export default function TopHeader() {
  const username = localStorage.getItem("username") || "User";

  return (
    <div className="top-header">
      <div className="logo">⬡ INVNTRY</div>
      <input className="search" placeholder="Search inventory..." />
      <div className="header-right">
        <span className="notification">🔔</span>
        <div className="profile">
          <img src="https://i.pravatar.cc/40" alt="profile" />
          <span>Hi {username}</span>
        </div>
      </div>
    </div>
  );
}