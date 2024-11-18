// index.js
import React from "react";
import ReactDOM from "react-dom/client";
import { Provider } from 'react-redux';
import store from "./store/store";
import App from "./App";
import { BrowserRouter ,Route, Routes, useLocation } from "react-router-dom";
import Header from "./common/AdminHeader";
import Footer from "./common/Footer";
import { ModalProvider } from "./components/ModalProvider";





const root = ReactDOM.createRoot(document.getElementById("app"));
root.render(
  <Provider store={store}>
    <ModalProvider>
      <BrowserRouter>
        <Header />
        <div role="main" className="main" style={{ minHeight: "700px" }}>
          <App />
        </div>
        <Footer />
      </BrowserRouter>
    </ModalProvider>
  </Provider>
);
