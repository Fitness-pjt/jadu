import router from "@/router";
import axios from "axios";
import { defineStore } from "pinia";
import { ref } from "vue";
import { useProgramStore } from "./program";
import { handleError } from "@/utils/handleError";

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
        getTodoList(userId, todo.date);
        todoList.value.push(res.data); // 기존 배열에 새 항 추가
      })
      .catch((error) => {
        handleError(error);
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
        handleError(error);
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
        handleError(err);
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
        handleError(err);
      });
  };

  // 투두 초기 상태 불러오기
  const todoLikes = ref(new Map());
  const isFavorite = ref(null);
  // todo_likes DB에 todoId, userId가 일치하는 열이 있으면... 이미 좋아요가 눌러져 있다는 의미
  const getTodoLikesStatus = async (todoId, userId) => {
    const REST_API_URL = getRestApiUrl(userId) + `/${todoId}/likeTodo`;
    try {
      const response = await axios.get(REST_API_URL, {
        headers: {
          "access-token": sessionStorage.getItem("access-token"),
          "Content-Type": "application/json",
        },
        withCredentials: true,
      });
      todoLikes.value.set(todoId, response.data.isFavorite);
      return response.data.isFavorite;
    } catch (err) {
      handleError(err);
      return false;
    }
  };
  // 투두 좋아요 누르기
  const pushTodoLikes = async (todoId, userId) => {
    const REST_API_URL = getRestApiUrl(userId) + `/${todoId}/likeTodo`;
    try {
      await axios.post(REST_API_URL, null, {
        headers: {
          "access-token": sessionStorage.getItem("access-token"),
          "Content-Type": "application/json",
        },
        withCredentials: true,
      });
      todoLikes.value.set(todoId, true);
    } catch (err) {
      handleError(err);
    }
  };
  // 투두 좋아요 취소하기
  const cancelTodoLikes = async (todoId, userId) => {
    const REST_API_URL = getRestApiUrl(userId) + `/${todoId}/likeTodo`;
    try {
      await axios.delete(REST_API_URL, {
        headers: {
          "access-token": sessionStorage.getItem("access-token"),
          "Content-Type": "application/json",
        },
        withCredentials: true,
      });
      todoLikes.value.set(todoId, false);
    } catch (err) {
      handleError(err);
    }
  };

  // todo에 프로그램 추가하기
  const startProgram = async (programId, userId, startDate) => {
    try {
      const programStore = useProgramStore();

      const programData = await programStore.getProgramById(programId);

      // 2. 프로그램의 비디오 리스트 가져오기
      const programVideos = programData.videoIds || [];
      if (programVideos.length === 0) {
        throw new Error("프로그램에 등록된 영상이 없습니다.");
      }

      // 3. 프로그램 기간 계산 (주 단위를 일 단위로 변환)
      const totalDays = programData.durationWeeks * 7;

      const videoCount = programVideos.length;

      if (videoCount <= totalDays) {
        // 비디오 개수가 날짜보다 적거나 같을 때 기존 로직 유지
        const daysInterval = Math.floor(totalDays / videoCount);

        const todos = [];
        for (let i = 0; i < videoCount; i++) {
          const todoDate = new Date(startDate);
          todoDate.setDate(todoDate.getDate() + i * daysInterval);

          const todo = {
            programId,
            videoId: programVideos[i],
            date: todoDate.toISOString().split("T")[0],
            status: false,
            content: `${programData.title} - ${i + 1}회차 운동`,
          };

          await addTodo(todo, userId);
          todos.push(todo);
        }
        alert("프로그램이 Todo 리스트에 성공적으로 등록되었습니다!");
        router.push("/todo");

        return todos;
      } else {
        // 비디오 개수가 날짜보다 많을 때
        const basicAllocation = Math.floor(totalDays / videoCount); // 기본 할당 간격
        const extraVideos = videoCount % totalDays; // 초과 비디오 개수

        const todos = [];
        let videoIndex = 0;

        // 시작 날짜로부터 초과 비디오를 배분할 날짜 계산
        const extraDaysGap = Math.floor(totalDays / extraVideos); // 초과 비디오 간 날짜 간격
        const extraDays = [];
        for (let i = 0; i < extraVideos; i++) {
          extraDays.push(i * extraDaysGap); // 시작일로부터 간격으로 초과 날짜 설정
        }

        for (let day = 0; day < totalDays; day++) {
          const todoDate = new Date(startDate);
          todoDate.setDate(todoDate.getDate() + day);

          // 기본 할당된 비디오 추가
          if (videoIndex < videoCount) {
            const todo = {
              programId,
              videoId: programVideos[videoIndex],
              date: todoDate.toISOString().split("T")[0],
              status: false,
              content: `${programData.title} - ${videoIndex + 1}회차 운동`,
            };

            await addTodo(todo, userId);
            todos.push(todo);
            videoIndex++;
          }

          // 초과 비디오 추가
          if (extraDays.includes(day) && videoIndex < videoCount) {
            const extraTodoDate = new Date(startDate);
            extraTodoDate.setDate(extraTodoDate.getDate() + day);

            const extraTodo = {
              programId,
              videoId: programVideos[videoIndex],
              date: extraTodoDate.toISOString().split("T")[0],
              status: false,
              content: `${programData.title} - ${videoIndex + 1}회차 운동`,
            };

            await addTodo(extraTodo, userId);
            todos.push(extraTodo);
            videoIndex++;
          }
        }

        alert("프로그램이 Todo 리스트에 성공적으로 등록되었습니다!");
        router.push("/todo");

        return todos;
      }
    } catch (error) {
      handleError(error);
    }
  };

  // stores/todo.js
  const checkProgramProgress = async (programId, userId) => {
    try {
      const REST_API_URL = getRestApiUrl(userId) + `/progress/${programId}`;
      const response = await axios.get(REST_API_URL, {
        headers: {
          "access-token": sessionStorage.getItem("access-token"),
          "Content-Type": "application/json",
        },
      });
      console.log("Progress check response:", response.data); // 디버깅용
      if (response.data === undefined) {
        return { inProgress: false };
      }
      return response.data; // { inProgress: true/false }
    } catch (error) {
      handleError(err);
      return { inProgress: false };
    }
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
    startProgram,
    checkProgramProgress,
    todoLikes,
  };
});
