import React, { useEffect, useState } from 'react';
import axios from 'axios';
import Calendar from './MypageCalendar';
import { formatDateYMD } from '../tools'; // tools.js 파일에서 함수 import
import './Mypagehome.css';

const MypageHome = () => {
  const [result, setResult] = useState(null);
  const [calendarEvents, setCalendarEvents] = useState([]);
  const [month, setMonth] = useState(new Date().getMonth() + 1);
  const [checkedMonth, setCheckedMonth] = useState([new Date().getMonth() + 1]);

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
  const fetchCalendarData = async () => {
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
      fetchCalendarData(newMonth);
      return newMonth;
    });
  };

  // 데이터와 캘린더 데이터 가져오기
  useEffect(() => {
    fetchData();
  }, [month]);

  return (
    <div className="container py-4">
      <  div className="row">
        {/* 메인 콘텐츠 */}
        <div className="col-lg-9">
          <div className="main-content">
            <div className="row" style={{ paddingBottom: '20px' }}>
              <div className="col-3" style={{ display: 'flex', alignItems: 'center', justifyContent: 'center' }}>
                <div className="thumb-info-side-image-wrapper">
                  <img src="/assets/profile.webp" className="img-fluid" alt="" style={{ width: '140px' }} />
                </div>
              </div>
              <div className="col-9 table-container">
                <table className="table table-bordered" style={{ marginBottom: 0 }}>
                  <thead>
                    <tr>
                      <th colSpan="3">
                        {/* 여기에 콘텐츠 추가 */}
                      </th>
                    </tr>
                  </thead>
                  <tbody style={{ textAlign: 'center' }}>
                    <tr>
                      {/* 여기에 콘텐츠 추가 */}
                    </tr>
                  </tbody>
                </table>
              </div>
            </div>  
            <div className="row" style={{ paddingTop: '20px' }}>
              <table className="table table-bordered" style={{ textAlign: 'center' }}>
                <tbody>
                  <tr>
                    <th>등록한 이력서&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{result?.myState.rsm_cnt || 0}</th>
                    <th>추천 공고&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(로직 준비중)</th>
                    <th>스크랩 공고&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{result?.myState.scrap_cnt || 0}</th>
                    <th>받은 제안&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{result?.myState.prpsl_cnt || 0}</th>
                  </tr>
                </tbody>
              </table>
            </div>
            <div className="row" style={{ paddingTop: '20px', borderBottom: '1px solid #eaeaea' }}>
              <div className="col pb-3">
                <table className="table table-bordered table-apply-state">
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
                      <td>{result?.applyStateSum || 0}</td>
                      <td>{result?.EachCndtnApplyCount.cndtn_402 || 0}</td>
                      <td>{result?.EachCndtnApplyCount.cndtn_401 || 0}</td>
                      <td>{result?.EachCndtnApplyCount.cndtn_405 || 0}</td>
                      <td>{result?.EachCndtnApplyCount.cndtn_406 || 0}</td>
                      <td>{result?.EachCndtnApplyCount.cndtn_408 || 0}</td>
                      <td>{result?.EachCndtnApplyCount.cndtn_407 || 0}</td>
                      <td>(준비중)</td>
                    </tr>
                  </tbody>
                </table>
              </div>
            </div>
            <div className="row" style={{ paddingTop: '20px' }}>
              <Calendar events={calendarEvents} customButtons={{
                myPrev: {
                  text: '<',
                  click: () => handleMonthChange('prev')
                },
                myNext: {
                  text: '>',
                  click: () => handleMonthChange('next')
                }
              }} />
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default MypageHome;
