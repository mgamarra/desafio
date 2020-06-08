package br.com.challenge.core.data.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.querydsl.jpa.impl.JPAQueryFactory;

public abstract class BaseRepository {

	@PersistenceContext
	protected EntityManager entityManager;

	/*
	Protected Methods
	 */

	protected JPAQueryFactory getJPAQueryFactory() {
		return new JPAQueryFactory(entityManager);
	}

	protected boolean containsInPersistentContext(Object entity) {
		return entityManager.contains(entity);
	}
}
