import React from 'react';
import EnterMypageSidebar from "./EnterMypageSidebar"; 

const EnterSidebarLayout = ({ showSidebarEnter, children }) => {
  return (
    <div className="container">
      <div className="row">
        {showSidebarEnter && <EnterMypageSidebar />}
        <div className={`col-lg-${showSidebarEnter ? 9 : 12}`}>
          {children}
        </div>
      </div>
    </div>
  );
};

export default EnterSidebarLayout;
