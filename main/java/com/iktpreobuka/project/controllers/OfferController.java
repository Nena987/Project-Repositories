package com.iktpreobuka.project.controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.iktpreobuka.project.entities.OfferEntity;
import com.iktpreobuka.project.entities.UserEntity;
import com.iktpreobuka.project.entities.UserEntity.UserRole;
import com.iktpreobuka.project.entities.OfferEntity.OfferStatus;
import com.iktpreobuka.project.repositories.CategoryRepository;
import com.iktpreobuka.project.repositories.OfferRepository;
import com.iktpreobuka.project.repositories.UserRepository;
import com.iktpreobuka.project.services.BillService;
import com.iktpreobuka.project.services.OfferService;

@RestController
@RequestMapping(path = "/project/offers")
public class OfferController {

	@Autowired
	OfferRepository offerRepository;

	@Autowired
	CategoryRepository categoryRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	BillService billService;

	@Autowired
	OfferService offerService;

	List<OfferEntity> offers = new ArrayList<>();

	/*
	 * 3.3 kreirati REST endpoint koja vraća listu svih ponuda • putanja
	 * /project/offers
	 */

	@GetMapping
	public Iterable<OfferEntity> getAll() {
		return (List<OfferEntity>) offerRepository.findAll();
	}

	/*
	 * 3.4 kreirati REST endpoint koji omogućava dodavanje nove ponude • putanja
	 * /project/offers • metoda treba da vrati dodatu ponudu
	 */
	/*
	 * @RequestMapping(method = RequestMethod.POST, value = "/") public OfferEntity
	 * createNewOffer(@RequestBody OfferEntity newOffer) { newOffer.setId(new
	 * Random().nextInt()); offers.add(newOffer); return newOffer; }
	 */

	@PostMapping
	public OfferEntity createNewOffer(@DateTimeFormat(iso = ISO.DATE) @RequestBody OfferEntity offer) {
		return offerRepository.save(offer);
	}

	/*
	 * 3.5 kreirati REST endpoint koji omogućava izmenu postojeće ponude • putanja
	 * /project/offers/{ • ukoliko je prosleđen ID koji ne pripada nijednoj ponudi
	 * treba da vrati null , a u suprotnom vraća podatke ponude sa izmenjenim
	 * vrednostima • NAPOMENA : u okviru ove metode ne menjati vrednost atributa
	 * offer status
	 */

	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public OfferEntity changeOneOffer(@RequestBody OfferEntity changeOffer, @PathVariable Integer id) {
		if (offerRepository.existsById(id)) {
			OfferEntity offer = offerRepository.findById(id).get();
			if (changeOffer.getOfferName() != null)
				offer.setOfferName(changeOffer.getOfferName());
			if (changeOffer.getOfferDescription() != null)
				offer.setOfferDescription(changeOffer.getOfferDescription());
			if (changeOffer.getOfferCreated() != null)
				offer.setOfferCreated(changeOffer.getOfferCreated());
			if (changeOffer.getOfferExpires() != null)
				offer.setOfferExpires(changeOffer.getOfferExpires());
			if (changeOffer.getRegularPrice() != null)
				offer.setRegularPrice(changeOffer.getRegularPrice());
			if (changeOffer.getActionPrice() != null)
				offer.setActionPrice(changeOffer.getActionPrice());
			if (changeOffer.getImagePath() != null)
				offer.setImagePath(changeOffer.getImagePath());
			if (changeOffer.getAvailableOffers() != null)
				offer.setAvailableOffers(changeOffer.getAvailableOffers());
			if (changeOffer.getBoughtOffers() != null)
				offer.setBoughtOffers(changeOffer.getBoughtOffers());
			return offerRepository.save(offer);
		}
		return null;
	}

	/*
	 * 3.6 kreirati REST endpoint koji omogućava brisanje postojeće ponude • putanja
	 * /project/offers/{ • ukoliko je prosleđen ID koji ne pripada nijednoj ponudi
	 * metoda treba da vrati null , a u suprotnom vraća podatke o ponudi koja je
	 * obrisana
	 */

	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public OfferEntity removeOffer(@PathVariable Integer id) {
		if (offerRepository.existsById(id)) {
			OfferEntity offer = offerRepository.findById(id).get();
			offerRepository.delete(offer);
			return offer;
		}
		return null;
	}

	/*
	 * 3.7 kreirati REST endpoint koji vraća ponudu po vrednosti prosleđenog ID a •
	 * putanja /project/offers/{ • u slučaju da ne postoji ponuda sa traženom
	 * vrednošću ID a vratiti null
	 */

	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public OfferEntity findOfferById(@PathVariable Integer id) {
		if (offerRepository.existsById(id)) {
			OfferEntity offer = offerRepository.findById(id).get();
			return offer;
		}
		return null;
	}

	/*
	 * 3.8 kreirati REST endpoint koji omogućava promenu vrednosti atributa offer
	 * status postojeće ponude • putanja /project/ changeOffer /{id}/status/{ •
	 * ukoliko je prosleđen ID koji ne pripada nijednoj ponudi metoda treba da vrati
	 * null , a u suprotnom vraća podatke o ponudi koja je obrisana
	 */

	/* izmenjena da otkazuje racune ako je ponuda istekla */

	@PutMapping(path = "/changeOffer/{id}/status/{status}")
	public OfferEntity changeOfferStatus(@PathVariable Integer id, @PathVariable OfferStatus status) {
		if (offerRepository.existsById(id)) {
			OfferEntity offer = offerRepository.findById(id).get();
			offer.setOfferStatus(status);
			if (offer.getOfferStatus().equals(OfferStatus.EXPIRED))
				billService.cancelBIllsWithExpiredOffer(id);
			return offerRepository.save(offer);
		}
		return null;
	}

	/*
	 * 3.9 kreirati REST endpoint koji omogućava pronalazak svih ponuda čija se
	 * akcijska cena nalazi u odgovarajućem rasponu • putanja /project/ findByPrice
	 * lowerPrice }/ upperPrice
	 */

	@GetMapping(path = "/findByPrice/{lowerPrice}/and/{upperPrice}")
	public List<OfferEntity> FindByPriceRange(@PathVariable Double lowerPrice, @PathVariable Double upperPrice) {
		return (List<OfferEntity>) offerRepository.findByActionPriceBetween(lowerPrice, upperPrice);
	}

	/*
	 * 2.3 omogućiti dodavanje kategorije i korisnika koji je kreirao ponudu •
	 * izmeniti prethodnu putanju za dodavanje ponude • putanja / offers categoryId
	 * }/ sellerId } • NAPOMENA : samo korisnik sa ulogom ROLE_SELLER ima pravo da
	 * bude postavljen kao onaj ko je kreirao napravio ponudu (u suprotnom ne
	 * dozvoliti kreiranje ponude ); Kao datum kreiranja ponude postaviti trenutni
	 * datum i ponuda ističe za 10 dana od dana kreiranja
	 */
	@PostMapping(path = "/{categoryId}/seller/{sellerId}")
	public OfferEntity newOfferWithCatAndUser(@PathVariable Integer categoryId, @PathVariable Integer sellerId,
			@DateTimeFormat(iso = ISO.DATE) @RequestBody OfferEntity offer) {
		if (categoryRepository.existsById(categoryId)) {
			if (userRepository.existsById(sellerId)) {
				UserEntity user = userRepository.findById(sellerId).get();
				if (user.getUserRole().equals(UserRole.ROLE_SELLER)) {
					offer.setUser(user);
					offer.setCategory(categoryRepository.findById(categoryId).get());
					offer.setOfferCreated(LocalDate.now());
					offer.setOfferExpires(offer.getOfferCreated().plusDays(10));
					return offerRepository.save(offer);
				}
			}

		}
		return null;
	}

	/*
	 * 2.4 omogućiti izmenu kategorije ponude • izmeniti prethodnu putanju za izmenu
	 * ponude • putanja / offers /{id}/ categoryId }
	 */

	@PutMapping(path = "/{offerId}/category/{categoryId}")
	public OfferEntity changeOfferCategory(@PathVariable Integer offerId, @PathVariable Integer categoryId) {
		if (offerRepository.existsById(offerId)) {
			OfferEntity offer = offerRepository.findById(offerId).get();
			offer.setCategory(categoryRepository.findById(categoryId).get());
			return offerRepository.save(offer);
		}
		return null;
	}

	/*
	 * 3.2 kreirati REST endpoint koji omogućava upload slike za kreiranu ponudu •
	 * putanja /project/offers/uploadImage/{id}
	 */

	@PutMapping(path = "/uploadImage/{id}")
	public OfferEntity addImageToOffer(@PathVariable Integer id, @RequestParam("file") MultipartFile file)
			throws IOException {
		return offerService.addImageToOffer(id, file);
	}
}
