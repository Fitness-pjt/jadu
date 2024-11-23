// DB로부터 들어오는 날짜 포멧하는 메서드
// ex)2024-11-23T14:30:00 => 2024년 11월 23일 형태로 변경

export const formattedDate = (dateString) => {
  if (!dateString) return "";
  const date = new Date(dateString);

  return new Intl.DateTimeFormat("ko-KR", {
    year: "numeric",
    month: "long",
    day: "numeric",
  }).format(date);
};

// DB로부터 들어오는 날짜 포맷하는 메서드
// ex) 2024-11-23T14:30:00 => 2024년 11월 23일 오후 2:30 형태로 변경
export const formattedDateTime = (dateString) => {
  if (!dateString) return "";

  const date = new Date(dateString);

  return new Intl.DateTimeFormat("ko-KR", {
    year: "numeric",
    month: "long",
    day: "numeric",
    hour: "numeric",
    minute: "numeric",
    hour12: true, // 오전/오후 표시를 위해 12시간제를 사용
  }).format(date);
};
