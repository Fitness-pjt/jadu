package com.ssafy.ssafit.model.dto;

import java.util.List;

public class Program {

	private int programId;
	private int userId;
	private String title;
	private String description;
	private int durationWeeks;
	private String createdAt;
	private String level;
	private String programImgPath;
	private int videoCnt;

	private List<String> videoIds; // video_id 리스트 추가

	// 기존 getter/setter들...

	public List<String> getVideoIds() {
		return videoIds;
	}

	public void setVideoIds(List<String> videoIds) {
		this.videoIds = videoIds;
	}

	public Program(int programId, int userId, String title, String description, int durationWeeks, String createdAt,
			String level) {
		super();
		this.programId = programId;
		this.userId = userId;
		this.title = title;
		this.description = description;
		this.durationWeeks = durationWeeks;
		this.createdAt = createdAt;
		this.level = level;
	}

	public Program(int programId, int userId, String title, String description, int durationWeeks, String createdAt,
			String level, String programImgPath, int videoCnt) {
		this.programId = programId;
		this.userId = userId;
		this.title = title;
		this.description = description;
		this.durationWeeks = durationWeeks;
		this.createdAt = createdAt;
		this.level = level;
		this.programImgPath = programImgPath;
		this.videoCnt = videoCnt;

	}

	public Program() {
		// TODO Auto-generated constructor stub
	}

	public int getProgramId() {
		return programId;
	}

	public void setProgramId(int programId) {
		this.programId = programId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getDurationWeeks() {
		return durationWeeks;
	}

	public void setDurationWeeks(int durationWeeks) {
		this.durationWeeks = durationWeeks;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getProgramImgPath() {
		return programImgPath;
	}

	public void setProgramImgPath(String programImgPath) {
		this.programImgPath = programImgPath;
	}

	public int getVideoCnt() {
		return videoCnt;
	}

	public void setVideoCnt(int videoCnt) {
		this.videoCnt = videoCnt;
	}

	@Override
	public String toString() {
		return "Program [programId=" + programId + ", userId=" + userId + ", title=" + title + ", description="
				+ description + ", durationWeeks=" + durationWeeks + ", createdAt=" + createdAt + ", level=" + level
				+ ", videoIds=" + videoIds + "]";
	}

}
