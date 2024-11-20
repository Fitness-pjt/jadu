package com.ssafy.ssafit.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.ssafit.model.dto.FileDto;
import com.ssafy.ssafit.model.dto.User;
import com.ssafy.ssafit.service.file.FileService;
import com.ssafy.ssafit.service.user.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/file")
@Tag(name = "File API", description = "파일 업로드, 조회")
public class FileController {
	FileService fileService;
	UserService userService;

	public FileController(FileService fileService, UserService userService) {
		this.fileService = fileService;
		this.userService = userService;
	}

	@Operation(summary = "파일 업로드 API")
	@PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file, HttpServletRequest request) {

//		User loginUser = (User) session.getAttribute("loginUser");
//		if (loginUser == null) {
//			return new ResponseEntity<>("로그인이 필요한 서비스입니다.", HttpStatus.UNAUTHORIZED);
//		}

		// SecurityContext에서 인증된 사용자 정보 가져오기
		User loginUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String fileCase = request.getHeader("file-case");

//		System.out.println(fileCase);

		FileDto fileDto = new FileDto();

		fileDto.setFileCase(fileCase);
		String filePath = fileService.upload(file, loginUser, fileDto);

		switch (fileCase) {

		case "PROFILE":
			loginUser.setProfileImgPath(filePath);
			userService.updateProfilePath(loginUser);

			break;
		case "BOARD":
			break;
		case "QUESTION":
			break;

		}

		if (filePath != null)
			return new ResponseEntity<>(filePath, HttpStatus.OK);

		return new ResponseEntity<>("Fail to Upload", HttpStatus.NO_CONTENT);
	}

}
