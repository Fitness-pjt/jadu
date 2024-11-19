import router from "@/router";
import axios from "axios";
import { defineStore } from "pinia";
import { ref } from "vue";

export const useTodoStore = defineStore("todo", () => {
  // Todo 날짜 전역으로 관리
  const selectedDate = ref(new Date().toISOString().slice(0, 10)); //선택된 날짜를 저장

  const setSelectedDate = (date) => {
    selectedDate.value = date;
  };

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
      // 요청 후 userId에 해당하는 todo만 todoList에 담기
      todoList.value = res.data;
    });
  };

  // 투두 추가하기
  const addTodo = (todo, userId) => {
    // console.log("todo.date :>> ", todo.date);
    // console.log("userId :>> ", userId);
    const REST_API_URL = getRestApiUrl(userId) + `/${todo.date}`;
    axios
      .post(REST_API_URL, todo, {
        headers: {
          "access-token": sessionStorage.getItem("access-token"),
          "Content-Type": "application/json",
        },
        withCredentials: true,
        목,
      })
      .then((res) => {
        // console.log("투두 추가하기", res.data);

        // 새로운 투두 항목을 todoList에 추가
        todoList.value.push(res.data); // 기존 배열에 새 항 추가
      })
      .catch((error) => {
        // console.log("error.request", error.request);
        // if (error.response) {
        //   console.log("Error response data:", error.response.data); // 응답 본문
        //   console.log("Error response status:", error.response.status); // 상태 코드
        //   console.log("Error response headers:", error.response.headers); // 헤더
        // } else if (error.request) {
        //   console.log("No response received:", error.request); // 요청은 성공적으로 보내졌지만 응답을 받지 못함
        // } else {
        //   console.log("Other error:", error.message); // 요청이 보내지지 못한 경우
        // }

        if (error.response && error.response.status === 401) {
          // 토큰이 만료되었으므로 access-token을 삭제
          sessionStorage.removeItem("access-token");

          // 로그인 페이지로 리다이렉트
          router.push("/login");
        }
      });
  };

  // 투두 삭제하기
  const deleteTodo = (userId, todoId) => {
    const REST_API_URL = getRestApiUrl(userId) + `/${todoId}`;
    axios
      .delete(REST_API_URL, {
        headers: {
          "access-token": sessionStorage.getItem("access-token"),
          "Content-Type": "application/json",
        },
        withCredentials: true,
      })
      .then((res) => {
        // console.log("deleteTodo res.data :>> ", res.data);

        // 성공적으로 삭제되었으면, todoList에서 해당 todoId를 가진 항목을 제거
        todoList.value = todoList.value.filter(
          (todo) => todo.todoId !== todoId
        );
      })
      .catch((err) => {
        console.log("err :>> ", err.response);
      });
  };

  return {
    selectedDate,
    setSelectedDate,
    getTodoList,
    todoList,
    addTodo,
    deleteTodo,
  };
});
