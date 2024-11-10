package com.ssafy.ssafit.model.dto;

public class Question {
	private int questionId;
	private String title;
	private String writer; // user의 nickname 보여주기
	private String content;
	private String createdAt;
	private int userId;
	private int programId;

	public Question() {
		super();
	}

	public Question(int questionId, String title, String writer, String content, String createdAt, int userId,
			int programId) {
		super();
		this.questionId = questionId;
		this.title = title;
		this.writer = writer;
		this.content = content;
		this.createdAt = createdAt;
		this.userId = userId;
		this.programId = programId;

	}

	public int getQuestionId() {
		return questionId;
	}

	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public int getProgramId() {
		return programId;
	}

	public void setProgramId(int programId) {
		this.programId = programId;
	}

	@Override
	public String toString() {
		return "question [questionId=" + questionId + ", title=" + title + ", writer=" + writer + ", content=" + content
				+ ", createdAt=" + createdAt + ", userId=" + userId + ", programId=" + programId + "]";
	}

}
