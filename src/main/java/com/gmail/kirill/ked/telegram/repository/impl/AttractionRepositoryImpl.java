package com.gmail.kirill.ked.telegram.repository.impl;

import com.gmail.kirill.ked.telegram.repository.AttractionRepository;
import com.gmail.kirill.ked.telegram.repository.model.Attraction;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.Query;

@Repository
public class AttractionRepositoryImpl extends GenericRepositoryImpl<Long, Attraction> implements AttractionRepository {
    @Override
    public Attraction getByName(String name) {
        Query query = entityManager.createQuery("select e from " + entityClass.getName() + " e where e.name =:name");
        query.setParameter("name", name);
        try {
            return (Attraction) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}