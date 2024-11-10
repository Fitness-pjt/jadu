package com.ssafy.ssafit.service.board;

import java.util.List;

import com.ssafy.ssafit.model.dto.Answer;

public interface AnswerService {

	// 전체 답변 조회
	public List<Answer> getAnswerList(int questionId);

	// 답변 등록
	public void writeAnswer(Answer answer);

	// 답변 삭제
	public boolean deleteAnswer(int answerId);

	// 답변 수정
	public void updateAnswer(Answer answer);

	// 답변 작성자 userId 찾기 
	public int readAnswer(int answerId);
}
