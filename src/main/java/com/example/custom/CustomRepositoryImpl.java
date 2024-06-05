package com.example.custom;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Repository
public class CustomRepositoryImpl implements CustomRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Transactional
	@Override
	public void depositUpdate(int id, int amount) {
		entityManager.createNativeQuery("update customer c set c.balance=c.balance + ?1 where c.id=?2")
				.setParameter(1, amount).setParameter(2, id).executeUpdate();

	}

	@Transactional
	@Override
	public void withdwarlUpdate(int id, int amount) {

		entityManager.createNativeQuery("update customer c set c.balance=c.balance - ?1 where c.id=?2")
				.setParameter(1, amount).setParameter(2, id).executeUpdate();

	}

	@Transactional
	@Override
	public void moneyTransfer(int id, int amount, int receiverId) {
		entityManager.createNativeQuery("update customer c set c.balance=c.balance - ?1 where c.id=?2")
		.setParameter(1, amount).setParameter(2, id).executeUpdate();
		
		entityManager.createNativeQuery("update customer c set c.balance=c.balance + ?1 where c.id=?2")
		.setParameter(1, amount).setParameter(2, receiverId).executeUpdate();

	}

}
