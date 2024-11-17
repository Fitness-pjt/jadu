package com.ssafy.ssafit.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.ssafit.model.dto.Review;
import com.ssafy.ssafit.model.dto.User;
import com.ssafy.ssafit.service.board.ReviewService;
import com.ssafy.ssafit.service.user.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/program/{programId}")
@Tag(name = "Review API", description = "리뷰 CRUD")
@CrossOrigin("*")
public class ReviewController {
	private ReviewService reviewService;
	private UserService userService;

	public ReviewController(ReviewService reviewService, UserService userService) {
		super();
		this.reviewService = reviewService;
		this.userService = userService;
	}

	// 전체 리뷰 조회하기
	@GetMapping("/review")
	@Operation(summary = "전체 리뷰 조회", description = "프로그램의 리뷰를 모두 조회합니다.")
	public ResponseEntity<?> getList(@PathVariable("programId") int programId) {
		
		List<Review> list = reviewService.getReviewList(programId);

		if(list == null || list.size() ==0) {
			return new ResponseEntity<>("답변 없음.", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Review>>(list, HttpStatus.OK);
	}

	// 리뷰 하나 조회하기
	@GetMapping("/review/{reviewId}")
	@Operation(summary = "리뷰 하나 조회", description = "프로그램의 특정 리뷰 하나를 상세보기 합니다.")
	public ResponseEntity<?> detailReview(@PathVariable("reviewId") int reviewId,
			@PathVariable("programId") int programId) {
		Review review = reviewService.readReview(reviewId);

		if (review != null) {
			return new ResponseEntity<Review>(review, HttpStatus.OK);
		}
		return new ResponseEntity<String>("리뷰를 찾을 수 없음", HttpStatus.NOT_FOUND);
	}

	// 리뷰 등록하기
	@PostMapping("/review")
	@Operation(summary = "리뷰 등록하기", description = "리뷰를 등록합니다.")
	public ResponseEntity<?> writeReview(@PathVariable("programId") int programId, @RequestBody Review review,
			HttpSession session) {
		User loginUser = (User) session.getAttribute("loginUser");

		if (loginUser == null) {
			return new ResponseEntity<>("로그인 정보 없음.", HttpStatus.UNAUTHORIZED);
		}

		int userId = loginUser.getUserId();
		review.setUserId(userId);
		review.setProgramId(programId);

		reviewService.writeReview(review);

		return new ResponseEntity<Review>(review, HttpStatus.CREATED);
	}

	// 리뷰 삭제하기
	@DeleteMapping("/review/{reviewId}")
	@Operation(summary = "리뷰 삭제하기", description = "리뷰를 삭제합니다.")
	public ResponseEntity<String> delete(@PathVariable("programId") int programId,
			@PathVariable("reviewId") int reviewId, HttpSession session) {

		Review review = reviewService.readReview(reviewId);
		int writerUserId = review.getUserId(); // 글을 작성한 유저 아이디

		User loginUser = (User) session.getAttribute("loginUser");

		if (loginUser == null) {
			return new ResponseEntity<>("로그인 정보 없음.", HttpStatus.UNAUTHORIZED);
		}

		int loginUserId = loginUser.getUserId(); // 로그인한 유저 아이디

		if (writerUserId != loginUserId) {
			return new ResponseEntity<>("글 작성자만 삭제 가능합니다.", HttpStatus.UNAUTHORIZED);
		}

		boolean isDeleted = reviewService.deleteReview(reviewId);
		if (isDeleted) {
			return new ResponseEntity<>("게시글 삭제 성공", HttpStatus.OK);
		}
		return new ResponseEntity<>("게시글 삭제 실패", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	// 리뷰 수정하기
	@PutMapping("/review/{reviewId}")
	@Operation(summary = "리뷰 수정하기", description = "리뷰를 수정합니다.")
	public ResponseEntity<String> update(@PathVariable("programId") int programId,
			@PathVariable("reviewId") int reviewId, @RequestBody Review review, HttpSession session) {

		Review updateReview = reviewService.readReview(reviewId);
		int writerUserId = updateReview.getUserId(); // 글을 작성한 유저 아이디

		User loginUser = (User) session.getAttribute("loginUser");

		if (loginUser == null) {
			return new ResponseEntity<>("로그인 정보 없음.", HttpStatus.UNAUTHORIZED);
		}

		int loginUserId = loginUser.getUserId(); // 로그인한 유저 아이디

		if (writerUserId != loginUserId) {
			return new ResponseEntity<>("글 작성자만 수 가능합니다.", HttpStatus.UNAUTHORIZED);
		}
		

		review.setReviewId(reviewId);
		reviewService.updateReview(review);
		return new ResponseEntity<String>("게시글 수정 성공", HttpStatus.OK);
	}

}
