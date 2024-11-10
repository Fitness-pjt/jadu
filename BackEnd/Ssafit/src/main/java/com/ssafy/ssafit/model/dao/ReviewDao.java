package com.ssafy.ssafit.model.dao;

import java.util.List;

import com.ssafy.ssafit.model.dto.Review;

public interface ReviewDao {

	// 전체 리뷰 조회
	public abstract List<Review> selectAllReview(int programId);

	// ID에 해당하는 리뷰 하나 가져오기
	public abstract Review selectOneReview(int reviewId);

	// 리뷰 등록
	public void insertReview(Review review);

	// 리뷰 삭제
	public int deleteReview(int reviewId);

	// 리뷰 수정
	public void updateReview(Review review);

	

	// 검색 기능
//		public List<Review> search(SearchCondition condition);
}
