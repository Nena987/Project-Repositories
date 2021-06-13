package com.iktpreobuka.project.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class CategoryEntity {
	
	@Id
	@GeneratedValue (strategy = GenerationType.AUTO)
	protected Integer id;
	@Column (nullable = false)
	protected String categoryName;
	@Column
	protected String categoryDescription;
	
	@OneToMany(mappedBy = "category", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JsonIgnore
	@Column (name = "offers")
	public List<OfferEntity> offers;
	
	
	public CategoryEntity() {
		super();
	}
	public CategoryEntity(String categoryName, String categoryDescription) {
		super();
		this.categoryName = categoryName;
		this.categoryDescription = categoryDescription;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		id = id;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getCategoryDescription() {
		return categoryDescription;
	}
	public void setCategoryDescription(String categoryDescription) {
		this.categoryDescription = categoryDescription;
	}
	
	

}
