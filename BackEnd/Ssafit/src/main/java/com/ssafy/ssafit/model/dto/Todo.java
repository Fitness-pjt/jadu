package com.ssafy.ssafit.model.dto;

public class Todo {
	private int todoId; // 투두 아이디
	private String date; // 투두가 실행되는 날짜
	private boolean status; // 투두를 완료 했는지 안 했는지
	private String content; // 투두 내용

	private int userId; // 투두를 추가한 유저 = 로그인한 유저
	private int programId; // 어떤 프로그램이 추가 되었는지
	private String videoId; // 프로그램 내 어떤 영상이 투두에 추가되었는지

	private int likeCnt; // 내 투두 하나에 대해서 사람들이 남긴 좋아요 수

	public Todo() {
	}

	public Todo(int todoId, String date, boolean status, String content, int userId, int programId, String videoId,
			int likeCnt) {
		super();
		this.todoId = todoId;
		this.date = date;
		this.status = status;
		this.content = content;
		this.userId = userId;
		this.programId = programId;
		this.videoId = videoId;
		this.likeCnt = likeCnt;
	}

	public int getTodoId() {
		return todoId;
	}

	public void setTodoId(int todoId) {
		this.todoId = todoId;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

	public String getVideoId() {
		return videoId;
	}

	public void setVideoId(String videoId) {
		this.videoId = videoId;
	}

	public int getLikeCnt() {
		return likeCnt;
	}

	public void setLikeCnt(int likeCnt) {
		this.likeCnt = likeCnt;
	}

	@Override
	public String toString() {
		return "Todo [todoId=" + todoId + ", date=" + date + ", status=" + status + ", content=" + content + ", userId="
				+ userId + ", programId=" + programId + ", videoId=" + videoId + ", likeCnt=" + likeCnt + "]";
	}

}
