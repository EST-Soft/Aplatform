
import { Route, Routes } from 'react-router-dom';
import BoardList from './routes/BoardList';
import Home from './routes/Home';
import React from 'react';
import BoardDetail from './routes/BoardDetail';
import BoardWrite from './routes/BoardWrite';
import BoardUpdate from "./routes/BoardUpdate";
import 'bootstrap/dist/css/bootstrap.min.css';
import Header from "./layout/Header";
import Footer from "./layout/Footer";

function App() {
  return (
    <div className="wrapper">
      <Header />
      <div role="main" className="main" style={{ minHeight: '700px' }}>
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/board" element={<BoardList />} />
          <Route path="/board/:idx" element={<BoardDetail />} />
          <Route path="/write" element={<BoardWrite />} />
          <Route path="/update/:idx" element={<BoardUpdate />} />
        </Routes>
      </div>
      <Footer />
    </div>
  );
}

export default App;