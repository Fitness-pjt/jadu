<!-- ProgramList.vue -->
<template>
  <div class="container py-4">
    <div class="row row-cols-1 row-cols-md-2 row-cols-lg-3 g-4">
      <div class="col" v-for="program in programs" :key="program.programId">
        <RouterLink
          :to="{
            name: 'programDetail',
            params: { programId: program.programId },
          }"
          class="text-decoration-none d-flex justify-content-center align-item-center"
        >
          <ProgramListItem :program="program" />
        </RouterLink>
      </div>
    </div>
  </div>
</template>

<script setup>
import ProgramListItem from "./ProgramListItem.vue";

defineProps({
  programs: {
    type: Array,
    required: true,
  },
});
</script>
