package com.ssafy.ssafit.filter;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//@Component
//public class JwtAuthFilter extends OncePerRequestFilter { 
//
//	@Override
//	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//			throws ServletException, IOException {
//		
//		 // 요청 헤더 출력
//        System.out.println("Request Headers: ");
//        request.getHeaderNames().asIterator().forEachRemaining(headerName -> {
//            System.out.println(headerName + ": " + request.getHeader(headerName));
//        });
//        
//        
//		String token = request.getHeader("Authorization");
//		System.out.println(token);
//		
//	}
//
//}
