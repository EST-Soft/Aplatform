import React from 'react';
import AdminSidebar from './AdminSidebar'; // 경로를 맞게 설정

const SidebarLayout = ({ showSidebar, children }) => {
  return (
    <div className="container">
      <div className="row">
        {showSidebar && <AdminSidebar />}
        <div className={`col-lg-${showSidebar ? 9 : 12}`}>
          {children}
        </div>
      </div>
    </div>
  );
};

export default SidebarLayout;
