import { ref, computed } from "vue";
import { defineStore } from "pinia";
import axios from "axios";
import router from "@/router";

export const useQuestionStore = defineStore("question", () => {
  const getRestApiUrl = (programId) => {
    return `http://localhost:8080/program/${programId}/question`;
  };

  const question = ref({});

  // 질문 등록하기
  const createQuestion = (question, programId) => {
    const REST_API_URL = getRestApiUrl(programId);

    axios
      .post(REST_API_URL, question, {
        headers: {
          "access-token": sessionStorage.getItem("access-token"),
        },
      })
      .then((res) => {
        console.log("res.data", res.data);
        router.replace({ name: "question" });
      });
  };

  const questionList = ref([]);

  // 질문 전체 조회하기
  const getQuestionList = (programId) => {
    const REST_API_URL = getRestApiUrl(programId);

    axios.get(REST_API_URL).then((res) => {
      console.log("질문 리스트 조회", res.data);
      questionList.value = res.data;
    });
  };

  const singleQuestion = ref({});
  // 질문 하나 조회하기
  const getQuestion = (programId, questionId) => {
    const REST_API_URL = getRestApiUrl(programId) + `/${questionId}`;
    axios.get(REST_API_URL).then((res) => {
      console.log("질문 하나 조회", res.data);
      singleQuestion.value = res.data;
    });
  };

  // 질문 삭제하기
  const deleteQuestion = (programId, questionId) => {
    const REST_API_URL = getRestApiUrl(programId) + `/${questionId}`;
    axios.delete(REST_API_URL).then((res) => {
      console.log("res.data", res.data);
      router.replace({ name: "question", params: { programId: programId } });
    });
  };

  // 질문 수정하기
  const updateQuestion = (programId) => {
    const REST_API_URL = getRestApiUrl(programId);
    console.log("singleQuestion", singleQuestion);
    const questionId = singleQuestion.value.id;

    axios.put(REST_API_URL, singleQuestion.value).then((res) => {
      console.log("res.data", res.data);

      // 라우터 이동
      router
        .replace({
          name: "questionDetail",
          params: { programId: programId, questionId: questionId },
        })
        .catch((err) => {
          console.error("Router navigation failed:", err);
        });
    });
  };

  return {
    question,
    createQuestion,
    getQuestionList,
    questionList,
    getQuestion,
    singleQuestion,
    deleteQuestion,
    updateQuestion,
  };
});
