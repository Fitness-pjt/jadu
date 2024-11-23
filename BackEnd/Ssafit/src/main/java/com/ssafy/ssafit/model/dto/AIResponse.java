package com.ssafy.ssafit.model.dto;

import java.util.List;

public class AIResponse {

	private String title;
	private String description;
	private List<String> keywords;

	public AIResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AIResponse(String title, String description, List<String> keywords) {
		super();
		this.title = title;
		this.description = description;
		this.keywords = keywords;
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

	public List<String> getKeywords() {
		return keywords;
	}

	public void setKeywords(List<String> keywords) {
		this.keywords = keywords;
	}

	@Override
	public String toString() {
		return "AIResponse [title=" + title + ", description=" + description + ", keywords=" + keywords + "]";
	}

}
