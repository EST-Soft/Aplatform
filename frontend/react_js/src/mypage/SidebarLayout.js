import React from 'react';
import MypageSidebar from './MypageSidebar'; // 경로를 맞게 설정

const SidebarLayout = ({ showSidebar, children }) => {
  return (
    <div className="container">
      <div className="row">
        {showSidebar && <MypageSidebar />}
        <div className={`col-lg-${showSidebar ? 9 : 12}`}>
          {children}
        </div>
      </div>
    </div>
  );
};

export default SidebarLayout;
