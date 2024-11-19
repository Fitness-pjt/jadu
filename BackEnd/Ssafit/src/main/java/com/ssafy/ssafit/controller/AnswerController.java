package com.ssafy.ssafit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.ssafit.model.dto.Answer;
import com.ssafy.ssafit.model.dto.User;
import com.ssafy.ssafit.service.board.AnswerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/program/{programId}/question/{questionId}")
@Tag(name = "Answer API", description = "답변 CRUD")
public class AnswerController {
	private AnswerService answerService;

	@Autowired
	public AnswerController(AnswerService answerService) {
		this.answerService = answerService;
	}

	// 전체 답변 조회하기
	@GetMapping("/answer")
	@Operation(summary = "전체 답변 조회", description = "질문의 답변을 모두 조회합니다.")
	public ResponseEntity<?> getList(@PathVariable("programId") int programId,
			@PathVariable("questionId") int questionId) {
		List<Answer> list = answerService.getAnswerList(questionId);
		
		if(list == null || list.size() ==0) {
			return new ResponseEntity<>("답변 없음.", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Answer>>(list, HttpStatus.OK);
	}

	// 답변 등록하기
	@PostMapping("/answer")
	@Operation(summary = "답변 등록하기", description = "답변을 등록합니다.")
	public ResponseEntity<?> writeanswer(@PathVariable("programId") int programId,
			@PathVariable("questionId") int questionId, @RequestBody Answer answer, HttpSession session) {
		
		User loginUser = (User) session.getAttribute("loginUser");

		if (loginUser == null) {
			return new ResponseEntity<>("로그인 정보 없음.", HttpStatus.UNAUTHORIZED);
		}

		int userId = loginUser.getUserId();
		answer.setUserId(userId);
		answer.setQuestionId(questionId);

//		System.out.println(answer);

		answerService.writeAnswer(answer);

		return new ResponseEntity<Answer>(answer, HttpStatus.CREATED);
	}

	// 답변 삭제하기
	@DeleteMapping("/answer/{answerId}")
	@Operation(summary = "답변 삭제하기", description = "답변을 삭제합니다.")
	public ResponseEntity<String> delete(@PathVariable("programId") int programId, @PathVariable("questionId") int questionId,
			@PathVariable("answerId") int answerId, HttpSession session) {

		int writerUserId = answerService.readAnswer(answerId); // 글을 작성한 유저 아이디

		User loginUser = (User) session.getAttribute("loginUser");

		if (loginUser == null) {
			return new ResponseEntity<>("로그인 정보 없음.", HttpStatus.UNAUTHORIZED);
		}

		int loginUserId = loginUser.getUserId(); // 로그인한 유저 아이디

		if (writerUserId != loginUserId) {
			return new ResponseEntity<>("글 작성자만 삭제 가능합니다.", HttpStatus.UNAUTHORIZED);
		}

		boolean isDeleted = answerService.deleteAnswer(answerId);
		if (isDeleted) {
			return new ResponseEntity<>("답변 삭제 성공", HttpStatus.OK);
		}
		return new ResponseEntity<>("답변 삭제 실패", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	// 답변 수정하기
	@PutMapping("/answer/{answerId}")
	@Operation(summary = "답변 수정하기", description = "답변을 수정합니다.")
	public ResponseEntity<String> update(@PathVariable("programId") int programId, @PathVariable("questionId") int questionId,
			@PathVariable("answerId") int answerId, @RequestBody Answer answer, HttpSession session) {

		int writerUserId = answerService.readAnswer(answerId); // 글을 작성한 유저 아이디

		User loginUser = (User) session.getAttribute("loginUser");

		if (loginUser == null) {
			return new ResponseEntity<>("로그인 정보 없음.", HttpStatus.UNAUTHORIZED);
		}

		int loginUserId = loginUser.getUserId(); // 로그인한 유저 아이디

		if (writerUserId != loginUserId) {
			return new ResponseEntity<>("글 작성자만 수 가능합니다.", HttpStatus.UNAUTHORIZED);
		}

		answer.setAnswerId(answerId);
		answerService.updateAnswer(answer);
		return new ResponseEntity<String>("답변 수정 성공", HttpStatus.OK);
	}

}
