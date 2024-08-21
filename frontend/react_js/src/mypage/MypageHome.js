import React, { useEffect, useState } from 'react';
import axios from 'axios';
import Calendar from './MypageCalendar';
import { formatDateYMD } from '../tools'; // tools.js 파일에서 함수 import
import './Mypagehome.css';
import profileImage from '../assets/profile.webp';
const MypageHome = () => {
  const [result, setResult] = useState(null);
  const [calendarEvents, setCalendarEvents] = useState([]);
  const [month, setMonth] = useState(new Date().getMonth() + 1);
  const [checkedMonth, setCheckedMonth] = useState([new Date().getMonth() + 1]);
  const [applyStateSum,setApplyStatesum]= useState(null);
  // 날짜 포맷 함수
  const formatDateYMD = (dateString) => {
    const date = new Date(dateString);
    return date.toISOString().split('T')[0]; // "YYYY-MM-DD" 형식으로 반환
  };

  // 데이터를 가져오는 함수
  const fetchData = async () => {
    try {
      const response = await axios.get('/user/mypage/', {
        params: { mbr_sq: 1, month }
      });
      setResult(response.data);
      setCalendarEvents(makeCalendarDatas(response.data.calendarData));
      setApplyStatesum(getApplyStateSum());
    } catch (error) {
      console.error('데이터 가져오기 실패', error);
    }
  };

  // 이력서 상태 합계를 계산하는 함수
  const getApplyStateSum = () => {
    if (!result || !result.EachCndtnApplyCount) {
      return 0;
    }

    let sum = 0;
    for (const key in result.EachCndtnApplyCount) {
      sum += result.EachCndtnApplyCount[key];
    }
    return sum;
  };

  // Calendar 이벤트 생성 함수
  const makeCalendarDatas = (toParsingData) => {
    if (!toParsingData) {
      return [];
    }

    return toParsingData.map((temp) => {
      let event = {
        id: temp.jbp_sq,
        title: '',
        date: temp.dtm,
        backgroundColor: '',
        description: temp.jbp_tl
      };

      switch (temp.tbl_type) {
        case 'S':
          event.title = '스크랩공고마감';
          event.backgroundColor = '#d3d3d3';
          break;
        case 'I':
          event.title = '면접';
          event.backgroundColor = '#add8e6';
          break;
        case 'PP':
          event.title = '제안받은공고마감';
          event.backgroundColor = '#f08080';
          break;
        case 'AC':
          event.title = '지원공고';
          event.backgroundColor = '#87cefa';
          break;
        default:
          break;
      }
      return event;
    });
  };

  // 캘린더 데이터 가져오기
  const fetchCalendarData = async (month) => {
    if (!checkedMonth.includes(month)) {
      try {
        const response = await axios.get('/user/mypage/calendar', { params: { month } });
        const events = makeCalendarDatas(response.data);
        setCalendarEvents(events);
        setCheckedMonth([...checkedMonth, month]);
      } catch (error) {
        console.error('캘린더 데이터 가져오기 실패', error);
      }
    }
  };

  // 월 변경 핸들러
  const handleMonthChange = (direction) => {
    setMonth((prevMonth) => {
      const newMonth = direction === 'next' ? prevMonth + 1 : prevMonth - 1;
      console.log("newMonth",newMonth)
      fetchCalendarData(newMonth);
      return newMonth;
    });
  };

  // 데이터와 캘린더 데이터 가져오기
  useEffect(() => {
    fetchData();
  }, [month]);

  return (
    <div>
      {result && (
   <>
          <div className="row" style={{ paddingBottom: '20px', borderBottom: '1px solid #eaeaea' }}>
            <div className="col-3" style={{ display: 'flex', alignItems: 'center', justifyContent: 'center' }}>
              <div className="thumb-info-side-image-wrapper">
                <img src={profileImage} className="img-fluid" alt="" style={{ width: '140px' }} />
                {/* 나중에 야돈대신 들어갈 자리 :src="{{result.rsmInfo.rsm_img_file_url}}" */}
              </div>
            </div>
            <div className="col-9 table-container">
              <table  className="table table-bordered"  bordered style={{ marginBottom: 0 }}>
                <thead>
                  <tr>
                    <th colSpan="3">
                      <h2 className="font-weight-bold" style={{ marginBottom: '10px' }}>{result.rsmInfo.rsm_tl}</h2>
                      <span>-최종 수정일자&nbsp;&nbsp;:&nbsp;&nbsp;{formatDateYMD(result.rsmInfo.updt_dtm)}</span>
                      <span style={{ marginLeft: '20px' }}>-포지션 수락 여부&nbsp;&nbsp;:&nbsp;&nbsp;{result.mbrInfo.pstn_prpsl_accept_yn === 'Y' ? '수락' : '거절'}</span>
                    </th>
                  </tr>
                </thead>
                <tbody style={{ textAlign: 'center' }}>
                  <tr>
                    <th>{result.mbrInfo.mbr_name}</th>
                    <th>{result.mbrInfo.mbr_mp}</th>
                    <th>{result.mbrInfo.mbr_eml_adrs}</th>
                  </tr>
                </tbody>
              </table>
            </div>
          </div>
          <div className="row" style={{ paddingTop: '20px' }}>
            <table className="table table-bordered"  bordered style={{ textAlign: 'center' }}>
              <tr>
                <th>등록한 이력서&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{result.myState.rsm_cnt}</th>
                <th>추천 공고&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(로직 준비중)</th>
                <th>스크랩 공고&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{result.myState.scrap_cnt}</th>
                <th>받은 제안&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{result.myState.prpsl_cnt}</th>
              </tr>
            </table>
          </div>
          <div className="row" style={{ paddingTop: '20px', borderBottom: '1px solid #eaeaea' }}>
            <div className="col pb-3">
              <table bordered className="table table-bordered table-apply-state">
                <thead>
                  <tr>
                    <th rowSpan="2">전체</th>
                    <th colSpan="2">진행중</th>
                    <th colSpan="2">면접</th>
                    <th rowSpan="2">불합격</th>
                    <th rowSpan="2">합격</th>
                    <th rowSpan="2">마감</th>
                  </tr>
                  <tr>
                    <th>열람</th>
                    <th>미열람</th>
                    <th>대기</th>
                    <th>완료</th>
                  </tr>
                </thead>
                <tbody>
                  <tr>
                    <td>{applyStateSum}</td>
                    <td>{result.EachCndtnApplyCount.cndtn_402}</td>
                    <td>{result.EachCndtnApplyCount.cndtn_401}</td>
                    <td>{result.EachCndtnApplyCount.cndtn_405}</td>
                    <td>{result.EachCndtnApplyCount.cndtn_406}</td>
                    <td>{result.EachCndtnApplyCount.cndtn_408}</td>
                    <td>{result.EachCndtnApplyCount.cndtn_407}</td>
                    <td>(준비중)</td>
                  </tr>
                </tbody>
              </table>
            </div>
          </div>
          <div className="row" style={{ paddingTop: '20px' }}>
          <Calendar events={calendarEvents} onMonthChange={handleMonthChange} />
          </div>
        </>
      )}
      </div>
  );
};

export default MypageHome;
