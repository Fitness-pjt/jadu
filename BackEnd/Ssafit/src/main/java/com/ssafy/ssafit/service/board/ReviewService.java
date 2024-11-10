package com.ssafy.ssafit.service.board;

import java.util.List;

import com.ssafy.ssafit.model.dto.Review;

public interface ReviewService {
		// 전체 리뷰 조회
		public  List<Review> getReviewList(int programId);

		// ID에 해당하는 리뷰 하나 가져오기
		public  Review readReview(int reviewId);

		// 리뷰 등록
		public void writeReview(Review review);

		// 리뷰 삭제
		public boolean deleteReview(int reviewId);

		// 리뷰 수정
		public void updateReview(Review review);


}
