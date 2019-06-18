package com.gmail.kirill.ked.telegram.repository;

import com.gmail.kirill.ked.telegram.repository.model.City;

public interface CityRepository extends GenericRepository<Long, City> {
    City getByName(String name);
}