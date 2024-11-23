import router from "@/router";
import axios from "axios";
import { defineStore } from "pinia";
import { ref } from "vue";

export const useTodoStore = defineStore("todo", () => {
  // Todo 날짜 전역으로 관리
  const selectedDate = ref(null); //선택된 날짜를 저장

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
    axios
      .get(REST_API_URL)
      .then((res) => {
        // 요청 후 userId에 해당하는 todo만 todoList에 담기
        todoList.value = res.data;
      })
      .catch((err) => {
        // console.log("getTodoList err.message", err.response);
      });
  };

  // 투두 추가하기
  const addTodo = (todo, userId) => {
    const REST_API_URL = getRestApiUrl(userId) + `/${todo.date}`;
    axios
      .post(REST_API_URL, todo, {
        headers: {
          "access-token": sessionStorage.getItem("access-token"),
          "Content-Type": "application/json",
        },
        withCredentials: true,
      })
      .then((res) => {
        // console.log("투두 추가하기", res.data);

        // 새로운 투두 항목을 todoList에 추가
        todoList.value.push(res.data); // 기존 배열에 새 항 추가
      })
      .catch((error) => {
        if (error.response && error.response.status === 401) {
          // 토큰이 만료되었으므로 access-token을 삭제
          sessionStorage.removeItem("access-token");
          sessionStorage.removeItem("refresh-token");

          // 로그인 페이지로 리다이렉트
          router.replace("/login");
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
      .catch((error) => {
        if (error.response && error.response.status === 401) {
          // 토큰이 만료되었으므로 access-token을 삭제
          sessionStorage.removeItem("access-token");
          sessionStorage.removeItem("refresh-token");

          // 로그인 페이지로 리다이렉트
          router.replace("/login");
        }
      });
  };

  // 투두 내용 수정하기
  const updateTodoContent = (userId, todoId, content) => {
    const REST_API_URL = getRestApiUrl(userId) + `/${todoId}/content`;
    axios
      .put(REST_API_URL, content, {
        headers: {
          "access-token": sessionStorage.getItem("access-token"),
          "Content-Type": "application/json",
        },
        withCredentials: true,
      })
      .then((res) => {
        // console.log("updateTodoContent res.data", res.data);
      })
      .catch((err) => {
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

  // 투두 상태 수정하기
  const updateTodoStatus = (userId, todoId, status) => {
    const REST_API_URL = getRestApiUrl(userId) + `/${todoId}/status`;
    axios
      .put(REST_API_URL, status, {
        headers: {
          "access-token": sessionStorage.getItem("access-token"),
          "Content-Type": "application/json",
        },
        withCredentials: true,
      })
      .then((res) => {
        // console.log("updateTodoStatus res.data", res.data);
      })
      .catch((err) => {
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

  // 투두 초기 상태 불러오기
  const isFavorite = ref(null);
  // todo_likes DB에 todoId, userId가 일치하는 열이 있으면... 이미 좋아요가 눌러져 있다는 의미
  const getTodoLikesStatus = (todoId, userId) => {
    const REST_API_URL = getRestApiUrl(userId) + `/${todoId}/likeTodo`;
    axios
      .get(REST_API_URL, {
        headers: {
          "access-token": sessionStorage.getItem("access-token"),
          "Content-Type": "application/json",
        },
        withCredentials: true,
      })
      .then((res) => {
        // console.log("todo 초기 상태 :>> ", res.data.isFavorite);
        return res.data.isFavorite;
      })
      .catch((err) => {
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

  // 투두 좋아요 누르기
  const pushTodoLikes = (todoId, userId) => {
    const REST_API_URL = getRestApiUrl(userId) + `/${todoId}/likeTodo`;
    axios
      .post(REST_API_URL, null, {
        headers: {
          "access-token": sessionStorage.getItem("access-token"),
          "Content-Type": "application/json",
        },
        withCredentials: true,
      })
      .then((res) => {
        // console.log("res.data :>> ", res.data);
      })
      .catch((err) => {
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
  // 투두 좋아요 취소하기
  const cancelTodoLikes = (todoId, userId) => {
    const REST_API_URL = getRestApiUrl(userId) + `/${todoId}/likeTodo`;
    axios
      .delete(REST_API_URL, {
        headers: {
          "access-token": sessionStorage.getItem("access-token"),
          "Content-Type": "application/json",
        },
        withCredentials: true,
      })
      .then((res) => {
        // console.log("res.data :>> ", res.data);
      })
      .catch((err) => {
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

  // 투두 몇 개 있는지 조회하기
  const getTodoCount = () => {};

  return {
    selectedDate,
    setSelectedDate,
    getTodoList,
    todoList,
    addTodo,
    deleteTodo,
    updateTodoContent,
    updateTodoStatus,
    getTodoLikesStatus,
    pushTodoLikes,
    cancelTodoLikes,
    isFavorite,
  };
});
