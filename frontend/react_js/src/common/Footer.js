import React from 'react';
import '../styles/Footer.css';

const Footer = () => {
  return (
    <footer className="footer">
      <div className="container">
        <div className="logo-container">
          <a href="/">
            <img src="/img/jobs-icon.png" height="32" alt="Logo" />
          </a>
        </div>
        <div className="text-container">
          <p>© Copyright 2024. All Rights Reserved.</p>
        </div>
        <div className="nav-container">
          <nav id="sub-menu">
            <ul className="nav-list">
              <li className="nav-item"><a href="page-faq.html" className="nav-link">FAQ's</a></li>
              <li className="nav-item"><a href="sitemap.html" className="nav-link">Sitemap</a></li>
              <li className="nav-item"><a href="contact-us.html" className="nav-link">Contact Us</a></li>
            </ul>
          </nav>
        </div>
      </div>
    </footer>
  );
};

export default Footer;