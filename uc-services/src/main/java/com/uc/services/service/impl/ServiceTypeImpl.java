package com.uc.services.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uc.services.entity.ServiceType;
import com.uc.services.repository.ServiceTypeRepository;
import com.uc.services.request.AddServiceTypeRequest;
import com.uc.services.service.ServiceTypeService;

@Service
public class ServiceTypeImpl implements ServiceTypeService {

	@Autowired
	private ServiceTypeRepository serviceTypeRepo;
	
	
	@Override
	public List<ServiceType> getAllServiceTypes() {
		return serviceTypeRepo.getServiceTypes();
	}
	
	
	@Override
	public Long addServiceType(AddServiceTypeRequest req) {
		ServiceType serviceType = new ServiceType();
		serviceType.setName(req.getName());
		serviceType.setDescription(req.getDescription());
		return serviceTypeRepo.save(serviceType);
	}
	
	@Override
	public ServiceType getServiceTypeById(String id) {
		return serviceTypeRepo.getServiceTypeById(Long.valueOf(id));
	}
	
}
