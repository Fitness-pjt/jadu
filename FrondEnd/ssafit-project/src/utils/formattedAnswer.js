// 프론트에서 선택한 값을 서버 + DB에서 사용하는 데이터로 가공하는 포멧 메서드

export const formatAnswer = (questionId, answer) => {
  switch (questionId) {
    case "gender":
      return answer === "남" ? "MALE" : "FEMALE";
    case "age":
      if (answer === "10대") return "10";
      if (answer === "20~30대") return "2030";
      if (answer === "40~50대") return "4050";
      if (answer === "60대") return "60";
    case "shape":
      if (answer === "마름") return "SLIM";
      if (answer === "보통") return "NORMAL";
      if (answer === "살집 있음") return "FAT";
    case "goal":
      if (answer === "다이어트") return "DIET";
      if (answer === "근육 증진") return "MUSCLE";
      if (answer === "재활") return "RECOVERY";
      if (answer === "체력 향상") return "STAMINA";
    case "experience":
      if (answer == "1") return 1;
      if (answer == "2") return 2;
      if (answer == "3") return 3;
      if (answer == "4") return 4;
      if (answer == "5") return 5;
    case "location":
      if (answer === "헬스장") return "FITNESS";
      if (answer === "집") return "HOME";
      if (answer === "야외") return "OUTSIDE";
      if (answer === "기타") return "ETC";
    case "keyword":
      return answer;
    case "frequency":
      if (answer === "주 1회") return 1;
      if (answer === "주 2회") return 2;
      if (answer === "주 3회") return 3;
      if (answer === "주 4회") return 4;
      if (answer === "주 5회") return 5;
    case "duration":
      if (answer === "1주") return 1;
      if (answer === "2주") return 2;
      if (answer === "3주") return 3;
      if (answer === "4주") return 4;
    case "fighting":
      return answer === "네" ? true : false;
  }
};
