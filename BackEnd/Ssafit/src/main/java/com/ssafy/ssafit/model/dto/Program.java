package com.ssafy.ssafit.model.dto;

import java.util.List;

public class Program {

	private Integer programId;
	private Integer userId;
	private String title;
	private String description;
	private Integer durationWeeks;
	private String createdAt;
	private String level;
	private List<String> videoIds; // video_id 리스트 추가

	// 기존 getter/setter들...

	public List<String> getVideoIds() {
		return videoIds;
	}

	public void setVideoIds(List<String> videoIds) {
		this.videoIds = videoIds;
	}

	public Program(Integer programId, Integer userId, String title, String description, Integer durationWeeks,
			String createdAt, String level) {
		super();
		this.programId = programId;
		this.userId = userId;
		this.title = title;
		this.description = description;
		this.durationWeeks = durationWeeks;
		this.createdAt = createdAt;
		this.level = level;
	}

	public Program() {
		// TODO Auto-generated constructor stub
	}

	public Integer getProgramId() {
		return programId;
	}

	public void setProgramId(Integer programId) {
		this.programId = programId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
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

	public Integer getDurationWeeks() {
		return durationWeeks;
	}

	public void setDurationWeeks(Integer durationWeeks) {
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

	@Override
	public String toString() {
		return "Program [programId=" + programId + ", userId=" + userId + ", title=" + title + ", description="
				+ description + ", durationWeeks=" + durationWeeks + ", createdAt=" + createdAt + ", level=" + level
				+ "]";
	}

}
