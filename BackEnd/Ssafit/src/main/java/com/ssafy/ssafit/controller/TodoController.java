package com.ssafy.ssafit.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.ssafit.model.dto.Todo;
import com.ssafy.ssafit.model.dto.User;
import com.ssafy.ssafit.service.todo.TodoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/{userId}/todo")
@Tag(name = "Todo API", description = "Todo API 명세서")
public class TodoController {

	private TodoService todoService;

	public TodoController(TodoService todoService) {
		this.todoService = todoService;
	}

	// 특정 날짜에 작성된 투두 전체 조회
	@GetMapping("/{date}")
	@Operation(summary = "특정 날짜 투두 전체 조회", description = "특정 날짜에 작성된 투두 리스트를 조회합니다.")
	public ResponseEntity<?> getList(@PathVariable("date") String date, @PathVariable("userId") int userId) {
		List<Todo> list = todoService.getTodoList(date, userId);

		if (list == null) {
			return new ResponseEntity<>("이 날짜에 투두 없음", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Todo>>(list, HttpStatus.OK);
	}

	///////////////////////////////////
	// 로그인한 유저만 가능
	// 특정 날짜에 투두 등록하기 (content만)
	@PostMapping("/{date}")
	@Operation(summary = "특정 날짜 투두 등록하기", description = "특정 날짜에 투두를 추가합니다.")
	public ResponseEntity<?> writeTodo(@PathVariable("userId") int userId, @PathVariable("date") String date,
			@RequestBody Todo todo, HttpSession session) {
		User loginUser = (User) session.getAttribute("loginUser");

		if (loginUser == null) {
			return new ResponseEntity<>("로그인 정보 없음.", HttpStatus.UNAUTHORIZED);
		}

		int loginUserId = loginUser.getUserId(); // 현재 로그인한 사용자의 id

		// 로그인한 유저와 현재 투두 페이지의 유저가 동일한 경우에만 글 작성 가능
		if (loginUserId != userId) {
			return new ResponseEntity<>("본인 페이지 아님. 등록 불가", HttpStatus.NOT_ACCEPTABLE);
		}
		todo.setUserId(loginUserId);
		todo.setDate(date);

		todoService.writeTodo(todo);
		return new ResponseEntity<Todo>(todo, HttpStatus.CREATED);
	}

	// 하나의 투두 삭제하기
	@DeleteMapping("/{todoId}")
	@Operation(summary = "투두 하나 삭제하기", description = "투두 하나의 항목을 삭제합니다.")
	public ResponseEntity<String> delete(@PathVariable("userId") int userId, @PathVariable("todoId") int todoId,
			HttpSession session) {

		User loginUser = (User) session.getAttribute("loginUser");

		if (loginUser == null) {
			return new ResponseEntity<>("로그인 정보 없음.", HttpStatus.UNAUTHORIZED);
		}

		int loginUserId = loginUser.getUserId(); // 로그인한 유저 id
		if (loginUserId != userId) {
			return new ResponseEntity<>("본인 페이지 아님. 삭제 불가", HttpStatus.NOT_ACCEPTABLE);
		}

		boolean isDeleted = todoService.deleteTodo(todoId, loginUserId);
		if (isDeleted) {
			return new ResponseEntity<>("게시글 삭제 성공", HttpStatus.OK);
		}
		return new ResponseEntity<>("게시글 삭제 실패", HttpStatus.INTERNAL_SERVER_ERROR);

	}

	// 하나의 투두 내용 수정하기

	// 하나의 투두 상태 수정하기

}
