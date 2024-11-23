package com.ssafy.ssafit.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Component
@ConfigurationProperties(prefix = "youtube.api")

public class YoutubeProperties {
	private String key; // API 키
	private int maxResults; // 검색 결과 수
	private String regionCode = "KR"; // 지역 코드
	private String relevanceLanguage = "ko"; // 관련 언어
	private String order = "relevance"; // 정렬 기준
	private boolean safeSearch = true; // 세이프 서치 여부
	private String videoDuration = "medium"; // 비디오 길이
	private String videoDefinition = "any"; // 비디오 화질
	private String videoType = "any"; // 비디오 타입

	// Getters and Setters
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public int getMaxResults() {
		return maxResults;
	}

	public void setMaxResults(int maxResults) {
		this.maxResults = maxResults;
	}

	public String getRegionCode() {
		return regionCode;
	}

	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}

	public String getRelevanceLanguage() {
		return relevanceLanguage;
	}

	public void setRelevanceLanguage(String relevanceLanguage) {
		this.relevanceLanguage = relevanceLanguage;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public boolean isSafeSearch() {
		return safeSearch;
	}

	public void setSafeSearch(boolean safeSearch) {
		this.safeSearch = safeSearch;
	}

	public String getVideoDuration() {
		return videoDuration;
	}

	public void setVideoDuration(String videoDuration) {
		this.videoDuration = videoDuration;
	}

	public String getVideoDefinition() {
		return videoDefinition;
	}

	public void setVideoDefinition(String videoDefinition) {
		this.videoDefinition = videoDefinition;
	}

	public String getVideoType() {
		return videoType;
	}

	public void setVideoType(String videoType) {
		this.videoType = videoType;
	}
}