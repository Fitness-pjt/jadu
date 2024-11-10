package com.ssafy.ssafit.service.board;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.ssafit.model.dao.ReviewDao;
import com.ssafy.ssafit.model.dto.Review;

@Service
public class ReviewServiceImpl implements ReviewService {
	
	private ReviewDao reviewDao;
	
	public ReviewServiceImpl(ReviewDao reviewDao) {
		super();
		this.reviewDao = reviewDao;
	}

	@Override
	public List<Review> getReviewList(int programId) {
		return reviewDao.selectAllReview(programId);
	}

	@Override
	public Review readReview(int reviewId) {
		return reviewDao.selectOneReview(reviewId);
	}

	@Override
	public void writeReview(Review review) {
		reviewDao.insertReview(review);
	}

	@Override
	public boolean deleteReview(int reviewId) {
		int result = reviewDao.deleteReview(reviewId);
		return result == 1;
	}

	@Override
	public void updateReview(Review review) {
		reviewDao.updateReview(review);		
	}

}
