package com.iktpreobuka.project.services;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.iktpreobuka.project.entities.OfferEntity;
import com.iktpreobuka.project.repositories.OfferRepository;

@Service
public class OfferServiceImpl implements OfferService {

	@Autowired
	private OfferRepository offerRepository;

	@Autowired
	private UploadService uploadService;

	private static String UPLOADED_FOLDER = "E:\\SpringTemp\\";

	/*
	 * 2.1 u servisu zaduženom za rad sa ponudama, napisati metodu koja za prosleđen
	 * ID ponude, vrši izmenu broja kupljenih/dostupnih ponuda
	 */

	@Override
	public OfferEntity changeAvailableOffers(Integer id) {
		if (!offerRepository.existsById(id))
			return null;
		OfferEntity offer = offerRepository.findById(id).get();

		offer.setAvailableOffers(offer.getAvailableOffers() - 1);
		offer.setBoughtOffers(offer.getBoughtOffers() + 1);
		offerRepository.save(offer);

		return offer;
	}

	@Override
	public OfferEntity changeOfferAfterCancelation(OfferEntity offer) {

		offer.setAvailableOffers(offer.getAvailableOffers() + 1);
		offer.setBoughtOffers(offer.getBoughtOffers() - 1);
		offerRepository.save(offer);

		return offer;
	}

	/*
	 * 3.1 napisati metodu u servisu zaduženom za rad sa ponudama koja proverava da
	 * li postoje ponude za datu kategoriju (kategoriju koja želi da se obriše)
	 */

	@Override
	public Boolean findCategoryWithNonExpiredOffers(Integer categoryId) {
		if (offerRepository.findByCategoryId(categoryId) != null) {
			List<OfferEntity> offers = offerRepository.findAllByCategoryId(categoryId);
			LocalDate date = LocalDate.now();
			for (OfferEntity offer : offers) {
				if (offer.getOfferExpires().isAfter(date)) {
					return true;
				}
			}
		}
		return false;
	}

	/* metoda za dodavanje slike */

	@Override
	public OfferEntity addImageToOffer(Integer id, MultipartFile file) throws IOException {
		if (!offerRepository.existsById(id))
			return null;

		OfferEntity offer = offerRepository.findById(id).get();
		offer.setImagePath(uploadService.singleImageUpload(file));
		offerRepository.save(offer);

		return offer;
	}
}
