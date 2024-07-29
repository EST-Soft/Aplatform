import React, { useRef, useEffect } from 'react';
// 패키지 설치 필요(FullCalendar), (dayGridPlugin) [npm install @fullcalendar/react @fullcalendar/daygrid]
import FullCalendar from '@fullcalendar/react';
import dayGridPlugin from '@fullcalendar/daygrid';

/**
 * FullCalendar 컴포넌트는 React로 구현된 FullCalendar 라이브러리를 사용합니다.
 * @param {Object} props - 컴포넌트 속성
 * @param {Array} props.events - 초기 이벤트 배열
 * @param {Object} props.customButtons - 커스텀 버튼 설정
 */
const Calendar = (props) => {
  const calendarRef = useRef(null);

  // 캘린더 옵션 설정
  const calendarOptions = {
    plugins: [dayGridPlugin],
    customButtons: props.customButtons,
    headerToolbar: {
      right: 'today myPrev,myNext'
    },
    initialView: 'dayGridMonth',
    // dayMaxEvents: true,              // 한 날짜에 너무 많은 이벤트가 있으면 +19 more 이런 식으로 표현됨
    initialEvents: props.events
  };

  // 컴포넌트가 마운트되거나 props가 변경될 때 캘린더의 초기 이벤트를 설정
  useEffect(() => {
    if (calendarRef.current) {
      const calendarApi = calendarRef.current.getApi();
      calendarApi.addEventSource(props.events);
    }
  }, [props.events]);

  return (
    <FullCalendar
      ref={calendarRef}
      {...calendarOptions}
    />
  );
};

export default Calendar;
