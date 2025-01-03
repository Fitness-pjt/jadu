import router from "@/router";
import { handleError } from "@/utils/handleError";
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
  const createReview = async (review, programId) => {
    const REST_API_URL = getRestApiUrl(programId);

    try {
      const response = await axios.post(REST_API_URL, review, {
        headers: {
          "access-token": sessionStorage.getItem("access-token"),
          "Content-Type": "application/json",
        },
        withCredentials: true,
      });

      // 새로운 리뷰를 리스트에 바로 추가
      reviewList.getReviewList(programId);
      reviewList.value.unshift(response.data); // 최신 리뷰를 맨 위에 추가
    } catch (error) {
      handleError(error);
    }
  };

  // 리뷰 전체 조회하기
  const getReviewList = async (programId) => {
    const REST_API_URL = getRestApiUrl(programId);
    try {
      const response = await axios.get(REST_API_URL);
      // console.log("res.data", response.data);
      reviewList.value = response.data;
    } catch (error) {
      console.error("Error fetching answer list:", error);
    }
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
        handleError(error);
      });
  };

  // 리뷰 수정하기
  const updateReview = (programId, reviewId, content) => {
    const REST_API_URL = getRestApiUrl(programId);
    // const reviewId = singleReview.value.id;

    axios.put(REST_API_URL, content).then((res) => {
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
