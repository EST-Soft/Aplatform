// index.js
import React from "react";
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
  const isAdmin = location.pathname.startsWith("/admin");

  return (
    <>
      {/* 모든 페이지에 헤더/푸터 랜더링하기 */}
      <Header />
      <div role="main" className="main" style={{ minHeight: "700px" }}>
        <App />
      </div>
      <Footer />
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
