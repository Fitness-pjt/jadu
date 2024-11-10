package com.ssafy.ssafit.service.board;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.ssafit.model.dao.AnswerDao;
import com.ssafy.ssafit.model.dto.Answer;



@Service
public class AnswerServiceImpl implements AnswerService {
	
	private AnswerDao answerDao;
	
	@Autowired
	public AnswerServiceImpl(AnswerDao answerDao) {
		this.answerDao = answerDao;
	}

	@Override
	public List<Answer> getAnswerList(int questionId) {
		return answerDao.selectAllAnswer(questionId);
	}

	@Override
	public void writeAnswer(Answer answer) {
		answerDao.insertAnswer(answer);
	}

	@Override
	public boolean deleteAnswer(int answerId) {
		int result = answerDao.deleteAnswer(answerId);
		return result == 1;
	}

	@Override
	public void updateAnswer(Answer answer) {
		answerDao.updateAnswer(answer);		
	}

	@Override
	public int readAnswer(int answerId) {
		return answerDao.selectOneAnswer(answerId);
	}

}
