import React, { useState, useEffect } from 'react';
import axios from 'axios';
// import { useSelector } from 'react-redux';

const EnterChangePassword = () => {
  const [agree, setAgree] = useState(false); // 유의사항 동의
  const [entrprsPswrd, setEntrprsPswrd] = useState(''); // 기존 비밀번호
  const [entrprsPswrdChng, setEntrprsPswrdChng] = useState(''); // 변경할 비밀번호
  const [entrprsPswrdChngCheck, setEntrprsPswrdChngCheck] = useState(''); // 변경할 비밀번호 확인
  const [entrprsSp, setEntrprsSp] = useState(''); // 기업회원순번

  const [errorEntrprsPswrdChng, setErrorEntrprsPswrdChng] = useState(''); // 변경할 비밀번호 에러
  const [errorPswrdConfirm, setErrorPswrdConfirm] = useState(''); // 비밀번호 형식 에러

  // Redux store에서 데이터 가져오기 (필요한 경우)
  // const enterMember = useSelector(state => state.enterMember); // 주석처리된 데이터값

  // useEffect(() => {
  //   if (enterMember) {
  //     setEntrprsSp(enterMember.pk); // 기업회원순번 설정
  //   }
  // }, [enterMember]);

  // 비밀번호 변경하기
  // const handleSubmit = async (e) => {
  //   e.preventDefault();
  //   console.log("비밀번호 변경");

  //   if (!/^[A-Za-z0-9]{4,20}$/.test(entrprsPswrdChng)) {
  //     setErrorEntrprsPswrdChng("변경할 비밀번호 형식이 올바르지 않습니다.");
  //     return alert("변경할 비밀번호 형식이 올바르지 않습니다.");
  //   }

  //   if (!/^[A-Za-z0-9]{4,20}$/.test(entrprsPswrdChngCheck)) {
  //     setErrorEntrprsPswrdChng("변경할 비밀번호 형식이 올바르지 않습니다.");
  //     return alert("변경할 비밀번호 형식이 올바르지 않습니다.");
  //   }

  //   if (entrprsPswrdChng !== entrprsPswrdChngCheck) {
  //     setErrorPswrdConfirm("변경할 비밀번호가 일치하지 않습니다.");
  //     return alert("변경할 비밀번호가 일치하지 않습니다.");
  //   }

  //   const data = {
  //     entrprsPswrd,
  //     entrprsPswrdChng,
  //     entrprsPswrdChngCheck,
  //     entrprsSp,
  //   };

  //   try {
  //     const res = await axios.post('http://localhost:80/enter/enterChangPassword', data);
  //     console.log(res.data);
  //     if (res.data === '수정완료') {
  //       alert('비밀번호가 변경되었습니다.');
  //       setEntrprsPswrd('');
  //       setEntrprsPswrdChng('');
  //       setEntrprsPswrdChngCheck('');
  //     } else if (res.data === '비밀번호 불일치') {
  //       alert('비밀번호가 일치하지 않습니다.');
  //       setEntrprsPswrd('');
  //       setEntrprsPswrdChng('');
  //       setEntrprsPswrdChngCheck('');
  //     }
  //   } catch (error) {
  //     console.error(error);
  //   }
  // };

  return (
    <>
      <section className="page-header page-header-modern bg-color-grey page-header-lg">
        <div className="container">
          <div className="row">
            <div className="col-md-12 align-self-center p-static order-2 text-center">
              <h1 className="font-weight-bold text-dark">비밀번호 변경</h1>
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

                {/* <form onSubmit={handleSubmit}> */}
                <form>
                  <div className="form-group">
                    <label htmlFor="userId">기존비밀번호(필수)</label>
                    <input
                      type="password"
                      className="form-control"
                      value={entrprsPswrd}
                      onChange={(e) => setEntrprsPswrd(e.target.value)}
                      required
                    />
                  </div>
                  <div className="form-group">
                    <label htmlFor="applicant">변경할 비밀번호(필수)</label>
                    <input
                      type="password"
                      className="form-control"
                      value={entrprsPswrdChng}
                      onChange={(e) => setEntrprsPswrdChng(e.target.value)}
                      placeholder="영문, 숫자를 포함한 4자 이상 20자 이내"
                      required
                    />
                  </div>
                  <div className="form-group">
                    <label htmlFor="applicant">변경된 비밀번호 확인(필수)</label>
                    <input
                      type="password"
                      className="form-control"
                      value={entrprsPswrdChngCheck}
                      onChange={(e) => setEntrprsPswrdChngCheck(e.target.value)}
                      placeholder="영문, 숫자를 포함한 4자 이상 20자 이내"
                      required
                    />
                  </div>
                  <div className="form-check">
                    <input
                      type="checkbox"
                      className="form-check-input"
                      checked={agree}
                      onChange={(e) => setAgree(e.target.checked)}
                      required
                    />
                    <label className="form-check-label" htmlFor="agree">
                      유의사항을 모두 확인 했으며, 이에 동의합니다.
                    </label>
                  </div>
                  <button type="submit" className="btn btn-danger mt-3" disabled={!agree}>
                    변경하기
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

export default EnterChangePassword;
