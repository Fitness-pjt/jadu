package com.ssafy.ssafit.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ssafy.ssafit.model.dto.Program;

public interface ProgramDao {
	void insertProgram(Program program);

	void insertVideoIfNotExists(String videoId);

	void insertProgramVideo(Integer programId, String videoId);

	// 단일 프로그램 조회
	Program selectByProgramId(int programId);

	// 전체 프로그램 조회
	List<Program> selectAllPrograms();

	// 사용자별 프로그램 조회
	List<Program> selectByUserId(int userId);

	int deleteProgramVideos(int programId);

	int updateProgram(Program program);

	int deleteProgram(int programId);

	void updateProgramImgPath(@Param("programId") int programId, @Param("filePath") String filePath);

	boolean insertProgramLike(int programId, int userId);

	boolean deleteProgramLike(int programId, int userId);

	boolean checkProgramLike(int programId, int userId);

	int getProgramLikeCount(int programId);

}
