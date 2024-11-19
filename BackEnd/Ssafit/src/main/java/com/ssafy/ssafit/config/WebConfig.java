package com.ssafy.ssafit.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.ssafy.ssafit.interceptor.JwtInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	private JwtInterceptor jwtInterceptor;

	public WebConfig(JwtInterceptor jwtInterceptor) {
		this.jwtInterceptor = jwtInterceptor;
	}
	
//	@Override
//	public void addInterceptors(InterceptorRegistry registry) {
//		registry.addInterceptor(jwtInterceptor).addPathPatterns("/**/todo/**") // 인증이 필요한 경로
//		.excludePathPatterns("/user/**", "/swagger-ui/**", "/v3/api-docs/**", "/**/todo/**/read"); // 인증이 불필요한 경로
//	}
	
	// Vue에 들어오는 모든 경로에 대해서 CORS 설정 추가 
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**") // 모든 경로에 대해 CORS 허용
        .allowedOrigins("http://localhost:5173") // Vue.js 주소 명시
        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // 허용할 HTTP 메서드
        .allowedHeaders("*") // 모든 헤더 허용
        .allowCredentials(true); // 인증 정보 허용
	}
}
