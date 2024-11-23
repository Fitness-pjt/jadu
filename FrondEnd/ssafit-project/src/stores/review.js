import router from "@/router";
import axios from "axios";
import { defineStore } from "pinia";
import { ref } from "vue";

export const useReviewStore = defineStore("review", () => {
  const getRestApiUrl = (programId) => {
    return `http://localhost:8080/program/${programId}/review`;
  };

  const review = ref({});
  const reviewList = ref([]);

  // 리뷰 등록하기
  const createReview = (review, programId) => {
    const REST_API_URL = getRestApiUrl(programId);

    axios
      .post(REST_API_URL, review, {
        headers: {
          "access-token": sessionStorage.getItem("access-token"),
          "Content-Type": "application/json",
        },
        withCredentials: true,
      })
      .then((res) => {
        // 새로운 리뷰를 리스트에 바로 추가
        reviewList.value.unshift(res.data); // 최신 리뷰를 맨 위에 추가
      })
      .catch((error) => {
        // 토큰 만료 시, 로그인 화면으로 이동
        if (error.response && error.response.status === 401) {
          // 토큰이 만료되었으므로 access-token을 삭제
          sessionStorage.removeItem("access-token");
          sessionStorage.removeItem("refresh-token");

          alert("로그인이 만료되었습니다. 로그인 페이지로 이동합니다.");

          // 로그인 페이지로 리다이렉트
          router.replace("/login");
        }
      });
  };

  // 리뷰 전체 조회하기
  const getReviewList = (programId) => {
    const REST_API_URL = getRestApiUrl(programId);

    axios.get(REST_API_URL).then((res) => {
      console.log("res.data", res.data);
      reviewList.value = res.data;
    });
  };

  // 리뷰 삭제하기
  const deleteReview = (programId, reviewId) => {
    const REST_API_URL = getRestApiUrl(programId) + `/${reviewId}`;
    axios
      .delete(REST_API_URL, {
        headers: {
          "access-token": sessionStorage.getItem("access-token"),
          "Content-Type": "application/json",
        },
        withCredentials: true,
      })
      .then((res) => {
        // console.log("res.data", res.data);
        reviewList.value = reviewList.value.filter(
          (review) => review.reviewId !== reviewId
        );
        router.replace({ name: "review", params: { programId: programId } });
      })
      .catch((error) => {
        // 토큰 만료 시, 로그인 화면으로 이동
        if (error.response && error.response.status === 401) {
          // 토큰이 만료되었으므로 access-token을 삭제
          sessionStorage.removeItem("access-token");
          sessionStorage.removeItem("refresh-token");

          alert("로그인이 만료되었습니다. 로그인 페이지로 이동합니다.");

          // 로그인 페이지로 리다이렉트
          router.replace("/login");
        }
      });
  };

  // 리뷰 수정하기
  const updateReview = (programId, reviewId, content) => {
    const REST_API_URL = getRestApiUrl(programId);
    // const reviewId = singleReview.value.id;

    axios.put(REST_API_URL, content).then((res) => {
      console.log("res.data", res.data);

      // 라우터 이동
      router
        .replace({
          name: "reviewDetail",
          params: { programId: programId, reviewId: reviewId },
        })
        .catch((err) => {
          console.error("Router navigation failed:", err);
        });
    });
  };

  return {
    review,
    createReview,
    getReviewList,
    reviewList,
    deleteReview,
    updateReview,
  };
});
