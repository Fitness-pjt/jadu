package com.ssafy.ssafit.service.program;

import java.util.List;

import com.ssafy.ssafit.model.dto.Program;
import com.ssafy.ssafit.model.dto.User;

public interface ProgramService {

	public void createProgramWithVideos(Program program) throws Exception;

	// 단일 프로그램 조회
	Program selectByProgramId(int programId) throws Exception;

	// 전체 프로그램 조회
	List<Program> selectAllPrograms() throws Exception;

	// 사용자별 프로그램 조회
	List<Program> selectByUserId(int userId) throws Exception;

	// 프로그램 수정
	public Program updateProgram(Program program);

	// 프로그램 삭제
	public boolean deleteProgram(int programId);

	// 프로필 경로 지정
	public void updateProgramImgPath(int programId, String filePath);

	boolean checkProgramLike(int programId, int userId);

	int getProgramLikeCount(int programId);

	boolean toggleProgramLike(int programId, int userId);

}
