import { ref, computed } from "vue";
import { defineStore } from "pinia";
import axios from "axios";
import router from "@/router";
import { Base64 } from "js-base64";

export const useTodoStore = defineStore("todo", () => {
  const getRestApiUrl = (userId) => {
    return `http://localhost:8080/${userId}/todo`;
  };

  const todoList = ref([]);

  // 사용자 투두 리스트 전체 조회하기
  const getTodoList = (userId, date) => {
    const REST_API_URL = getRestApiUrl(userId) + `/${date}`;
    axios.get(REST_API_URL).then((res) => {
      console.log("res.data", res.data);
    });
  };

  return { getRestApiUrl, getTodoList };
});
