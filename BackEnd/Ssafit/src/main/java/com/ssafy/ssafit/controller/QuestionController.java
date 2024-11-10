package com.ssafy.ssafit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.ssafit.model.dto.Question;
import com.ssafy.ssafit.model.dto.User;
import com.ssafy.ssafit.service.board.QuestionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/program/{programId}")
@Tag(name = "Question API", description = "질문 CRUD")
public class QuestionController {
	private QuestionService questionService;

	@Autowired
	public QuestionController(QuestionService questionService) {
		super();
		this.questionService = questionService;
	}

	// 전체 질문 조회하기
	@GetMapping("/question")
	@Operation(summary = "전체 질문 조회", description = "프로그램의 질문을 모두 조회합니다.")
	public ResponseEntity<List<Question>> getList(@PathVariable("programId") int programId) {
		List<Question> list = questionService.getQuestionList(programId);
		return new ResponseEntity<List<Question>>(list, HttpStatus.OK);
	}

	// 질문 하나 조회하기
	@GetMapping("/question/{questionId}")
	@Operation(summary = "질문 하나 조회", description = "프로그램의 특정 질문 하나를 상세보기 합니다.")
	public ResponseEntity<?> detailquestion(@PathVariable("questionId") int questionId,
			@PathVariable("programId") int programId) {
		Question question = questionService.readQuestion(questionId);

		if (question != null) {
			return new ResponseEntity<Question>(question, HttpStatus.OK);
		}
		return new ResponseEntity<String>("질문를 찾을 수 없음", HttpStatus.NOT_FOUND);
	}

	// 질문 등록하기
	@PostMapping("/question")
	@Operation(summary = "질문 등록하기", description = "질문을 등록합니다.")
	public ResponseEntity<?> writequestion(@PathVariable("programId") int programId, @RequestBody Question question,
			HttpSession session) {
		User loginUser = (User) session.getAttribute("loginUser");

		if (loginUser == null) {
			return new ResponseEntity<>("로그인 정보 없음.", HttpStatus.UNAUTHORIZED);
		}

		int userId = loginUser.getUserId();
		question.setUserId(userId);
		question.setProgramId(programId);
		
		System.out.println(question);

		questionService.writeQuestion(question);

		return new ResponseEntity<Question>(question, HttpStatus.CREATED);
	}

	// 질문 삭제하기
	@DeleteMapping("/question/{questionId}")
	@Operation(summary = "질문 삭제하기", description = "질문을 삭제합니다.")
	public ResponseEntity<String> delete(@PathVariable("programId") int programId,
			@PathVariable("questionId") int questionId, HttpSession session) {

		Question question = questionService.readQuestion(questionId);
		int writerUserId = question.getUserId(); // 글을 작성한 유저 아이디

		User loginUser = (User) session.getAttribute("loginUser");

		if (loginUser == null) {
			return new ResponseEntity<>("로그인 정보 없음.", HttpStatus.UNAUTHORIZED);
		}

		int loginUserId = loginUser.getUserId(); // 로그인한 유저 아이디

		if (writerUserId != loginUserId) {
			return new ResponseEntity<>("글 작성자만 삭제 가능합니다.", HttpStatus.UNAUTHORIZED);
		}

		boolean isDeleted = questionService.deleteQuestion(questionId);
		if (isDeleted) {
			return new ResponseEntity<>("질문 삭제 성공", HttpStatus.OK);
		}
		return new ResponseEntity<>("질문 삭제 실패", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	// 질문 수정하기
	@PutMapping("/question/{questionId}")
	@Operation(summary = "질문 수정하기", description = "질문을 수정합니다.")
	public ResponseEntity<String> update(@PathVariable("programId") int programId,
			@PathVariable("questionId") int questionId, @RequestBody Question question, HttpSession session) {

		Question updatequestion = questionService.readQuestion(questionId);
		int writerUserId = updatequestion.getUserId(); // 글을 작성한 유저 아이디

		User loginUser = (User) session.getAttribute("loginUser");

		if (loginUser == null) {
			return new ResponseEntity<>("로그인 정보 없음.", HttpStatus.UNAUTHORIZED);
		}

		int loginUserId = loginUser.getUserId(); // 로그인한 유저 아이디

		if (writerUserId != loginUserId) {
			return new ResponseEntity<>("글 작성자만 수 가능합니다.", HttpStatus.UNAUTHORIZED);
		}
		

		question.setQuestionId(questionId);
		System.out.println(question);
		questionService.updateQuestion(question);
		return new ResponseEntity<String>("질문 수정 성공", HttpStatus.OK);
	}

}
