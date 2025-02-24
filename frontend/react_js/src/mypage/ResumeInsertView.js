import React, { useState, useEffect } from 'react';
import axios from 'axios';
// import AttachmentsResume from '@/components/fo/enterprise/resume/AttachmentsResume';
// import SelfintoductionsResume from '@/components/fo/enterprise/resume/SelfintoductionsResume';
// import CertificatesResume from '@/components/fo/enterprise/resume/CertificatesResume';
// import SkillsResume from '@/components/fo/enterprise/resume/SkillsResume';
// import SearchPopup from '@/components/fo/user/common/SearchPopup';

const ResumeForm = () => {
  const [formData, setFormData] = useState({
    title: '',
    name: '',
    gender: '',
    birthDate: '',
    phone: '',
    address: '',
    email: '',
    salary: '',
    education: '',
    grade: '',
    // 다른 데이터 필드들 추가
  });

  useEffect(() => {
    console.log('Component mounted');
    // 페이지 입장 시 정보 받아오기
    // 예: axios.get('http://localhost:80/resumes/getInfo').then(response => { ... });
  }, []);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prevData) => ({ ...prevData, [name]: value }));
  };

  const handleGenderChange = (e) => {
    setFormData((prevData) => ({ ...prevData, gender: e.target.value }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    console.log('Submitting form');

    const form = new FormData();
    for (const [key, value] of Object.entries(formData)) {
      form.append(key, value);
    }

    axios.post('http://localhost:80/resumes/test', form, {
      headers: {
        'Content-Type': 'multipart/form-data',
      },
    })
      .then((response) => {
        console.log(response);
      })
      .catch((error) => {
        console.error(error);
      });
  };

  return (
    <form id="completeForm" className="contact-form form-style-4 form-style-4-text-dark" onSubmit={handleSubmit}>
      <div className="row">
        <h1 className="form-group col-md-12">
          <strong className="font-weight-extra-bold">이력서 등록</strong>
        </h1>
        <hr className="gradient" />
      </div>
      <div className="col pb-3 pt-3">
        <div className="row">
          <div className="form-group col-md-6">
            <input
              type="text"
              value={formData.title}
              maxLength="20"
              className="form-control text-3 h-auto py-2"
              placeholder="제목"
              name="title"
              onChange={handleChange}
              required
            />
          </div>
        </div>
        <div className="row">
          <section className="call-to-action with-full-borders mb-2 mainContainer">
            <div className="col-sm-2 col-lg-2">
              <div className="col-sm-12 col-lg-12 imgContainer">
                <span className="img-thumbnail d-block">
                  <img
                    className="img-fluid"
                    src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSdraZyZ8CC81iPsZST1_TLB0SBOXC6wfvQ_Exx1uKkGsFpi4t_Mj4o8HhM_vPwI60yBF8&usqp=CAU"
                    alt="Profile"
                    style={{ width: '200px' }}
                  />
                  <a href="#" className="imgIcon">
                    <i className="fa-regular fa-trash-can"></i>
                  </a>
                </span>
              </div>
            </div>
            <div className="row col-sm-10 col-lg-10">
              <div className="col-sm-4 col-lg-4">
                <input
                  type="text"
                  value={formData.name}
                  maxLength="100"
                  className="form-control text-3 h-auto py-2"
                  placeholder="이름"
                  name="name"
                  onChange={handleChange}
                  required
                />
              </div>
              <div className="col-sm-3 col-lg-2">
                <div className="form-check form-check-inline">
                  <label className="form-check-label">
                    <input
                      className="form-check-input"
                      type="radio"
                      id="rsm_gndr_cd1"
                      value="201"
                      name="gender"
                      checked={formData.gender === '201'}
                      onChange={handleGenderChange}
                      required
                    />
                    남
                  </label>
                </div>
                <div className="form-check form-check-inline">
                  <label className="form-check-label">
                    <input
                      className="form-check-input"
                      type="radio"
                      id="rsm_gndr_cd2"
                      value="202"
                      name="gender"
                      checked={formData.gender === '202'}
                      onChange={handleGenderChange}
                      required
                    />
                    여
                  </label>
                </div>
              </div>
              <div className="col-sm-2 col-lg-2">
                <input
                  type="text"
                  value={formData.birthDate}
                  maxLength="100"
                  className="form-control text-3 h-auto py-2"
                  name="birthDate"
                  placeholder="생년월일"
                  onChange={handleChange}
                  required
                />
              </div>
              <div className="col-sm-2 col-lg-4">
                <input
                  type="text"
                  value={formData.phone}
                  maxLength="100"
                  className="form-control text-3 h-auto py-2"
                  name="phone"
                  placeholder="전화번호"
                  onChange={handleChange}
                  required
                />
              </div>
              <hr className="mt-3 mb-1" />
              <div className="col-sm-2 col-lg-8">
                <input
                  type="text"
                  value={formData.address}
                  maxLength="100"
                  className="form-control text-3 h-auto py-2"
                  name="address"
                  placeholder="주소"
                  onChange={handleChange}
                  required
                />
              </div>
              <div className="col-sm-3 col-lg-4">
                <input
                  type="text"
                  value={formData.email}
                  maxLength="100"
                  className="form-control text-3 h-auto py-2"
                  name="email"
                  placeholder="이메일"
                  onChange={handleChange}
                  required
                />
              </div>
              <hr className="mt-3 mb-1" />
              <div className="col-sm-4 col-lg-3">
                <input
                  type="text"
                  value={formData.salary}
                  maxLength="100"
                  className="form-control text-3 h-auto py-2"
                  name="salary"
                  placeholder="희망연봉"
                  onChange={handleChange}
                  required
                />
              </div>
              <div className="col-sm-3 col-lg-3">
                <input
                  type="text"
                  value={formData.education}
                  maxLength="100"
                  className="form-control text-3 h-auto py-2"
                  name="education"
                  placeholder="최종학력"
                  onChange={handleChange}
                  required
                />
              </div>
              <div className="col-sm-3 col-lg-3">
                <input
                  type="text"
                  value={formData.grade}
                  maxLength="100"
                  className="form-control text-3 h-auto py-2"
                  name="grade"
                  placeholder="학점"
                  onChange={handleChange}
                  required
                />
              </div>
              <div className="col-sm-3 col-lg-3"></div>
            </div>
          </section>
          <div className="row"></div>
        </div>
      </div>
      <div className="col pb-3 pt-1">
        <h4 className="mb-0">
          학력 &nbsp;
          <a href="#" className="btn btn-primary btn-circle mb-2">
            <i className="fa fa-plus"></i>
          </a>
        </h4>
        <hr className="mt-1 mb-2" />
        <div>
          {/* <SearchPopup /> */}
        </div>
      </div>
      <div className="col pb-3 pt-1">
        <h4 className="mb-0">
          경력 &nbsp;
          <a href="#" className="btn btn-primary btn-circle mb-2">
            <i className="fa fa-plus"></i>
          </a>
        </h4>
        <hr className="mt-1 mb-2" />
        <div>여기에 컴포넌트</div>
      </div>
      {/* <SkillsResume />
      <CertificatesResume />
      <SelfintoductionsResume /> */}
      <div className="col pb-3 pt-1">
        {/* <AttachmentsResume /> */}
        <div className="col-lg-12">
          <div className="float-end">
            <a href="#" className="btn btn-outline btn-xl btn-primary mb-2">저장</a>
            <a href="#" className="btn btn-outline btn-xl btn-light mb-2">취소</a>
            <button type="submit" className="btn btn-primary">테스트</button>
          </div>
        </div>
      </div>
    </form>
  );
};

export default ResumeForm;
