<template>
    <div class="container py-4">
        <div class="row">
            <EntMypageSidebar/>
            <div class="col-lg-9">
                <router-view/>
            </div>
        </div>
    </div>
</template>

<script setup>
    import EntMypageSidebar from "@/components/fo/enterprise/enterpriseMypage/EntMypageSidebar";
</script>
