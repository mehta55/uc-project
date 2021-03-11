package com.uc.offers.service;

import java.util.List;

import com.uc.offers.entity.Offer;
import com.uc.offers.request.AddOfferRequest;

public interface OfferService {

	public Long addOffer(AddOfferRequest req);

	public List<Offer> getOffersForEmail(String email);

	public Offer getOfferById(String id, String forEmail);

	public boolean deleteOfferById(String id);
	
}
