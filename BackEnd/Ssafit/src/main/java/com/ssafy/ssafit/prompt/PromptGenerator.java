package com.ssafy.ssafit.prompt;

// PromptGenerator.java

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ssafy.ssafit.model.dto.UserInfo;
@Component
public class PromptGenerator {

	
	
	public String generatePrompt(UserInfo userInfo) {
		
		StringBuilder prompt = new StringBuilder();
		
		int frequency = userInfo.getFrequency();
		int duration = userInfo.getDuration();
		
		int videoNum  = frequency * duration; // 총 받을 비디오 ID 개수 ex) frequency : 주 2회, duration : 3주 라면 => 총 6개의 videoId 필요
		int AIkeywordNum = frequency; // AI로부터 추천 받을 키워드 개수 주 몇 회 운동할지 == frequency 와 동일 : 매 주 다른 운동 진행

		prompt.append("당신은 20년 차의 엄청난 지식과 경험을 가진 운동 전문 트레이너이자 데이터 분석가입니다. 당신은 사용자 데이터를 바탕으로 명확하고 정밀한 데이터를 분석하여 가장 적합한 운동 영상을 추천할 수 있습니다.\n\n");

		prompt.append("다음 정보를 가진 사용자에게 맞춤형 운동 프로그램을 생성하기 위해, 사용자에게 딱 맞는 운동 영상 검색 키워드 ").append(AIkeywordNum).append("개를 추천해주세요.\n\n");

		prompt.append("[사용자 정보]\n");
		prompt.append("1. 운동 목표: ").append(getGoalDescription(userInfo.getGoal())).append("\n");
		prompt.append("2. 성별: ").append(getGenderDescription(userInfo.getGender())).append("\n");
		prompt.append("3. 연령대: ").append(getAgeDescription(userInfo.getAge())).append("\n");
		prompt.append("4. 체형: ").append(getShapeDescription(userInfo.getShape())).append("\n");
		prompt.append("5. 운동 경험: ").append(getExperienceDescription(userInfo.getExperience())).append("\n");
		prompt.append("6. 선호 운동 장소: ").append(getLocationDescription(userInfo.getLocation())).append("\n");
		prompt.append("7. 선호하는 운동 부위:  ").append(getKeywordDescription(userInfo.getKeyword())).append("\n\n");
		prompt.append("8. 주간 운동 빈도: ").append(userInfo.getFrequency()).append("회\n");
		prompt.append("9. 몇 주 프로그램: ").append(userInfo.getDuration()).append("주\n");
		
		prompt.append("[요구사항]\n");
		prompt.append("1. 각 키워드는 유튜브 운동 영상 검색에 최적화되어야 합니다.\n");
		prompt.append("2. 키워드는 한글로 작성해주세요.\n");
		prompt.append("3. 각 키워드는 쉼표(,)로 구분해주세요.\n");
		prompt.append("4. 키워드에는 운동 난이도, 부위, 시간 등의 구체적인 정보를 포함해주세요.\n");
		prompt.append("5. 사용자의 경험 수준에 맞는 난이도의 운동을 추천해주세요.\n");
		prompt.append("6. 생성된 각 키워드는 `String` 형태로 반환되며, 최종적으로 `List` 형태의 배열에 담기도록 작성해주세요.\n\n");

		prompt.append("키워드 형식 예시: '초보자 홈트 전신운동, 체지방감소 스쿼트 루틴' 등\n\n");

		prompt.append("추천 키워드(").append(AIkeywordNum).append("개):");

		return prompt.toString();
	}


	private String getKeywordDescription(List<Integer> keyword) {

		Map<Integer, String> keywordMap = new HashMap<>();
		keywordMap.put(1, "상체");
		keywordMap.put(2, "하체");
		keywordMap.put(3, "가슴");
		keywordMap.put(4, "등");
		keywordMap.put(5, "어깨");
		keywordMap.put(6, "복부");

		String result = "";

		for (int idx : keyword) {
			result = result + "," + keywordMap.get(idx);
		}

		return result;
	}

	private String getGoalDescription(String goal) {
		return switch (goal) {
		case "DIET" -> "체중 감량과 체지방 감소";
		case "MUSCLE" -> "근력 향상과 근육량 증가";
		case "RECOVERY" -> "재활과 통증 관리";
		case "STAMINA" -> "지구력과 심폐지구력 향상";
		default -> goal;
		};
	}

	private String getGenderDescription(String gender) {
		return switch (gender) {
		case "MALE" -> "남성";
		case "FEMALE" -> "여성";
		default -> gender;
		};
	}

	private String getAgeDescription(String age) {
		return switch (age) {
		case "10" -> "10대";
		case "2030" -> "20-30대";
		case "4050" -> "40-50대";
		case "60" -> "60대 이상";
		default -> age;
		};
	}

	private String getShapeDescription(String shape) {
		return switch (shape) {
		case "SLIM" -> "마른 체형";
		case "NORMAL" -> "보통 체형";
		case "FAT" -> "통통한 체형";
		default -> shape;
		};
	}

	private String getExperienceDescription(int experience) {
		return switch (experience) {
		case 1 -> "운동 초보자";
		case 2 -> "기초 운동 경험자";
		case 3 -> "중급 운동자";
		case 4 -> "숙련된 운동자";
		case 5 -> "전문가 수준";
		default -> String.valueOf(experience);
		};
	}

	private String getLocationDescription(String location) {
		return switch (location) {
		case "HOME" -> "집";
		case "FITNESS" -> "헬스장";
		case "OUTSIDE" -> "실외";
		case "ETC" -> "기타";
		default -> location;
		};
	}
}
