package com.ssafy.ssafit.service.board;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.ssafit.model.dao.QuestionDao;
import com.ssafy.ssafit.model.dto.Question;


@Service
public class QuestionServiceImpl implements QuestionService {
	
	private QuestionDao questionDao;
	
	@Autowired
	public QuestionServiceImpl(QuestionDao questionDao) {
		super();
		this.questionDao = questionDao;
	}

	@Override
	public List<Question> getQuestionList(int programId) {
		return questionDao.selectAllQuestion(programId);
	}

	@Override
	public Question readQuestion(int questionId) {
		return questionDao.selectOneQuestion(questionId);
	}

	@Override
	public void writeQuestion(Question question) {
		questionDao.insertQuestion(question);
	}

	@Override
	public boolean deleteQuestion(int questionId) {
		int result = questionDao.deleteQuestion(questionId);
		return result == 1;
	}

	@Override
	public void updateQuestion(Question question) {
		questionDao.updateQuestion(question);		
	}

}
