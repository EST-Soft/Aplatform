import React, {useState} from "react";
import ReactDOM from "react-dom/client";
import App from "./App";
import { BrowserRouter, useLocation } from "react-router-dom";
//import MainHeader from "./common/MainHeader";
import Footer from "./common/Footer";
import { ModalProvider } from "./components/ModalProvider";
import Header from "./admin/Header";

// const Layout = () => {
//   const location = useLocation();
//   const isAdmin = location.pathname.startsWith("/admin");

//   return (
//     <>      
//       {!isAdmin && <MainHeader />}
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
  const isAdminLogin = location.pathname === "/admin/login"; 

  const [adminInfo, setAdminInfo] = useState(null); // 관리자의 정보를 상태로 관리
  return (
    <>
      {/* 모든 페이지에 헤더/푸터 랜더링하기 */}
      {!isAdminLogin && <Header adminInfo={adminInfo}/>} {/* admin이 로그인하면 보이는 헤더임*/}
      <div role="main" className="main" style={{ minHeight: "800px" }}>
        <App setAdminInfo={setAdminInfo} />{/*App.js에 setAdminInfo전달 */}
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