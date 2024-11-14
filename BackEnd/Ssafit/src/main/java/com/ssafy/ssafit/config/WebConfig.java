package com.ssafy.ssafit.config;

import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.ssafy.ssafit.interceptor.JwtInterceptor;

public class WebConfig implements WebMvcConfigurer {

	private JwtInterceptor jwtInterceptor;

	public WebConfig(JwtInterceptor jwtInterceptor) {
		this.jwtInterceptor = jwtInterceptor;
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(jwtInterceptor).addPathPatterns("/**/todo/**") // 인증이 필요한 경로
		.excludePathPatterns("/user/**", "/swagger-ui/**", "/v3/api-docs/**", "/**/todo/**/read"); // 인증이 불필요한 경로
	}
}
