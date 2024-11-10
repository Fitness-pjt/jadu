package com.ssafy.ssafit.model.dto;

public class UserInfo {
	private Integer userId;
	private String goal;
	private String gender;
	private String age;
	private String shape;
	private String experience;
	private String location;
	private Integer frequency;

	public Integer getUserId() {
		return userId;
	}

	public String getGoal() {
		return goal;
	}

	public String getGender() {
		return gender;
	}

	public String getAge() {
		return age;
	}

	public String getShape() {
		return shape;
	}

	public String getExperience() {
		return experience;
	}

	public String getLocation() {
		return location;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public void setGoal(String goal) {
		this.goal = goal;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public void setShape(String shape) {
		this.shape = shape;
	}

	public void setExperience(String experience) {
		this.experience = experience;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public void setFrequency(Integer frequency) {
		this.frequency = frequency;
	}

	public Integer getFrequency() {
		return frequency;
	}

	public UserInfo(Integer userId, String goal, String gender, String age, String shape, String experience,
			String location, Integer frequency) {
		this.userId = userId;
		this.goal = goal;
		this.gender = gender;
		this.age = age;
		this.shape = shape;
		this.experience = experience;
		this.location = location;
		this.frequency = frequency;
	}

	public UserInfo() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "UserInfo [userId=" + userId + ", goal=" + goal + ", gender=" + gender + ", age=" + age + ", shape="
				+ shape + ", experience=" + experience + ", location=" + location + ", frequency=" + frequency + "]";
	}

}
