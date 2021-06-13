package com.iktpreobuka.project.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.iktpreobuka.project.entities.BillEntity;

public interface BillService {

	public List<BillEntity> findBillsCreatedBetween(LocalDate startDate, LocalDate endDate);

	public Boolean findBillsByCategory(Integer categoryId);

	public BillEntity changeBill(@PathVariable Integer id,
			@DateTimeFormat(iso = ISO.DATE) @RequestBody BillEntity changeBill) throws Exception;

	public BillEntity createBillWihtOfferAndBuyey(@PathVariable Integer offerId, @PathVariable Integer buyerId,
			@DateTimeFormat(iso = ISO.DATE) @RequestBody BillEntity bill) throws Exception;

	public Boolean findActiveBills(Integer CategoryId);

	public List<BillEntity> cancelBIllsWithExpiredOffer(Integer offerId);
}
