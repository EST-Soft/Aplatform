<template>
    <div class="container py-4" v-if="result">
        <div class="row mt-5">
            <div class="col-6" style="border-right: 1px solid #eaeaea;">
                <div class="mb-3">
                    <h4 style="margin-bottom:0;" data-v-0266d942="">
                        <svg xmlns="http://www.w3.org/2000/svg" width="25" height="25" fill="currentColor" class="bi bi-arrow-right-square-fill" viewBox="0 0 16 16" data-v-0266d942=""><path d="M0 14a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2H2a2 2 0 0 0-2 2zm4.5-6.5h5.793L8.146 5.354a.5.5 0 1 1 .708-.708l3 3a.5.5 0 0 1 0 .708l-3 3a.5.5 0 0 1-.708-.708L10.293 8.5H4.5a.5.5 0 0 1 0-1" data-v-0266d942=""></path></svg> &nbsp;&nbsp;
                        최근 등록한 공고 
                    </h4>
                </div>
                <table class="table table-bordered" style="text-align: center;">
                    <thead>
                        <tr>
                            <th>
                                공고ID
                            </th>
                            <th>
                                공고 이름
                            </th>
                            <th>
                                등록일자
                            </th>
                            <th>
                                상태
                            </th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr v-for="(jobPost, i) in result.myJobPostSimpleData" :key="i">
                            <td>
                                {{ jobPost.entrprs_sq }} - {{ jobPost.jbp_sq }}
                            </td>
                            <td>
                                {{ jobPost.jbp_ttl }}
                            </td>
                            <td>
                                {{ formatDateYMD(jobPost.insrt_dtm) }}
                            </td>
                            <td>
                                <template v-if="jobPost.jbp_cndtn == 701">예정</template>
                                <template v-if="jobPost.jbp_cndtn == 702">진행중</template>
                                <template v-if="jobPost.jbp_cndtn == 703">접수마감</template>
                                <template v-if="jobPost.jbp_cndtn == 704">종료</template>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
            <div class="col-6">
                <div style="display: flex; align-items: center; justify-content: space-between;">
                    <div class="mb-3" style="display: inline-block;">
                        <h4 style="margin-bottom:0;" data-v-0266d942="">
                            <svg xmlns="http://www.w3.org/2000/svg" width="25" height="25" fill="currentColor" class="bi bi-arrow-right-square-fill" viewBox="0 0 16 16" data-v-0266d942=""><path d="M0 14a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2H2a2 2 0 0 0-2 2zm4.5-6.5h5.793L8.146 5.354a.5.5 0 1 1 .708-.708l3 3a.5.5 0 0 1 0 .708l-3 3a.5.5 0 0 1-.708-.708L10.293 8.5H4.5a.5.5 0 0 1 0-1" data-v-0266d942=""></path></svg> &nbsp;&nbsp;
                            공고별 인재 추천 
                        </h4>
                    </div>
                    <select>
                        <option v-for="postID in jobPosts" :key="postID" :value="postID">{{ entprs_sq }} - {{ postID }}</option>
                    </select>
                </div>
                <table class="table table-bordered" style="text-align: center;">
                    <thead>
                        <tr>
                            <th>
                                인 재
                            </th>
                            <th>
                                이력서 제목
                            </th>
                            <th>
                                등록일
                            </th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>
                                홍길동
                            </td>
                            <td>
                                뽑아주세용
                            </td>
                            <td>
                                2024.07.08
                            </td>
                        </tr>
                        <tr>
                            <td>
                                김남길
                            </td>
                            <td>
                                vue 초 고 수
                            </td>
                            <td>
                                2024.07.01
                            </td>
                        </tr>
                        <tr>
                            <td>
                                고승원
                            </td>
                            <td>
                                스프링 끝판왕
                            </td>
                            <td>
                                2024.07.08
                            </td>
                        </tr>
                        <tr>
                            <td>
                                김동수
                            </td>
                            <td>
                                신입 개발자입니다
                            </td>
                            <td>
                                2024.06.18
                            </td>
                        </tr>
                        <tr>
                            <td>
                                스티븐
                            </td>
                            <td>
                                경력 20년 게임 개발자
                            </td>
                            <td>
                                2024.07.04
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
        <div class="row mt-5">
            <ChartComponent/>
        </div>
    </div>
</template>

<script setup>
    import { onMounted, ref, computed } from 'vue';
    import { api } from '@/axios';
    import { formatDateYMD } from '@/tools';
    import ChartComponent from '@/components/fo/enterprise/ChartComponent.vue';

    let entprs_sq = ref(1);
    let result = ref(null);
    let jobPosts = computed(() => {
        let temp = [];
        
        for(const post of result.value.myJobPostSimpleData){
            temp.push(post.jbp_sq);
        }
        return temp;
    });

    onMounted(async()=>{
        result.value = await api.$get("/enterprise", {
            params : {
                entprs_sq : entprs_sq.value
            }
        });
    });
    

</script>

<style>

</style>