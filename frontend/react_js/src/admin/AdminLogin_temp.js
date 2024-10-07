import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

function AdminLogin({ setAdminInfo }) {
  const [admId, setAdmId] = useState('');
  const [admPswrd, setAdmPswrd] = useState('');
  const navigate = useNavigate();  // useNavigate는 컴포넌트 최상단에서 호출
  
  // 아이디/비번 찾기 상태 (탭 전환)
  const [isFindAcc, setIsFindAcc] = useState(false);
  const [isFindPw, setIsFindPw] = useState(false);

  // 로그인 폼 제출d 이벤트 처리
  const handleSubmit = (e) => {
    e.preventDefault();

    // database 의 id 와 pswrd 로직을 비교하는 분기문
    if (!admId || !admPswrd) {
      alert("아이디와 비밀번호를 입력해주세요.");
      return;
    }

    const loginData = {
      admId: admId,
      admPswrd: admPswrd,
    };

    console.log("데이터: ", JSON.stringify(loginData));

    
    // axois로 백엔드와 통신 로직
    axios.post('/admin/login', loginData)
      .then((resp) => {
        if(resp.status === 200){
          setAdminInfo({ admId : resp.data.admId, admName : resp.data.admName});
          // 경로 이동
          navigate('/admin/board/JobPosting');
          alert("관리자 " + admId + "계정으로 로그인 되었습니다.");
        }
      })
      .catch((error) => {
        if(error.response && error.response.status === 401){
          alert("아이디 또는 비밀번호가 틀립니다");
          
        } else {
          // console.log("Response Error: ", error.response.data);
          alert("에러내용: " + JSON.stringify(error.response));
        }
      });
  };

  return (
    <div className='display-6'>
      {/* 로그인 화면 */}
      <div className='page-header page-header-lg bg-color-white' />
      
      {!isFindAcc ? (
        <form onSubmit={handleSubmit} className='d-flex justify-content-center'>
          <div>
            <div>
              <img
                alt="Logo"
                width='160'
                src="/img/jobs-icon.png" />
              <div className='d-flex fs-6'>admin</div>
            </div>
            <br />
            <div className='p-1 bg-primary' />
            <div className='card'>
              <div className='px-3 py-3 fs-4'>
                ID<br />
                <input
                  type='text'
                  placeholder='관리자 계정'
                  value={admId}
                  onChange={(e) => setAdmId(e.target.value)}
                  id="admId"
                  name="admId"
                  className="form-control text-3 h-auto py-2"
                />
              </div>
              <div className='px-3 py-3 fs-4'>
                Password<br />
                <input
                  type='password'
                  placeholder='관리자 비밀번호'
                  value={admPswrd}
                  onChange={(e) => setAdmPswrd(e.target.value)}
                  id="admPswrd"
                  name="admPswrd"
                  className="form-control text-3 h-auto py-2"
                />
              </div>
              
              <div className="d-flex">
                <input
                  type="submit"
                  value="sign In"
                  className="btn btn-primary w-100 mb-3 px-3 py-3 fs-5"
                  data-loading-text="Loading..."
                />
              </div>
              
              <p className='d-flex justify-content-center'>
                forgot your access?{' '}
                <span
                  onClick={() => setIsFindAcc(true)}
                  style={{ cursor: 'pointer', color: 'blue' }}
                >
                  find acc
                </span>
              </p>
            </div>
          </div>
        </form>
      ) : (
        // 아이디 찾기와 비밀번호 찾기 화면 전환
        <div className=''>
          <div className='card-body'>
            {/* 아이디 찾기/비밀번호 찾기 탭 버튼 */}
            <div className='row'>
              <div className='d-flex'>
                <button onClick={() => setIsFindPw(false)} 
                        className='btn btn-primary w-100 mb-3 px-3 py-3 fs-6 '>
                  아이디찾기
                </button>

                <button onClick={() => setIsFindPw(true)} 
                        className=' btn btn-primary w-100 mb-3 px-3 py-3 fs-6 '>
                  비밀번호찾기
                </button>
              </div>
            </div>
          {/* 정보 조회하는 페이지 ( 페이지만 구현, 백엔드 로직 x 240930) */}
            <div> 
              {!isFindPw ? (
                <>
                  {/* 아이디 찾기 폼 */}
                  <div className='fs-5'>
                    이름<br />
                    <input
                      type='text'
                      placeholder='이름을 입력해주세요'
                      className="form-control text-3 h-auto py-2"
                    />
                  </div>
                  <div className='fs-5'>
                    이메일<br />
                    <input
                      type='email'
                      placeholder='이메일을 입력해주세요'
                      className="form-control text-3 h-auto py-2"
                    />
                  </div>
                  <div>
                    <button type='submit' 
                            className='btn btn-primary w-100 mb-3 px-3 py-3 fs-5'>
                      아이디 찾기
                    </button>
                  </div>
                </>
              ) : (
                <>
                  {/* 비밀번호 찾기 폼 */}
                  <div className='fs-5'>
                    아이디<br />
                    <input
                      type='text'
                      placeholder='아이디를 입력해주세요'
                      className="form-control text-3 h-auto py-2"
                    />
                  </div>
                  <div className='fs-5'>
                    이름<br />
                    <input
                      type='text'
                      placeholder='이름을 입력해주세요'
                      className="form-control text-3 h-auto py-2"
                    />
                  </div>
                  <div className='fs-5'>
                    이메일<br />
                    <input
                      type='email'
                      placeholder='이메일을 입력해주세요'
                      className="form-control text-3 h-auto py-2"
                    />
                  </div>
                  <div>
                    <button type='submit' 
                            className='btn btn-primary w-100 mb-3 px-3 py-3 fs-5'>
                      비밀번호 찾기
                    </button>
                  </div>
                </>
              )}
              <br />           
            </div>
          </div>
        </div>
      )}
    </div>
  );
}

export default AdminLogin;
