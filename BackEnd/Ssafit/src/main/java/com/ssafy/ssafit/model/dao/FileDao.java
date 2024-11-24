package com.ssafy.ssafit.model.dao;

import org.apache.ibatis.annotations.Param;

import com.ssafy.ssafit.model.dto.FileDto;

public interface FileDao {
	
	void addFile(FileDto fileDto);

	void updateFile(@Param("questionId") int questionId, @Param("questionFileName") String questionFileName);

	
	
}
