package com.iktpreobuka.project.entities;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.NotNull;

@Entity
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class OfferEntity {

	public enum OfferStatus {
		WAIT_FOR_APPROVING, APPROVED, DECLINED, EXPIRED
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected Integer id;

	@NotBlank(message = "Offer name must not be null or blank.")
	@Column(name = "Offer_name")
	protected String offerName;

	@NotBlank(message = "Offer descritpion must not be null or blank.")
	@Size(min = 5, max = 20, message = "Offer description must have between {min} and {max} characters.")
	@Column(name = "Offer_description")
	protected String offerDescription;

	@Column(name = "Offer_created")
	protected LocalDate offerCreated;

	@Column(name = "Offer_expires")
	protected LocalDate offerExpires;

	@NotNull
	@Min(value = 1, message = "Regular price cannot be lower than 1.")
	@Column(name = "Regular_price")
	protected Double regularPrice;

	@NotNull
	@Min(value = 1, message = "Action price cannot be lower than 1.")
	@Column(name = "Action_price")
	protected Double actionPrice;

	@NotBlank(message = "Image path must not be null or blank.")
	@Column(name = "Image_path")
	protected String imagePath;

	@NotNull
	@Min(value = 0, message = "Number od available offers cannot be under 0.")
	@Column(name = "Available_offers")
	protected Integer availableOffers;

	@NotNull
	@Min(value = 0, message = "Number od bought offers cannot be under 0.")
	@Column(name = "Bought_offers")
	protected Integer boughtOffers;

	@Column(name = "Offer_status")
	protected OfferStatus offerStatus;

	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "category")
	private CategoryEntity category;

	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "seller")
	private UserEntity seller;

	@OneToMany(mappedBy = "offer", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JsonIgnore
	@Column(name = "bills")
	public List<BillEntity> bills;

	@OneToMany(mappedBy = "offer", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JsonIgnore
	@Column(name = "voucher")
	public List<VoucherEntity> vouchers;

	public OfferEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OfferEntity(Integer id, String offerName, String offerDescription, LocalDate offerCreated,
			LocalDate offerExpires, Double regularPrice, Double actionPrice, String imagePath, Integer availableOffers,
			Integer boughtOffers, OfferStatus offerStatus) {
		super();
		this.id = id;
		this.offerName = offerName;
		this.offerDescription = offerDescription;
		this.offerCreated = offerCreated;
		this.offerExpires = offerExpires;
		this.regularPrice = regularPrice;
		this.actionPrice = actionPrice;
		this.imagePath = imagePath;
		this.availableOffers = availableOffers;
		this.boughtOffers = boughtOffers;
		this.offerStatus = offerStatus;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOfferName() {
		return offerName;
	}

	public void setOfferName(String offerName) {
		this.offerName = offerName;
	}

	public String getOfferDescription() {
		return offerDescription;
	}

	public void setOfferDescription(String offerDescription) {
		this.offerDescription = offerDescription;
	}

	public LocalDate getOfferCreated() {
		return offerCreated;
	}

	public void setOfferCreated(LocalDate offerCreated) {
		this.offerCreated = offerCreated;
	}

	public LocalDate getOfferExpires() {
		return offerExpires;
	}

	public void setOfferExpires(LocalDate offerExpires) {
		this.offerExpires = offerExpires;
	}

	public Double getRegularPrice() {
		return regularPrice;
	}

	public void setRegularPrice(Double regularPrice) {
		this.regularPrice = regularPrice;
	}

	public Double getActionPrice() {
		return actionPrice;
	}

	public void setActionPrice(Double actionPrice) {
		this.actionPrice = actionPrice;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public Integer getAvailableOffers() {
		return availableOffers;
	}

	public void setAvailableOffers(Integer availableOffers) {
		this.availableOffers = availableOffers;
	}

	public Integer getBoughtOffers() {
		return boughtOffers;
	}

	public void setBoughtOffers(Integer boughtOffers) {
		this.boughtOffers = boughtOffers;
	}

	public OfferStatus getOfferStatus() {
		return offerStatus;
	}

	public void setOfferStatus(OfferStatus offerStatus) {
		this.offerStatus = offerStatus;
	}

	public CategoryEntity getCategory() {
		return category;
	}

	public void setCategory(CategoryEntity category) {
		this.category = category;
	}

	public UserEntity getUser() {
		return seller;
	}

	public void setUser(UserEntity user) {
		this.seller = user;
	}

}