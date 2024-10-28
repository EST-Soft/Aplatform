<template>
    <div v-if="result">
        <div v-if="result.totalPostsCount != 0">
            <div class="row">
                <div style="display: flex; justify-content: right;">
                    <input type="text" maxlength="80" class="form-control text-1" placeholder="검색어 입력" style="width: 140px;" />
                    <span class="center" style="margin-left: 10px">
                        <i class="fas fa-search" style="width: max-content; font-size: 20px"></i>
                    </span>
                </div>
            </div>
            <div class="row" style="margin-top: 20px;">
                <!-- 리스트 -->
                <div v-for="(post, i) in result.jobPosts" :key="i" style="border-bottom: 1px solid #eae; margin-bottom: 16px;">
                    <table class="table table-bordered" style="margin-bottom: 10; table-layout: fixed; width: 100%;">
                        <thead>
                            <tr>
                                <th colspan="2" style="font-size: large;">
                                    {{ post.entrprs_name }}
                                </th>
                                <th colspan="3">
                                    {{ post.jbp_ttl }}
                                </th>
                                <th colspan="1" style="text-align: center;">
                                    조회수&nbsp;&nbsp;{{ post.hits }}
                                </th>
                            </tr>
                        </thead>
                        <tbody style="text-align: center;">
                            <tr style="vertical-align: middle;">
                                <th>
                                    급여&nbsp;&nbsp;{{ post.slry }}
                                </th>
                                <th>
                                    {{ post.edctn }}
                                </th>
                                <th>
                                    {{ post.cr }}
                                </th>
                                <th>
                                    {{ post.work_form }}
                                </th>
                                <th style="line-height: 1.2em;">
                                    제안일<br/>{{formatDateYMD(post.prpsl_dtm)}}
                                </th>
                                <th style="line-height: 1.2em;">
                                    마감일<br/>{{formatDateYMD(post.regstr_dln_dtm)}}
                                </th>
                            </tr>
                            <tr>
                                <th>근무지역</th>
                                <th colspan="2" style="text-align: left; white-space: nowrap; overflow: hidden; text-overflow: ellipsis;">
                                    <template v-if="!post.areas">해당사항없음</template>
                                    <template v-for="(area, i) in post.areas" :key="i">
                                        {{ area }}<span v-if="i < post.areas.length - 1">, </span>
                                    </template>
                                </th>
                                <th>직종</th>
                                <th colspan="2" style="text-align: left; white-space: nowrap; overflow: hidden; text-overflow: ellipsis;">
                                    <template v-if="!post.jobs">해당사항없음</template>
                                    <template v-for="(job, i) in post.jobs" :key="i">
                                        {{ job }}<span v-if="i < post.jobs.length - 1">, </span>
                                    </template>
                                </th>
                            </tr>
                            <tr>
                                <th>필요스킬</th>
                                <th colspan="4" style="text-align: left; white-space: nowrap; overflow: hidden; text-overflow: ellipsis;">
                                    <template v-if="!post.skills">해당사항없음</template>
                                    <template v-for="(skill, i) in post.skills" :key="i">
                                        {{ skill }}<span v-if="i < post.skills.length - 1">,&nbsp;&nbsp;</span>
                                    </template>
                                </th>
                                <th>
                                    <a class="btn btn-outline btn-primary" style="padding: 4px 8px;">수 락</a>
                                    <a class="btn btn-outline btn-secondary" @click="refuseProposedPostion(post.pstn_prpsl_sq)" style="padding: 4px 8px; margin-left: 10px;">거 절</a>
                                </th>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
            <!-- 페이지네이션 -->
            <div class="row" style="margin-top: 35px;">
                <div class="col-3"></div>
                <div class="col-6" style="display: flex; justify-content: center;">
                    <paginate
                        :page-count="result.totalPostsCount"
                        :page-range="5"
                        :margin-pages="0"
                        :break-view-class="'dispalyNone pointer'"
                        :page-class="'pointer'"
                        :prev-class="'pointer'"
                        :next-class="'pointer'"
                        :click-handler="getProposedPositionList"
                        :prev-text="'<<'"
                        :next-text="'>>'"
                    >
                    </paginate>
                </div>
                <div class="col-3"></div>
            </div>
        </div>
        <div v-else>
            <div class="row" style="text-align: center;">
                <h3><br><br><br><br>제안 받은 포지션이 없습니다.</h3>
            </div>
        </div>
    </div>
</template>

<script setup>
    import { ref, onMounted } from 'vue'
    import Paginate from 'vuejs-paginate-next';
    import { api } from '@/axios';
    import { formatDateYMD } from '@/tools';

    let result = ref(null);
    let currentPage = ref(1);

    onMounted(() => getProposedPositionList(currentPage.value));

    async function getProposedPositionList(pageNum){
        currentPage.value = pageNum;
        result.value = await api.$get("user/mypage/ppJobPost", {
            params : {
                mbr_sq : 1,
                page_num : currentPage.value
            }
        })
    }

    async function refuseProposedPostion(pstn_prpsl_sq){
        try{
            await api.$patch("/user/mypage/refusePp", {pstn_prpsl_sq : pstn_prpsl_sq});
            alert("포지션 제안을 거절했습니다.");
            // result.value.jobPosts.splice(result.value.findIndex(jobPost => jobPost.pstn_prpsl_sq == pstn_prpsl_sq), 1);
            getProposedPositionList(currentPage.value);
        }catch(err){
            alert(err);
        }
    }

</script>

<style>
    .dispalyNone {
        display: none;
    }
    .pointer {
        cursor: pointer;
    }
    .center {
        display: flex;
        align-items: center;
        justify-content: center;
    }
    
</style>
