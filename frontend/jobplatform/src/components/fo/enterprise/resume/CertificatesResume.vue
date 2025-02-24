<template>
    <div class="col pb-3 pt-1">
        <div class="col-lg-4">
            <h4 class="mb-0">
                자격/면허증 &nbsp;
                <a href="#" class="btn btn-primary btn-circle mb-2" @click="addComponents()">
                    <i class="fa fa-plus"></i>
                </a>
            </h4>
            <hr class="mt-1 mb-2" />
            <div>
                <div v-for="(certificateData, index) in certificateDatas" :key="index">
                    <div>
                        <a class="btn btn-outline btn-rounded btn-light" @click="removeComponents(certificateData.id)">
                            컴포먼트
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref } from "vue";

const certificateDatas = ref([]);
let count = 1;

// 입력창 생성
const addComponents = () => {
    certificateDatas.value.push({ id: count });
    count++;
};
// 입력창 생성
const removeComponents = (event) => {
    let index = 0;

    certificateDatas.value.forEach((certificateData, dataIndex) => {
        if (certificateData.id == event) {
            index = dataIndex;
            return;
        }
    });

    certificateDatas.value.splice(index, 1);
};
</script>

<style scoped></style>