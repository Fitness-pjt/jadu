package com.ssafy.ssafit.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI springOpenAPI() {
		return new OpenAPI()
				.info(new Info().title("SSAFIT RESTful API")
				.description("SSAFIT 관통프로젝트 REST API 입니다.")
				.version("v0.0.1")
				.license(new License().name("SSAFY").url("https://www.ssafy.com")));
	}
}
