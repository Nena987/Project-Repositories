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
import com.iktpreobuka.project.repositories.BillRepository;
import com.iktpreobuka.project.repositories.CategoryRepository;
import com.iktpreobuka.project.repositories.OfferRepository;
import com.iktpreobuka.project.repositories.UserRepository;
import com.iktpreobuka.project.services.BillService;

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

	@Autowired
	private BillService billService;

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
	 * 5. 1 proširiti metodu za dodavanje računa tako da se smanji broj dostupnih
	 * ponuda ponude sa računa , odnosno poveća broj kupljenih
	 */

	@PostMapping(path = "/{offerId}/buyer/{buyerId}")
	public BillEntity createBillWihtOfferAndBuyey(@PathVariable Integer offerId, @PathVariable Integer buyerId,
			@DateTimeFormat(iso = ISO.DATE) @RequestBody BillEntity bill) throws Exception {
		return billService.createBillWihtOfferAndBuyey(offerId, buyerId, bill);
	}

	/*
	 * 5. 2 proširiti metodu za izmenu računa tako da ukoliko se račun proglašava
	 * otkazanim tada treba povećati broj dostupnih ponuda ponude sa računa ,
	 * odnosno smanjiti broj kupljenih
	 */

	@PutMapping(path = "/{id}")
	public BillEntity changeBill(@PathVariable Integer id,
			@DateTimeFormat(iso = ISO.DATE) @RequestBody BillEntity changeBill) throws Exception {
		return billService.changeBill(id, changeBill);
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
		return billRepository.findByBuyerId(buyerId);
	}

	/*
	 * 3.8 kreirati REST endpoint za pronalazak svih računa određene kategorije •
	 * putanja /project/bills/findByCategory/{
	 */
	@GetMapping(path = "/findByCategory/{categoryId}")
	public List<BillEntity> findBillByCategory(@PathVariable Integer categoryId) {
		if (categoryRepository.existsById(categoryId)) {
			return billRepository.findByOfferCategoryId(categoryId);
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
		return billService.findBillsCreatedBetween(startDate, endDate);
	}

}
