<template>
    <div class="col pb-3 pt-1">
        <h4 class="mb-0">
            자격/면허증 &nbsp;
            <div class="btn btn-primary btn-circle mb-2" @click="addComponents">
                <i class="fa fa-plus"></i>
            </div>
        </h4>
        <hr class="mt-1 mb-2" />
        <div>
            <div v-for="(certificateData,) in certificateDatas" :key="certificateData.id"
                class="form-control text-3 h-auto py-2certificate-item">
                <input type="text" v-model="certificateData.name" placeholder="자격증 이름" />
                <button class="btn btn-outline-secondary" @click="searchCertificate()">
                    검색
                </button>
                <input type="text" v-model="certificateData.issuer" placeholder="발급처" />
                <input type="date" v-model="certificateData.issueDate" />
                <button class="btn btn-outline btn-danger" @click="removeComponents(certificateData.id)">
                    삭제
                </button>
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
// 자격증 검색
const searchCertificate = () => {
    event.preventDefault()
}
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

<style scoped>
.certificate-item {
    display: flex;
    align-items: center;
    margin-bottom: 1rem;
}

.certificate-item input[type="text"],
.certificate-item input[type="date"] {
    margin-right: 0.5rem;
    flex: 1;
}

.input-group .btn {
    margin-left: 0.5rem;
}

.certificate-item button {
    margin-left: 0.5rem;
}

.modal-dialog {
    max-width: 600px;
}
</style>
