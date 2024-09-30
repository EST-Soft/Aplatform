import React, { useState, useEffect } from 'react';
import axios from 'axios';
// import ReactQuill from 'react-quill';
// import 'react-quill/dist/quill.snow.css';

function FAQ() {

    // P4 화면설계서 PPT 24p , adm-012
    /* 설계 의도 : QnA 가 포함된 아코디언 한 개를 생성해서
                  데이터베이스 상의 FaqSq가 증가할 때 마다 아코디언 컴포넌트 하위로
                  또 다른 아코디언이 생성되어 누적되는 형식으로 기획
                  240930 미완성. ( 화면 랜더링 해도 컴포넌트 한 칸만 뜰것임)
    */
    const [faqData, setFaqData] = useState([]);

    useEffect(() => {
        axios
            .get('faq')
            .then(response => setFaqData(response.data))
            .catch(error => console.error('FAQ 데이터가 안보임', error));
    }, []);

    return (
        <>
            <div className="col-xl-7">
                {/* <h4 className="font-weight-bold text-dark">자주 묻는 질문</h4> */}
                <div className="accordion accordion-lg" id="accordion5">
                    {faqData.length > 0 ? (
                        faqData.map(faq => (
                            <div className="card card-default" key={faq.faqSq}>
                                <div className="card-header">
                                    <h4 className="card-title m-0">
                                        <a className="accordion-toggle collapsed" 
                                           data-bs-toggle="collapse" 
                                           data-bs-parent="#accordion5" 
                                           data-bs-target={`#collapse${faq.faqSq}`}
                                           onClick={(e) => e.preventDefault()}
                                           aria-expanded="false"
                                           href="#">
                                            {faq.faqName}
                                        </a>
                                    </h4>
                                </div>
                                <div id={`collapse${faq.faqSq}`} className="collapse" data-bs-parent="#accordion5">
                                    <div className="card-body">
                                        <div dangerouslySetInnerHTML={{ __html: faq.faqCntnt }} />
                                    </div>
                                </div>
                            </div>
                        ))
                    ) : (
                        <div className="card card-default">
                            <div className="card-header">
                                <h4 className="card-title m-0">
                                    <a className="accordion-toggle collapsed" 
                                       data-bs-toggle="collapse" 
                                       data-bs-parent="#accordion5" 
                                       data-bs-target="#collapse0"
                                       onClick={(e) => e.preventDefault()}
                                       aria-expanded="false"
                                       href="#">
                                        데이터 없음
                                    </a>
                                </h4>
                            </div>
                            <div id="collapse0" className="collapse" data-bs-parent="#accordion5">
                                <div className="card-body">
                                    데이터가 없습니다.
                                </div>
                            </div>
                        </div>
                    )}
                </div>
            </div>
        </>
    );
}

export default FAQ;
