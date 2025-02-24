import React from "react";
import { Outlet } from 'react-router-dom';

const EnterMypage = () =>{


    return (
        <div className="container py-4">
        <div className="row">
          <div className="col-lg-9">
            <Outlet /> {/* 자식 컴포넌트를 여기에 렌더링 */}
          </div>
        </div>
      </div>
    );
};

export default EnterMypage;