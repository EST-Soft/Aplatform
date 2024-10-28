import React, { useState, useEffect } from 'react';

// React 컴포넌트
const EnterSignOut = () => {
  // 상태 정의
  const [entrprsId, setEntrprsId] = useState(''); // 기업회원 아이디
  const [entrprsPswrd, setEntrprsPswrd] = useState(''); // 기업회원 비밀번호
  const [agree, setAgree] = useState(false); // 약관 동의
  const [entrprsSq, setEntrprsSq] = useState(''); // 기업회원 순번

  // 데이터 가져오기
  useEffect(() => {
    // 주석 처리된 코드
    // const fetchData = async () => {
    //   try {
    //     const res = await axios.get('http://localhost:80/enter/getEnterInfo', {
    //       params: { entrprsSq }
    //     });
    //     setEntrprsId(res.data.entrprs_id);
    //     console.log(res.data);
    //   } catch (error) {
    //     console.error(error);
    //   }
    // };

    // fetchData();
  }, [entrprsSq]);

  // 탈퇴하기
  const handleSubmit = (e) => {
    e.preventDefault();

    // 주석 처리된 코드
    // if (window.confirm("정말 탈퇴하시겠습니까?")) { // 탈퇴 신청 여부
    //   const data = {
    //     entrprsPswrd,
    //     entrprsSq
    //   };

    //   try {
    //     const res = await axios.post('http://localhost:80/enter/enterSignOut', data);
    //     console.log(res.data);
    //     if (res.data === '비밀번호 불일치') {
    //       alert("비밀번호가 일치하지 않습니다.");
    //       setEntrprsPswrd('');
    //     } else if (res.data === '탈퇴완료') {
    //       alert('탈퇴 되었습니다.');
    //       window.location.href = "http://localhost:8080/enter/login";
    //     }
    //   } catch (error) {
    //     console.error(error);
    //   }
    // } else { // 취소
    //   setEntrprsPswrd('');
    // }
  };

  return (
    <>
      <section className="page-header page-header-modern bg-color-grey page-header-lg">
        <div className="container">
          <div className="row">
            <div className="col-md-12 align-self-center p-static order-2 text-center">
              <h1 className="font-weight-bold text-dark">회원 탈퇴</h1>
            </div>
          </div>
        </div>
      </section>

      <div className="container mt-5">
        <div className="row justify-content-center">
          <div className="col-md-8">
            <div className="card">
              <div className="card-body">
                <p className="text-dark">
                  지금까지 이용해주셔서 감사 드립니다. 탈퇴하기 전 아래 유의사항을 확인해 주세요.
                </p>
                <p className="text-muted">
                  탈퇴하신 아이디는 복구가 불가능하며, 추후 동일한 아이디로 재가입이 되지 않습니다.
                  잡코리아와 알바몬 사이트에서 동시에 탈퇴됩니다.
                </p>
                <p className="text-muted">
                  이력서 정보, 구직 활동내역, 유료서비스, e-머니가 모두 삭제되며, 삭제된 데이터는 복구되지 않습니다.
                  회원탈퇴 시 등록한 게시물은 삭제되지 않으므로, 삭제를 원하시면 회원탈퇴 전에 삭제해 주시기 바랍니다.
                  소셜 로그인 회원의 경우, 모든 정보가 삭제되며 같은 소셜 아이디로 재가입 시 신규 회원으로 가입됩니다.
                </p>

                <form onSubmit={handleSubmit}>
                  <div className="form-group">
                    <label htmlFor="userId">아이디(필수)</label>
                    <input
                      type="text"
                      className="form-control"
                      value={entrprsId}
                      readOnly
                      required
                    />
                  </div>
                  <div className="form-group">
                    <label htmlFor="applicant">비밀번호(필수)</label>
                    <input
                      type="password"
                      className="form-control"
                      value={entrprsPswrd}
                      onChange={(e) => setEntrprsPswrd(e.target.value)}
                      placeholder="비밀번호를 입력해주세요"
                      required
                    />
                  </div>
                  <div className="form-check">
                    <input
                      type="checkbox"
                      className="form-check-input"
                      id="agree"
                      checked={agree}
                      onChange={(e) => setAgree(e.target.checked)}
                      required
                    />
                    <label className="form-check-label" htmlFor="agree">
                      유의사항을 모두 확인 했으며, 이에 동의합니다.
                    </label>
                  </div>
                  <button
                    type="submit"
                    className="btn btn-danger mt-3"
                    disabled={!agree}
                  >
                    탈퇴하기
                  </button>
                </form>
              </div>
            </div>
          </div>
        </div>
      </div>
    </>
  );
};

export default EnterSignOut;
