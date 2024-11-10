package com.ssafy.ssafit.service.board;

import java.util.List;

import com.ssafy.ssafit.model.dto.Question;

public interface QuestionService {
	// 전체 질문 조회
	public List<Question> getQuestionList(int programId);

	// ID에 해당하는 질문 하나 가져오기
	public Question readQuestion(int questionId);

	// 질문 등록
	public void writeQuestion(Question question);

	// 질문 삭제
	public boolean deleteQuestion(int questionId);

	// 질문 수정
	public void updateQuestion(Question question);
}
