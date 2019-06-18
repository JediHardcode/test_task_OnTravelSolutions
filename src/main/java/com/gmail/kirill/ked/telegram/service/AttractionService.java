package com.gmail.kirill.ked.telegram.service;

import com.gmail.kirill.ked.telegram.service.exception.AttractionException;
import com.gmail.kirill.ked.telegram.service.model.attraction.UpdateAttractionDTO;

public interface AttractionService {
    void delete(Long id) throws AttractionException;

    void update(UpdateAttractionDTO updateDTO) throws AttractionException;
}