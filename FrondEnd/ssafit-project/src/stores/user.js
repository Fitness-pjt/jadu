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
        // console.log("err", err);
      });
  };

  // 사용자 정보 불러오기
  const userMap = ref(new Map());
  const userProfileImg = ref(null);
  const userNickname = ref(null);
  const userName = ref(null);
  const userStatus = ref(true); // 기본값 true

  const getUserListProfileInfo = async (userId) => {
    try {
      const res = await axios.get(`${REST_API_URL}/${userId}`);
      // userMap에 유저 정보 저장
      userMap.value.set(userId, {
        userNickname: res.data.userNickname,
        profileImgPath: res.data.profileImgPath,
      });
    } catch (error) {
      console.error(`Error fetching user ${userId}:`, error);
    }
  };
  const getUserById = (userId) => {
    return userMap.value.get(userId);
  };

  const getUserProfileInfo = (userId) => {
    axios.get(`${REST_API_URL}/${userId}`).then((res) => {
      // console.log("사용자 정보", res.data);
      userNickname.value = res.data.userNickname;
      userProfileImg.value = res.data.profileImgPath;
      userName.value = res.data.userName;
      userStatus.value = res.data.userStatus; // 추가
    });
  };

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
      // console.log(nickname);
      const response = await axios.get(
        `${REST_API_URL}/check-nickname/${nickname}`
      );
      return response.data.available;
    } catch (error) {
      console.error("닉네임 중복 체크 실패:", error);
      return false;
    }
  };

  // 프로필 이미지 업로드 함수 추가
  const uploadProfileImage = async (formData) => {
    try {
      // Authorization 헤더 추가
      const response = await axios.post(
        "http://localhost:8080/file/upload",
        formData,
        {
          headers: {
            "Content-Type": "multipart/form-data",
            "access-token": sessionStorage.getItem("access-token"),
            "file-case": "PROFILE",
          },
        }
      );
      if (response.data) {
        userProfileImg.value = response.data;
        return response.data;
      }
    } catch (error) {
      // 토큰 관련 에러 처리 추가
      if (error.response?.status === 401) {
        console.error("인증 토큰이 유효하지 않습니다.");
        // 필요한 경우 로그인 페이지로 리다이렉트 추가
        // router.push('/login');
      }
      console.error("이미지 업로드 실패:", error);
      throw error;
    }
  };

  const updateUser = async (updateData) => {
    try {
      const response = await axios.put(
        `${REST_API_URL}/${updateData.userId}`,
        {
          userNickname: updateData.nickname,
          profileImgPath: updateData.profileImage,
          userName: updateData.name,
          userStatus: updateData.status,
        },
        {
          headers: {
            "Content-Type": "application/json",
            "access-token": sessionStorage.getItem("access-token"),
          },
          withCredentials: true,
        }
      );

      // 성공 시 store의 상태 업데이트
      userNickname.value = response.data.userNickname;
      userProfileImg.value = response.data.profileImgPath;
      userStatus.value = response.data.userStatus;
      return response.data;
    } catch (error) {
      console.error("유저 정보 업데이트 실패:", error);
      throw error;
    }
  };
  return {
    userSignUp,
    getUserListProfileInfo,
    getUserProfileInfo,
    getUserListProfileInfo,
    userNickname,
    checkEmailDuplicate,
    checkNicknameDuplicate,
    userProfileImg,
    getUserById,
    uploadProfileImage,
    updateUser,
    userMap,
    userName,
    userStatus,
  };
});
