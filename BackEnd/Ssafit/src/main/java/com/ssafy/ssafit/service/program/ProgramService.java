package com.ssafy.ssafit.service.program;

import java.util.List;

import com.ssafy.ssafit.model.dto.Program;

public interface ProgramService {

	
	public void createProgramWithVideos(Program program) throws Exception;
	
	// 단일 프로그램 조회
    Program selectByProgramId(int programId) throws Exception;
    
    // 전체 프로그램 조회
    List<Program> selectAllPrograms() throws Exception;
    
    // 사용자별 프로그램 조회
    List<Program> selectByUserId(int userId) throws Exception;
}
