package com.iktpreobuka.project.services;

import org.springframework.http.ResponseEntity;

import com.iktpreobuka.project.entities.BillEntity;
import com.iktpreobuka.project.entities.VoucherEntity;
import com.iktpreobuka.project.entities.dto.VoucherDTO;

public interface VoucherService {

	public VoucherEntity createVoucherWithBillPaymentMade(BillEntity bill) throws Exception;

	public ResponseEntity<?> createNewVoucher(Integer offerId, Integer buyerId, VoucherDTO newVoucher) throws Exception;

}
