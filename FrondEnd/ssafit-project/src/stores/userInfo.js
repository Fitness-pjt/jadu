import { ref, computed, reactive } from "vue";
import { defineStore } from "pinia";
import axios from "axios";
import router from "@/router";

const REST_API_URL = "http://localhost:8080/user/info";
export const useUserInfoStore = defineStore("userInfo", () => {
  // userInfoList 초기화
  const userInfoList = ref({});

  const getUserInfo = () => {
    axios
      .get(REST_API_URL, {
        headers: {
          "access-token": sessionStorage.getItem("access-token"),
          "Content-Type": "application/json",
        },
        withCredentials: true,
      })
      .then((res) => {
        // console.log("API 응답 데이터: ", res.data); // 응답 데이터 구조 확인
        userInfoList.value = res.data || {}; // 응답 데이터를 userInfoList에 할당
        // console.log("userInfoList.value :>> ", userInfoList.value);
        if (Object.keys(userInfoList.value).length > 0) {
          exerciseInfo.value = userInfoList.value; // 데이터가 있을 경우만 업데이트
        }
      })
      .catch((err) => {
        console.error("API 요청 오류: ", err);
        if (err.response && err.response.status === 401) {
          // 토큰이 만료되었으므로 access-token을 삭제
          sessionStorage.removeItem("access-token");
          sessionStorage.removeItem("refresh-token");
          alert("로그인이 만료되었습니다. 로그인 페이지로 이동합니다.");
          // 로그인 페이지로 리다이렉트
          router.replace("/login");
        }
      });
  };

  const sendAnswerToServer = (userInfo) => {
    // console.log("userInfo :>> ", userInfo);
    console.log("userInfoList.value :>> ", userInfoList.value);
    const isUpdate =
      userInfoList.value && Object.keys(userInfoList.value).length > 0;
    // 데이터가 있으면 put 요청, 없으면 post 요청 날리기
    const method = isUpdate ? "put" : "post";
    axios({
      method,
      url: REST_API_URL,
      data: userInfo,
      headers: {
        "access-token": sessionStorage.getItem("access-token"),
        "Content-Type": "application/json",
      },
      withCredentials: true,
    })
      .then((res) => {
        userInfoList.value = res.data;
      })
      .catch((err) => {
        // console.log("err.response :>> ", err.response);
        if (err.response && err.response.status === 401) {
          // 토큰이 만료되었으므로 access-token을 삭제
          sessionStorage.removeItem("access-token");
          sessionStorage.removeItem("refresh-token");
          alert("로그인이 만료되었습니다. 로그인 페이지로 이동합니다.");
          // 로그인 페이지로 리다이렉트
          router.replace("/login");
        }
      });
  };

  // 업데이트를 하기 위한 기본 객체 생성
  const exerciseInfo = ref({
    gender: "",
    age: "",
    shape: "",
    goal: "",
    experience: 3,
    location: "",
    keyword: [],
    frequency: "",
    duration: "",
    fighting: "",
  });

  // AI Program REST 호출
  // userInfoStore에서 API 호출을 처리하는 메서드 수정
  const createAIProgram = async (userInfo) => {
    try {
      const response = await axios.post(
        "http://localhost:8080/chatGPT",
        userInfo,
        {
          headers: {
            "access-token": sessionStorage.getItem("access-token"),
            "Content-Type": "application/json",
          },
          withCredentials: true,
        }
      );
      console.log("API 응답 데이터 프로그램 ID: ", response.data); // 응답 데이터 구조 확인
      return response.data; // programId를 반환
    } catch (err) {
      console.error("API 요청 오류: ", err);
      if (err.response && err.response.status === 401) {
        sessionStorage.removeItem("access-token");
        sessionStorage.removeItem("refresh-token");
        alert("로그인이 만료되었습니다. 로그인 페이지로 이동합니다.");
        router.replace("/login");
      }
    }
  };

  return {
    sendAnswerToServer,
    getUserInfo,
    userInfoList,
    exerciseInfo,
    createAIProgram,
  };
});
