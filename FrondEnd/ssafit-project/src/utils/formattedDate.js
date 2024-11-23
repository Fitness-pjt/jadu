// DB로부터 들어오는 날짜 포멧하는 메서드
// ex)2024-11-23 => 2024년 11월 23일 형태로 변경

export const formattedDate = (dateString) => {
  if (!dateString) return "";
  const date = new Date(dateString);

  return new Intl.DateTimeFormat("ko-KR", {
    year: "numeric",
    month: "long",
    day: "numeric",
  }).format(date);
};
