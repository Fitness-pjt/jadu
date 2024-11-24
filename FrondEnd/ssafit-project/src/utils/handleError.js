import router from "@/router";

export const handleError = (err) => {
  if (err.response && err.response.status === 401) {
    // 인증 관련 에러 처리
    sessionStorage.removeItem("access-token");
    sessionStorage.removeItem("refresh-token");
    alert("로그인이 만료되었습니다. 로그인 페이지로 이동합니다.");
    router.replace("/login");
  } else {
    // 기타 에러 처리
    console.error("오류 발생:", err);
    // 필요한 경우 사용자에게 에러 메시지 표시
    if (err.response?.data?.message) {
      console.log(err.response.data.message);
    } else {
      console.log("요청 처리 중 오류가 발생했습니다.");
    }
  }
};
