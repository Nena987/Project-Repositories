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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.NotNull;

@Entity
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class UserEntity {

	public enum UserRole {
		ROLE_CUSTOMER, ROLE_ADMIN, ROLE_SELLER
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected Integer id;

	@NotBlank(message = "First name must not be null or blank.")
	@Column(name = "First_name")
	protected String firstName;

	@NotBlank(message = "Last name must not be null or blank.")
	@Column(name = "Last_name")
	protected String lastName;

	@NotBlank(message = "Username must not be null or blank.")
	@Column(name = "Username")
	@Size(min = 5, max = 20, message = "User name must have between {min} and {max} characters.")
	protected String username;

	@NotBlank(message = "Password must not be null or blank.")
	@Size(min = 5, message = "Password must have al least {min} characters.")
	@Pattern(regexp = "[a-zA-Z0-9]", message = "Password is not valid.")
	@Column(name = "Password")
	protected String password;

	@NotBlank(message = "Email must not be null or blank.")
	@Column(name = "Email")
	protected String email;

	@Column(name = "User_Role")
	protected UserRole userRole;

	@OneToMany(mappedBy = "seller", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JsonIgnore
	@Column(name = "offers")
	public List<OfferEntity> offers;

	@OneToMany(mappedBy = "buyer", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JsonIgnore
	@Column(name = "bills")
	public List<BillEntity> bills;

	@OneToMany(mappedBy = "buyer", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JsonIgnore
	@Column(name = "vouchers")
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
