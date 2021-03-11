package com.uc.offers.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uc.offers.entity.Offer;
import com.uc.offers.repository.OfferRepository;
import com.uc.offers.request.AddOfferRequest;
import com.uc.offers.service.OfferService;

@Service
public class OfferServiceImpl implements OfferService {

	@Autowired
	private OfferRepository offersRepo;
	
	public Long addOffer(AddOfferRequest req) {
		Offer offer = new Offer();
		offer.setForEmail(req.getForEmail());
		offer.setDiscount(Integer.valueOf(req.getDiscount()));
		offer.setCapping(Integer.valueOf(req.getCapping()));
		offer.setDescription(req.getDescription());
		return offersRepo.save(offer);
	}
	
	public List<Offer> getOffersForEmail(String email) {
		return offersRepo.findByForEmail(email);
	}
	
	public Offer getOfferById(String id, String forEmail) {
		return StringUtils.isEmpty(forEmail) ? offersRepo.findById(Long.valueOf(id))
				: offersRepo.findByIdAndForEmail(Long.valueOf(id), forEmail);
	}

	@Override
	public boolean deleteOfferById(String id) {
		return offersRepo.deleteById(Long.valueOf(id));
	}
}
