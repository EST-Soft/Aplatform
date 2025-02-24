// UserIdForm.jsx
import React from "react";

export default function UserIdForm({ handleLoginSubmit }) {
  return (
    <form onSubmit={(e) => handleLoginSubmit(e, "id")}>
      <div className="row">
        <div className="form-group col">
          <label className="form-label mb-1 text-2">이름</label>
          <input
            type="text"
            maxLength="100"
            className="form-control text-3 h-auto py-2"
            name="name"
            placeholder="이름을 입력해주세요"
            autoComplete="off"
            onFocus={(e) => (e.target.placeholder = "")}
            onBlur={(e) => (e.target.placeholder = "이름을 입력해주세요")}
          />
        </div>
      </div>
      <div className="row">
        <div className="form-group col">
          <label className="form-label mb-1 text-2">이메일</label>
          <input
            type="email"
            maxLength="100"
            className="form-control text-3 h-auto py-2"
            name="email"
            placeholder="이메일을 입력해주세요"
            autoComplete="off"
            onFocus={(e) => (e.target.placeholder = "")}
            onBlur={(e) => (e.target.placeholder = "이메일을 입력해주세요")}
          />
        </div>
      </div>
      <div className="d-flex justify-content-center">
        <input
          type="submit"
          value="아이디찾기"
          className="btn btn-primary w-100 mb-3"
          data-loading-text="Loading..."
        />
      </div>
    </form>
  );
}
