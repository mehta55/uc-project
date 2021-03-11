package com.uc.services.service.impl;


import static com.uc.common.Constants.HTTP;
import static com.uc.common.Constants.APIKEY_HEADER;

import static com.uc.common.Constants.Services.FEEDBACKS;
import static com.uc.common.Constants.Services.USER_MANAGEMENT;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.uc.common.config.CommonFileProperties;
import com.uc.common.exception.BusinessException;
import com.uc.services.dto.Feedback;
import com.uc.services.dto.FetchFeedbackResponse;
import com.uc.services.dto.FetchUsersReponse;
import com.uc.services.dto.User;
import com.uc.services.entity.ServiceProvider;
import com.uc.services.entity.ServiceType;
import com.uc.services.repository.ServiceProviderRepository;
import com.uc.services.repository.ServiceTypeRepository;
import com.uc.services.response.DetailedServiceProvider;
import com.uc.services.response.ServiceSearchReponse;
import com.uc.services.service.SearchService;

@Service
public class SearchServiceImpl implements SearchService {

	public static final Logger logger = LoggerFactory.getLogger(SearchServiceImpl.class);

	@Autowired
	private ServiceProviderRepository serviceProviderRepo;

	@Autowired
	private ServiceTypeRepository serviceTypeRepo;
	
	@Autowired
	private WebClient.Builder webClient;
	
	@Autowired
	private CommonFileProperties commonFileProps;
	
	public ServiceSearchReponse searchServices(String serviceTypeId, String pincode) {
		ServiceType serviceType = serviceTypeRepo.getServiceTypeById(Long.valueOf(serviceTypeId));
		List<DetailedServiceProvider> detailedServiceProviders = new LinkedList<>();
		if(Objects.nonNull(serviceType)) {
			List<ServiceProvider> serviceProviders = serviceProviderRepo.getServiceProviders().stream()
					.filter(serviceProvider -> serviceProvider.getServiceTypeId().equals(Long.valueOf(serviceTypeId))
							&& serviceProvider.getPincode().equals(pincode))
					.collect(Collectors.toList());
			
			List<String> providerIds = serviceProviders.stream().map(ServiceProvider::getServiceProviderId).map(String::valueOf)
					.collect(Collectors.toList());
			
			Map<String, User> users = fetchUserDetailsOfProviders(providerIds);
			Map<String, List<Feedback>> feedbacks = fetchFeedbacksOfProviders(providerIds);
			
			detailedServiceProviders = serviceProviders.parallelStream()
					.map(provider -> detailedProvider(users, feedbacks, provider)).collect(Collectors.toList());
			
		} else {
			throw new BusinessException("Sorry! we are not providing this service yet");
		}
		
		return new ServiceSearchReponse(serviceType, detailedServiceProviders);
	}
	
	private Map<String, List<Feedback>> fetchFeedbacksOfProviders(List<String> providerIds) {
		logger.info("fetchFeedbacksOfProvider() sarted: for {}", providerIds);
		Map<String, List<Feedback>> feedbacks = new HashMap<>();
		
		if(providerIds.isEmpty()) {
			return feedbacks;
		}
		
		try {
			String rsrc = new StringBuilder().append("/uc/feedbacks/v1/feedback/fetch")
					.append("?forUsers=").append(encoded(String.join(",", providerIds)))
					.toString();
			
			String reqURL = new StringBuilder().append(HTTP).append(FEEDBACKS).append(rsrc).toString();
			
			FetchFeedbackResponse response = webClient.build()
					.get().uri(URI.create(reqURL))
					.header(APIKEY_HEADER, commonFileProps.getApiKey())
					.retrieve()
					.bodyToMono(FetchFeedbackResponse.class)
					.doOnError(error -> logger.error("fetchFeedbacksOfProvider() {}: Mono completed with error.", providerIds))
					.block();
			
			feedbacks =  response.getFeedbacks();
		} catch (Exception e) {
			logger.error("fetchFeedbacksOfProvider() error: for {}", providerIds, e);
		}
		
		
		logger.info("fetchFeedbacksOfProvider() exit: fetched {}", feedbacks.size());
		return feedbacks;
	}

	private Map<String, User> fetchUserDetailsOfProviders(List<String> providerIds) {
		logger.info("fetchUserDetailsOfProviders() sarted: for {}", providerIds);
		Map<String, User> users = new HashMap<>();
		
		if(providerIds.isEmpty()) {
			return users;
		}
		
		try {
			String rsrc = new StringBuilder().append("/uc/user-management/v1/user/fetch")
					.append("?ids=").append(encoded(String.join(",", providerIds)))
					.toString();
			
			String reqURL = new StringBuilder().append(HTTP).append(USER_MANAGEMENT).append(rsrc).toString();
			
			FetchUsersReponse response = webClient.build()
					.get().uri(URI.create(reqURL))
					.header(APIKEY_HEADER, commonFileProps.getApiKey())
					.retrieve()
					.bodyToMono(FetchUsersReponse.class)
					.doOnError(error -> logger.error("fetchUserDetailsOfProviders() {}: Mono completed with error.", providerIds))
					.block();
			
			users =  response.getUsers();
		} catch (Exception e) {
			logger.error("fetchUserDetailsOfProviders() error: for {}", providerIds, e);
		}
		
		
		logger.info("fetchUserDetailsOfProviders() exit: fetched {}", users.size());
		return users;
	}
	
	private String encoded(String str) throws UnsupportedEncodingException {
		return URLEncoder.encode(str, "UTF-8");
	}

	private DetailedServiceProvider detailedProvider(Map<String, User> users, Map<String, List<Feedback>> feedbacks, ServiceProvider serviceProvider) {
		User user = users.get(serviceProvider.getServiceProviderId().toString());
		List<Feedback> feedbacksForProvider = feedbacks.get(serviceProvider.getServiceProviderId().toString());
		Double avgRating = 0.0;
		if(Objects.nonNull(feedbacksForProvider)) {
			avgRating = feedbacksForProvider.stream().map(Feedback::getRating)
					.flatMapToInt(rating -> IntStream.of(Integer.valueOf(rating))).average().getAsDouble();
		}
		return new DetailedServiceProvider(serviceProvider, user, feedbacksForProvider, avgRating);
	}

	
}
