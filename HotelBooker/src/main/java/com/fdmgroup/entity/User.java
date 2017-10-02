package com.fdmgroup.entity;

import javax.persistence.*;

@Entity
@Table(name = "Users")
public class User {

	@Id
	@Column
	private Integer id;
	
	@Column(name = "first_name")
	private String firstname;
	
	@Column(name = "last_name")
	private String lastname;
	
	@Column
	private String email;
	
	@Column(name = "billing_street")
	private String billingStreet;
	
	@Column(name = "billing_postcode")
	private String billingPostCode;
	
	@Column(name = "billing_country")
	private String billingCountry;
	
	/**
	 * Copies non-Id column values from a given User object
	 * @param user
	 */
	public void clone(User user) {
		setFirstname(user.getFirstname());
		setLastname(user.getLastname());
		setEmail(user.getEmail());
		setBillingStreet(user.getBillingStreet());
		setBillingPostCode(user.getBillingPostCode());
		setBillingCountry(user.getBillingCountry());
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public void setFirstname(String firstname){
		this.firstname=firstname;
	}

	public void setLastname(String lastname){
		this.lastname=lastname;
	}

	public String getFirstname(){
		return firstname;
	}

	public String getLastname(){
		return lastname;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBillingStreet() {
		return billingStreet;
	}

	public void setBillingStreet(String billingStreet) {
		this.billingStreet = billingStreet;
	}

	public String getBillingPostCode() {
		return billingPostCode;
	}

	public void setBillingPostCode(String billingPostCode) {
		this.billingPostCode = billingPostCode;
	}

	public String getBillingCountry() {
		return billingCountry;
	}

	public void setBillingCountry(String billingCountry) {
		this.billingCountry = billingCountry;
	}

}
