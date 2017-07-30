package com.fdmgroup.service;

import javax.persistence.*;

//The @Entity annotation indicates this is a JPA Entity
@Entity

// Define table name if different from Entity name
@Table(name = "trainee_table")
public class Trainee {

	// Declare an attribute of the Entity as a Primary Key Column
	@Id
	@Column
	private int traineeId;
	
	// Declare other attributes as Columns
	@Column
	private String username;
	
	// Define name of column in database if different from attribute name
	@Column(name = "first_name")
	private String firstname;
	
	@Column(name = "last_name")
	private String lastname;

	public int getTraineeId() {
		return traineeId;
	}

	public void setTraineeId(int traineeId) {
		this.traineeId = traineeId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + traineeId;
		result = prime * result
				+ ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Trainee other = (Trainee) obj;
		if (traineeId != other.traineeId)
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	public void update(Trainee trainee) {
		setUsername(trainee.getUsername());
		setFirstname(trainee.getFirstname());
		setLastname(trainee.getLastname());		
	}

	
}
