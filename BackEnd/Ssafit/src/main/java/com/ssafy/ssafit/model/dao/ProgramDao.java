package com.ssafy.ssafit.model.dao;

import com.ssafy.ssafit.model.dto.Program;

public interface ProgramDao {
	void insertProgram(Program program);

	void insertVideoIfNotExists(String videoId);

	void insertProgramVideo(Integer programId, String videoId);
}
