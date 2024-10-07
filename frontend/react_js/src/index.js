// index.js
import React, { useState } from "react";
import ReactDOM from "react-dom/client";
import App from "./App";
import { BrowserRouter, useLocation } from "react-router-dom";
import Header from "./common/Header";
import Footer from "./common/Footer";
import { ModalProvider } from "./components/ModalProvider";
// import AdminLogin from "./admin/AdminLogin_temp";

// const Layout = () => {  
//   const location = useLocation();
//   const isAdmin = location.pathname.startsWith("/admin");

//   return (
//     <>         
//       {/* 관리자 페이지가 아닐 경우, 헤더/ 푸터 랜더링하기 */}
//       {!isAdmin && <Header />}
//       <div role="main" className="main" style={{minHeight: "700px"}}>
//         {<App />}
//       </div>
//       {!isAdmin&&<Footer />}
//     </>  
//   );
// };

const Layout = () => {
  const location = useLocation();
  // const isAdmin = location.pathname.startsWith("/admin");
  const isAdminLogin = location.pathname === "/admin/login"; 
  const [adminInfo, setAdminInfo] = useState(null);
  return (
    <>
      {/* 모든 페이지에 헤더/푸터 랜더링하기
      <Header />
      <div role="main" className="main" style={{ minHeight: "700px" }}>
        <App />
      </div>
      <Footer /> */}
      <>
        {/* 로그인 페이지가 아니고 로그인된 경우에만 헤더를 표시 */}
      {!isAdminLogin && adminInfo && <Header admName={adminInfo.admName} />}
      
      <div role="main" className="main" style={{ minHeight: "700px" }}>
        <App setAdminInfo={setAdminInfo} adminInfo={adminInfo} /> {/* App.js에서 로그인 성공 시 adminInfo를 설정 */}
      </div>
      
      {/* 로그인 페이지가 아니고 로그인된 경우에만 푸터를 표시 */}
      {!isAdminLogin && adminInfo && <Footer />}
      </>
    </>
  );
};


const root = ReactDOM.createRoot(document.getElementById("app"));
root.render(
  <ModalProvider>
    <BrowserRouter>
      <Layout />
    </BrowserRouter>
  </ModalProvider>
);

