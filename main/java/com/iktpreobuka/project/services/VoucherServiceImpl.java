package com.iktpreobuka.project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.iktpreobuka.project.controllers.util.RESTError;
import com.iktpreobuka.project.entities.BillEntity;
import com.iktpreobuka.project.entities.OfferEntity;
import com.iktpreobuka.project.entities.UserEntity;
import com.iktpreobuka.project.entities.VoucherEntity;
import com.iktpreobuka.project.entities.dto.VoucherDTO;
import com.iktpreobuka.project.entities.UserEntity.UserRole;
import com.iktpreobuka.project.repositories.OfferRepository;
import com.iktpreobuka.project.repositories.UserRepository;
import com.iktpreobuka.project.repositories.VoucherRepository;

@Service
public class VoucherServiceImpl implements VoucherService {

	@Autowired
	VoucherRepository voucherRepository;

	@Autowired
	OfferRepository offerRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	EmailService emailService;

	/*
	 * u okviru servisa zaduženog za rad sa vaučerima, napisati metodu koja vrši
	 * kreiranje vaučera na osnovu prosleđenog računa
	 */

	@Override
	public VoucherEntity createVoucherWithBillPaymentMade(BillEntity bill) throws Exception {
		VoucherEntity voucher = new VoucherEntity();
		voucher.setUser(bill.getUser());
		voucher.setOffer(bill.getOffer());
		voucher.setIsUsed(false);
		voucher.setExpirationDate(bill.getBillCreated().plusMonths(6));
		voucherRepository.save(voucher);
		emailService.sendVoucherMail(voucher);
		return voucher;
	}

	/*
	 * u okviru metode za kreiranje vaučera servisa, zaduženog za rad sa vaučerima,
	 * pozvati metodu email servisa
	 * 
	 * 1 7 Izmeniti kontroler tako da koristi VoucherDTO prilikom pravljenja novog
	 * Voucher a
	 */

	public ResponseEntity<?> createNewVoucher(@PathVariable Integer offerId, @PathVariable Integer buyerId,
			@DateTimeFormat(iso = ISO.DATE) @RequestBody VoucherDTO newVoucher) throws Exception {
		if (offerRepository.existsById(offerId)) {
			if (userRepository.existsById(buyerId)) {
				UserEntity user = userRepository.findById(buyerId).get();
				OfferEntity offer = offerRepository.findById(offerId).get();
				VoucherEntity voucher = new VoucherEntity();
				if (user.getUserRole().equals(UserRole.ROLE_CUSTOMER)) {
					voucher.setUser(user);
					voucher.setOffer(offer);
					voucher.setExpirationDate(newVoucher.getExpirationDate());
					voucher.setIsUsed(newVoucher.getIsUsed());
					voucherRepository.save(voucher);
					emailService.sendVoucherMail(voucher);
					return new ResponseEntity<VoucherEntity>(voucher, HttpStatus.CREATED);
				}
			}
		}
		return new ResponseEntity<RESTError>(new RESTError(1, "Voucher not found."), HttpStatus.NOT_FOUND);
	}

}
