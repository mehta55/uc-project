package com.uc.services.service;

import java.util.List;

import com.uc.services.entity.ServiceType;
import com.uc.services.request.AddServiceTypeRequest;

public interface ServiceTypeService {

	public Long addServiceType(AddServiceTypeRequest req);
	
	public List<ServiceType> getAllServiceTypes();
	
	public ServiceType getServiceTypeById(String id);
}
