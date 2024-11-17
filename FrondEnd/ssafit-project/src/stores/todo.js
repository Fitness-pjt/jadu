import { ref, computed } from "vue";
import { defineStore } from "pinia";
import axios from "axios";
import router from "@/router";
import { Base64 } from "js-base64";

export const useTodoStore = defineStore("todo", () => {
  const getRestApiUrl = (userId) => {
    return `http://localhost:8080/${userId}/todo`;
  };

  // 사용자 투두 리스트 전체 조회하기
  const todoList = ref([]);
  const getTodoList = (userId, date) => {
    // TodoList 초기화하기
    todoList.value = [];
    const REST_API_URL = getRestApiUrl(userId) + `/${date}`;
    axios.get(REST_API_URL).then((res) => {
      // console.log("res.data", res.data);
      // 요청 후 userId에 해당하는 todo만 todoList에 담기
      todoList.value = res.data;
    });
  };

  // 투두 추가하기
  const addTodo = (todo, userId) => {
    const REST_API_URL = getRestApiUrl(userId) + `/${todo.date}`;
    axios
      .post(REST_API_URL, todo, {
        headers: {
          "access-token": sessionStorage.getItem("access-token"),
        },
      })
      .then((res) => {
        // console.log("투두 추가하기", res.data);
      })
      .catch((error) => {
        if (error.response && error.response.status === 401) {
          // 토큰이 만료되었으므로 access-token을 삭제
          sessionStorage.removeItem("access-token");

          // 로그인 페이지로 리다이렉트
          router.push("/login");
        }
      });
  };

  return { getTodoList, todoList, addTodo };
});
