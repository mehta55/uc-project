package com.uc.feedback.service;

import com.uc.feedback.request.FeedbackRequest;
import com.uc.feedback.response.FetchFeedbacksResponse;

public interface FeedbackService {

	public Long saveFeedback(FeedbackRequest req, String byUser);
	
	public FetchFeedbacksResponse fetchFeedbacks(String forUsers);
}
