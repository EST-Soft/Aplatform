import React, { useState } from 'react';

const ResumeDetail = () => {
  const [selfintoductionDatas, setSelfintoductionDatas] = useState([]);
  const [count, setCount] = useState(1);

  const addComponents = () => {
    setSelfintoductionDatas((prevData) => [
      ...prevData,
      { id: count, title: '', content: '' }
    ]);
    setCount((prevCount) => prevCount + 1);
  };

  const removeComponents = (id) => {
    setSelfintoductionDatas((prevData) => prevData.filter(data => data.id !== id));
  };

  return (
    <div className="col pb-3 pt-1">
      <h4 className="mb-0">
        자기소개서 &nbsp;
        <button className="btn btn-primary btn-circle mb-2" onClick={addComponents}>
          <i className="fa fa-plus"></i>
        </button>
      </h4>
      <hr className="mt-1 mb-2" />
      <div>
        {selfintoductionDatas.map((selfintoductionData) => (
          <section className="call-to-action with-full-borders mb-2" key={selfintoductionData.id}>
            <div className="col-lg-12">
              <div className="form-group col">
                <div className="col-lg-5 form-check-inline">
                  <input
                    type="text"
                    maxLength="50"
                    className="form-control text-3 h-auto py-2"
                    name="title"
                    placeholder="제목"
                    value={selfintoductionData.title}
                    onChange={(e) => {
                      const updatedData = selfintoductionDatas.map((data) =>
                        data.id === selfintoductionData.id
                          ? { ...data, title: e.target.value }
                          : data
                      );
                      setSelfintoductionDatas(updatedData);
                    }}
                    required
                  />
                </div>
                <div className="col-lg-1 form-check-inline">
                  <button className="btn btn-primary btn-circle mb-2 imgIcon" onClick={() => removeComponents(selfintoductionData.id)}>
                    <i className="fa fa-minus"></i>
                  </button>
                </div>
              </div>
            </div>
            <div className="col-lg-12">
              <div className="form-group col">
                <textarea
                  maxLength="5000"
                  rows="8"
                  className="form-control text-3 h-auto py-2"
                  name="content"
                  placeholder="내용"
                  value={selfintoductionData.content}
                  onChange={(e) => {
                    const updatedData = selfintoductionDatas.map((data) =>
                      data.id === selfintoductionData.id
                        ? { ...data, content: e.target.value }
                        : data
                    );
                    setSelfintoductionDatas(updatedData);
                  }}
                  required
                ></textarea>
              </div>
            </div>
          </section>
        ))}
      </div>
    </div>
  );
};

export default ResumeDetail;
