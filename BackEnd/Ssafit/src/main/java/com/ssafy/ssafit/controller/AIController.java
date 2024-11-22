package com.ssafy.ssafit.controller;

import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.ssafit.model.dto.UserInfo;
import com.ssafy.ssafit.prompt.PromptGenerator;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Open AI", description = "Open AI 명세서")
public class AIController {

	private final OpenAiChatModel openAiChatModel;
	private PromptGenerator promptGenerator;

	public AIController(OpenAiChatModel openAiChatModel, PromptGenerator promptGenerator) {
		super();
		this.openAiChatModel = openAiChatModel;
		this.promptGenerator = promptGenerator;
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

//		return new ResponseEntity<String>(response, HttpStatus.OK);
		return null;
	}

}
