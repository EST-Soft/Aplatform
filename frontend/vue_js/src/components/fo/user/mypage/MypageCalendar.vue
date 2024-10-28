<template>
    <FullCalendar :options="calendarOptions" ref="calendar"/>
</template>

<script setup>
    import { defineProps, ref, defineExpose } from 'vue'
    import FullCalendar from '@fullcalendar/vue3'
    import dayGridPlugin from '@fullcalendar/daygrid'

    const props = defineProps({
        events: {
            type : Array
        },
        customButtons: {
            type : Object
        }
    });

    const calendar = ref(null);

    const calendarOptions = {
        plugins: [dayGridPlugin],
        customButtons: props.customButtons,
        headerToolbar:{
            right: 'today myPrev,myNext'
        },
        initialView: 'dayGridMonth',
        // dayMaxEvents: true,              //한 날짜에 너무 많은 이벤트 있으면 +19 more 이런식으로 표현됨
        initialEvents: props.events
    }

    defineExpose({
        calendar
    });

</script>


<!-- 참고할만한 fullcalendar 내용 : https://velog.io/@kyh196201/FullCalendar.io -->