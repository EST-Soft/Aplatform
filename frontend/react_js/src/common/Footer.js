import React from 'react';

const Footer = () => {
  return (
    // <footer className="footer">
    //   <div className="container">
    //     <div className="logo-container">
    //       <a href="/">
    //         <img src="/img/jobs-icon.png" height="32" alt="Logo" />
    //       </a>
    //     </div>
    //     <div className="text-container">
    //       <p>© Copyright 2024. All Rights Reserved.</p>
    //     </div>
    //     <div className="nav-container">
    //       <nav id="sub-menu">
    //         <ul className="nav-list">
    //           <li className="nav-item"><a href="page-faq.html" className="nav-link">FAQ's</a></li>
    //           <li className="nav-item"><a href="sitemap.html" className="nav-link">Sitemap</a></li>
    //           <li className="nav-item"><a href="contact-us.html" className="nav-link">Contact Us</a></li>
    //         </ul>
    //       </nav>
    //     </div>
    //   </div>
    // </footer>
    <footer id="footer" className="bg-color-grey border-top-0">
    <div className="footer-copyright footer-top-border bg-color-grey">
        <div className="container py-2">
            <div className="row py-4">
                <div className="col-lg-1 d-flex align-items-center justify-content-center justify-content-lg-start mb-2 mb-lg-0">
                    <a href="index.html" className="logo pe-0 pe-lg-3">
                        <img alt="Porto Website Template" src="/img/jobs-icon.png" height="32"/>
                    </a>
                </div>
                <div className="col-lg-7 d-flex align-items-center justify-content-center justify-content-lg-start mb-4 mb-lg-0">
                    <p>© Copyright 2023. All Rights Reserved.</p>
                </div>
                <div className="col-lg-4 d-flex align-items-center justify-content-center justify-content-lg-end">
                    <nav id="sub-menu">
                        <ul>
                            <li><i className="fas fa-angle-right"></i><a href="page-faq.html" className="ms-1 text-decoration-none text-color-hover-primary"> FAQ's</a></li>
                            <li><i className="fas fa-angle-right"></i><a href="sitemap.html" className="ms-1 text-decoration-none text-color-hover-primary"> Sitemap</a></li>
                            <li><i className="fas fa-angle-right"></i><a href="contact-us.html" className="ms-1 text-decoration-none text-color-hover-primary"> Contact Us</a></li>
                        </ul>
                    </nav>
                </div>
            </div>
        </div>
    </div>
</footer>
  );
};

export default Footer;