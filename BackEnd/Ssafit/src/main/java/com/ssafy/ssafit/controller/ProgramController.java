package com.ssafy.ssafit.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.ssafit.model.dto.Program;
import com.ssafy.ssafit.model.dto.User;
import com.ssafy.ssafit.service.program.ProgramService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/program")
public class ProgramController {

	private ProgramService programService;

	public ProgramController(ProgramService programService) {
		this.programService = programService;
	}
	
	@PostMapping
	public ResponseEntity<?> createProgram(@RequestBody Program program, HttpSession session) {
	    User loginUser = (User) session.getAttribute("loginUser");
	    if (loginUser == null) {
	        return new ResponseEntity<>("로그인이 필요한 서비스입니다.", HttpStatus.UNAUTHORIZED);
	    }

	    try {
	        program.setUserId(loginUser.getUserId());
	        
	        // 프로그램 생성 및 비디오 연결
	        programService.createProgramWithVideos(program);
	        
	        return new ResponseEntity<>(program, HttpStatus.CREATED);
	    } catch (Exception e) {
	        return new ResponseEntity<>("프로그램 생성 중 오류가 발생했습니다: " + e.getMessage(), 
	                                  HttpStatus.BAD_REQUEST);
	    }
	}

}
