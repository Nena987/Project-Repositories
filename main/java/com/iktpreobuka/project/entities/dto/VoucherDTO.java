package com.iktpreobuka.project.entities.dto;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;

import com.iktpreobuka.project.entities.OfferEntity;
import com.iktpreobuka.project.entities.UserEntity;
import com.sun.istack.NotNull;

public class VoucherDTO {

	@Column
	@NotBlank(message = "Expiration Date must not be null or blank.")
	@Future
	protected LocalDate expirationDate;

	@Column
	@AssertFalse
	@NotNull
	protected Boolean isUsed;

	@NotNull
	private OfferEntity offer;

	@NotNull
	private UserEntity buyer;

	public VoucherDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LocalDate getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(LocalDate expirationDate) {
		this.expirationDate = expirationDate;
	}

	public Boolean getIsUsed() {
		return isUsed;
	}

	public void setIsUsed(Boolean isUsed) {
		this.isUsed = isUsed;
	}

	public OfferEntity getOffer() {
		return offer;
	}

	public void setOffer(OfferEntity offer) {
		this.offer = offer;
	}

	public UserEntity getBuyer() {
		return buyer;
	}

	public void setBuyer(UserEntity buyer) {
		this.buyer = buyer;
	}

}
