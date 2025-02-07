package com.bubai.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;

@Entity
public class User {
	
	@jakarta.persistence.Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long Id;
	
	private String firstName;
	
	private String lastName;
	
	private String password;
	
	private String email;
	
	private String role;
	
	private String mobile;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Address>addresses = new ArrayList<>();
	
	@Embedded
	@ElementCollection
	@CollectionTable(name = "payment_information", joinColumns =@JoinColumn(name="user_id"))
	private List<PaymentInformation> paymentInformations = new ArrayList<>();
	
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Rating>ratings = new ArrayList<>();
	
	
	@JsonIgnore
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Review>reviews = new ArrayList<>();
	
	private LocalDateTime createdAt;
	
	
	public User() {
		// TODO Auto-generated constructor stub
	}
	
	


	public User(Long id, String firstName, String lastName, String password, String email, String role, String mobile,
			List<Address> addresses, List<PaymentInformation> paymentInformations, List<Rating> ratings,
			List<Review> reviews, LocalDateTime createdAt) {
		super();
		Id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.email = email;
		this.role = role;
		this.mobile = mobile;
		this.addresses = addresses;
		this.paymentInformations = paymentInformations;
		this.ratings = ratings;
		this.reviews = reviews;
		this.createdAt = createdAt;
	}
	




	public Long getId() {
		return Id;
	}


	public void setId(Long id) {
		Id = id;
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


	public String getRole() {
		return role;
	}


	public void setRole(String role) {
		this.role = role;
	}


	public String getMobile() {
		return mobile;
	}


	public void setMobile(String mobile) {
		this.mobile = mobile;
	}


	public List<Address> getAddresses() {
		return addresses;
	}


	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}


	public List<PaymentInformation> getPaymentInformations() {
		return paymentInformations;
	}


	public void setPaymentInformations(List<PaymentInformation> paymentInformations) {
		this.paymentInformations = paymentInformations;
	}


	public List<Rating> getRatings() {
		return ratings;
	}


	public void setRatings(List<Rating> ratings) {
		this.ratings = ratings;
	}


	public List<Review> getReviews() {
		return reviews;
	}


	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}


	public LocalDateTime getCreatedAt() {
		return createdAt;
	}


	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	
	
	
	
	
	
	

}
