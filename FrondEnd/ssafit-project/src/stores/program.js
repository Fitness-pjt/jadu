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

      console.log("Response:", response.data); // 응답 데이터 로깅
      return response.data;
    } catch (err) {
      console.error("Error details:", err.response || err); // 상세 에러 로깅
      error.value =
        err.response?.data?.message || "프로그램 생성 중 오류가 발생했습니다.";
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
      console.error("Error fetching programs:", err);
      error.value =
        err.response?.data?.message ||
        "프로그램 목록 조회 중 오류가 발생했습니다.";
      throw error.value;
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
      console.error("Error fetching program:", err);
      error.value =
        err.response?.data?.message || "프로그램 조회 중 오류가 발생했습니다.";
      throw error.value;
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

      console.log('Updating program data:', programData);  // 요청 데이터 로깅

      const response = await axios.put(`${BASE_URL}/${programId}`, programData, {
        headers: {
          'Content-Type': 'application/json',
          "access-token": sessionStorage.getItem("access-token"),
          "Access-Control-Allow-Origin": "*",
        },
        withCredentials: true,
      });

      console.log('Update Response:', response.data);  // 응답 데이터 로깅

      // 현재 프로그램 데이터 업데이트
      currentProgram.value = response.data;

      // programs 배열에서도 해당 프로그램 업데이트
      const index = programs.value.findIndex(p => p.programId === programId);
      if (index !== -1) {
        programs.value[index] = response.data;
      }

      return response.data;

    } catch (err) {
      console.error('Error updating program:', err);
      error.value = err.response?.data?.message || '프로그램 수정 중 오류가 발생했습니다.';
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
      programs.value = programs.value.filter(p => p.programId !== programId);

      // 현재 프로그램이 삭제된 프로그램이면 null로 설정
      if (currentProgram.value?.programId === programId) {
        currentProgram.value = null;
      }

    } catch (err) {
      console.error('Error deleting program:', err);
      error.value = err.response?.data?.message || '프로그램 삭제 중 오류가 발생했습니다.';
      throw error.value;
    } finally {
      isLoading.value = false;
    }
  }


  return {
    isLoading,
    error,
    programs,
    currentProgram,
    createProgram,
    getAllPrograms,
    getProgramById,
    getProgramsByUserId,
    updateProgram, 
    deleteProgram  
  }
})
