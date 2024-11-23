package com.ssafy.ssafit.service.todo;

import java.util.List;

import com.ssafy.ssafit.model.dto.Todo;

public interface TodoService {

	// 특정 날짜에 작성된 투두 전체 조회
	public List<Todo> getTodoList(String date, int userId);

	// 특정 날짜에 투두 등록하기 (content만)
	public void writeTodo(Todo todo);

	// 하나의 투두 삭제하기
	public boolean deleteTodo(int todoId, int userId);

	// 하나의 투두 내용 / 상태 수정하기
	public void modifyTodoContent(Todo todo);

	// 하나의 투두 상태 수정하기
	public void modifyTodoStatus(int todoId, boolean isCompleted);

	// 투두 좋아요 누른 상태 불러오기
	public boolean getTodoLikesStatus(int todoId, int userId);
	
	// 투두 좋아요 누르기 == todo-likes 테이블에 추가하기
	public void putTodoLikes(int todoId, int userId);

	// 투두 좋아요 취소하기 == todo-likes 테이블에서 삭제하기
	public void deleteTodoLikes(int todoId, int userId);
}
