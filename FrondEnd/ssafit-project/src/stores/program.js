// stores/program.js
import { defineStore } from "pinia";
import { ref } from "vue";
import axios from "axios";
import { handleError } from "@/utils/handleError";

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
    try {
      isLoading.value = true;
      error.value = null;

      const response = await axios.post(`${BASE_URL}`, programData, {
        headers: {
          "Content-Type": "application/json",
          // JWT 토큰이 필요한 경우 추가
          "access-token": sessionStorage.getItem("access-token"),
          "Access-Control-Allow-Origin": "*",
        },
        withCredentials: true,
      });

      return response.data;
    } catch (err) {
      handleError(err);
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
      handleError(err);
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
      return response.data;
    } catch (err) {
      handleError(err);
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
