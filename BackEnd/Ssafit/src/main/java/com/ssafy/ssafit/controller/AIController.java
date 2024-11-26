package com.ssafy.ssafit.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.ssafit.model.dto.AIResponse;
import com.ssafy.ssafit.model.dto.Program;
import com.ssafy.ssafit.model.dto.User;
import com.ssafy.ssafit.model.dto.UserInfo;
import com.ssafy.ssafit.model.dto.YoutubeVideoDto;
import com.ssafy.ssafit.prompt.PromptGenerator;
import com.ssafy.ssafit.service.program.ProgramService;
import com.ssafy.ssafit.service.user.UserService;
import com.ssafy.ssafit.service.youtube.YoutubeService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Open AI", description = "Open AI 명세서")
public class AIController {

	private final OpenAiChatModel openAiChatModel;
	private PromptGenerator promptGenerator;
	private YoutubeService youtubeService;
	private ProgramService programService;


	public AIController(OpenAiChatModel openAiChatModel, PromptGenerator promptGenerator, YoutubeService youtubeService,
			ProgramService programService) {
		this.openAiChatModel = openAiChatModel;
		this.promptGenerator = promptGenerator;
		this.youtubeService = youtubeService;
		this.programService = programService;
	}

	@PostMapping("/chatGPT")
	public ResponseEntity<?> chat(@RequestBody UserInfo userInfo) throws JsonMappingException, JsonProcessingException {
//		System.out.println(userInfo);
		User loginUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		int videoNum = userInfo.getDuration(); // 하나의 키워드로 받을 영상 수
//		System.out.println(videoNum);
		
		String message = promptGenerator.generatePrompt(userInfo);
		// System.out.println(message);

		String response = openAiChatModel.call(message);
//		System.out.println("[AI 답변] : " + response);

		ObjectMapper mapper = new ObjectMapper();
		AIResponse aiResponse = mapper.readValue(response, AIResponse.class);

//		System.out.println("[AI 답변 JSON] : " + aiResponse);

		List<String> keywordsList = aiResponse.getKeywords();

		List<YoutubeVideoDto> videoList = new ArrayList<>();
		for (int i = 0; i < keywordsList.size(); i++) {
			videoList.addAll(youtubeService.searchVideos(keywordsList.get(i) + "운동, workout", videoNum));
		}
		
		System.out.println(videoList);

		Program program = new Program();
		List<String> videoIds = new ArrayList<>();

		for (YoutubeVideoDto video : videoList) {
			videoIds.add(video.getVideoId());
		}
		
		String userNickName = loginUser.getUserNickname();
		program.setVideoIds(videoIds);
		program.setUserId(userInfo.getUserId());
		program.setTitle(aiResponse.getTitle());
		program.setDescription(userNickName +"님을 위한 AI 맞춤형 프로그램 입니다.\n" + aiResponse.getDescription());
		program.setDurationWeeks(userInfo.getDuration());

		String level = "";
		if (userInfo.getExperience() == 1 || userInfo.getExperience() == 2)
			level = "BEGINNER";
		else if (userInfo.getExperience() == 3 || userInfo.getExperience() == 4)
			level = "INTERMEDIATE";
		else if (userInfo.getExperience() == 5)
			level = "ADVANCED";

		program.setLevel(level);
		program.setVideoCnt(videoIds.size());

		String thumbnailImg = videoList.get(0).getThumbnailMediumUrl();
		program.setProgramImgPath(thumbnailImg);

		try {
			programService.createProgramWithVideos(program);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		return new ResponseEntity<>(program.getProgramId(), HttpStatus.OK);
//		return null;
	}

}
