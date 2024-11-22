package com.ssafy.ssafit.model.dto;

import java.math.BigInteger;

public class YoutubeVideoDto {
    private String videoId;
    private String title;
    private String description;
    private String thumbnailUrl;
    private String publishedAt;
    private String channelId;
    private String channelTitle;
    private String thumbnailDefaultUrl;
    private String thumbnailMediumUrl;
    private String thumbnailHighUrl;
    // BigInteger로 변경 (YouTube API가 반환하는 타입)
    private BigInteger viewCount;
    private BigInteger likeCount;
    private BigInteger commentCount;
    private String duration;
    private String definition;
    private Boolean licensedContent;  // boolean -> Boolean
    
    // Private constructor
    private YoutubeVideoDto() {}
    
    // Getters
    public String getVideoId() { return videoId; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getThumbnailUrl() { return thumbnailUrl; }
    public String getPublishedAt() { return publishedAt; }
    public String getChannelId() { return channelId; }
    public String getChannelTitle() { return channelTitle; }
    public String getThumbnailDefaultUrl() { return thumbnailDefaultUrl; }
    public String getThumbnailMediumUrl() { return thumbnailMediumUrl; }
    public String getThumbnailHighUrl() { return thumbnailHighUrl; }
    public BigInteger getViewCount() { return viewCount; }
    public BigInteger getLikeCount() { return likeCount; }
    public BigInteger getCommentCount() { return commentCount; }
    public String getDuration() { return duration; }
    public String getDefinition() { return definition; }
    public Boolean getLicensedContent() { return licensedContent; }
    
    // Static builder method
    public static Builder builder() {
        return new Builder();
    }
    
    // Builder class
    public static class Builder {
        private YoutubeVideoDto dto;
        
        Builder() {
            dto = new YoutubeVideoDto();
        }
        
        public Builder videoId(String videoId) {
            dto.videoId = videoId;
            return this;
        }
        
        public Builder title(String title) {
            dto.title = title;
            return this;
        }
        
        public Builder description(String description) {
            dto.description = description;
            return this;
        }
        
        public Builder thumbnailUrl(String thumbnailUrl) {
            dto.thumbnailUrl = thumbnailUrl;
            return this;
        }
        
        public Builder publishedAt(String publishedAt) {
            dto.publishedAt = publishedAt;
            return this;
        }
        
        public Builder channelId(String channelId) {
            dto.channelId = channelId;
            return this;
        }
        
        public Builder channelTitle(String channelTitle) {
            dto.channelTitle = channelTitle;
            return this;
        }
        
        public Builder thumbnailDefaultUrl(String url) {
            dto.thumbnailDefaultUrl = url;
            return this;
        }
        
        public Builder thumbnailMediumUrl(String url) {
            dto.thumbnailMediumUrl = url;
            return this;
        }
        
        public Builder thumbnailHighUrl(String url) {
            dto.thumbnailHighUrl = url;
            return this;
        }
        
        public Builder viewCount(BigInteger viewCount) {
            dto.viewCount = viewCount;
            return this;
        }
        
        public Builder likeCount(BigInteger likeCount) {
            dto.likeCount = likeCount;
            return this;
        }
        
        public Builder commentCount(BigInteger commentCount) {
            dto.commentCount = commentCount;
            return this;
        }
        
        public Builder duration(String duration) {
            dto.duration = duration;
            return this;
        }
        
        public Builder definition(String definition) {
            dto.definition = definition;
            return this;
        }
        
        public Builder licensedContent(Boolean licensedContent) {
            dto.licensedContent = licensedContent;
            return this;
        }
        
        public YoutubeVideoDto build() {
            return dto;
        }
    }
}