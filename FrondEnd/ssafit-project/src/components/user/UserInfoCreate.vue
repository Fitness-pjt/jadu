<template>
  <div class="container">
    <!-- 질문 화면 -->
    <div v-if="isQuestionPage">
      <UserInfoItem
        :question="questions[currentQuestionIndex]"
        :userInput="answers[currentQuestionIndex]"
        :on-submit="handleNextQuestion"
        :can-go-back="currentQuestionIndex > 0"
      />
    </div>

    <!-- 생성하기 화면 -->
    <div
      v-else-if="!isLoading"
      class="d-flex flex-column justify-content-center align-items-center my-5 py-5"
    >
      <h2 class="text-center mb-4 fw-bold text-navy">
        나만의 맞춤형 프로그램을 생성해 보세요!
      </h2>
      <p class="text-center mb-4 text-muted" style="max-width: 500px">
        AI가 당신의 데이터를 바탕으로 완벽한 프로그램을 설계합니다. <br />
        지금 생성 버튼을 클릭하여 당신만의 프로그램을 만나보세요!
      </p>
      <button @click="createAIProgram" class="btn btn-navy btn-lg shadow">
        생성하기
      </button>
    </div>

    <!-- 로딩 중 화면 -->
    <div
      v-else
      class="d-flex flex-column justify-content-center align-items-center my-5 py-5"
    >
      <div class="loader mb-4"></div>
      <p class="mt-4 text-center">
        AI가 당신의 입력 데이터를 분석 중입니다. <br />
        잠시만 기다려주세요!
      </p>
    </div>
  </div>
</template>

<script setup>
import { computed, onMounted, ref, watch } from "vue";
import UserInfoItem from "../user/UserInfoItem.vue";
import { useUserInfoStore } from "@/stores/userInfo";
import { useLoginStore } from "@/stores/login";
import { questions } from "@/utils/userInfoQuestions";
import { formatAnswer } from "@/utils/formattedAnswer";
import { useRouter } from "vue-router";

const userInfoStore = useUserInfoStore();
const loginStore = useLoginStore();

const currentQuestionIndex = ref(0);
const answers = ref({}); // 답변을 담는 객체
const isQuestionPage = ref(true); // 질문 화면 페이지
const isValidInput = ref(true);

// 전역 변수 관리
const userInfoList = computed(() => userInfoStore.userInfoList);
const programId = computed(() => userInfoStore.programId);

watch(
  () => userInfoList.value,
  (newList, oldList) => {
    // console.log("newList ,>> ", newList);
  },
  { deep: true }
);

// 답변 유효성 검사
const validateAnswer = (answer) => {
  // 중간 checkbox 데이터 선택 안 하면 안 넘어가게 하기
  if (answer.length <= 0) {
    // console.log("answer.length :>> ", answer.length);
    return false;
  }

  return true;
};

// 다음 질문으로 넘어갈 때, answer로 선택한 답변 answers 객체에 담기
const handleNextQuestion = (answer) => {
  // console.log("answer", answer);
  // 답변이 다 작성되지 않은 경우, alert 띄우고 return
  if (!validateAnswer(answer)) {
    isValidInput.value = false;
    alert("답변을 입력해주세요.");
    return;
  }

  isValidInput.value = true;

  const question = questions[currentQuestionIndex.value];

  // 선택된 데이터를 DB에 넣을 수 있는 형태로 데이터 가공하기
  const formattedAnswer = formatAnswer(question.id, answer);

  // 현재 질문의 ID와 값을 저장
  answers.value[question.id] = formattedAnswer;

  if (currentQuestionIndex.value === questions.length - 1) {
    createProgram(); // 서버로 답변 전송 메서드 호출
    isQuestionPage.value = false; // 질문 화면 이제 끝
  } else {
    currentQuestionIndex.value++;
  }
};

//  "생성하기" 버튼 클릭 시, 답변을 서버로 전송
const createProgram = () => {
  // keyword를 제외한 나머지 데이터를 userInfoList로 필터링
  const userList = Object.entries(answers.value)
    .filter(([key, value]) => key !== "keyword" && key !== "fighting")
    .reduce((acc, [key, value]) => {
      acc[key] = value;
      return acc;
    }, {});

  // userId key에 loginUserId 추가
  userList.userId = loginStore.loginUserId;

  // keyword만 포함된 데이터를 keywordList로 필터링
  const userInfoKeywordList = answers.value.keyword || [];

  // userInfoKeywordList를 index + 1 형태로 가공
  const formattedKeywordList = userInfoKeywordList.map((_, index) => index + 1);

  userList.keyword = formattedKeywordList;

  // console.log("마지막 다음 :>> ", userList);

  // REST API 호출
  userInfoStore.sendAnswerToServer(userList);

  // console.log("rest api 호출 후 :>> ", userInfoList.value);
  // console.log("rest api 호출 후:>> ", userList);
  userInfoList.value = userList;
  console.log("userInfoList에 userList 담은 후 :>> ", userInfoList.value);
};

// 이전으로 돌아가는 버튼
// const handlePreviousQuestion = () => {
//   if (currentQuestionIndex.value > 0) {
//     currentQuestionIndex.value--;
//   }
// };

const router = useRouter();
const isLoading = ref(false);
// AI program 추천 중
const createAIProgram = async () => {
  // alert("프로그램 생성 중입니다.");
  // console.log("userInfoList AI임? :>> ", userInfoList.value);

  // 로딩 상태 변수 선언
  isLoading.value = true; // 로딩 시작

  try {
    // userInfoStore.createAIProgram 호출 후 프로그램 ID를 받음
    const programIdResponse = await userInfoStore.createAIProgram(
      userInfoList.value
    );
    // 프로그램 Id가 존재할 경우, 라우터 처리
    if (programIdResponse) {
      // console.log("useUserInfoStore.programId", programIdResponse);
      // programId가 존재하면 라우터 처리
      router.push(`/program/${programIdResponse}`);
    }
  } catch (error) {
    // console.error("프로그램 생성 중 오류 발생 : ", error)
  } finally {
    // 로딩 종료
    isLoading.value = false;
  }
};

onMounted(() => {
  userInfoStore.getUserInfo();
});
</script>

<style scoped>
.container {
  max-width: 600px;
  margin: 0 auto;
}

h2 {
  font-size: 1.5rem;
  color: #333;
}

.text-navy {
  color: #133e87; /* 강조색상 */
}

.fw-bold {
  font-weight: bold; /* 굵은 글씨 */
}

.btn {
  padding: 12px 24px; /* 버튼 크기 증가 */
  font-size: 1.2rem; /* 텍스트 크기 증가 */
}

.btn:hover {
  background-color: #0d2e5f; /* 버튼 호버 색상 변경 */
  color: white;
  transition: background-color 0.3s ease; /* 부드러운 전환 효과 */
}

.text-muted {
  color: #6c757d; /* 부드러운 회색 */
  line-height: 1.6; /* 텍스트 간격 증가 */
}

/* 로딩 스피너 */
.loader {
  width: 80px;
  --b: 12px;
  aspect-ratio: 1;
  border-radius: 50%;
  padding: 1px;
  background: conic-gradient(#0000 10%, #133e87) content-box;
  -webkit-mask: repeating-conic-gradient(
      #0000 0deg,
      #000 1deg 20deg,
      #0000 21deg 36deg
    ),
    radial-gradient(
      farthest-side,
      #0000 calc(100% - var(--b) - 1px),
      #000 calc(100% - var(--b))
    );
  -webkit-mask-composite: destination-in;
  mask-composite: intersect;
  animation: l4 1s infinite steps(10);
}
@keyframes l4 {
  to {
    transform: rotate(1turn);
  }
}
</style>
