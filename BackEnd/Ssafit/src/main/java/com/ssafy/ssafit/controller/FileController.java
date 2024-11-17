package com.ssafy.ssafit.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.ssafit.model.dto.FileDto;
import com.ssafy.ssafit.model.dto.User;
import com.ssafy.ssafit.service.file.FileService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/file")
@Tag(name = "File API", description = "파일 업로드, 조회")
@CrossOrigin("*")
public class FileController {
	FileService fileService;

	public FileController(FileService fileService) {
		this.fileService = fileService;
	}

	@Operation(summary = "파일 업로드 API")
	@PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file, HttpSession session) {

		User loginUser = (User) session.getAttribute("loginUser");
		if (loginUser == null) {
			return new ResponseEntity<>("로그인이 필요한 서비스입니다.", HttpStatus.UNAUTHORIZED);
		}

		FileDto fileDto = new FileDto();

		fileDto.setFileCase("QUESTION");
		

		if (fileService.upload(file, loginUser, fileDto))
			return new ResponseEntity<>("file Uploaded", HttpStatus.OK);

		return new ResponseEntity<>("Fail to Upload", HttpStatus.NO_CONTENT);
	}

}
