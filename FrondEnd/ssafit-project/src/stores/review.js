import router from "@/router";
import axios from "axios";
import { defineStore } from "pinia";
import { ref } from "vue";

export const useReviewStore = defineStore("review", () => {
  const getRestApiUrl = (programId) => {
    return `http://localhost:8080/program/${programId}/review`; // Assuming reviews are a sub-resource
  };

  const review = ref({});

  // 리뷰 등록하기
  const createReview = (review, programId) => {
    const REST_API_URL = getRestApiUrl(programId);

    axios
      .post(REST_API_URL, review, {
        headers: {
          "access-token": sessionStorage.getItem("access-token"),
        },
      })
      .then((res) => {
        console.log("res.data", res.data);
        router.replace({ name: "review" });
      });
  };

  const reviewList = ref([]);

  // 리뷰 전체 조회하기
  const getReviewList = (programId) => {
    const REST_API_URL = getRestApiUrl(programId);

    axios.get(REST_API_URL).then((res) => {
      console.log("res.data", res.data);
      reviewList.value = res.data;
    });
  };

  const singleReview = ref({});
  // 리뷰 하나 조회하기
  const getReview = (programId, reviewId) => {
    const REST_API_URL = getRestApiUrl(programId) + `/${reviewId}`;
    axios.get(REST_API_URL).then((res) => {
      console.log("res.data", res.data);
      singleReview.value = res.data;
    });
  };

  // 리뷰 삭제하기
  const deleteReview = (programId, reviewId) => {
    const REST_API_URL = getRestApiUrl(programId) + `/${reviewId}`;
    axios.delete(REST_API_URL).then((res) => {
      console.log("res.data", res.data);
      router.replace({ name: "review", params: { programId: programId } });
    });
  };

  // 리뷰 수정하기
  const updateReview = (programId) => {
    const REST_API_URL = getRestApiUrl(programId);
    console.log("singleReview", singleReview);
    const reviewId = singleReview.value.id;

    axios.put(REST_API_URL, singleReview.value).then((res) => {
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
    getReview,
    singleReview,
    deleteReview,
    updateReview,
  };
});
