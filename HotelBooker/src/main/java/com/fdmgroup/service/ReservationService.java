package com.fdmgroup.service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import com.fdmgroup.entity.Reservation;

public class ReservationService {

	private EntityManagerFactory emf;

	public ReservationService(EntityManagerFactory emf) {
		this.emf = emf;
	}

	// N.B: Don't forget to close connection once transaction is complete!
	public EntityManager getEntityManager() {
		return emf.createEntityManager();
	}

	/**
	 * Fetch Reservation from database by Id
	 * @param id
	 * @return Reservation identified by the given id
	 */
	public Reservation findReservation(int id) {
		Reservation reservation;
		EntityManager em = getEntityManager();
		try {
			reservation = em.find(Reservation.class, id);
		} finally {
			em.close();
		}
		return reservation;
	}
	
	/**
	 * Fetch Reservation from database by reference string
	 * @param id
	 * @return Reservation identified by the given reference string
	 */
	public Reservation findReservationByReference(String reference) {
		EntityManager em = getEntityManager();
		TypedQuery<Reservation> query = em.createQuery(
				"SELECT r from Reservation r"
				+ " WHERE reference = '" + reference + "'", Reservation.class);
		Reservation result = null;
		if (query.getResultList().size() == 1) {
			result = query.getResultList().get(0); 
		}
		return result;
	}
	
	/**
	 * Insert Reservation into the database
	 * @param reservation
	 * @return the persisted Reservation instance
	 */
	public Reservation persistReservation(Reservation reservation) {
		EntityManager em = getEntityManager();
		EntityTransaction et = em.getTransaction();
		try {
			et.begin();
			em.persist(reservation);
			et.commit();
		} finally {
			em.close();
		}
		return reservation;
	}
	
	/**
	 * Remove a Trainee from the database if they exist
	 * @param id
	 */
	public void removeReservation(int id) {
		EntityManager em = getEntityManager();
		EntityTransaction et = em.getTransaction();
		Reservation reservation = em.find(Reservation.class, id);
		try {
			if (reservation != null) {
				et.begin();
				em.remove(reservation);
				et.commit();
			}
		} finally {
			em.close();
		}
	}
	
	/**
	 * Update an existing User instance
	 * @param user
	 */
	public void updateReservation(Reservation reservation) {
		EntityManager em = getEntityManager();
		EntityTransaction et = em.getTransaction();
		Reservation existingReservation = em.find(Reservation.class, reservation.getId());
		try {
			if (existingReservation != null) {
				et.begin();
				existingReservation.clone(reservation);
				et.commit();
			}
		} finally {
			em.close();
		}
	}

}
