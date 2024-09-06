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
                <div class="skill-category">
                    <h5>language</h5>
                    <div class="checkbox-group">
                        <input type="checkbox" id="java" value="Java" v-model="localSkills['language']"
                            @change="updateParent">
                        <label for="java">Java</label>
                        <input type="checkbox" id="c" value="C" v-model="localSkills['language']"
                            @change="updateParent">
                        <label for="c">C</label>
                        <input type="checkbox" id="cpp" value="C++" v-model="localSkills['language']"
                            @change="updateParent">
                        <label for="cpp">C++</label>
                    </div>
                </div>
                <div class="skill-category">
                    <h5>framework</h5>
                    <div class="checkbox-group">
                        <input type="checkbox" id="spring" value="Spring Boot" v-model="localSkills['framework']"
                            @change="updateParent">
                        <label for="spring">Spring Boot</label>
                        <input type="checkbox" id="react" value="React" v-model="localSkills['framework']"
                            @change="updateParent">
                        <label for="react">React</label>
                        <input type="checkbox" id="vue" value="Vue" v-model="localSkills['framework']"
                            @change="updateParent">
                        <label for="vue">Vue</label>
                    </div>
                </div>
                <div class="skill-category">
                    <h5>UI</h5>
                    <div class="checkbox-group">
                        <input type="checkbox" id="figma" value="Figma" v-model="localSkills['UI']"
                            @change="updateParent">
                        <label for="figma">Figma</label>
                        <input type="checkbox" id="photoshop" value="Photoshop" v-model="localSkills['UI']"
                            @change="updateParent">
                        <label for="photoshop">Photoshop</label>
                    </div>
                </div>
                <div class="mt-2">
                    <button class="btn btn-outline btn-danger" @click="removeComponents">삭제</button>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref, watch } from 'vue';
import { defineEmits } from 'vue';

const emit = defineEmits(['updateSkills', 'removeSkills']);

const localSkills = ref({
    'language': [],
    'framework': [],
    'UI': []
});

const skillsVisible = ref(false);

const toggleSkills = () => {
    skillsVisible.value = !skillsVisible.value;
};

const updateParent = () => {
    emit('updateSkills', localSkills.value);
};

const removeComponents = () => {
    emit('removeSkills');
};

// Watch localSkills to emit changes to the parent
watch(localSkills, (newSkills) => {
    emit('updateSkills', newSkills);
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