<template>
    <div class="col pb-3 pt-1">
        <h4 class="mb-0">
            스킬 &nbsp;
            <div class="btn btn-primary btn-circle mb-2" @click="toggleSkills">
                <i class="fa fa-plus"></i>
            </div>
        </h4>
        <hr class="mt-1 mb-2" />
        <div v-if="skillsVisible" class="skills-container">
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
    </div>
</template>



<script setup>
import axios from 'axios';
import { onMounted, ref, watch } from 'vue';
import { defineEmits } from 'vue';

const emit = defineEmits(['updateSkills']);

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
        const response = await axios.get("http://localhost:80/skl-cd/list");
        console.log(response.data);
        insertSklData(response.data);
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

/* const clearSkills = () => {
    for (const category in selectedSkills.value) {
        selectedSkills.value[category] = [];
    }
    emit('updateSkills', selectedSkills.value);
}; */


const skillsVisible = ref(false);

const toggleSkills = () => {
    skillsVisible.value = !skillsVisible.value;
};

/* const updateParent = () => {
    emit('updateSkills', localSkills.value);
}; */

watch(selectedSkills, (newVal) => {
    emit('updateSkills', newVal);
}, { deep: true });
</script>

<style scoped>
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