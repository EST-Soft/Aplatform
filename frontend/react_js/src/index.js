// index.js
import React from "react";
import ReactDOM from "react-dom/client";
import App from "./App";
import { BrowserRouter } from "react-router-dom";
import Header from "./common/AdminHeader";
import Footer from "./common/Footer";
import { ModalProvider } from "./components/ModalProvider";

const root = ReactDOM.createRoot(document.getElementById("app"));
root.render(
  <ModalProvider>
    <BrowserRouter>
      <Header />
      <div role="main" className="main" style={{ minHeight: "700px" }}>
        <App />
      </div>
      <Footer />
    </BrowserRouter>
  </ModalProvider>
);
