<template>
    <div class="col pb-3 pt-1">
        <div class="col-lg-4">
            <h4 class="mb-0">
                기술 &nbsp;
                <a href="#" class="btn btn-primary btn-circle mb-2" @click="addComponents()">
                    <i class="fa fa-plus"></i>
                </a>
            </h4>
            <hr class="mt-1 mb-2" />
            <div>
                <div v-for="(skillsData, index) in skillsDatas" :key="index">
                    <div>
                        <a class="btn btn-outline btn-rounded btn-light" @click="removeComponents(skillsData.id)">
                            데이터 뿌려주기
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref } from "vue";

const skillsDatas = ref([]);
let count = 1;

// 입력창 생성
const addComponents = () => {
    skillsDatas.value.push({ id: count });
    count++;
};

// // 입력창 생성   ================>>>>>>>   삭제기능 넣으면 너무 어려워지는데?
// const removeComponents = (event) => {
//     let index = 0;

//     skillsDatas.value.forEach((skillsData, dataIndex) => {
//         if (skillsData.id == event) {
//             index = dataIndex;
//             return;
//         }
//     });

//     skillsDatas.value.splice(index, 1);
// };
</script>

<style scoped></style>