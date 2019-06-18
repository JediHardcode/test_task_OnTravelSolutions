package com.gmail.kirill.ked.telegram.repository.impl;

import com.gmail.kirill.ked.telegram.repository.CityRepository;
import com.gmail.kirill.ked.telegram.repository.model.City;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.Query;

@Repository
public class CityRepositoryImpl extends GenericRepositoryImpl<Long, City> implements CityRepository {
    @Override
    public City getByName(String name) {
        Query query = entityManager.createQuery("select e from " + entityClass.getName() + " e where e.name =:name");
        query.setParameter("name", name);
        try {
            return (City) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}