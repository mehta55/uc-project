package com.uc.feedback.response;

import java.util.List;
import java.util.Map;

import com.uc.feedback.entity.Feedback;

public class FetchFeedbacksResponse {

	private Map<Long, List<Feedback>> feedbacks;

	public FetchFeedbacksResponse(Map<Long, List<Feedback>> feedbacks) {
		super();
		this.feedbacks = feedbacks;
	}

	public Map<Long, List<Feedback>> getFeedbacks() {
		return feedbacks;
	}

	public void setFeedbacks(Map<Long, List<Feedback>> feedbacks) {
		this.feedbacks = feedbacks;
	}

}
