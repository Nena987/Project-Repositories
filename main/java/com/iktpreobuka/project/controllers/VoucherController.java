package com.iktpreobuka.project.controllers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.project.entities.VoucherEntity;
import com.iktpreobuka.project.entities.dto.VoucherDTO;
import com.iktpreobuka.project.repositories.OfferRepository;
import com.iktpreobuka.project.repositories.UserRepository;
import com.iktpreobuka.project.repositories.VoucherRepository;
import com.iktpreobuka.project.services.VoucherService;

@RestController
@RequestMapping(path = "project/vouchers")
public class VoucherController {

	@Autowired
	VoucherRepository voucherRepository;

	@Autowired
	OfferRepository offerRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	VoucherService voucherService;

	/*
	 * 4.3 u paketu com.iktpreobuka.project.controllers napraviti klasu
	 * VoucherController sa REST endpoint om koji vraća listu svih vaučera
	 */
	@GetMapping
	public Iterable<VoucherEntity> getAll() {
		return (List<VoucherEntity>) voucherRepository.findAll();
	}

	/*
	 * 4.6 kreirati REST endpoint ove za dodavanje , izmenu i brisanje vaučera •
	 * putanja / vouchers offerId }/ buyerId } • NAPOMENA : samo korisnik sa ulogom
	 * ROLE_CUSTOMER se može naći kao kupac na vaučeru (u suprotnom ne dozvoliti
	 * //kreiranje vaučera
	 */

	@PostMapping(path = "/{offerId}/buyer/{buyerId}")
	public ResponseEntity<?> createNewVoucher(@PathVariable Integer offerId, @PathVariable Integer buyerId,
			@DateTimeFormat(iso = ISO.DATE) @RequestBody VoucherDTO voucher) throws Exception {
		return voucherService.createNewVoucher(offerId, buyerId, voucher);
	}

	/* putanja /project/vouchers/{id} izmena */

	@PutMapping(path = "/{id}")
	public VoucherEntity changeVoucher(@PathVariable Integer id,
			@DateTimeFormat(iso = ISO.DATE) @RequestBody VoucherEntity changeVoucher) {
		if (voucherRepository.existsById(id)) {
			VoucherEntity voucher = voucherRepository.findById(id).get();
			if (changeVoucher.getExpirationDate() != null)
				voucher.setExpirationDate(changeVoucher.getExpirationDate());
			if (changeVoucher.getIsUsed() != null)
				voucher.setIsUsed(changeVoucher.getIsUsed());
			return voucherRepository.save(voucher);
		}
		return null;
	}

	/* putanja /project/vouchers/{id} brisanje */

	@DeleteMapping(path = "/{id}")
	public VoucherEntity removeVoucher(@PathVariable Integer id) {
		if (voucherRepository.existsById(id)) {
			VoucherEntity voucher = voucherRepository.findById(id).get();
			voucherRepository.delete(voucher);
			return voucher;
		}
		return null;
	}

	/*
	 * 4.7 kreirati REST endpoint za pronalazak svih vaučera određenog kupca •
	 * putanja /project/ findByBuyer buyerId
	 */

	@GetMapping(path = "/findByBuyer/{buyerId}")
	public List<VoucherEntity> findVouchersByBuyers(@PathVariable Integer buyerId) {
		return voucherRepository.findByBuyerId(buyerId);
	}

	/*
	 * 4.8 kreirati REST endpoint za pronalazak svih vaučera određene ponude •
	 * putanja /project/ findByOffer offerId
	 */

	@GetMapping(path = "/findByOffer/{offerId}")
	public List<VoucherEntity> findVoucherByOffer(@PathVariable Integer offerId) {
		return voucherRepository.findByOfferId(offerId);
	}

	/*
	 * 4.9 kreirati REST endpoint za pronalazak svih vaučera koji nisu istekli •
	 * putanja /project/ findNonExpiredVoucher
	 */

	@GetMapping(path = "/findNonExpiredVoucher")
	public List<VoucherEntity> findNonExpiredVouchers(@DateTimeFormat(iso = ISO.DATE) @RequestParam LocalDate date) {
		return voucherRepository.findByExpirationDateBefore(date);
	}
}
