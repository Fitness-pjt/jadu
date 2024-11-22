package com.ssafy.ssafit.config;


import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.youtube.YouTube;

@Configuration
@EnableConfigurationProperties(YoutubeProperties.class)
public class YoutubeConfig {

	@Bean
	public YouTube youTube() {
		return new YouTube.Builder(new NetHttpTransport(), GsonFactory.getDefaultInstance(), request -> {
		}).setApplicationName("youtube").build();
	}
}