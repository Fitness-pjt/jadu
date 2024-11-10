package com.ssafy.ssafit.model.dto;

public class Answer {
	private int answerId;
	private String writer; // user의 nickname 보여주기
	private String content;
	private String createdAt;
	private int userId;
	private int questionId;

	public Answer() {
		super();
	}

	public Answer(int answerId, String writer, String content, String createdAt, int userId, int questionId) {
		super();
		this.answerId = answerId;
		this.writer = writer;
		this.content = content;
		this.createdAt = createdAt;
		this.userId = userId;
		this.questionId = questionId;

	}

	public int getAnswerId() {
		return answerId;
	}

	public void setAnswerId(int answerId) {
		this.answerId = answerId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getQuestionId() {
		return questionId;
	}

	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}

	@Override
	public String toString() {
		return "answer [answerId=" + answerId + ", writer=" + writer + ", content=" + content
				+ ", createdAt=" + createdAt + ", userId=" + userId + ", questionId=" + questionId + "]";
	}

}
