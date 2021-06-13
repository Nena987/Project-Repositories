package com.iktpreobuka.project.services;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.iktpreobuka.project.entities.BillEntity;
import com.iktpreobuka.project.entities.VoucherEntity;

public interface VoucherService {

	public VoucherEntity createVoucherWithBillPaymentMade(BillEntity bill) throws Exception;

	public VoucherEntity createNewVoucher(@PathVariable Integer offerId, @PathVariable Integer buyerId,
			@DateTimeFormat(iso = ISO.DATE) @RequestBody VoucherEntity voucher) throws Exception;

}
