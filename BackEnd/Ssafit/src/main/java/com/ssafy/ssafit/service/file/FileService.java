package com.ssafy.ssafit.service.file;

import org.springframework.web.multipart.MultipartFile;

import com.ssafy.ssafit.model.dto.FileDto;
import com.ssafy.ssafit.model.dto.User;

public interface FileService {
	
	
	String upload (MultipartFile file, User user,FileDto fileDto);
	
	String getFile(User user, String fileCase);
	
	
}
