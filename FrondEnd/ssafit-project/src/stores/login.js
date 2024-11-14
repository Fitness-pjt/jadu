import { ref, computed } from "vue";
import { defineStore } from "pinia";
import axios from "axios";
import router from "@/router";

const REST_API_URL = "http://localhost:8080/auth/login";

export const useLoginStore = defineStore("login", () => {
  const login = (loginUser) => {
    axios.post(REST_API_URL, loginUser).then((res) => {
      console.log("res.data :>> ", res.data);
      router.replace({ name: "home" });
    });
  };
  return { login };
});
