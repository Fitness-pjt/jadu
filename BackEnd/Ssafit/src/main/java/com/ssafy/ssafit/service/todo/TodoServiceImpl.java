package com.ssafy.ssafit.service.todo;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.ssafit.model.dao.TodoDao;
import com.ssafy.ssafit.model.dto.Todo;

@Service
public class TodoServiceImpl implements TodoService {
	
	private TodoDao todoDao;
	
	public TodoServiceImpl(TodoDao todoDao) {
		super();
		this.todoDao = todoDao;
	}

	@Override
	public List<Todo> getTodoList(String date, int userId) {
		return todoDao.selectAllTodo(date, userId);
	}

	@Override
	public void writeTodo(Todo todo) {
		todoDao.insertTodo(todo);		
	}

	@Override
	public boolean deleteTodo(int todoId, int userId) {
		int result = todoDao.deleteTodo(todoId, userId);
		return result == 1;
	}

	@Override
	public void modifyTodoContent(int todoId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void modifyTodoStatus(int todoId) {
		// TODO Auto-generated method stub
		
	}

}
