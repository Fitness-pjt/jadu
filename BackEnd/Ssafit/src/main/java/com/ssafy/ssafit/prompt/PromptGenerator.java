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
		int videoNum = frequency * duration;
		int AIkeywordNum = frequency;

		prompt.append("당신은 20년 차의 엄청난 지식과 경험을 가진 운동 전문 트레이너이자 데이터 분석가입니다. ")
				.append("당신은 사용자 데이터를 바탕으로 명확하고 정밀한 데이터를 분석하여 가장 적합한 운동 영상을 추천할 수 있습니다.\n\n");

		prompt.append("다음 정보를 가진 사용자에게 맞춤형 운동 프로그램을 생성하기 위해, 운동 프로그램 정보와 ").append(AIkeywordNum)
				.append("개의 검색 키워드를 추천해주세요.\n\n");

		prompt.append("[사용자 정보]\n");
		prompt.append("1. 운동 목표: ").append(getGoalDescription(userInfo.getGoal())).append("\n");
		prompt.append("2. 성별: ").append(getGenderDescription(userInfo.getGender())).append("\n");
		prompt.append("3. 연령대: ").append(getAgeDescription(userInfo.getAge())).append("\n");
		prompt.append("4. 체형: ").append(getShapeDescription(userInfo.getShape())).append("\n");
		prompt.append("5. 운동 경험: ").append(getExperienceDescription(userInfo.getExperience())).append("\n");
		prompt.append("6. 선호 운동 장소: ").append(getLocationDescription(userInfo.getLocation())).append("\n");
		prompt.append("7. 선호하는 운동 부위: ").append(getKeywordDescription(userInfo.getKeyword())).append("\n");
		prompt.append("8. 주간 운동 빈도: ").append(frequency).append("회\n");
		prompt.append("9. 몇 주 프로그램: ").append(duration).append("주\n\n");

		prompt.append("[중요 응답 규칙]\n");
		prompt.append("1. 마크다운이나 다른 형식 없이 순수한 JSON 문자열로만 응답해주세요.\n");
		prompt.append("2. 설명이나 부연설명 없이 JSON 데이터만 반환해주세요.\n");
		prompt.append("3. 다음 형식으로 정확히 응답해주세요:\n\n");
		prompt.append("{\"title\":\"프로그램 제목\",\"description\":\"프로그램 설명\",\"keywords\":[\"키워드1\",\"키워드2\"]}\n\n");

		prompt.append("[JSON 형식 규칙]\n");
		prompt.append("1. title: 20자 내외의 프로그램 제목\n");
		prompt.append("2. description: 사용자 맞춤형 프로그램 설명\n");
		prompt.append("3. keywords: 정확히 ").append(AIkeywordNum).append("개의 검색 키워드 배열\n\n");

		prompt.append("[키워드 규칙]\n");
		prompt.append("1. 한글로 작성\n");
		prompt.append("2. 운동 난이도와 부위 정보 포함\n");
		prompt.append("3. 유튜브 검색에 최적화된 형태\n");
		prompt.append("4. 사용자 경험 수준에 맞는 난이도\n");
		prompt.append("5. 키워드에는 반드시 성별에 대한 정보가 들어가야합니다. 남성에게는 남성에게 맞는 키워드를, 여성에게는 여성에게 맞는 키워드를 만들어주세요.\n\n");
		

		prompt.append("응답 예시:\n");
		prompt.append(
				"{\"title\":\"홈트로 시작하는 다이어트\",\"description\":\"운동 경험이 적은 20대 여성을 위한 체중 감량과 체형 관리 프로그램입니다. 전신 순환 운동과 근력 운동을 병행하여 기초 체력을 키우고 균형잡힌 몸매를 만들 수 있습니다.\",\"keywords\":[\"다이어트 홈트 입문 전신운동\",\"초보자 하체운동 스쿼트\",\"복부 집중 코어운동\"]}\n\n");

		prompt.append("위 예시와 동일한 형식으로 순수 JSON 문자열만 응답해주세요.");

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
