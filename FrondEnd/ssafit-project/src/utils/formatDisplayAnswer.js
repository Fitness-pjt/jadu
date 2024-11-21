// 서버 + DB로부터 받은 데이터를 프론트에서 가공하는 과정

export const formatDisplayAnswer = (questionId, answer) => {
  switch (questionId) {
    case "gender":
      return answer === "MALE" ? "남" : "여";
    case "age":
      if (answer === "10") return "10대";
      if (answer === "2030") return "20~30대";
      if (answer === "4050") return "40~50대";
      if (answer === "60") return "60대 이상";
      break;
    case "shape":
      if (answer === "SLIM") return "마름";
      if (answer === "NORMAL") return "보통";
      if (answer === "FAT") return "살집 있음";
      break;
    case "goal":
      if (answer === "DIET") return "다이어트";
      if (answer === "MUSCLE") return "근육 증진";
      if (answer === "RECOVERY") return "재활";
      if (answer === "STAMINA") return "체력 향상";
      break;
    case "experience":
      if (answer === 1) return "1";
      if (answer === 2) return "2";
      if (answer === 3) return "3";
      if (answer === 4) return "4";
      if (answer === 5) return "5";
      break;
    case "location":
      if (answer === "FITNESS") return "헬스장";
      if (answer === "HOME") return "집";
      if (answer === "OUTSIDE") return "야외";
      if (answer === "ETC") return "기타";
      break;
    case "keyword":
      return answer; // 선택된 부위는 배열 그대로 반환
    case "frequency":
      if (answer === 1) return "주 1회";
      if (answer === 2) return "주 2회";
      if (answer === 3) return "주 3회";
      if (answer === 4) return "주 4회";
      if (answer === 5) return "주 5회";
      break;
    case "duration":
      if (answer === 1) return "1주";
      if (answer === 2) return "2주";
      if (answer === 3) return "3주";
      if (answer === 4) return "4주";
      break;
    case "fighting":
      return answer ? "네" : "아직이요";
  }
};
