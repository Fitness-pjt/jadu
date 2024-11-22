package com.ssafy.ssafit.service.youtube;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoContentDetails;
import com.google.api.services.youtube.model.VideoListResponse;
import com.google.api.services.youtube.model.VideoStatistics;
import com.ssafy.ssafit.config.YoutubeProperties;
import com.ssafy.ssafit.model.dto.YoutubeVideoDto;

@Service
public class YoutubeServiceImpl implements YoutubeService {
	private final Logger log = LoggerFactory.getLogger(YoutubeService.class);
	private final YoutubeProperties youtubeProperties;
	private final YouTube youtube;
	
	public YoutubeServiceImpl(YoutubeProperties youtubeProperties, YouTube youtube) {
		this.youtubeProperties = youtubeProperties;
		this.youtube = youtube;
	}
	

	public List<YoutubeVideoDto> searchVideos(String query, int maxVideoNum) {
		try {
			// 1. 검색 요청 설정
			YouTube.Search.List search = youtube.search().list(Collections.singletonList("snippet"));
			search.setKey(youtubeProperties.getKey());
			search.setQ(query);
			search.setType(Collections.singletonList("video"));
			search.setMaxResults((long)maxVideoNum);
			search.setRegionCode(youtubeProperties.getRegionCode());
			search.setRelevanceLanguage(youtubeProperties.getRelevanceLanguage());
			search.setOrder(youtubeProperties.getOrder());

			// 2. 검색 실행
			SearchListResponse searchResponse = search.execute();

			// 3. 검색된 비디오 ID 목록 추출
			List<String> videoIds = searchResponse.getItems().stream().map(item -> item.getId().getVideoId())
					.collect(Collectors.toList());

			if (videoIds.isEmpty()) {
				return Collections.emptyList();
			}

			// 4. 비디오 상세 정보 요청
			YouTube.Videos.List videoRequest = youtube.videos()
					.list(Collections.singletonList("snippet,statistics,contentDetails,status"));
			videoRequest.setKey(youtubeProperties.getKey());
			videoRequest.setId(videoIds);

			// 5. 상세 정보 조회 실행
			VideoListResponse videoResponse = videoRequest.execute();

			// 6. 비디오 ID를 키로 하는 Map 생성
			Map<String, Video> videoDetailsMap = videoResponse.getItems().stream()
	                .filter(video -> video.getStatus().getEmbeddable()) // embeddable 필터링
	                .collect(Collectors.toMap(Video::getId, video -> video));

			// 7. 검색 결과와 상세 정보를 결합하여 DTO 생성
			return searchResponse.getItems().stream().map(
					searchResult -> convertToDto(searchResult, videoDetailsMap.get(searchResult.getId().getVideoId())))
					.collect(Collectors.toList());

		} catch (IOException e) {
			log.error("Error searching YouTube videos: ", e);
			return Collections.emptyList();
		}
	}
	
	public YoutubeVideoDto convertToDto(SearchResult searchResult, Video videoDetails) {
		YoutubeVideoDto.Builder builder = YoutubeVideoDto.builder().videoId(searchResult.getId().getVideoId())
				.title(searchResult.getSnippet().getTitle()).description(searchResult.getSnippet().getDescription())
				.publishedAt(searchResult.getSnippet().getPublishedAt().toString())
				.channelId(searchResult.getSnippet().getChannelId())
				.channelTitle(searchResult.getSnippet().getChannelTitle())
				.thumbnailDefaultUrl(searchResult.getSnippet().getThumbnails().getDefault().getUrl())
				.thumbnailMediumUrl(searchResult.getSnippet().getThumbnails().getMedium().getUrl())
				.thumbnailHighUrl(searchResult.getSnippet().getThumbnails().getHigh().getUrl());

		if (videoDetails != null && videoDetails.getStatistics() != null) {
			VideoStatistics statistics = videoDetails.getStatistics();
			builder.viewCount(statistics.getViewCount()) // BigInteger
					.likeCount(statistics.getLikeCount()) // BigInteger
					.commentCount(statistics.getCommentCount()); // BigInteger
		}

		if (videoDetails != null && videoDetails.getContentDetails() != null) {
			VideoContentDetails contentDetails = videoDetails.getContentDetails();
			builder.duration(contentDetails.getDuration()).definition(contentDetails.getDefinition())
					.licensedContent(contentDetails.getLicensedContent()); // Boolean
		}

		return builder.build();
	}


	
}
