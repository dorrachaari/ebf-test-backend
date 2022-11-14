package com.ebf.test.services;


import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

public class AbstractService<T> {
    @PersistenceContext
    EntityManager em;

    public T findById(Class<T> type, Long id) {
        if (id == null || type == null) return null;
        return em.find(type, id);
    }

    public List<T> findAll(Class<T> type) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(type);
        Root<T> rootEntry = criteriaQuery.from(type);

        CriteriaQuery<T> all = criteriaQuery.select(rootEntry);
        TypedQuery<T> allQuery = em.createQuery(all);
        return allQuery.getResultList();
    }

    @Transactional
    public <S extends T> S save(S entity) {
        em.persist(entity);
        return entity;
    }

    @Transactional
    public <S extends T> S merge(S entity) {
        em.merge(entity);
        return entity;
    }

    @Transactional
    public <S extends T> List<S> saveAll(Iterable<S> entities) {
        Assert.notNull(entities, "Entities must not be null!");
        List<S> result = new ArrayList<S>();
        for (S entity : entities) {
            result.add(save(entity));
        }
        return result;
    }

    @Transactional
    public void remove(T entity) {
        em.remove(entity);
    }
}
