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
      v-else
      class="d-flex flex-column justify-content-center align-items-center mt-5"
    >
      <h2 class="text-center mb-4">본인 맞춤형 프로그램을 생성하시겠습니까?</h2>
      <button @click="createAIProgram" class="btn btn-primary btn-lg">
        생성하기
      </button>
    </div>
  </div>
</template>

<script setup>
import { onMounted, ref, watch } from "vue";
import UserInfoItem from "../user/UserInfoItem.vue";
import { useUserInfoStore } from "@/stores/userInfo";
import { useLoginStore } from "@/stores/login";
import { questions } from "@/utils/userInfoQuestions";
import { formatAnswer } from "@/utils/formattedAnswer";

const userInfoStore = useUserInfoStore();
const loginStore = useLoginStore();

const currentQuestionIndex = ref(0);
const answers = ref({}); // 답변을 담는 객체
const isQuestionPage = ref(true); // 질문 화면 페이지
const isValidInput = ref(true);

// 답변 유효성 검사
const validateAnswer = (answer) => {
  // 중간 checkbox 데이터 선택 안 하면 안 넘어가게 하기
  if (answer.length <= 0) {
    console.log("answer.length :>> ", answer.length);
    return false;
  }

  return true;
};

// 다음 질문으로 넘어갈 때, answer로 선택한 답변 answers 객체에 담기
const handleNextQuestion = (answer) => {
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
  console.log("answer :>> ", answer);

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
  const userInfoList = Object.entries(answers.value)
    .filter(([key, value]) => key !== "keyword" && key !== "fighting")
    .reduce((acc, [key, value]) => {
      acc[key] = value;
      return acc;
    }, {});

  // userId key에 loginUserId 추가
  userInfoList.userId = loginStore.loginUserId;

  // keyword만 포함된 데이터를 keywordList로 필터링
  const userInfoKeywordList = answers.value.keyword || [];

  // userInfoKeywordList를 index + 1 형태로 가공
  const formattedKeywordList = userInfoKeywordList.map((_, index) => index + 1);

  userInfoList.keyword = formattedKeywordList;

  // console.log("userInfoList :>> ", userInfoList);

  // REST API 호출
  userInfoStore.sendAnswerToServer(userInfoList);
};

// 이전으로 돌아가는 버튼
// const handlePreviousQuestion = () => {
//   if (currentQuestionIndex.value > 0) {
//     currentQuestionIndex.value--;
//   }
// };

// AI program 추천 중
const createAIProgram = () => {
  alert("프로그램 생성 중입니다.");
  userInfoStore.createAIProgram();
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

.btn {
  width: 200px;
  padding: 10px;
}
</style>
