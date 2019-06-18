package com.gmail.kirill.ked.telegram.repository;

import java.util.List;

public interface GenericRepository<I, T> {
    void persist(T entity);

    void remove(T entity);

    void merge(T entity);

    T getById(I id);

    @SuppressWarnings({"unchecked", "rawtypes"})
    List<T> findAll();
}