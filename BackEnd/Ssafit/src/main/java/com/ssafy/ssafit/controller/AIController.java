package com.ssafy.ssafit.controller;

import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AIController {
	private final OpenAiChatModel openAiChatModel;

	public AIController(OpenAiChatModel openAiChatModel) {
		super();
		this.openAiChatModel = openAiChatModel;
	}

	@GetMapping("/chatGPT")
	public ResponseEntity<String> chat(@RequestParam("message") String message) {
		System.out.println(message);

		String response = openAiChatModel.call(message);
		System.out.println(response);
		
		
		return new ResponseEntity<String>(response, HttpStatus.OK);
	}

}
