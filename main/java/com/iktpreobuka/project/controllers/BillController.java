package com.iktpreobuka.project.controllers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.project.entities.BillEntity;
import com.iktpreobuka.project.entities.OfferEntity;
import com.iktpreobuka.project.entities.UserEntity;
import com.iktpreobuka.project.entities.UserEntity.UserRole;
import com.iktpreobuka.project.repositories.BillRepository;
import com.iktpreobuka.project.repositories.CategoryRepository;
import com.iktpreobuka.project.repositories.OfferRepository;
import com.iktpreobuka.project.repositories.UserRepository;

@RestController
@RequestMapping(path = "/project/bills")
public class BillController {

	@Autowired
	BillRepository billRepository;

	@Autowired
	OfferRepository offerRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	CategoryRepository categoryRepository;

	/*
	 * 3.3 u paketu com.iktpreobuka.project.controllers napraviti klasu
	 * BillController sa REST endpoint om koji vraća listu svih računa • putanja
	 * /project/bills
	 */

	@GetMapping
	public Iterable<BillEntity> getAll() {
		return (List<BillEntity>) billRepository.findAll();
	}

	/*
	 * 3.6 kreirati REST endpoint ove za dodavanje , izmenu i brisanje računa •
	 * putanja /project/ offerId }/ buyerId } dodavanje
	 */

	/*
	 * 5. 1 proširiti metodu za dodavanje računa tako da se smanji broj dostupnih
	 * ponuda ponude sa računa , odnosno poveća broj kupljenih
	 */

	@PostMapping(path = "/{offerId}/buyer/{buyerId}")
	public BillEntity createBillWihtOfferAndBuyey(@PathVariable Integer offerId, @PathVariable Integer buyerId,
			@DateTimeFormat(iso = ISO.DATE) @RequestBody BillEntity bill) {
		if (offerRepository.existsById(offerId)) {
			if (userRepository.existsById(buyerId)) {
				UserEntity user = userRepository.findById(buyerId).get();
				OfferEntity offer = offerRepository.findById(offerId).get();
				bill.setUser(user);
				bill.setOffer(offer);
				bill.getOffer().setAvailableOffers(bill.getOffer().getAvailableOffers() - 1);
				bill.getOffer().setBoughtOffers(bill.getOffer().getBoughtOffers() + 1);
				offerRepository.save(offer);
				return billRepository.save(bill);
			}
		}
		return null;
	}
	/*
	 * • putanja /project/bills/{ izmena i brisanje
	 */

	/*
	 * 5. 2 proširiti metodu za izmenu računa tako da ukoliko se račun proglašava
	 * otkazanim tada treba povećati broj dostupnih ponuda ponude sa računa ,
	 * odnosno smanjiti broj kupljenih
	 */

	@PutMapping(path = "/{id}")
	public BillEntity changeBill(@PathVariable Integer id,
			@DateTimeFormat(iso = ISO.DATE) @RequestBody BillEntity changeBill) {
		if (billRepository.existsById(id)) {
			BillEntity bill = billRepository.findById(id).get();
			if (changeBill.getPaymentMade() != null)
				bill.setPaymentMade(changeBill.getPaymentMade());
			if (changeBill.getPaymentCanceled() != null) {
				bill.setPaymentCanceled(changeBill.getPaymentCanceled());
				if (bill.getPaymentCanceled()) {
					bill.getOffer().setAvailableOffers(bill.getOffer().getAvailableOffers() + 1);
					bill.getOffer().setBoughtOffers(bill.getOffer().getBoughtOffers() - 1);
				}
			}
			if (changeBill.getBillCreated() != null)
				bill.setBillCreated(changeBill.getBillCreated());
			return billRepository.save(bill);
		}
		return null;
	}

	@DeleteMapping(path = "/{id}")
	public BillEntity removeOneBill(@PathVariable Integer id) {
		if (billRepository.existsById(id)) {
			BillEntity bill = billRepository.findById(id).get();
			billRepository.delete(bill);
			return bill;
		}
		return null;
	}

	/*
	 * 3.7 kreirati REST endpoint za pronalazak svih računa određenog kupca •
	 * putanja /project/ findByBuyer buyerId
	 */

	@GetMapping(path = "/findByBuyer/{buyerId}")
	public List<BillEntity> findBillByBuyer(@PathVariable Integer buyerId) {
		return billRepository.findByUserId(buyerId);
	}

	/*
	 * 3.8 kreirati REST endpoint za pronalazak svih računa određene kategorije •
	 * putanja /project/bills/findByCategory/{
	 */
	@GetMapping(path = "/findByCategory/{categoryId}")
	public List<BillEntity> findBillByCategory(@PathVariable Integer categoryId) {
		if (categoryRepository.existsById(categoryId)) {
			return billRepository.findByOfferCategory(categoryRepository.findById(categoryId).get());
		}
		return null;
	}

	/*
	 * 3.9 kreirati REST endpoint za pronalazak svih računa koji su kreirani u
	 * odgovarajućem vremenskom periodu • putanja
	 * /project/bills/findByDate/{startDate}/and/{
	 */

	@GetMapping(path = "/findByDate/{startDate}/and/{endDate}")
	public List<BillEntity> findBillByDate(@DateTimeFormat(iso = ISO.DATE) @PathVariable LocalDate startDate,
			@DateTimeFormat(iso = ISO.DATE) @PathVariable LocalDate endDate) {
		return (List<BillEntity>) billRepository.findByBillCreatedBetween(startDate, endDate);
	}
}
