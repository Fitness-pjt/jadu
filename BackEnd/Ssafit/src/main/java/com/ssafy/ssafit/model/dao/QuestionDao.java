package com.ssafy.ssafit.model.dao;

import java.util.List;

import com.ssafy.ssafit.model.dto.Question;

public interface QuestionDao {

	// 전체 질문 조회
	public abstract List<Question> selectAllQuestion(int programId);

	// ID에 해당하는 질문 하나 가져오기
	public abstract Question selectOneQuestion(int questionId);

	// 질문 등록
	public void insertQuestion(Question question);

	// 질문 삭제
	public int deleteQuestion(int questionId);

	// 질문 수정
	public void updateQuestion(Question question);

	// 검색 기능
	// public List<question> search(SearchCondition condition);
}
