package com.ssafy.ssafit.model.dao;

import java.util.List;

import com.ssafy.ssafit.model.dto.Answer;

public interface AnswerDao {
	// 전체 질문 조회
	public abstract List<Answer> selectAllAnswer(int questionId);

	// 질문 등록
	public void insertAnswer(Answer answer);

	// 질문 삭제
	public int deleteAnswer(int answerId);

	// 질문 수정
	public void updateAnswer(Answer answer);

	// 답변 작성자 찾기 
	public  int selectOneAnswer(int answerId);
}
