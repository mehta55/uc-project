package com.uc.services.service;

import java.util.List;

import com.uc.services.entity.ServiceProvider;
import com.uc.services.request.ProvideServiceRequest;

public interface ServiceProviderService {
	
	public List<ServiceProvider> getAllProvidedService(String providerId);

	public ServiceProvider getProvidedService(String id);

	public Long provideService(ProvideServiceRequest req, String userId, String pincode, String email);
	
	public boolean stopProvidedService(String serviceId, String userId);

	public boolean updateProvidedService(String serviceId, String userId, String fees, String comment);
}
