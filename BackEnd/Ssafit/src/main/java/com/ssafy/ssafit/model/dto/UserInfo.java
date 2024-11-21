package com.ssafy.ssafit.model.dto;

import java.util.List;

public class UserInfo {
	private int userId;
	private String gender;
	private String age;
	private String shape;
	private String goal;
	private int experience;
	private String location;
	private int frequency;
	private int duration;
	private List<Integer> keyword;

	public UserInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserInfo(int userId, String gender, String age, String shape, String goal, int experience, String location,
			int frequency, int duration, List<Integer> keyword) {
		super();
		this.userId = userId;
		this.gender = gender;
		this.age = age;
		this.shape = shape;
		this.goal = goal;
		this.experience = experience;
		this.location = location;
		this.frequency = frequency;
		this.duration = duration;
		this.keyword = keyword;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getShape() {
		return shape;
	}

	public void setShape(String shape) {
		this.shape = shape;
	}

	public String getGoal() {
		return goal;
	}

	public void setGoal(String goal) {
		this.goal = goal;
	}

	public int getExperience() {
		return experience;
	}

	public void setExperience(int experience) {
		this.experience = experience;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getFrequency() {
		return frequency;
	}

	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public List<Integer> getKeyword() {
		return keyword;
	}

	public void setKeyword(List<Integer> keyword) {
		this.keyword = keyword;
	}

	@Override
	public String toString() {
		return "UserInfo [userId=" + userId + ", gender=" + gender + ", age=" + age + ", shape=" + shape + ", goal="
				+ goal + ", experience=" + experience + ", location=" + location + ", frequency=" + frequency
				+ ", duration=" + duration + ", keyword=" + keyword + "]";
	}

}