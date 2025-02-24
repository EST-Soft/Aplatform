<template>
  <CommonHeader />
  <div role="main" class="main" style="min-height: 700px;">
    <router-view />
  </div>
  <CommonFooter />
</template>

<script setup>
  import CommonHeader from "@/components/fo/user/CommonHeader.vue";
  import CommonFooter from "@/components/fo/user/CommonFooter.vue";
</script>


<style>
  html, body {
    height : 100%;
    margin : 0;
  }

  .wrapper {
    display: flex;
    flex-direction: column;
    min-height: 100vh;
  }

  .main {
    flex : 1;
  }
</style>