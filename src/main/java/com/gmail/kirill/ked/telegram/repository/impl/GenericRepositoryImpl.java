package com.gmail.kirill.ked.telegram.repository.impl;

import com.gmail.kirill.ked.telegram.repository.GenericRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.lang.reflect.ParameterizedType;
import java.util.Collections;
import java.util.List;

public class GenericRepositoryImpl<I, T> implements GenericRepository<I, T> {
    private final static Logger logger = LoggerFactory.getLogger(GenericRepositoryImpl.class);
    @PersistenceContext
    protected EntityManager entityManager;
    protected Class<T> entityClass;

    @SuppressWarnings("unchecked")
    public GenericRepositoryImpl() {
        ParameterizedType superClass = (ParameterizedType) getClass().getGenericSuperclass();
        this.entityClass = (Class<T>) superClass.getActualTypeArguments()[1];
    }

    @Override
    public void persist(T entity) {
        entityManager.persist(entity);
    }

    @Override
    public void remove(T entity) {
        entityManager.remove(entity);
    }

    @Override
    public void merge(T entity) {
        entityManager.merge(entity);
    }

    @Override
    public T getById(I id) {
        return entityManager.find(entityClass, id);
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public List<T> findAll() {
        String query = "from " + entityClass.getName() + " e";
        Query q = entityManager.createQuery(query);
        try {
            return q.getResultList();
        } catch (NoResultException e) {
            logger.error(e.getMessage(), e);
            return Collections.emptyList();
        }
    }
}