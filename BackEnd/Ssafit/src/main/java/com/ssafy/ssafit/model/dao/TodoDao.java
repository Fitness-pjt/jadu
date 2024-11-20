package com.ssafy.ssafit.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ssafy.ssafit.model.dto.Todo;

public interface TodoDao {

	// 특정 날짜에 작성된 투두 전체 조회 
	public List<Todo> selectAllTodo(@Param("date") String date,  @Param("userId") int userId);
	
	// 특정 날짜에 투두 등록하기 (content만)
	public void insertTodo(Todo todo);
	
	// 하나의 투두 삭제하기 
	public int deleteTodo(@Param("todoId") int todoId, @Param("userId") int userId);
	
	// 하나의 투두 내용 수정하기 
	public void updateTodoContent(Todo todo);
	
	// 하나의 투두 상태 수정하기
	public void updateTodoStatus(@Param("todoId") int todoId, @Param("status") boolean isCompleted);
	
	// 하나의 투두 좋아요 수 조회하기 
	public int countTodoLikes(int todoId);
	
	// 투두 좋아요 상태 불러오기
	public int checkTodoLikeExists(@Param("todoId")int todoId, @Param("userId") int userId);

	// 투두 좋아요 누르기
	public void insertTodoLikes(@Param("todoId")int todoId, @Param("userId") int userId);
	
	// 투두 좋아요 취소하기
	public void deleteTodoLikes(@Param("todoId")int todoId, @Param("userId") int userId);
}
