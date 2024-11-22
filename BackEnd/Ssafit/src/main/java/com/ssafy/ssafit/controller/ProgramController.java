package com.ssafy.ssafit.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.ssafit.model.dto.Program;
import com.ssafy.ssafit.model.dto.User;
import com.ssafy.ssafit.service.program.ProgramService;

@RestController
@RequestMapping("/program")
public class ProgramController {

	private ProgramService programService;

	public ProgramController(ProgramService programService) {
		this.programService = programService;
	}

	@PostMapping
	public ResponseEntity<?> createProgram(@RequestBody Program program) {
		User loginUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (loginUser == null) {
			return new ResponseEntity<>("로그인이 필요한 서비스입니다.", HttpStatus.UNAUTHORIZED);
		}
//		System.out.println(program.toString());
		try {

			program.setUserId(loginUser.getUserId());

			// 프로그램 생성 및 비디오 연결
//			System.out.println("controller: " +program.toString());
			programService.createProgramWithVideos(program);

			return new ResponseEntity<>(program, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>("프로그램 생성 중 오류가 발생했습니다: " + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	// 단일 프로그램 조회
	@GetMapping("/{programId}")
	public ResponseEntity<?> selectByProgramId(@PathVariable("programId") int programId) {
		try {
			Program program = programService.selectByProgramId(programId);
			if (program == null) {
				return new ResponseEntity<>("프로그램을 찾을 수 없습니다.", HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<>(program, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("프로그램 조회 중 오류가 발생했습니다: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// 전체 프로그램 목록 조회
	@GetMapping
	public ResponseEntity<?> selectAllPrograms() {
		try {
			List<Program> programs = programService.selectAllPrograms();
			return new ResponseEntity<>(programs, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("프로그램 목록 조회 중 오류가 발생했습니다: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// 유저별 프로그램 조회
	@GetMapping("/user/{userId}")
	public ResponseEntity<?> selectByUserId(@PathVariable("userId") int userId) {
		try {
			List<Program> userPrograms = programService.selectByUserId(userId);
			if (userPrograms.isEmpty()) {
				return new ResponseEntity<>("해당 사용자의 프로그램이 없습니다.", HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(userPrograms, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("프로그램 조회 중 오류가 발생했습니다: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/{programId}")
	public ResponseEntity<?> updateProgram(@PathVariable int programId, @RequestBody Program program) {
		try {
			// 프로그램 ID 설정
			program.setProgramId(programId);

			// 현재 로그인한 사용자와 프로그램 작성자가 같은지 확인
			Program existingProgram = programService.selectByProgramId(programId);
			if (existingProgram == null) {
				return new ResponseEntity<>("프로그램이 없는디용.", HttpStatus.NOT_FOUND);
			}

			User loginUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			// SecurityContext나 JWT에서 사용자 ID를 가져와야 함
			if (existingProgram.getUserId() != loginUser.getUserId()) {
				return new ResponseEntity<>("프로그램의 작성자가 아닌디용.", HttpStatus.FORBIDDEN);
			}

			Program updatedProgram = programService.updateProgram(program);
			if (updatedProgram != null) {
				return new ResponseEntity<>(updatedProgram, HttpStatus.OK);
			} else {
				return new ResponseEntity<>("프로그램 수정에 실패했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	

	   @DeleteMapping("/{programId}")
	   public ResponseEntity<?> deleteProgram(@PathVariable int programId) {
	       try {
	           // 현재 로그인한 사용자와 프로그램 작성자가 같은지 확인
	           Program existingProgram = programService.selectByProgramId(programId);
	           if (existingProgram == null) {
	               return new ResponseEntity<>("프로그램을 찾을 수 없습니다.", HttpStatus.NOT_FOUND);
	           }

				User loginUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

	          if (existingProgram.getUserId() != loginUser.getUserId()) {
	               return new ResponseEntity<>("프로그램을 삭제할 권한이 없습니다.", HttpStatus.FORBIDDEN);
	          }

	           boolean isDeleted = programService.deleteProgram(programId);
	           if (isDeleted) {
	               return new ResponseEntity<>("프로그램이 성공적으로 삭제되었습니다.", HttpStatus.OK);
	           } else {
	               return new ResponseEntity<>("프로그램 삭제에 실패했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
	           }
	       } catch (Exception e) {
	           return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	       }
	   }

}
