<template>
    <div v-if="result && result.rsmInfo">
        <div class="row">
            <div class="col-6">
                <h4 style=" margin-bottom: 0; font-size: 23px; font-weight: bolder;">
                    포지션 제안 받기
                </h4>
            </div>
            <div class="col-6" style="display : flex">
                <input type="checkbox" v-model="ppAcceptYN" style="margin-left:auto; zoom:1.7;">
            </div>
        </div>


        <!-- 대표이력서 row -->
        <div class="row" style="margin-top: 35px;">
            <div class="col-6" style="padding-top: 7px;">
                <h4 style=" margin-bottom: 0;">
                    <svg xmlns="http://www.w3.org/2000/svg" width="25" height="25" fill="currentColor" class="bi bi-arrow-right-square-fill" viewBox="0 0 16 16">
                        <path d="M0 14a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2H2a2 2 0 0 0-2 2zm4.5-6.5h5.793L8.146 5.354a.5.5 0 1 1 .708-.708l3 3a.5.5 0 0 1 0 .708l-3 3a.5.5 0 0 1-.708-.708L10.293 8.5H4.5a.5.5 0 0 1 0-1"/>
                    </svg>
                    &nbsp;&nbsp;대표이력서
                </h4>
            </div>
            <div class="col-6" style="display : flex; padding:0">
                <!-- 변경 버튼은 클릭시 이력서 관리 페이지로 이동 -->
                <button class="btn btn-outline btn-rounded btn-primary mb-2" style="margin-left:auto; font-size: medium; padding: 2px 10px;">변경</button>
            </div>
        </div>
        <div class="row" style="margin-top: 10px; border: 1px solid #eaeaea; padding: 20px 10px;">
            <div class="col-3" style="display: flex; align-items: center; justify-content: center;">
                <div class="thumb-info-side-image-wrapper">
                    <img src="@/assets/profile.webp" class="img-fluid" alt="" style="width: 100px;">
                </div>
                <!-- 나중에 야돈대신 들어갈 자리 :src="{{result.rsmInfo.rsm_img_file_url}}" -->
            </div>
            <div class="col-9 table-container" >
                <table class="table">
                    <thead>
                        <tr>
                            <th colspan="3">
                                <h2 class="font-weight-bold" style="margin-bottom: 10px;">{{result.rsmInfo.rsm_ttl}}</h2>
                            </th>
                        </tr>
                    </thead>
                    <tbody style="text-align: left;">
                        <tr>
                            <th>
                                등록일자 : {{ formatDateYMD(result.rsmInfo.insrt_dtm) }}
                            </th>
                            <th>
                                최종수정일자 : {{ formatDateYMD(result.rsmInfo.updt_dtm) }}
                            </th>
                            <th>
                                
                            </th>
                        </tr>
                    </tbody>
                </table>
            </div>
            <!-- 저장된 지역 보여주기 -->
            <div v-if="selectedArea.length != 0">
                <span v-for="(area, i) in selectedArea" :key="i" class="showChecked">
                    {{ area.area_name }}
                </span>
            </div>
            <!-- 저장된 직업 보여주기 -->
            <div v-if="selectedJob.length != 0">
                <span v-for="(job, i) in selectedJob" :key="i" class="showChecked">
                    {{ job.job_sc_name }}
                </span>
            </div>
        </div>
        

        <div :class="{ 'disabled' : !ppAcceptYN }" style="margin-top: 30px;">  
            
            <!-- 제안 받을 근무지역 선택 row -->
            <div class="row" style="margin-top: 25px;">
                <div class="col">
                    <h4 style=" margin-bottom: 0;">
                        <svg xmlns="http://www.w3.org/2000/svg" width="25" height="25" fill="currentColor" class="bi bi-arrow-right-square-fill" viewBox="0 0 16 16">
                            <path d="M0 14a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2H2a2 2 0 0 0-2 2zm4.5-6.5h5.793L8.146 5.354a.5.5 0 1 1 .708-.708l3 3a.5.5 0 0 1 0 .708l-3 3a.5.5 0 0 1-.708-.708L10.293 8.5H4.5a.5.5 0 0 1 0-1"/>
                        </svg>
                        &nbsp;&nbsp;제안 받을 근무지역 선택
                    </h4>
                </div>
            </div>
            <!-- 선택한 지역 보여주기 -->
            <div v-if="checkedArea.length != 0">
                <span v-for="(area, i) in checkedArea" :key="i" class="showChecked">
                    {{ area.area_name }}
                    <i class="fas fa-times" @click="unchecked('area', area.area_sq)" style="cursor: pointer;"></i>
                </span>
                <span class="showChecked" style="cursor: pointer; background-color: #eaeaea;" @click="clear('area')">지역초기화</span>
             </div>
            <div class="row" style="margin-top: 20px; border: 1px solid #eaeaea; padding: 15px;">
                <div class="col-3 scrollbar" style="border-right: 1px solid #eaeaea;">
                    <ul style="margin: 0; padding: 0;">
                        <li v-for="(parentArea, i) in parentAreaList" :key="i" @click="chooseParentArea(parentArea.area_sq)" :class="{ 'selected' : parentAreaCode === parentArea.area_sq }" style="cursor: pointer; margin-bottom: 10px; list-style: none; text-align: center;">
                            {{parentArea.area_name.split("전체").join('')}}
                        </li>
                    </ul>
                </div>
                <div class="col-9 scrollbar">
                    <div class="row" v-if="parentAreaCode != 0">
                        <div class="col-4" style="margin-bottom: 9px;" v-for="(area, i) in filteredAreaList" :key="i">
                            <span><input type="checkbox" :id="area.area_sq" :value="area" v-model="checkedArea" style="cursor: pointer;"><label :for="area.area_sq" style="cursor: pointer;">&nbsp;&nbsp;{{area.area_name}}</label></span>
                        </div>
                    </div>
                </div>
            </div>
            

            <!-- 제안 받을 직업 row -->
            <div class="row" style="margin-top: 25px;">
                <div class="col">
                    <h4 style=" margin-bottom: 0;">
                        <svg xmlns="http://www.w3.org/2000/svg" width="25" height="25" fill="currentColor" class="bi bi-arrow-right-square-fill" viewBox="0 0 16 16">
                            <path d="M0 14a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2H2a2 2 0 0 0-2 2zm4.5-6.5h5.793L8.146 5.354a.5.5 0 1 1 .708-.708l3 3a.5.5 0 0 1 0 .708l-3 3a.5.5 0 0 1-.708-.708L10.293 8.5H4.5a.5.5 0 0 1 0-1"/>
                        </svg>
                        &nbsp;&nbsp;제안 받을 직업
                    </h4>
                </div>
            </div>
            <!-- 선택한 직업 보여주기 -->
            <div v-if="checkedJob.length != 0">
                <span v-for="(job, i) in checkedJob" :key="i" class="showChecked">
                    {{ job.job_sc_name }}
                    <i class="fas fa-times" @click="unchecked('job', job.job_sq)" style="cursor: pointer;"></i>
                </span>
                <span class="showChecked" style="cursor: pointer; background-color: #eaeaea;" @click="clear('job')">직업초기화</span>
             </div>
            <div class="row" style="margin-top: 20px; border: 1px solid #eaeaea; padding: 15px;">
                <div class="col-3 scrollbar" style="border-right: 1px solid #eaeaea;">
                    <ul style="margin: 0; padding: 0;">
                        <li @click="chooseJobLC('IT데이터')" :class="{ 'selected' : 'IT데이터' == selectedJobLC }" style="cursor: pointer; margin-bottom: 10px; list-style: none; text-align: center;">
                            IT데이터
                        </li>
                    </ul>
                </div>
                <div class="col-9 scrollbar">
                    <div class="row" v-if="selectedJobLC != ''">
                        <div class="col-4" style="margin-bottom: 9px;" v-for="(job, i) in filteredJobList" :key="i">
                            <span><input type="checkbox" :id="job.job_sq" :value="job" v-model="checkedJob" style="cursor: pointer;"><label :for="job.job_sq" style="cursor: pointer;">&nbsp;&nbsp;{{job.job_sc_name}}</label></span>
                        </div>
                    </div>
                </div>
            </div>

            <!-- 보내기 버튼 -->
             <div class="row" style="margin-top: 25px; display: flex; justify-content: center;">
                <button class="btn btn-outline btn-rounded btn-primary mb-2 " style="width: 100px;" @click="submitChecked()" >저 장</button>
             </div>
        </div>
    </div>
    <div v-else>
        <div>대표 이력서 없음</div>
    </div>
    
</template>

<script setup>
    import { ref, onMounted, computed, watch } from 'vue';
    import { api } from '@/axios.js';
    import { formatDateYMD } from '@/tools';
import { useStore } from 'vuex';

    let result = ref(null);
    let parentAreaCode = ref(101000);
    let selectedJobLC = ref('IT데이터');
    let ppAcceptYN = ref(false);
    let checkedArea = ref([]);
    let checkedJob = ref([]);
    let selectedArea = ref([]);
    let selectedJob = ref([]);

    const store = useStore();

    onMounted(async() => {
        result.value = await api.$get("/user/mypage/ppAcception", {
                params: {
                    mbr_sq : store.state.member.mbrSq
                }
            });

            ppAcceptYN.value = result.value.ppAcceptYN === 'Y';
            selectedArea.value = result.value.areaList.filter(area => result.value.selectedAreas.includes(area.area_sq));
            selectedJob.value = result.value.jobList.filter(job => result.value.selectedJobs.includes(job.job_sq));
    });

    watch(ppAcceptYN, async (newValue, oldValue) => {
        if (newValue === oldValue) return;
        if(newValue) await api.$patch("/user/mypage/ppAcception/Y", {mbr_sq : store.state.member.mbrSq});
        else await api.$patch("/user/mypage/ppAcception/N", {mbr_sq : store.state.member.mbrSq});
    });

    function chooseParentArea(area_sq){
        parentAreaCode.value = area_sq;
    }

    const parentAreaList = computed(() => {
        return result.value.areaList.filter(parentArea => parentArea.area_sq == parentArea.prnt_area_sq);
    });

    const filteredAreaList = computed(() => {
        if(parentAreaCode.value == 0) return [];
        return result.value.areaList.filter(area => area.prnt_area_sq == parentAreaCode.value);
    });

    function chooseJobLC(jobLC){
        selectedJobLC.value = jobLC;
    }

    const filteredJobList = computed(() => {
        if(selectedJobLC.value == '') return[];
        return result.value.jobList.filter(job => job.job_lc_name == selectedJobLC.value);
    });

    function unchecked(category, pkNum){
        if(category == 'area'){
            let index = checkedArea.value.findIndex(area => area.area_sq == pkNum);
            checkedArea.value.splice(index, 1);
            return;
        }
        if(category == 'job'){
            let index = checkedJob.value.findIndex(job => job.job_sq == pkNum);
            checkedJob.value.splice(index, 1);
            return;
        }
    }

    function clear(category){
        if(category == 'area'){
            checkedArea.value = [];
            return;
        }
        if(category == 'job'){
            checkedJob.value = [];
            return;
        }
    }

    async function submitChecked(){
        let areaAndJobLists = {checkedArea, checkedJob};
        try{
            await api.$post("/user/mypage/ppAcception?mbr_sq=1", areaAndJobLists);
            selectedArea.value = checkedArea.value;
            selectedJob.value = checkedJob.value;
            window.scrollTo({top : 0, behavior: "smooth"});
        }catch(err){
            console.error(err);
        }
    }


</script>



<style scoped>
.selected {
    font-size: 15px;
    font-weight: bold;
    text-decoration: underline;
    text-underline-offset: 0.5rem;
}
.scrollbar { 
    height: 250px;
    overflow-y: auto; 
}

/* 스크롤바의 폭 너비 */
.scrollbar::-webkit-scrollbar {
    width: 10px;  
}

.scrollbar::-webkit-scrollbar-thumb {
    background: rgb(95, 90, 108); /* 스크롤바 색상 */
    border-radius: 10px; /* 스크롤바 둥근 테두리 */
}

.scrollbar::-webkit-scrollbar-track {
    background: rgba(220, 20, 60, .1);  /*스크롤바 뒷 배경 색상*/
}

.disabled {
    opacity: 0.5;
    pointer-events: none;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    background: rgba(255, 255, 255, 1);
    z-index: 1;
}

.showChecked {
    display: inline-block;
    border: 1px solid #eaeaea;
    margin: 5px 3px;
    border-radius: 80px;
    padding: 5px 8px;
}
  
</style>