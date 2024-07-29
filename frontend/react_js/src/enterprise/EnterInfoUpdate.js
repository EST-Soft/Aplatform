import React, { useState } from "react";
import "./EnterInfoUpdate.css";

const EnterInfoUpdate = () => {
    const [entrprsId, setEntrprsId] = useState(''); // 기업아이디
    const [entrprsName, setEntrprsName] = useState(''); // 기업명
    const [zonecode, setZonecode] = useState(''); // 우편번호
    const [entrprsAdrs, setEntrprsAdrs] = useState(''); // 상세주소
    const [entrprsHp, setEntrprsHp] = useState(''); // 기업홈페이지
    const [entrprsPicName, setEntrprsPicName] = useState(''); // 담당자 이름
    const [entrprsPicEml, setEntrprsPicEml] = useState(''); // 담당자 이메일주소
    const [entrprsPicMp, setEntrprsPicMp] = useState(''); // 담당자 전화번호
    const [imgUrl, setImgUrl] = useState(''); // 이미지 임시 URL 저장
    const [img, setImg] = useState(0);
    const [file, setFile] = useState(null);
    const [errorEntrprsPicMp, setErrorEntrprsPicMp] = useState('');

    // useEffect(() => {
    //   const fetchData = async () => {
    //     try {
    //       const res = await axios.get('http://localhost:80/enter/getEnterInfo', {
    //         params: { entrprsSq: entrprsSq }
    //       });
    //       setEntrprsSq(res.data.entrprs_sq);
    //       setEntrprsId(res.data.entrprs_id);
    //       setEntrprsName(res.data.entrprs_name);
    //       setEntrprsAdrs(res.data.entrprs_adrs);
    //       setEntrprsHp(res.data.entrprs_hp);
    //       setEntrprsPicName(res.data.entrprs_pic_name);
    //       setEntrprsPicEml(res.data.entrprs_pic_eml);
    //       setEntrprsPicMp(res.data.entrprs_pic_mp);
    //       setZonecode(res.data.entrprs_zip_code);
    //     } catch (error) {
    //       console.error(error);
    //     }
    //   };
    //   fetchData();
    // }, [entrprsSq]);

    const openPostcode = () => {
        // Daum 주소검색 API를 사용하는 실제 코드
        // new window.daum.Postcode({
        //   oncomplete: (data) => {
        //     setZonecode(data.zonecode);
        //     setEntrprsAdrs(data.roadAddress);
        //   }
        // }).open();
    };

    const handleSubmit = async () => {
        if (!entrprsId || !entrprsName || !entrprsPicName || !entrprsPicMp || !entrprsPicEml) {
            alert('필수 항목을 모두 입력해 주세요.');
            return;
        }

        if (!/^01([0|1|6|7|8|9]?)?([0-9]{3,4})?([0-9]{4})$/.test(entrprsPicMp)) {
            setErrorEntrprsPicMp("휴대폰 번호 형식이 올바르지 않습니다. (-를 제외한 10자리 또는 11자리 숫자)");
            return alert(errorEntrprsPicMp);
        }

        try {
            const data = {
                entrprsId,
                entrprsName,
                entrprsAdrs,
                entrprsHp,
                entrprsPicName,
                entrprsPicEml,
                entrprsPicMp,
                entrprsZoneCode: zonecode
            };

            // Replace with actual API call
            // const res = await axios.post('http://localhost:80/enter/updateEnterInfo', data);
            // if (res.data === '수정완료') {
            //   alert('수정완료 되었습니다.');
            // } else {
            //   alert('수정 실패');
            // }
        } catch (error) {
            console.error(error);
        }
    };

    const uploadImg = (e) => {
        const selectedFile = e.target.files[0];
        if (selectedFile) {
            const url = URL.createObjectURL(selectedFile);
            setImgUrl(url);
            setImg(1);
            setFile(selectedFile);
        } else {
            console.error('No file selected');
            setImg(0);
        }
    };

    return (
        <>
            <section className="page-header page-header-modern bg-color-grey page-header-lg">
                <div className="container">
                    <div className="row">
                        <div className="col-md-12 align-self-center p-static order-2 text-center">
                            <h1 className="font-weight-bold text-dark">기업정보 수정</h1>
                        </div>
                    </div>
                </div>
            </section>

            <div className="d-flex justify-content-center mb-4">
                <div className="profile-image-outer-container">
                    <div className="profile-image-inner-container bg-color-primary">
                        <img src="/assets/avatar.jpg" alt="Profile Image" style={{ display: img === 0 ? 'block' : 'none' }} />
                        <img src={imgUrl} alt="Profile Image" style={{ display: img === 1 ? 'block' : 'none' }} />
                        <span className="profile-image-button bg-color-dark">
                            <i className="fas fa-camera text-light"></i>
                        </span>
                    </div>
                    <input type="file" id="file" className="form-control profile-image-input" onChange={uploadImg}
                        accept="image/gif, image/jpeg, image/png" />
                </div>
            </div>

            <div className="form-container">
                <div className="col-lg-11">
                    <form role="form" className="needs-validation" onSubmit={handleSubmit}>
                        <div className="form-group row">
                            <label className="col-lg-3 col-form-label form-control-label line-height-9 pt-2 text-2 required">
                                아이디
                            </label>
                            <div className="col-lg-9">
                                <input className="form-control text-3 h-auto py-2" type="text"
                                    name="entrprsId" value={entrprsId} readOnly />
                            </div>
                        </div>
                        <div className="form-group row">
                            <label className="col-lg-3 col-form-label form-control-label line-height-9 pt-2 text-2 required">
                                기업명
                            </label>
                            <div className="col-lg-9">
                                <input className="form-control text-3 h-auto py-2" type="text"
                                    name="entrprsName" value={entrprsName} onChange={(e) => setEntrprsName(e.target.value)} required />
                            </div>
                        </div>
                        <div className="form-group row">
                            <label className="col-lg-3 col-form-label form-control-label line-height-9 pt-2 text-2">
                                기업홈페이지
                            </label>
                            <div className="col-lg-9">
                                <input className="form-control text-3 h-auto py-2" type="url"
                                    name="entrprsHp" value={entrprsHp} onChange={(e) => setEntrprsHp(e.target.value)} />
                            </div>
                        </div>
                        <div className="form-group row mb-4">
                            <label className="col-lg-3 col-form-label form-control-label line-height-9 pt-2 text-2 required">기업 주소</label>
                            <div className="col-lg-7">
                                <input className="form-control text-3 h-auto py-2" type="text"
                                    name="entrprsAdrs" value={zonecode} onChange={(e) => setZonecode(e.target.value)} placeholder="우편번호" />
                            </div>
                            <div className="col-lg-2">
                                <input className="btn btn-primary text-3 h-auto py-2"
                                    type="button" name="entrprsId" value="검 색"
                                    onClick={openPostcode} />
                            </div>
                        </div>

                        <div className="form-group row">
                            <label
                                className="col-lg-3 col-form-label form-control-label line-height-9 pt-2 text-2 "></label>
                            <div className="col-lg-9">
                                <input className="form-control text-3 h-auto py-2" type="text" name="entrprsPicName"
                                    placeholder="상세주소" value={entrprsAdrs} onChange={(e) => setEntrprsAdrs(e.target.value)} />
                            </div>
                        </div>

                        <div className="form-group row">
                            <label className="col-lg-3 col-form-label form-control-label line-height-9 pt-2 text-2 required">
                                기업 담당자
                            </label>
                            <div className="col-lg-9">
                                <input className="form-control text-3 h-auto py-2" type="text"
                                    name="entrprsPicName" value={entrprsPicName} onChange={(e) => setEntrprsPicName(e.target.value)} required />
                            </div>
                        </div>
                        <div className="form-group row">
                            <label className="col-lg-3 col-form-label form-control-label line-height-9 pt-2 text-2 required">
                                기업 담당자 전화번호
                            </label>
                            <div className="col-lg-9">
                                <input className="form-control text-3 h-auto py-2" type="text"
                                    name="entrprsPicMp" value={entrprsPicMp} onChange={(e) => setEntrprsPicMp(e.target.value)} required />
                            </div>
                        </div>
                        <div className="form-group row">
                            <label className="col-lg-3 col-form-label form-control-label line-height-9 pt-2 text-2 required">
                                기업 담당자 이메일주소
                            </label>
                            <div className="col-lg-9">
                                <input className="form-control text-3 h-auto py-2" type="text"
                                    name="entrprsPicEml" value={entrprsPicEml} onChange={(e) => setEntrprsPicEml(e.target.value)} required />
                            </div>
                        </div>
                        <div className="form-group row">
                            <div className="form-group col-lg-9"></div>
                            <div className="d-flex justify-content-center">
                                <div className="form-group">
                                    <input
                                        type="button"
                                        value="Cancel"
                                        className="btn btn-danger btn-modern float-end"
                                    />
                                </div>
                                <div className="form-group">
                                    <input
                                        type="submit"
                                        value="Save"
                                        className="btn btn-primary btn-modern float-end"
                                    />
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </>
    );
};

export default EnterInfoUpdate;
