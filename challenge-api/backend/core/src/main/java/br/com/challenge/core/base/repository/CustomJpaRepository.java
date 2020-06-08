package br.com.challenge.core.base.repository;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class CustomJpaRepository<T, ID extends Serializable> extends SimpleJpaRepository<T, ID> {

    private final EntityManager entityManager;
    private final JpaEntityInformation<T, ?> entityInformation;

	/*
	Constructor
	 */

    public CustomJpaRepository(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
        this.entityInformation = entityInformation;
    }

	/*
	Override findOne to enable Filter to find a record by id.
	 */

    public T findOne(ID id) {
        try {
            return getQuery(new ByIdSpecification<T, ID>(entityInformation, id), (Sort) null).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

	/*
	Override transactional methods to force upper layer to init transaction
	 */

    @Transactional(propagation = Propagation.MANDATORY)
    public void delete(ID id) {
        super.deleteById(id);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void delete(T entity) {
        super.delete(entity);
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void delete(Iterable<? extends T> entities) {
        super.deleteAll(entities);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void deleteInBatch(Iterable<T> entities) {
        super.deleteInBatch(entities);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void deleteAll() {
        super.deleteAll();
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void deleteAllInBatch() {
        super.deleteAllInBatch();
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public <S extends T> S save(S entity) {
        return super.save(entity);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public <S extends T> S saveAndFlush(S entity) {
        return super.saveAndFlush(entity);
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public <S extends T> List<S> save(Iterable<S> entities) {
        return super.saveAll(entities);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void flush() {
        super.flush();
    }


	/*
	Specifications
	 */

    private static final class ByIdSpecification<T, ID> implements Specification<T> {

        private final JpaEntityInformation<T, ?> entityInformation;

        private final ID id;

        public ByIdSpecification(JpaEntityInformation<T, ?> entityInformation, ID id) {
            this.entityInformation = entityInformation;
            this.id = id;
        }

        public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
            return cb.equal(root.get(entityInformation.getIdAttribute()), id);
        }
    }
}
