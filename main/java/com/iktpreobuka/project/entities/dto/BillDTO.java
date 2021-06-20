package com.iktpreobuka.project.entities.dto;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;

import com.iktpreobuka.project.entities.OfferEntity;
import com.iktpreobuka.project.entities.UserEntity;

public class BillDTO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected Integer id;
	
	@NotBlank (message = "Payment made must not be null or blank.")
	@AssertFalse
	@Column
	protected Boolean paymentMade;
	
	@NotBlank (message = "Payment cancled must not be null or blank.")
	@AssertFalse
	@Column
	protected Boolean paymentCanceled;
	
	@Past (message = "Bill must be created in the past.")
	@Column
	protected LocalDate billCreated;
	
	private UserEntity buyer;
	
	private OfferEntity offer;

	public BillDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BillDTO(Integer id, Boolean paymentMade, Boolean paymentCanceled, LocalDate billCreated) {
		super();
		this.id = id;
		this.paymentMade = paymentMade;
		this.paymentCanceled = paymentCanceled;
		this.billCreated = billCreated;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Boolean getPaymentMade() {
		return paymentMade;
	}

	public void setPaymentMade(Boolean paymentMade) {
		this.paymentMade = paymentMade;
	}

	public Boolean getPaymentCanceled() {
		return paymentCanceled;
	}

	public void setPaymentCanceled(Boolean paymentCanceled) {
		this.paymentCanceled = paymentCanceled;
	}

	public LocalDate getBillCreated() {
		return billCreated;
	}

	public void setBillCreated(LocalDate billCreated) {
		this.billCreated = billCreated;
	}

	public UserEntity getBuyer() {
		return buyer;
	}

	public void setBuyer(UserEntity buyer) {
		this.buyer = buyer;
	}

	public OfferEntity getOffer() {
		return offer;
	}

	public void setOffer(OfferEntity offer) {
		this.offer = offer;
	}
	
	
}
