package com.ssafy.ssafit.service.youtube;

import java.util.List;

import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.Video;
import com.ssafy.ssafit.model.dto.YoutubeVideoDto;

public interface YoutubeService {
	public List<YoutubeVideoDto> searchVideos(String query);
	
	public YoutubeVideoDto convertToDto(SearchResult searchResult, Video videoDetails);
}
