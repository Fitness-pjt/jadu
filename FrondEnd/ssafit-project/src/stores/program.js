// stores/program.js
import { defineStore } from 'pinia'
import { ref } from 'vue'
import axios from 'axios'

// 백엔드 서버 URL 설정
const BASE_URL = 'http://localhost:8080/program'

export const useProgramStore = defineStore('program', () => {
  const isLoading = ref(false)
  const error = ref(null)
  const programs = ref([])  // 프로그램 목록 저장
  const currentProgram = ref(null)  // 현재 선택된 프로그램


  async function createProgram(programData) {

    console.log('programData :>> ', programData);


    try {
      isLoading.value = true
      error.value = null

      console.log('Sending program data:', programData);  // 요청 데이터 로깅

      const response = await axios.post(`${BASE_URL}`, programData, {
        headers: {
          'Content-Type': 'application/json',
          // JWT 토큰이 필요한 경우 추가
          "access-token": sessionStorage.getItem("access-token"),
          "Access-Control-Allow-Origin": "*",
        },
        withCredentials: true,

      });

      console.log('Response:', response.data);  // 응답 데이터 로깅
      return response.data;

    } catch (err) {
      console.error('Error details:', err.response || err);  // 상세 에러 로깅
      error.value = err.response?.data?.message || '프로그램 생성 중 오류가 발생했습니다.'
      throw error.value

    } finally {
      isLoading.value = false
    }
  }

  async function getAllPrograms() {
    try {
      isLoading.value = true
      error.value = null

      const response = await axios.get(`${BASE_URL}`, {
        headers: {
          "access-token": sessionStorage.getItem("access-token"),
          "Access-Control-Allow-Origin": "*",
        },
        withCredentials: true,
      })

      programs.value = response.data
      return response.data

    } catch (err) {
      console.error('Error fetching programs:', err)
      error.value = err.response?.data?.message || '프로그램 목록 조회 중 오류가 발생했습니다.'
      throw error.value
    } finally {
      isLoading.value = false
    }
  }

  // 단일 프로그램 조회
  async function getProgramById(programId) {
    try {
      isLoading.value = true
      error.value = null

      const response = await axios.get(`${BASE_URL}/${programId}`, {
        headers: {
          "access-token": sessionStorage.getItem("access-token"),
          "Access-Control-Allow-Origin": "*",
        },
        withCredentials: true,
      })

      currentProgram.value = response.data
      return response.data

    } catch (err) {
      console.error('Error fetching program:', err)
      error.value = err.response?.data?.message || '프로그램 조회 중 오류가 발생했습니다.'
      throw error.value
    } finally {
      isLoading.value = false
    }
  }

  // 사용자별 프로그램 조회
  async function getProgramsByUserId(userId) {
    try {
      isLoading.value = true
      error.value = null

      const response = await axios.get(`${BASE_URL}/user/${userId}`, {
        headers: {
          "access-token": sessionStorage.getItem("access-token"),
          "Access-Control-Allow-Origin": "*",
        },
        withCredentials: true,
      })

      programs.value = response.data
      return response.data

    } catch (err) {
      console.error('Error fetching user programs:', err)
      error.value = err.response?.data?.message || '사용자 프로그램 조회 중 오류가 발생했습니다.'
      throw error.value
    } finally {
      isLoading.value = false
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
    getProgramsByUserId
  }
})