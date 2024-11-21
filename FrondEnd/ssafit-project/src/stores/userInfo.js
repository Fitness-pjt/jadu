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
        // console.log("getUserInfo res.data", res.data);
        userInfoList.value = res.data;
        // Object.assign(userInfoList, res.data); // reactive 객체에 데이터 삽입

        console.log("userInfoList.value :>> ", userInfoList.value);
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

  const sendAnswerToServer = (userInfo) => {
    // console.log("userInfo :>> ", userInfo);
    const isUpdate =
      userInfoList.value && Object.keys(userInfoList.value).length > 0;
    console.log("isUpdate :>> ", isUpdate);
    // 데이터가 있으면 put 요청, 없으면 post 요청 날리기
    const method = isUpdate ? "put" : "post";
    console.log("sendAnswerToServer userInfo :>> ", userInfo);
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
        console.log("updateTodoContent res.data", res.data);
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

  const exerciseInfo = ref({
    gender: "",
    age: "",
    shape: "",
    goal: "",
    experience: 1,
    location: "",
    keyword: [],
    frequency: "",
    duration: "",
    fighting: "",
  });

  const updateExerciseInfo = (newInfo) => {
    exerciseInfo.value = { ...exerciseInfo.value, ...newInfo };
  };

  const resetExerciseInfo = () => {
    exerciseInfo.value = {
      gender: "",
      age: "",
      shape: "",
      goal: "",
      experience: 1,
      location: "",
      keyword: [],
      frequency: "",
      duration: "",
      fighting: "",
    };
  };

  return {
    sendAnswerToServer,
    getUserInfo,
    userInfoList,
    exerciseInfo,
    updateExerciseInfo,
    resetExerciseInfo,
  };
});
