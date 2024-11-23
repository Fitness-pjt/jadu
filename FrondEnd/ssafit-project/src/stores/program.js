// stores/program.js
import { defineStore } from "pinia";
import { ref } from "vue";
import axios from "axios";

// 백엔드 서버 URL 설정
const BASE_URL = "http://localhost:8080/program";

export const useProgramStore = defineStore("program", () => {
  const isLoading = ref(false);
  const error = ref(null);
  const programs = ref([]); // 프로그램 목록 저장
  const currentProgram = ref(null); // 현재 선택된 프로그램
  const likeCount = ref(0);
  const isLiked = ref(false);

  async function createProgram(programData) {
    // console.log('programData :>> ', programData);

    try {
      isLoading.value = true;
      error.value = null;

      console.log("Sending program data:", programData); // 요청 데이터 로깅

      const response = await axios.post(`${BASE_URL}`, programData, {
        headers: {
          "Content-Type": "application/json",
          // JWT 토큰이 필요한 경우 추가
          "access-token": sessionStorage.getItem("access-token"),
          "Access-Control-Allow-Origin": "*",
        },
        withCredentials: true,
      });

      // console.log("Response:", response.data); // 응답 데이터 로깅
      return response.data;
    } catch (err) {
      console.error("Error details:", err.response || err); // 상세 에러 로깅
      error.value =
        err.response?.data?.message || "프로그램 생성 중 오류가 발생했습니다.";

      // 토큰 만료 시, 로그인 화면으로 이동
      if (error.response && error.response.status === 401) {
        alert("로그인이 만료 되었습니다.");
        // 토큰이 만료되었으므로 access-token을 삭제
        sessionStorage.removeItem("access-token");
        sessionStorage.removeItem("refresh-token");

        alert("로그인이 만료되었습니다. 로그인 페이지로 이동합니다.");

        // 로그인 페이지로 리다이렉트
        router.replace("/login");
      }

      throw error.value;
    } finally {
      isLoading.value = false;
    }
  }

  async function getAllPrograms() {
    try {
      isLoading.value = true;
      error.value = null;

      const response = await axios.get(`${BASE_URL}`, {
        headers: {
          "access-token": sessionStorage.getItem("access-token"),
          "Access-Control-Allow-Origin": "*",
        },
        withCredentials: true,
      });

      programs.value = response.data;
      return response.data;
    } catch (err) {
      console.error("Error fetching programs:", err.response);
      // error.value =
      //   err.response?.data?.message ||
      //   "프로그램 목록 조회 중 오류가 발생했습니다.";

      // 토큰 만료 시, 로그인 화면으로 이동
      if (err.response && err.response.status === 401) {
        // 토큰이 만료되었으므로 access-token을 삭제
        sessionStorage.removeItem("access-token");
        sessionStorage.removeItem("refresh-token");

        alert("로그인이 만료되었습니다. 로그인 페이지로 이동합니다.");

        // 로그인 페이지로 리다이렉트
        router.replace("/login");
      }
      // throw error.value;
    } finally {
      isLoading.value = false;
    }
  }

  // 단일 프로그램 조회
  async function getProgramById(programId) {
    try {
      isLoading.value = true;
      error.value = null;

      const response = await axios.get(`${BASE_URL}/${programId}`, {
        headers: {
          "access-token": sessionStorage.getItem("access-token"),
          "Access-Control-Allow-Origin": "*",
        },
        withCredentials: true,
      });

      currentProgram.value = response.data;
      console.log("currentProgram.value :>> ", currentProgram.value);
      return response.data;
    } catch (err) {
      // console.error("Error fetching program:", err);
      // error.value =
      //   err.response?.data?.message || "프로그램 조회 중 오류가 발생했습니다.";
      // throw error.value;

      // 토큰 만료 시, 로그인 화면으로 이동
      if (err.response && err.response.status === 401) {
        // 토큰이 만료되었으므로 access-token을 삭제
        sessionStorage.removeItem("access-token");
        sessionStorage.removeItem("refresh-token");

        alert("로그인이 만료되었습니다. 로그인 페이지로 이동합니다.");

        // 로그인 페이지로 리다이렉트
        router.replace("/login");
      }
    } finally {
      isLoading.value = false;
    }
  }

  // 사용자별 프로그램 조회
  async function getProgramsByUserId(userId) {
    try {
      isLoading.value = true;
      error.value = null;

      const response = await axios.get(`${BASE_URL}/user/${userId}`, {
        headers: {
          "access-token": sessionStorage.getItem("access-token"),
          "Access-Control-Allow-Origin": "*",
        },
        withCredentials: true,
      });

      programs.value = response.data;
      return response.data;
    } catch (err) {
      console.error("Error fetching user programs:", err);
      error.value =
        err.response?.data?.message ||
        "사용자 프로그램 조회 중 오류가 발생했습니다.";
      throw error.value;
    } finally {
      isLoading.value = false;
    }
  }
  async function updateProgram(programId, programData) {
    try {
      isLoading.value = true;
      error.value = null;

      console.log("Updating program data:", programData); // 요청 데이터 로깅

      const response = await axios.put(
        `${BASE_URL}/${programId}`,
        programData,
        {
          headers: {
            "Content-Type": "application/json",
            "access-token": sessionStorage.getItem("access-token"),
            "Access-Control-Allow-Origin": "*",
          },
          withCredentials: true,
        }
      );

      console.log("Update Response:", response.data); // 응답 데이터 로깅

      // 현재 프로그램 데이터 업데이트
      currentProgram.value = response.data;

      // programs 배열에서도 해당 프로그램 업데이트
      const index = programs.value.findIndex((p) => p.programId === programId);
      if (index !== -1) {
        programs.value[index] = response.data;
      }

      return response.data;
    } catch (err) {
      console.error("Error updating program:", err);
      error.value =
        err.response?.data?.message || "프로그램 수정 중 오류가 발생했습니다.";
      throw error.value;
    } finally {
      isLoading.value = false;
    }
  }

  async function deleteProgram(programId) {
    try {
      isLoading.value = true;
      error.value = null;

      await axios.delete(`${BASE_URL}/${programId}`, {
        headers: {
          "access-token": sessionStorage.getItem("access-token"),
          "Access-Control-Allow-Origin": "*",
        },
        withCredentials: true,
      });

      // 프로그램 목록에서 삭제된 프로그램 제거
      programs.value = programs.value.filter((p) => p.programId !== programId);

      // 현재 프로그램이 삭제된 프로그램이면 null로 설정
      if (currentProgram.value?.programId === programId) {
        currentProgram.value = null;
      }
    } catch (err) {
      console.error("Error deleting program:", err);
      error.value =
        err.response?.data?.message || "프로그램 삭제 중 오류가 발생했습니다.";
      throw error.value;
    } finally {
      isLoading.value = false;
    }
  }

  const updateProgramThumbnail = async (formData, programId) => {
    try {
      // 파일 업로드 API 호출
      const response = await axios.post(
        "http://localhost:8080/file/upload",
        formData,
        {
          headers: {
            "Content-Type": "multipart/form-data",
            "access-token": sessionStorage.getItem("access-token"),
            "file-case": "PROGRAM",
            "program-id": programId,
          },
        }
      );

      if (response.data) {
        // currentProgram 상태 업데이트
        currentProgram.value = {
          ...currentProgram.value,
          programImgPath: response.data.filePath,
        };

        // programs 배열에서도 해당 프로그램 업데이트
        const index = programs.value.findIndex(
          (p) => p.programId === programId
        );
        if (index !== -1) {
          programs.value[index] = {
            ...programs.value[index],
            programImgPath: response.data.filePath,
          };
        }

        return response.data;
      }
    } catch (error) {
      // 토큰 관련 에러 처리
      if (error.response?.status === 401) {
        console.error("인증 토큰이 유효하지 않습니다.");
      }
      console.error("이미지 업로드 실패:", error);
      throw error;
    }
  };

  async function checkLikeStatus(programId) {
    // 로그인 상태가 아니면 false 반환
    if (!sessionStorage.getItem("access-token")) {
      isLiked.value = false;
      return false;
    }

    try {
      const response = await axios.get(`${BASE_URL}/${programId}/like/check`, {
        headers: {
          "access-token": sessionStorage.getItem("access-token"),
        },
      });
      isLiked.value = response.data;
      return response.data;
    } catch (err) {
      console.error("좋아요 상태 확인 실패:", err);
      isLiked.value = false;
      return false;
    }
  }

  // 좋아요 수 가져오기
  async function getLikeCount(programId) {
    try {
      const response = await axios.get(`${BASE_URL}/${programId}/like/count`, {
        headers: {
          "access-token": sessionStorage.getItem("access-token"),
        },
      });
      likeCount.value = response.data;
      return response.data;
    } catch (err) {
      console.error("좋아요 수 가져오기 실패:", err);
      likeCount.value = 0;
      return 0;
    }
  }

  // 좋아요 토글
  async function toggleProgramLike(programId) {
    if (!sessionStorage.getItem("access-token")) {
      throw new Error("로그인이 필요한 기능입니다.");
    }

    try {
      const response = await axios.post(
        `${BASE_URL}/${programId}/like/toggle`,
        null,
        {
          headers: {
            "access-token": sessionStorage.getItem("access-token"),
          },
        }
      );

      isLiked.value = response.data.liked;
      likeCount.value = response.data.likeCount;

      return response.data;
    } catch (err) {
      console.error("좋아요 토글 실패:", err);
      throw err;
    }
  }

  return {
    isLoading,
    error,
    programs,
    currentProgram,
    likeCount,
    isLiked,
    createProgram,
    getAllPrograms,
    getProgramById,
    getProgramsByUserId,
    updateProgram,
    deleteProgram,
    updateProgramThumbnail,
    checkLikeStatus,
    getLikeCount,
    toggleProgramLike,
  };
});
