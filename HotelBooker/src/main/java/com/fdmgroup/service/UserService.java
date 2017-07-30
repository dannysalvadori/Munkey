package com.fdmgroup.service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import com.fdmgroup.entity.User;

public class UserService {

	private EntityManagerFactory emf;

	public UserService(EntityManagerFactory emf) {
		this.emf = emf;
	}

	// N.B: Don't forget to close connection once transaction is complete!
	public EntityManager getEntityManager() {
		return emf.createEntityManager();
	}

	/**
	 * Fetch user from database by Id
	 * @param id
	 * @return user identified by the given id
	 */
	public User findUser(int id) {
		User user;
		EntityManager em = getEntityManager();
		try {
			user = em.find(User.class, id);
		} finally {
			em.close();
		}
		return user;
	}
	
	/**
	 * Insert user into the database
	 * @param user
	 * @return the persisted User instance
	 */
	public User persistUser(User user) {
		EntityManager em = getEntityManager();
		EntityTransaction et = em.getTransaction();
		try {
			et.begin();
			em.persist(user);
			et.commit();
		} finally {
			em.close();
		}
		return user;
	}
	
	/**
	 * Remove a Trainee from the database if they exist
	 * @param id
	 */
	public void removeUser(int id) {
		EntityManager em = getEntityManager();
		EntityTransaction et = em.getTransaction();
		User user = em.find(User.class, id);
		try {
			if (user != null) {
				et.begin();
				em.remove(user);
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
	public void updateUser(User user) {
		EntityManager em = getEntityManager();
		EntityTransaction et = em.getTransaction();
		User existingUser = em.find(User.class, user.getId());
		try {
			if (existingUser != null) {
				et.begin();
				existingUser.clone(user);
				et.commit();
			}
		} finally {
			em.close();
		}
	}

}
