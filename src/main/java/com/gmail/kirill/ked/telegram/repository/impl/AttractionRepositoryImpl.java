package com.gmail.kirill.ked.telegram.repository.impl;

import com.gmail.kirill.ked.telegram.repository.AttractionRepository;
import com.gmail.kirill.ked.telegram.repository.model.Attraction;
import org.springframework.stereotype.Repository;

@Repository
public class AttractionRepositoryImpl extends GenericRepositoryImpl<Long, Attraction> implements AttractionRepository {
}