package com.fdmgroup.entity;

import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name = "Reservations")
public class Reservation {

	@Id
	@Column
	private Integer id;
	
	@Column(unique=true)
	private String reference;
	
	@ManyToOne(optional=false, targetEntity=User.class)
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne(optional=false, targetEntity=Hotel.class) 
    @JoinColumn(name="hotel_id", nullable=false, updatable=false)
	private Hotel hotel;
	
	@Column(name = "start_date")
	private Date startDate;
	
	@Column(name = "end_date")
	private Date endDate;
	
	@Column(name = "number_of_guests")
	private Integer numberOfGuests;
	
	public Reservation(){}
	
	public Reservation(Integer id) {
		this.id = id;
	}
	
	// Copies non-Id column values from a given Reservation instance
	public void clone(Reservation reservation) {
		this.reference = reservation.getReference();
		this.user = reservation.getUser();
		this.hotel = reservation.getHotel();
		this.startDate = reservation.getStartDate();
		this.endDate = reservation.getEndDate();
		this.numberOfGuests = reservation.getNumberOfGuests();
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public Hotel getHotel() {
		return hotel;
	}

	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	public Integer getNumberOfGuests() {
		return numberOfGuests;
	}
	
	public void setNumberOfGuests(Integer numberOfGuests) {
		this.numberOfGuests = numberOfGuests;
	}
	
}
