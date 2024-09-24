<template>
    <div v-if="isVisible" class="modal-overlay" @click.self="close">
        <div class="modal-content">
            <div class="skills-container">
                <div class="skill-card">
                    <div v-for="(skills, category) in localSkills" :key="category" class="skill-category">
                        <h5>{{ category }}</h5>
                        <div class="checkbox-group">
                            <div v-for="skill in skills" :key="skill.sklScName" class="checkbox-item">
                                <input type="checkbox" :id="skill.sklScName" :value="skill"
                                    v-model="selectedSkills[category]" @change="updateParent" />
                                <label :for="skill.sklScName">{{ skill.sklScName }}</label>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="btn btn-secondary mt-3" @click="skillsSave">저장</div>
            <div class="btn btn-secondary mt-3" @click="close">닫기</div>
        </div>
    </div>
</template>



<script setup>
import { api } from '../../../../axios';
import { onMounted, ref } from 'vue';
import { defineEmits, defineProps } from 'vue';

const props = defineProps({
    isVisible: {
        type: Boolean,
        required: true
    },
    skillsData: {
        type: Object,
        required: true
    }
})

console.log(props.isVisible)

const emit = defineEmits(['update:isVisible', 'update:skillsData']);

const localSkills = ref({
    'Language': [],
    'Framework': [],
    'Tool': []
});

const selectedSkills = ref({
    'Language': [],
    'Framework': [],
    'Tool': []
});


onMounted(async () => {
    try {
        const response = await api.$get('/skl-cd/list');
        console.log(response);
        insertSklData(response);
    } catch (error) {
        console.error('Error:', error);
    }
});

const insertSklData = (data) => {
    data.forEach(item => {
        const { sklLcName, sklScName } = item;
        if (sklLcName === "언어") {
            if (!localSkills.value['Language'].includes(sklScName)) {
                localSkills.value['Language'].push(item);
            }
        } else if (sklLcName === "프레임워크") {
            if (!localSkills.value['Framework'].includes(sklScName)) {
                localSkills.value['Framework'].push(item);
            }
        } else if (sklLcName === "툴") {
            if (!localSkills.value['Tool'].includes(sklScName)) {
                localSkills.value['Tool'].push(item);
            }
        }
    });

    console.log(localSkills.value)
};


const close = () => {
    emit('update:isVisible', false);
};

const skillsSave = () => {
    console.log(selectedSkills.value)
    emit('update:skillsData', selectedSkills.value);
    close()
}

/* watch(selectedSkills, (newVal) => {
    emit('update:skillsDatas', newVal);
}); */


</script>

<style scoped>
.modal-overlay {
    position: fixed;
    z-index: 10000;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background: rgba(0, 0, 0, 0.5);
    display: flex;
    justify-content: center;
    align-items: center;
}

.modal-content {
    background: #fff;
    padding: 20px;
    border-radius: 8px;
    width: 90%;
    max-width: 500px;
}

.skills-container {
    display: flex;
    flex-direction: column;
}

.skill-card {
    border: 1px solid #ddd;
    border-radius: 8px;
    padding: 1rem;
    margin-bottom: 1rem;
    background-color: #f9f9f9;
}

.skill-category {
    margin-bottom: 1rem;
}

.skill-category h5 {
    margin-bottom: 0.5rem;
    font-size: 1.1rem;
}

.checkbox-group {
    display: flex;
    flex-wrap: wrap;
}

.checkbox-group input[type="checkbox"] {
    margin-right: 0.5rem;
}

.checkbox-group label {
    margin-right: 1rem;
    font-size: 0.9rem;
}

.mt-2 {
    margin-top: 0.5rem;
}

.btn-circle {
    width: 2rem;
    height: 2rem;
    border-radius: 50%;
}

.skill-card .btn-outline {
    margin-left: 0.5rem;
}
</style>