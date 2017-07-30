package com.fdmgroup.service;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import com.fdmgroup.entity.Trainee;

public class TraineeService {

	private EntityManagerFactory emf;

	public TraineeService(EntityManagerFactory emf) {
		this.emf = emf;
	}

	// Best practise is to use a new EntityManager per transaction,
	// so create a method to get one.
	// Compare this to a 'getConnection()' method in JDBC
	// Don't forget to close this once your transaction is complete!

	public EntityManager getEntityManager() {
		return emf.createEntityManager();
	}

	// Write a Trainee to the database
	public Trainee persistTrainee(Trainee trainee) {
		EntityManager em = getEntityManager();
		EntityTransaction et = em.getTransaction();
		et.begin();
		em.persist(trainee);
		et.commit();
		em.close();
		return trainee;
	}

	// Remove a Trainee from the database if they exist
	public void removeTrainee(int id) {
		EntityManager em = getEntityManager();
		EntityTransaction et = em.getTransaction();
		Trainee trainee = em.find(Trainee.class, id);
		try {
			if (trainee != null) {
				et.begin();
				em.remove(trainee);
				et.commit();
			}
		} finally {
			em.close();
		}
	}

	// Update an existing Trainee's first name
	public void updateTraineeFirstName(Trainee trainee) {
		EntityManager em = getEntityManager();
		EntityTransaction et = em.getTransaction();
		Trainee existingTrainee = em.find(Trainee.class, trainee.getTraineeId());
		try {
			if (existingTrainee != null) {
				et.begin();
				existingTrainee.setFirstname(trainee.getFirstname());
				et.commit();
			}
		} finally {
			em.close();
		}
	}
	
	// Update an existing Trainee
	public void updateTrainee(Trainee trainee) {
		EntityManager em = getEntityManager();
		EntityTransaction et = em.getTransaction();
		Trainee existingTrainee = em.find(Trainee.class, trainee.getTraineeId());
		try {
			if (existingTrainee != null) {
				et.begin();
				existingTrainee.clone(trainee);
				et.commit();
			}
		} finally {
			em.close();
		}
	}

	// Retrieve a Trainee from the database
	public Trainee findTrainee(int id) {
		Trainee trainee = new Trainee();
		EntityManager em = getEntityManager();
		try {
			trainee = em.find(Trainee.class, id);
		} finally {
			em.close();
		}
		return trainee;
	}

	// Get a list of all trainees in the trainees_table
	public List<Trainee> findAllTrainees() {
		
		TypedQuery<Trainee> query = 
				getEntityManager().createQuery("SELECT t from Trainee t", Trainee.class);
		
		return query.getResultList();
	}
}
