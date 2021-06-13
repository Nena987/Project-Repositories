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
public class UserEntity {
	
	public enum UserRole {
		ROLE_CUSTOMER, ROLE_ADMIN, ROLE_SELLER}
	
	@Id
	@GeneratedValue (strategy = GenerationType.AUTO)
	protected Integer id;
	@Column (name = "First_name")
	protected String firstName;
	@Column (name = "Last_name")
	protected String lastName;
	@Column (name = "Username")
	protected String username;
	@Column (name = "Password")
	protected String password;
	@Column (name = "Email")
	protected String email;
	@Column (name = "User_Role")
    protected UserRole userRole;
	
	@OneToMany(mappedBy = "seller", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JsonIgnore
	@Column (name = "offers")
	public List<OfferEntity> offers;
	
	@OneToMany(mappedBy = "buyer", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JsonIgnore
	@Column (name = "bills")
	public List<BillEntity> bills;
	
	@OneToMany(mappedBy = "buyer", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JsonIgnore
	@Column (name = "vouchers")
	public List<VoucherEntity> vouchers;
    
	public UserEntity() {
		super();
	}

	public UserEntity(String firstName, String lastName, String username, String password, String email,
			UserRole userRole) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.password = password;
		this.email = email;
		this.userRole = userRole;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public UserRole getUserRole() {
		return userRole;
	}

	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}
	
	
    

}
