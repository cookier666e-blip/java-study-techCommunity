<script setup lang="ts">
import { computed, onMounted, ref } from "vue";
import { ElAlert, ElButton, ElSkeleton, ElTag } from "element-plus";
import "element-plus/es/components/alert/style/css";
import "element-plus/es/components/button/style/css";
import "element-plus/es/components/skeleton/style/css";
import "element-plus/es/components/skeleton-item/style/css";
import "element-plus/es/components/tag/style/css";
import { getHealth, type HealthResponse } from "../api/health";

const health = ref<HealthResponse | null>(null);
const isLoading = ref(false);
const errorMessage = ref("");

const isHealthy = computed(() => health.value?.status === "UP");

async function loadHealth(): Promise<void> {
  isLoading.value = true;
  errorMessage.value = "";

  try {
    health.value = await getHealth();
  } catch (error) {
    health.value = null;
    errorMessage.value = error instanceof Error ? error.message : "请求失败";
  } finally {
    isLoading.value = false;
  }
}

onMounted(loadHealth);
</script>

<template>
  <section class="status-page" aria-labelledby="status-title">
    <div class="page-heading">
      <div>
        <p class="eyebrow">System</p>
        <h1 id="status-title">服务状态</h1>
      </div>
      <ElButton :loading="isLoading" @click="loadHealth">重新检查</ElButton>
    </div>

    <div class="status-panel" aria-live="polite">
      <ElSkeleton v-if="isLoading && !health" :rows="2" animated />

      <ElAlert
        v-else-if="errorMessage"
        :title="errorMessage"
        type="error"
        :closable="false"
        show-icon
      />

      <dl v-else-if="health" class="status-details">
        <div class="status-details__row">
          <dt>运行状态</dt>
          <dd>
            <ElTag :type="isHealthy ? 'success' : 'danger'" effect="dark">
              {{ health.status }}
            </ElTag>
          </dd>
        </div>
        <div class="status-details__row">
          <dt>服务名称</dt>
          <dd>{{ health.application }}</dd>
        </div>
        <div class="status-details__row">
          <dt>接口地址</dt>
          <dd><code>GET /api/health</code></dd>
        </div>
      </dl>
    </div>
  </section>
</template>
