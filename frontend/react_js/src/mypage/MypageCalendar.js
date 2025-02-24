import React, { useRef, useEffect } from 'react';
import FullCalendar from '@fullcalendar/react';
import dayGridPlugin from '@fullcalendar/daygrid';
import styled from 'styled-components';

// FullCalendar를 감싸는 스타일드 컴포넌트
const CalendarWrapper = styled.div`
  .fc .fc-scroller {
    overflow: auto;
  }

  .fc .fc-scroller::-webkit-scrollbar {
    width: 12px;
  }

  .fc .fc-scroller::-webkit-scrollbar-track {
    background: #f1f1f1;
  }

  .fc .fc-scroller::-webkit-scrollbar-thumb {
    background: #88888845;
    border-radius: 10px;
  }

  .fc .fc-scroller::-webkit-scrollbar-thumb:hover {
    background: #5555558a;
  }
  .fc-button {
    background-color: #007bff; /* 기본 배경 색상 */
    color: white;
    border: none;
    border-radius: 4px;
    padding: 5px 10px;
    cursor: pointer;

    &:disabled {
      background-color: #cccccc; /* 비활성화 상태 배경 색상 */
      cursor: not-allowed;
    }
  }

  .fc-button:not(:disabled):hover {
    background-color: #0056b3; /* 호버 시 배경 색상 */
  }
`;

const Calendar = (props) => {
  const calendarRef = useRef(null);

  // 캘린더 옵션 설정
  const calendarOptions = {
    plugins: [dayGridPlugin],
    customButtons: {
      myPrev: {
        text: '<',
        click: () => {
          if (calendarRef.current) {
            calendarRef.current.getApi().prev();
            props.onMonthChange('prev');
          }
        }
      },
      myNext: {
        text: '>',
        click: () => {
          if (calendarRef.current) {
            calendarRef.current.getApi().next();
            props.onMonthChange('next');
          }
        }
      },
    },
    headerToolbar: {
      left: 'title',
      center: '',
      right: 'today myPrev,myNext'
    },
    initialView: 'dayGridMonth',
    initialEvents: props.events,
  };

  useEffect(() => {
    if (calendarRef.current) {
      const calendarApi = calendarRef.current.getApi();
      calendarApi.addEventSource(props.events);
    }
  }, [props.events]);

  return (
    <CalendarWrapper>
      <FullCalendar
        ref={calendarRef}
        {...calendarOptions}
      />
    </CalendarWrapper>
  );
};

export default Calendar;
