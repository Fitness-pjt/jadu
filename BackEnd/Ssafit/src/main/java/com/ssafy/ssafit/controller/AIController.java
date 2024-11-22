package com.ssafy.ssafit.controller;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.ssafit.model.dto.UserInfo;
import com.ssafy.ssafit.prompt.PromptGenerator;
import com.ssafy.ssafit.service.youtube.YoutubeService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Open AI", description = "Open AI 명세서")
public class AIController {

	private final OpenAiChatModel openAiChatModel;
	private PromptGenerator promptGenerator;
	private YoutubeService youtubeService;

	public AIController(OpenAiChatModel openAiChatModel, PromptGenerator promptGenerator, YoutubeService youtubeService) {
		this.openAiChatModel = openAiChatModel;
		this.promptGenerator = promptGenerator;
		this.youtubeService = youtubeService;
	}

//	@GetMapping("/chatGPT")
//	public ResponseEntity<String> chat(@RequestParam("message") String message) {
//		System.out.println(message);
//
//		String response = openAiChatModel.call(message);
//		System.out.println(response);
//		
//		
//		return new ResponseEntity<String>(response, HttpStatus.OK);
//	}

	@PostMapping("/chatGPT")
	public ResponseEntity<String> chat(@RequestBody UserInfo userInfo) {
		System.out.println(userInfo);

		String message = promptGenerator.generatePrompt(userInfo);
		System.out.println(message);

		String response = openAiChatModel.call(message);
		System.out.println("AI 답변 : " + response);
		
		 // 쉼표(,)를 기준으로 문자열 분리
        String[] keywordsArray = response.split(",");
        
//        System.out.println(Arrays.toString(keywordsArray));

        // Trim 처리하여 공백 제거 후 List로 변환
        List<String> keywordsList = new ArrayList<>();
        
        for (String keyword : keywordsArray) {
        	keywordsList.add(keyword.trim());
        }
        
        for(int i=0; i<keywordsList.size(); i++) {
        	youtubeService.searchVideos(keywordsList.get(i));
        }

        // 결과 출력
        System.out.println("키워드 리스트: " + keywordsList);
		
		

//		return new ResponseEntity<String>(response, HttpStatus.OK);
		return null;
	}

}
