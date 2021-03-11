package com.uc.services.service;

import com.uc.services.response.ServiceSearchReponse;

public interface SearchService {

	public ServiceSearchReponse searchServices(String serviceTypeId, String pincode);
	
}
