package com.iktpreobuka.project.entities;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class VoucherEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected Integer id;
	@Column
	protected LocalDate expirationDate;
	@Column
	protected Boolean isUsed;

	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "offer")
	private OfferEntity offer;

	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "buyer")
	private UserEntity buyer;

	public VoucherEntity() {
		super();
	}

	public VoucherEntity(LocalDate expirationDate, Boolean isUsed) {
		super();
		this.expirationDate = expirationDate;
		this.isUsed = isUsed;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public UserEntity getUser() {
		return buyer;
	}

	public void setUser(UserEntity user) {
		this.buyer = user;
	}
	
	

}
