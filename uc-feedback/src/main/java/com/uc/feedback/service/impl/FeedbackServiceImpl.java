package com.uc.feedback.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uc.feedback.entity.Feedback;
import com.uc.feedback.repository.FeedbackRepositoy;
import com.uc.feedback.request.FeedbackRequest;
import com.uc.feedback.response.FetchFeedbacksResponse;
import com.uc.feedback.service.FeedbackService;

@Service
public class FeedbackServiceImpl implements FeedbackService {

	@Autowired
	private FeedbackRepositoy feedbackRepo;

	@Override
	public Long saveFeedback(FeedbackRequest req, String byUser) {
		Feedback feedback = new Feedback();
		feedback.setForUser(Long.valueOf(req.getForUser()));
		feedback.setRating(Integer.valueOf(req.getRating()));
		feedback.setByUser(byUser);
		feedback.setComment(req.getComment());
		return feedbackRepo.save(feedback);
	}

	public FetchFeedbacksResponse fetchFeedbacks(String forUsers) {
		List<Long> requestedForUserIds = Arrays.stream(forUsers.split(",")).map(Long::valueOf)
				.collect(Collectors.toList());
		Map<Long, List<Feedback>> feedbacks = feedbackRepo.getFeedbacks().stream()
				.filter(feedback -> requestedForUserIds.contains(feedback.getForUser()))
				.collect(Collectors.groupingBy(Feedback::getForUser));
		return new FetchFeedbacksResponse(feedbacks);
	}
}
