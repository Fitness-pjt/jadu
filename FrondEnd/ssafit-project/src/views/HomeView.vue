<template>
  <div>
    <ProgramBanner />
    <ProgramList :programs="programStore.programs" />
  </div>
</template>

<script setup>
import ProgramBanner from "@/components/program/ProgramBanner.vue";
import ProgramList from "@/components/program/ProgramList.vue";
import { useLoginStore } from "@/stores/login";

import { useProgramStore } from "@/stores/program";
import { onMounted } from "vue";

const programStore = useProgramStore();

onMounted(async () => {
  try {
    await programStore.getAllPrograms();
  } catch (error) {
    console.error("Failed to load programs:", error);
  }
});
</script>

<style scoped>
p {
  padding: 1rem;
  font-size: large;
  font-weight: bold;
}
</style>
