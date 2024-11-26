import router from "@/router";
import { handleError } from "@/utils/handleError";
import axios from "axios";
import { defineStore } from "pinia";
import { ref } from "vue";

export const useAnswerStore = defineStore("answer", () => {
  const getRestApiUrl = (programId, questionId) => {
    return `http://localhost:8080/program/${programId}/question/${questionId}/answer`;
  };

  const answer = ref({});
  const answerList = ref([]);

  // 답변 등록하기
  const createAnswer = async (answer, programId, questionId) => {
    const REST_API_URL = getRestApiUrl(programId, questionId);

    try {
      const response = await axios.post(REST_API_URL, answer, {
        headers: {
          "access-token": sessionStorage.getItem("access-token"),
          "Content-Type": "application/json",
        },
        withCredentials: true,
      });

      getAnswerList(programId, questionId);
      // 최신 답변을 제일 위로 추가
      answerList.value.unshift(response.data); // answerList에 추가
    } catch (error) {
      handleError(error);
    }
  };

  const getAnswerList = async (programId, questionId) => {
    const REST_API_URL = getRestApiUrl(programId, questionId);
    try {
      const response = await axios.get(REST_API_URL);
      // console.log("res.data", response.data);
      answerList.value = response.data; // 데이터를 받아서 업데이트
    } catch (error) {
      console.error("Error fetching answer list:", error);
    }
  };

  // 답변 삭제하기
  const deleteAnswer = (programId, questionId, answerId) => {
    const REST_API_URL = getRestApiUrl(programId, questionId) + `/${answerId}`;
    axios
      .delete(REST_API_URL, {
        headers: {
          "access-token": sessionStorage.getItem("access-token"),
          "Content-Type": "application/json",
        },
        withCredentials: true,
      })
      .then((res) => {
        // console.log("res.data", res.data);
        answerList.value = answerList.value.filter(
          (answer) => answer.answerId !== answerId
        );
        router.replace({
          name: "answer",
        });
      })
      .catch((error) => {
        handleError(error);
      });
  };

  // 답변 수정하기
  const updateAnswer = (programId, questionId, answerId, content) => {
    const REST_API_URL = getRestApiUrl(programId, questionId) + `/${answerId}`;
    // const answerId = singleAnswer.value.id;

    axios
      .put(REST_API_URL, content, {
        headers: {
          "access-token": sessionStorage.getItem("access-token"),
          "Content-Type": "application/json",
        },
        withCredentials: true,
      })
      .then((res) => {
        // 라우터 이동
        router
          .replace({
            name: "answer",
            params: {
              programId: programId,
              questionId: questionId,
              answerId: answerId,
            },
          })
          .catch((err) => {
            console.error("Router navigation failed:", err);
          });
      })
      .catch((error) => {
        handleError(error);
      });
  };

  return {
    answer,
    createAnswer,
    getAnswerList,
    answerList,
    deleteAnswer,
    updateAnswer,
  };
});
