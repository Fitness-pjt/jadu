import { ref, computed } from "vue";
import { defineStore } from "pinia";
import axios from "axios";
import router from "@/router";

const REST_API_URL = "http://localhost:8080/user";

export const useUserStore = defineStore("user", () => {
  // 회원가입
  const userSignUp = (user) => {
    axios
      .post(`${REST_API_URL}/signup`, user)
      .then((res) => {
        // console.log("res.data : >>", res.data);
        // alert("회원가입 성공");
        router.push({ name: "login" });
      })
      .catch((err) => {
        console.log("err", err);
      });
  };

  // 사용자 정보 불러오기
  const userMap = ref(new Map());

  const userProfileImg = ref(null);
  const userNickname = ref(null);

  const getUserProfileInfo = async (userId) => {
    try {
      const res = await axios.get(`${REST_API_URL}/${userId}`);
      // userMap에 유저 정보 저장
      userMap.value.set(userId, {
        userNickname: res.data.userNickname,
        profileImgPath: res.data.profileImgPath
      });
    } catch (error) {
      console.error(`Error fetching user ${userId}:`, error);
    }
  };
  const getUserById = (userId) => {
    return userMap.value.get(userId);
  };


  // const getUserProfileInfo = (userId) => {
  //   axios.get(`${REST_API_URL}/${userId}`).then((res) => {
  //     console.log("사용자 정보", res.data);
  //     userNickname.value = res.data.userNickname;
  //     userProfileImg.value=res.data.profileImgPath;
  //   });
  // };

  // 이메일 중복 체크
  const checkEmailDuplicate = async (email) => {
    try {
      const response = await axios.get(`${REST_API_URL}/check-email/${email}`);
      return response.data.available;
    } catch (error) {
      console.error("이메일 중복 체크 실패:", error);
      return false;
    }
  };

  // 닉네임 중복 체크
  const checkNicknameDuplicate = async (nickname) => {
    try {
      console.log(nickname)
      const response = await axios.get(`${REST_API_URL}/check-nickname/${nickname}`)
      console.log('response :>> ', response);
      return response.data.available;
    } catch (error) {
      console.error("닉네임 중복 체크 실패:", error);
      return false;
    }
  };



  return {
    userSignUp,
    getUserProfileInfo,
    userNickname,
    checkEmailDuplicate,
    checkNicknameDuplicate,
    userProfileImg,
    getUserById
  };
});
