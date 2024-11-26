package com.ssafy.ssafit.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.ssafit.model.dto.Todo;
import com.ssafy.ssafit.model.dto.User;
import com.ssafy.ssafit.service.todo.TodoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

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
//		System.out.println(list);

		if (list == null || list.size() == 0) {
			return new ResponseEntity<>("이 날짜에 투두 없음", HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Todo>>(list, HttpStatus.OK);
	}

	///////////////////////////////////
	// 로그인한 유저만 가능 == 로그인 인증 필요
	// 특정 날짜에 투두 등록하기 (content만)
	@PostMapping("/{date}")
	@Operation(summary = "특정 날짜 투두 등록하기", description = "특정 날짜에 투두를 추가합니다.")
	public ResponseEntity<?> writeTodo(@PathVariable("userId") int userId, @PathVariable("date") String date,
			@RequestBody Todo todo) {

		// SecurityContext에서 인증된 사용자 정보 가져오기
		User loginUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		int loginUserId = loginUser.getUserId(); // 현재 로그인한 사용자의 id

		// 로그인한 유저와 현재 투두 페이지의 유저가 동일한 경우에만 글 작성 가능
		if (loginUserId != userId) {
			return new ResponseEntity<>("본인 페이지 아님. 등록 불가", HttpStatus.NOT_ACCEPTABLE);
		}
		todo.setUserId(userId);
		todo.setDate(date);
		// System.out.println(todo);

		todoService.writeTodo(todo);
		return new ResponseEntity<Todo>(todo, HttpStatus.CREATED);
	}

	// 하나의 투두 삭제하기
	@DeleteMapping("/{todoId}")
	@Operation(summary = "투두 하나 삭제하기", description = "투두 하나의 항목을 삭제합니다.")
	public ResponseEntity<String> delete(@PathVariable("userId") int userId, @PathVariable("todoId") int todoId) {

		// SecurityContext에서 인증된 사용자 정보 가져오기
		User loginUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

//		System.out.println(loginUser);

		// 얘는 이제 filter에서 확인해줌
//		if (loginUser == null) {
//			return new ResponseEntity<>("로그인 정보 없음.", HttpStatus.UNAUTHORIZED);
//		}

		// 이건 인터셉터가 확인해줌?
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

	// 하나의 PUT 요청으로 기능을 세분화하기 => URL을 세분화하여 사용하기
	// 하나의 투두 내용 수정하기
	@PutMapping("/{todoId}/content")
	@Operation(summary = "투두 내용 수정하기", description = "투두 항목의 내용을 수정합니다.")
	public ResponseEntity<?> updateTodoContent(@PathVariable("userId") int userId, @PathVariable("todoId") int todoId,
			@RequestBody Todo todo) {
		User loginUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		int loginUserId = loginUser.getUserId(); // 로그인한 유저 id
		if (loginUserId != userId) {
			return new ResponseEntity<>("본인 페이지 아님. 수정 불가", HttpStatus.NOT_ACCEPTABLE);
		}

		todo.setTodoId(todoId);
		todo.setUserId(loginUserId);
		todoService.modifyTodoContent(todo);
		return new ResponseEntity<>(todo, HttpStatus.OK);

	}

	// 하나의 투두 상태 수정하기
	@PutMapping("/{todoId}/status")
	@Operation(summary = "투두 상태 변경하기", description = "투두 항목의 완료 여부를 수정합니다.")
	public ResponseEntity<?> updateTodoContent(@PathVariable("userId") int userId, @PathVariable("todoId") int todoId,
			@RequestBody boolean isCompleted) {

		User loginUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		int loginUserId = loginUser.getUserId(); // 로그인한 유저 id
		if (loginUserId != userId) {
			return new ResponseEntity<>("본인 페이지 아님. 수정 불가", HttpStatus.NOT_ACCEPTABLE);
		}

		todoService.modifyTodoStatus(todoId, isCompleted);
		return new ResponseEntity<>(isCompleted, HttpStatus.OK);

	}

	// 투두 좋아요 초기 상태 불러오기
	@GetMapping("/{todoId}/likeTodo")
	@Operation(summary = "투두 좋아요 초기 상태 불러오기", description = "투두가 좋아요가 눌린 상태인지 아닌지 확인합니다.")
	public ResponseEntity<Map<String, Object>> getLikeTodoStatue(@PathVariable("userId") int userId,
			@PathVariable("todoId") int todoId) {
		User loginUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		int loginUserId = loginUser.getUserId();

		boolean result = todoService.getTodoLikesStatus(todoId, loginUserId);
		
		Map<String, Object> response = new HashMap<>();
		response.put("isFavorite", result);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// 투두 좋아요 누르기
	@PostMapping("/{todoId}/likeTodo")
	@Operation(summary = "투두 좋아요 누르기", description = "완료된 투두의 좋아요를 누릅니다.")
	public ResponseEntity<Map<String, Object>> putLikeTodo(@PathVariable("userId") int userId, @PathVariable("todoId") int todoId) {

		User loginUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		System.out.println(loginUser);

		int loginUserId = loginUser.getUserId();

		boolean alreadyLiked = todoService.getTodoLikesStatus(todoId, loginUserId);
		
		Map<String, Object> response = new HashMap<>();

		if (!alreadyLiked) {
			// 좋아요가 눌리지 않았다면, 좋아요를 눌러서 저장함
			todoService.putTodoLikes(todoId, loginUserId);
			response.put("isFavorite", true);
			response.put("message", "좋아요 누름");
		} else {
			// 이미 좋아요가 눌려 있으면 아무것도 하지 않음
			response.put("isFavorite", false);
			response.put("message", "좋아요 이미 눌려있음");
		}
		return new ResponseEntity<>(response, HttpStatus.OK); // 이미 좋아요 눌려 있음 : false 반환

	}

	// 투두 좋아요 취소하기
	@DeleteMapping("/{todoId}/likeTodo")
	@Operation(summary = "투두 좋아요 취소하기", description = "완료된 투두의 좋아요를 취소합니다.")
	public ResponseEntity<Map<String, Object>> cancelLikeTodo(@PathVariable("userId") int userId,
			@PathVariable("todoId") int todoId) {

		User loginUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//			System.out.println(loginUser);

		int loginUserId = loginUser.getUserId();

		boolean alreadyLiked = todoService.getTodoLikesStatus(todoId, loginUserId);
		
		Map<String, Object> response = new HashMap<>();

		if (!alreadyLiked) {
			// 좋아요가 눌리지 않았다면, 아무것도 하지 않음
			response.put("isFavorite", true);
			response.put("message", "좋아요 이미 취소되어 있음");
		} else {
			// 이미 좋아요가 눌려 있으면 DB에서 삭제하기
			todoService.deleteTodoLikes(todoId, loginUserId);
			response.put("isFavorite", false);
			response.put("message", "좋아요 취소됨");
		}
		return new ResponseEntity<>(response, HttpStatus.OK); // 좋아요 취소하기 : false 반환

	}
	
	// TodoController에 추가
	@GetMapping("/progress/{programId}")
	public ResponseEntity<?> checkProgramProgress(
	    @PathVariable int userId,
	    @PathVariable int programId
	) {
	    boolean inProgress = todoService.hasUncompletedTodos(programId, userId);
	    Map<String, Boolean> response = new HashMap<>();
	    response.put("inProgress", inProgress);
	    return ResponseEntity.ok(response);
	}
	
	@GetMapping("/{todoId}/likeTodo/count")
	@Operation(summary = "투두 좋아요 개수 조회", description = "완료된 투두의 전체 좋아요 개수를 조회합니다.")
	public ResponseEntity<Integer> getTodoLikeCount(
	        @PathVariable("userId") int userId,
	        @PathVariable("todoId") int todoId) {
	    
	    // SecurityContext에서 인증된 사용자 정보 가져오기
	    User loginUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	    
	    
	    // 좋아요 개수 조회
	    int likeCount = todoService.getTodoLikeCount(todoId);
	    
	    
	    
	    return new ResponseEntity<>(likeCount, HttpStatus.OK);
	}

}
