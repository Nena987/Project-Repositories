package com.iktpreobuka.project.services;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.iktpreobuka.project.entities.OfferEntity;

public interface OfferService {

	public OfferEntity changeAvailableOffers(Integer id);

	public OfferEntity changeOfferAfterCancelation(OfferEntity offer);

	public Boolean findCategoryWithNonExpiredOffers(Integer categoryId);

	public OfferEntity addImageToOffer(Integer id, MultipartFile file) throws IOException;
}
