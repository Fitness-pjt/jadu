import { ref, computed } from "vue";
import { defineStore } from "pinia";
import axios from "axios";
import router from "@/router";
import { handleError } from "@/utils/handleError";

export const useQuestionStore = defineStore("question", () => {
  const getRestApiUrl = (programId) => {
    return `http://localhost:8080/program/${programId}/question`;
  };

  // const question = ref({});
  const questionList = ref([]);

  // 질문 등록하기
  const createQuestion = (question, programId) => {
    console.log("question 등록 request 에 담김", question);
    const REST_API_URL = getRestApiUrl(programId);

    axios
      .post(REST_API_URL, question, {
        headers: {
          "access-token": sessionStorage.getItem("access-token"),
          "Content-Type": "application/json",
        },
        withCredentials: true,
      })
      .then((res) => {
        // console.log("res.data", res.data);
        questionList.value.push(res.data);
        // router.replace({ name: "question" });
      })
      .catch((error) => {
        // 토큰 만료 시, 로그인 화면으로 이동
        if (error.response && error.response.status === 401) {
          // 토큰이 만료되었으므로 access-token을 삭제
          sessionStorage.removeItem("access-token");
          sessionStorage.removeItem("refresh-token");

          alert("로그인이 만료되었습니다. 로그인 페이지로 이동합니다.");

          // 로그인 페이지로 리다이렉트
          router.replace("/login");
        }
      });
  };

  // 질문 전체 조회하기
  const getQuestionList = (programId) => {
    const REST_API_URL = getRestApiUrl(programId);

    axios.get(REST_API_URL).then((res) => {
      // console.log("질문 리스트 조회", res.data);
      questionList.value = res.data;
    });
  };

  const singleQuestion = ref({});
  // 질문 하나 조회하기
  const getQuestion = (programId, questionId) => {
    const REST_API_URL = getRestApiUrl(programId) + `/${questionId}`;
    axios.get(REST_API_URL).then((res) => {
      // console.log("질문 하나 조회", res.data);
      singleQuestion.value = res.data;
    });
  };

  // 질문 삭제하기
  const deleteQuestion = (programId, questionId) => {
    const REST_API_URL = getRestApiUrl(programId) + `/${questionId}`;
    axios
      .delete(REST_API_URL, {
        headers: {
          "access-token": sessionStorage.getItem("access-token"),
          "Content-Type": "application/json",
        },
        withCredentials: true,
      })
      .then((res) => {
        router.replace({ name: "question", params: { programId: programId } });
      })
      .catch((error) => {
        // 토큰 만료 시, 로그인 화면으로 이동
        if (error.response && error.response.status === 401) {
          // 토큰이 만료되었으므로 access-token을 삭제
          sessionStorage.removeItem("access-token");
          sessionStorage.removeItem("refresh-token");

          alert("로그인이 만료되었습니다. 로그인 페이지로 이동합니다.");

          // 로그인 페이지로 리다이렉트
          router.replace("/login");
        }
      });
  };

  // 질문 수정하기
  const updateQuestion = (programId) => {
    const REST_API_URL =
      getRestApiUrl(programId) + `/${singleQuestion.value.questionId}`;
    console.log("singleQuestion", singleQuestion.value);

    axios
      .put(REST_API_URL, singleQuestion.value, {
        headers: {
          "access-token": sessionStorage.getItem("access-token"),
          "Content-Type": "application/json",
        },
        withCredentials: true,
      })
      .then((res) => {
        // console.log("res.data", res.data);
      })
      .catch((error) => {
        // 토큰 만료 시, 로그인 화면으로 이동
        if (error.response && error.response.status === 401) {
          // 토큰이 만료되었으므로 access-token을 삭제
          sessionStorage.removeItem("access-token");
          sessionStorage.removeItem("refresh-token");

          alert("로그인이 만료되었습니다. 로그인 페이지로 이동합니다.");

          // 로그인 페이지로 리다이렉트
          router.replace("/login");
        }
      });
  };

  // 질문 등록시 이미지 업로드
  const questionFileImg = ref(null);
  const uploadFile = async (formData) => {
    try {
      // Authorization 헤더 추가
      const response = await axios.post(
        "http://localhost:8080/file/upload",
        formData,
        {
          headers: {
            "Content-Type": "multipart/form-data",
            "access-token": sessionStorage.getItem("access-token"),
            "file-case": "QUESTION",
          },
        }
      );

      if (response.data) {
        questionFileImg.value = response.data;
        return response.data;
      }
    } catch (error) {
      handleError(error);
    }
  };

  return {
    createQuestion,
    getQuestionList,
    questionList,
    getQuestion,
    singleQuestion,
    deleteQuestion,
    updateQuestion,
    uploadFile,
  };
});
