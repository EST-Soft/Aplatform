import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';

  
  // 사이드바 구동 로직

import styles from './AdminSidebar.module.css';

const AdminSidebar = () => {
    
  const [openMenus, setOpenMenus] = useState({
    notice: false,
    user: false,
    community: false,
    customer: false,
  });

  const [activeMenu, setActiveMenu] = useState(null);
  const [isSidebarOpen, setIsSidebarOpen] = useState(false); // 기본값 false로 설정

  const toggle = (menu) => {
    setOpenMenus((prevState) => ({
      ...prevState,
      [menu]: !prevState[menu],
    }));
    setActiveMenu(menu);
  };

  const toggleSidebar = () => {
    setIsSidebarOpen((prevState) => !prevState); // 사이드바 열고 닫기
  };

  // 화면 크기에 따라 사이드바 상태 유지 ( 반응형 로직, 미완성 240930)

  useEffect(() => {
    const handleResize = () => {
      if (window.innerWidth > 900) {
        setIsSidebarOpen(true); // 큰 화면에서는 사이드바 열기
      } else {
        setIsSidebarOpen(false); // 작은 화면에서는 사이드바 닫기
      }
    };

    // 컴포넌트가 마운트될 때와 화면 크기 변경될 때 이벤트 리스너 추가
    window.addEventListener('resize', handleResize);
    handleResize(); // 컴포넌트가 마운트될 때도 실행

    // 컴포넌트가 언마운트될 때 이벤트 리스너 제거
    return () => {
      window.removeEventListener('resize', handleResize);
    };
  }, []);

  return (
    <div>

    {/* 작은 화면에서 버튼을 눌러 사이드바를 여닫음 , '◁' : '▷' 버튼안에 뜨는 여닫는 글자*/}
      <button className={styles.toggleButton} onClick={toggleSidebar}>
        {isSidebarOpen ? '◁' : '▷'}
      </button>


      {/* 사이드바 */}
      <div className={`${styles.adminSideBar} ${isSidebarOpen ? styles.open : styles.closed}`}>
        <ul className={styles.menuDetail}>
          <li>
            <div onClick={() => toggle('notice')}
              className={activeMenu === 'notice' ? styles.activeMenu : ''}>공고 관리</div>
            {openMenus.notice && (
              <ul className={styles.subMenu}>
                <li>
                  <Link to="/admin/board/JobPosting">공고 관리</Link>
                </li>
              </ul>
            )}
          </li>
          <li>
            <div onClick={() => toggle('user')}
              className={activeMenu === 'user' ? styles.activeMenu : ''}>사용자 관리</div>
            {openMenus.user && (
              <ul className={styles.subMenu}>
                <li><Link to="/admin/board/MemberAccount">회원 관리</Link></li>
                <li><Link to="/admin/board/EnterPrise">기업 관리</Link></li>
                <li><Link to="/admin/board/Manager">관리자 관리</Link></li>
              </ul>
            )}
          </li>
          <li>
            <div onClick={() => toggle('community')}
              className={activeMenu === 'community' ? styles.activeMenu : ''}>커뮤니티 관리</div>
            {openMenus.community && (
              <ul className={styles.subMenu}>
                <li><Link to="/admin/board/Qna">Q & A 게시판 관리</Link></li>
                <li><Link to="/admin/board/General">일반 게시판 관리</Link></li>
                <li><Link to="/admin/board/FeedBack">피드백 게시판 관리</Link></li>
              </ul>
            )}
          </li>
          <li>
            <div onClick={() => toggle('customer')}
              className={activeMenu === 'customer' ? styles.activeMenu : ''}>고객센터</div>
            {openMenus.customer && (
              <ul className={styles.subMenu}>
                <li><Link to="#">문의 내역 게시판</Link></li>
                <li><Link to="/admin/FAQ">자주 묻는 질문 게시판</Link></li>
              </ul>
            )}
          </li>
        </ul>
      </div>
    </div>
  );
};

export default AdminSidebar;
