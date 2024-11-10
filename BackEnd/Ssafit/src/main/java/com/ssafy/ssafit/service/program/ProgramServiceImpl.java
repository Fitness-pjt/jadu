package com.ssafy.ssafit.service.program;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.ssafit.model.dao.ProgramDao;
import com.ssafy.ssafit.model.dto.Program;

@Service
public class ProgramServiceImpl implements ProgramService {

	private final ProgramDao programDao;

	public ProgramServiceImpl(ProgramDao programDao) {
		this.programDao = programDao;
	}

	@Override
	@Transactional
	public void createProgramWithVideos(Program program) throws Exception {
		// 1. 프로그램 기본 정보 저장
		programDao.insertProgram(program); // AUTO_INCREMENT된 ID가 program에 설정됨

		// 2. 비디오 정보 저장 (없는 비디오 ID라면 video 테이블에 추가)
		for (String videoId : program.getVideoIds()) {
			programDao.insertVideoIfNotExists(videoId);
		}

		// 3. 프로그램-비디오 연결 정보 저장
		for (String videoId : program.getVideoIds()) {
			programDao.insertProgramVideo(program.getProgramId(), videoId);
		}
	}
}
