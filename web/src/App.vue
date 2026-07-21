<script setup lang="ts">
import { RouterLink, RouterView } from "vue-router";
</script>

<template>
  <div class="app-shell">
    <header class="app-header">
      <div class="app-header__inner">
        <RouterLink class="brand" to="/">技术社区</RouterLink>
        <span class="environment-label">Development</span>
      </div>
    </header>

    <main class="app-main">
      <RouterView />
    </main>
  </div>
</template>

