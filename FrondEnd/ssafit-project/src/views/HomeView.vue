<template>
  <div>
    <!-- <p>홈 화면입니다.</p>
    <RouterLink :to="getRoute(1)">
      <button>
        <UserNameTag :user-id="1" />
      </button>
    </RouterLink>

    <RouterLink :to="getRoute(2)">
      <button>
        <UserNameTag :user-id="2" />
      </button>
    </RouterLink>

    <RouterLink :to="getRoute(1)">
      <button>
        <UserNameTag :user-id="1" />
      </button>
    </RouterLink>
    <RouterLink :to="getRoute(2)">
      <button>
        <UserNameTag :user-id="2" />
      </button>
    </RouterLink> -->
    <ProgramBanner />

    <ProgramList :programs="programStore.programs" />
  </div>
</template>

<script setup>
import UserNameTag from "@/components/common/UserNameTag.vue";
import ProgramBanner from "@/components/program/ProgramBanner.vue";
import { useLoginStore } from "@/stores/login";
import ProgramList from "@/components/program/ProgramList.vue";

import { onMounted} from 'vue'
import { useProgramStore } from "@/stores/program";



const loginStore = useLoginStore();
const programStore = useProgramStore();


onMounted(async () => {
  try {
    await programStore.getAllPrograms()
  } catch (error) {
    console.error('Failed to load programs:', error)
  }
})


const getRoute = (userId) => {
  return loginStore.loginUserId === userId
    ? { name: "mypage", params: { userId } }
    : { name: "profile", params: { userId } };
};
</script>

<style scoped>
p {
  padding: 1rem;
  font-size: large;
  font-weight: bold;
}
</style>
