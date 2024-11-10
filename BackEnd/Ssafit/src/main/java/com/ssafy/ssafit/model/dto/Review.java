package com.ssafy.ssafit.model.dto;

public class Review {
	private int reviewId;
	private String title;
	private String writer; // user의 nickname 보여주기
	private String content;
	private int star;
	private String createdAt;
	private int userId;
	private int programId;

	public Review() {
		super();
	}

	public Review(int reviewId, String title, String writer, String content, int star, String createdAt, int userId,
			int programId) {
		super();
		this.reviewId = reviewId;
		this.title = title;
		this.writer = writer;
		this.content = content;
		this.star = star;
		this.createdAt = createdAt;
		this.userId = userId;
		this.programId = programId;

	}

	public int getReviewId() {
		return reviewId;
	}

	public void setReviewId(int reviewId) {
		this.reviewId = reviewId;
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

	public int getStar() {
		return star;
	}

	public void setStar(int star) {
		this.star = star;
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
		return "Review [reviewId=" + reviewId + ", title=" + title + ", writer=" + writer + ", content=" + content
				+ ", star=" + star + ", createdAt=" + createdAt + ", userId=" + userId + ", programId=" + programId
				+ "]";
	}

}
