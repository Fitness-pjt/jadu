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
        console.log("res.data : >>", res.data);
        // alert("회원가입 성공");
        router.push({ name: "login" });
      })
      .catch((err) => {
        console.log("err", err);
      });
  };

  return { userSignUp };
});
