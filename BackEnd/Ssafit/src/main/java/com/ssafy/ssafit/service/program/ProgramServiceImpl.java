package com.ssafy.ssafit.service.program;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.ssafit.model.dao.ProgramDao;
import com.ssafy.ssafit.model.dto.Program;

@Service
@Transactional
public class ProgramServiceImpl implements ProgramService {

	private final ProgramDao programDao;

	public ProgramServiceImpl(ProgramDao programDao) {
		this.programDao = programDao;
	}

	@Override
	public void createProgramWithVideos(Program program) throws Exception {
		// 1. 프로그램 기본 정보 저장
//		System.out.println(program.toString());
		programDao.insertProgram(program); // AUTO_INCREMENT된 ID가 program에 설정됨

//		System.out.println("프로그램 생성 후" + program.toString());
		// 2. 비디오 정보 저장 (없는 비디오 ID라면 video 테이블에 추가)
		for (String videoId : program.getVideoIds()) {
			programDao.insertVideoIfNotExists(videoId);
		}

		// 3. 프로그램-비디오 연결 정보 저장
		for (String videoId : program.getVideoIds()) {
			programDao.insertProgramVideo(program.getProgramId(), videoId);
		}
	}

	@Override
	public Program selectByProgramId(int programId) throws Exception {
		Program program = programDao.selectByProgramId(programId);
		if (program == null) {
			throw new Exception("프로그램을 찾을 수 없습니다.");
		}
		return program;
	}

	@Override
	public List<Program> selectAllPrograms() throws Exception {
		List<Program> programs = programDao.selectAllPrograms();
		if (programs == null) {
			throw new Exception("프로그램 목록을 불러오는데 실패했습니다.");
		}
		return programs;
	}

	@Override
	public List<Program> selectByUserId(int userId) throws Exception {
		List<Program> userPrograms = programDao.selectByUserId(userId);
		if (userPrograms == null) {
			throw new Exception("사용자의 프로그램 목록을 불러오는데 실패했습니다.");
		}
		return userPrograms;
	}

	public Program updateProgram(Program program) {
		try {
			// 기존 program_video 관계 삭제
			programDao.deleteProgramVideos(program.getProgramId());

			// 프로그램 정보 업데이트
			int result = programDao.updateProgram(program);

			if (result > 0 && program.getVideoIds() != null && !program.getVideoIds().isEmpty()) {
				// 새로운 program_video 관계 추가
				for (String videoId : program.getVideoIds()) {
					programDao.insertVideoIfNotExists(videoId);
				}

				for (String videoId : program.getVideoIds()) {
					programDao.insertProgramVideo(program.getProgramId(), videoId);
				}

			}

			return programDao.selectByProgramId(program.getProgramId());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean deleteProgram(int programId) {
		try {
			// program_video 관계 먼저 삭제
			programDao.deleteProgramVideos(programId);

			// 프로그램 삭제
			return programDao.deleteProgram(programId) > 0;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public void updateProgramImgPath(int programId, String filePath) {
		// TODO Auto-generated method stub
		try {
			programDao.updateProgramImgPath(programId, filePath);
		} catch (Exception e) {

		}
	}

	@Override
	public boolean checkProgramLike(int programId, int userId) {
		return programDao.checkProgramLike(programId, userId);
	}

	@Override
	public int getProgramLikeCount(int programId) {
		return programDao.getProgramLikeCount(programId);
	}

	@Override
	@Transactional
	public boolean toggleProgramLike(int programId, int userId) {
		if (checkProgramLike(programId, userId)) {
			programDao.deleteProgramLike(programId, userId);
			return false;
		} else {
			programDao.insertProgramLike(programId, userId);
			return true;
		}
	}
}
