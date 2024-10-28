import React, { useState } from 'react';
// import axios from 'axios';

// Define your React component
const EnterSignUp = () => {
  // Define state variables (commented out data handling)
  // const [cmnRgtrrtnNmbr, setCmnRgtrrtnNmbr] = useState('');
  // const [numState, setNumState] = useState(false);
  // const [entrprsId, setEntrprsId] = useState('');
  // const [entrprsIdYn, setEntrprsIdYn] = useState(false);
  // const [entrprsPswrd, setEntrprsPswrd] = useState('');
  // const [pswrdConfirm, setPswrdConfirm] = useState('');
  // const [entrprsPicName, setEntrprsPicName] = useState('');
  // const [entrprsName, setEntrprsName] = useState('');
  // const [entrprsPicMp, setEntrprsPicMp] = useState('');
  // const [entrprsPicEml, setEntrprsPicEml] = useState('');
  // const [entrprsEmlRcvYn, setEntrprsEmlRcvYn] = useState('');
  // const [entrprsPrvcyTrmsYn, setEntrprsPrvcyTrmsYn] = useState(false);
  // const [gndrCtryCd, setGndrCtryCd] = useState('');
  // const [emailKey, setEmailKey] = useState('');
  // const [emailCode, setEmailCode] = useState('');
  // const [errorEntrprsId, setErrorEntrprsId] = useState('');
  // const [errorEntrprsPswrd, setErrorEntrprsPswrd] = useState('');
  // const [errorEntrprsPswrdConfirm, setErrorEntrprsPswrdConfirm] = useState('');
  // const [errorEntrprsPicName, setErrorEntrprsPicName] = useState('');
  // const [errorEntrprsPicEml, setErrorEntrprsPicEml] = useState('');
  // const [errorEntrprsPicMp, setErrorEntrprsPicMp] = useState('');
  // const [errorEntrprsEmlRcvYn, setErrorEntrprsEmlRcvYn] = useState('');
  // const [errorEntrprsPrvcyTrmsYn, setErrorEntrprsPrvcyTrmsYn] = useState('');
  // const [errorPswrdConfirm, setErrorPswrdConfirm] = useState('');
  // const [errorEmlChck, setErrorEmlChck] = useState('');

  // const [state, setState] = useState({
  //   terms: [],
  // });

  const handleSubmit = (event) => {
    event.preventDefault();
    // Handle form submission
    alert('Form submitted');
  };

  // const fetchTerms = async () => {
  //   try {
  //     const { data } = await getTermsContents();
  //     setState(prevState => ({
  //       ...prevState,
  //       terms: data,
  //     }));
  //   } catch (error) {
  //     console.error("데이터를 가져오는 중 오류가 발생했습니다.", error);
  //   }
  // };

  // const emlSend = () => {
  //   alert("인증코드가 발송되었습니다.");
  //   // Placeholder for email sending logic
  // };

  // const emlChck = () => {
  //   alert("인증완료");
  //   // Placeholder for email checking logic
  // };
  

  return (
    <div role="main" className="main">
      <section className="page-header page-header-modern bg-color-grey page-header-lg">
        <div className="container">
          <div className="row">
            <div className="col-md-12 align-self-center p-static order-2 text-center">
              <h1 className="font-weight-bold text-dark">회원가입</h1>
            </div>
          </div>
        </div>
      </section>

      <div className="container py-4">
        <div className="row justify-content-center">
          <div className="col-md-6 col-lg-5">
            <form id="frmSignUp" onSubmit={handleSubmit}>
              <div className="form-group col">
                <label className="form-label text-color-dark text-3">
                  사업자 번호 입력 <span className="text-color-danger">*</span>
                </label>
                <input
                  type="text"
                  // value={cmnRgtrrtnNmbr}
                  // onChange={(e) => setCmnRgtrrtnNmbr(e.target.value)}
                  className="form-control form-control-lg text-4"
                  placeholder="사업자 번호 10자리를 입력해주세요"
                  // required
                />
              </div>
              {/* 사업자 번호 입력 끝 */}
              {/* Conditional Rendering */}
              <div>
                <div className="row">
                  <div className="form-group col">
                    <label className="form-label text-color-dark text-3">
                      아이디 <span className="text-color-danger">*</span>
                    </label>
                    <input
                      type="text"
                      // value={entrprsId}
                      // onChange={(e) => setEntrprsId(e.target.value)}
                      className="form-control form-control-lg text-4"
                      placeholder="영문, 숫자를 포함한 4자 이상 20자 이내"
                      // required
                    />
                    <div>
                      {/* {entrprsIdYn && <div>{errorEntrprsId}</div>} */}
                    </div>
                  </div>
                  <div className="form-group col-3">
                    <div className="form-label text-color-dark text-3">&nbsp;</div>
                    <button
                      type="button"
                      // onClick={idCheck}
                      className="btn btn-quaternary mb-2"
                    >
                      중복확인
                    </button>
                  </div>
                </div>

                <div className="row">
                  <div className="form-group col">
                    <label className="form-label text-color-dark text-3">
                      비밀번호 <span className="text-color-danger">*</span>
                    </label>
                    <input
                      type="password"
                      // value={entrprsPswrd}
                      // onChange={(e) => setEntrprsPswrd(e.target.value)}
                      className="form-control form-control-lg text-4"
                      placeholder="영문, 숫자를 포함한 4자 이상 20자 이내"
                      // required
                    />
                    <div>
                      {/* {errorEntrprsPswrd && <div className="error">{errorEntrprsPswrd}</div>} */}
                    </div>
                  </div>
                </div>
                <div className="row">
                  <div className="form-group col">
                    <label className="form-label text-color-dark text-3">
                      비밀번호 확인 <span className="text-color-danger">*</span>
                    </label>
                    <input
                      type="password"
                      // value={pswrdConfirm}
                      // onChange={(e) => setPswrdConfirm(e.target.value)}
                      className="form-control form-control-lg text-4"
                      // required
                    />
                    <div>
                      {/* {errorEntrprsPswrdConfirm && <div className="error">{errorEntrprsPswrdConfirm}</div>} */}
                    </div>
                  </div>
                </div>
                <div className="row">
                  <div className="form-group col">
                    <label className="form-label text-color-dark text-3">
                      이름 <span className="text-color-danger">*</span>
                    </label>
                    <input
                      type="text"
                      // value={entrprsPicName}
                      // onChange={(e) => setEntrprsPicName(e.target.value)}
                      className="form-control form-control-lg text-4"
                      // required
                    />
                    <div>
                      {/* {errorEntrprsPicName && <div className="error">{errorEntrprsPicName}</div>} */}
                    </div>
                  </div>
                </div>
                <div className="row">
                  <div className="form-group col">
                    <label className="form-label text-color-dark text-3">
                      기업명 <span className="text-color-danger">*</span>
                    </label>
                    <input
                      type="text"
                      // value={entrprsName}
                      // onChange={(e) => setEntrprsName(e.target.value)}
                      className="form-control form-control-lg text-4"
                      placeholder="기업명"
                      // required
                    />
                  </div>
                </div>
                <div className="row">
                  <div className="form-group col">
                    <label className="form-label text-color-dark text-3">
                      휴대폰 <span className="text-color-danger">*</span>
                    </label>
                    <input
                      type="text"
                      // value={entrprsPicMp}
                      // onChange={(e) => setEntrprsPicMp(e.target.value)}
                      className="form-control form-control-lg text-4"
                      placeholder="휴대폰 번호"
                      // required
                    />
                    <div>
                      {/* {errorEntrprsPicMp && <div className="error">{errorEntrprsPicMp}</div>} */}
                    </div>
                  </div>
                  <div className="form-group col-3">
                    <div className="form-label text-color-dark text-3">&nbsp;</div>
                    <button
                      type="button"
                      // onClick={smsSend}
                      className="btn btn-quaternary mb-2"
                    >
                      인증번호 발송
                    </button>
                  </div>
                </div>
                <div className="row">
                  <div className="form-group col">
                    <label className="form-label text-color-dark text-3">
                      이메일 <span className="text-color-danger">*</span>
                    </label>
                    <input
                      type="email"
                      // value={entrprsPicEml}
                      // onChange={(e) => setEntrprsPicEml(e.target.value)}
                      className="form-control form-control-lg text-4"
                      placeholder="이메일 주소"
                      // required
                    />
                    <div>
                      {/* {errorEntrprsPicEml && <div className="error">{errorEntrprsPicEml}</div>} */}
                    </div>
                  </div>
                  <div className="form-group col-3">
                    <div className="form-label text-color-dark text-3">&nbsp;</div>
                    <button
                      type="button"
                      // onClick={emlSend}
                      className="btn btn-quaternary mb-2"
                    >
                      인증번호 발송
                    </button>
                  </div>
                </div>
                <div className="row">
                  <div className="form-group col">
                    <label className="form-label text-color-dark text-3">
                      이메일 인증코드
                    </label>
                    <input
                      type="text"
                      // value={emailCode}
                      // onChange={(e) => setEmailCode(e.target.value)}
                      className="form-control form-control-lg text-4"
                      placeholder="인증코드"
                      // required
                    />
                    <div>
                      {/* {errorEmlChck && <div className="error">{errorEmlChck}</div>} */}
                    </div>
                  </div>
                  <div className="form-group col-3">
                    <div className="form-label text-color-dark text-3">&nbsp;</div>
                    <button
                      type="button"
                      // onClick={emlChck}
                      className="btn btn-quaternary mb-2"
                    >
                      인증확인
                    </button>
                  </div>
                </div>
                <div className="row">
                  <div className="form-group col">
                    <label className="form-label text-color-dark text-3">
                      이메일 수신 여부
                    </label>
                    <select
                      // value={entrprsEmlRcvYn}
                      // onChange={(e) => setEntrprsEmlRcvYn(e.target.value)}
                      className="form-control form-control-lg text-4"
                    >
                      <option value="Y">수신</option>
                      <option value="N">미수신</option>
                    </select>
                    <div>
                      {/* {errorEntrprsEmlRcvYn && <div className="error">{errorEntrprsEmlRcvYn}</div>} */}
                    </div>
                  </div>
                </div>
                <div className="row">
                  <div className="form-group col">
                    <div className="custom-control custom-checkbox">
                      <input
                        type="checkbox"
                        // checked={entrprsPrvcyTrmsYn}
                        // onChange={(e) => setEntrprsPrvcyTrmsYn(e.target.checked)}
                        className="custom-control-input"
                        id="customCheck1"
                        // required
                      />
                      <label className="custom-control-label text-color-dark text-3" htmlFor="customCheck1">
                        개인정보 취급방침 동의
                        <a
                          href="#"
                          onClick={(e) => e.preventDefault()}
                          className="font-weight-semibold"
                        >
                          {/* {state.terms.length > 0 ? "개인정보취급방침보기" : "개인정보취급방침 보기"} */}
                        </a>
                      </label>
                    </div>
                    <div>
                      {/* {errorEntrprsPrvcyTrmsYn && <div className="error">{errorEntrprsPrvcyTrmsYn}</div>} */}
                    </div>
                  </div>
                </div>

                <div className="form-group mb-4">
                  <button
                    type="submit"
                    className="btn btn-primary btn-modern float-right"
                  >
                    회원가입
                  </button>
                </div>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
  );
};

export default EnterSignUp;
