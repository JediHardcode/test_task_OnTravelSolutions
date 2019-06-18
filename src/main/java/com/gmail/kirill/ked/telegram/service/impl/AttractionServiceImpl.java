package com.gmail.kirill.ked.telegram.service.impl;

import com.gmail.kirill.ked.telegram.repository.AttractionRepository;
import com.gmail.kirill.ked.telegram.repository.model.Attraction;
import com.gmail.kirill.ked.telegram.service.AttractionService;
import com.gmail.kirill.ked.telegram.service.exception.AttractionException;
import com.gmail.kirill.ked.telegram.service.model.attraction.UpdateAttractionDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class AttractionServiceImpl implements AttractionService {
    private final static Logger logger = LoggerFactory.getLogger(AttractionServiceImpl.class);
    private final AttractionRepository attractionRepository;

    public AttractionServiceImpl(AttractionRepository attractionRepository) {
        this.attractionRepository = attractionRepository;
    }

    @Override
    @Transactional
    public void delete(Long id) throws AttractionException {
        Attraction attraction = attractionRepository.getById(id);
        if (attraction == null) {
            logger.info("not found attraction with id {}", id);
            throw new AttractionException("not found attraction with id ".concat(String.valueOf(id)));
        }
        attractionRepository.remove(attraction);
        logger.info("attraction with name {} successfully deleted ", attraction.getName());
    }

    @Override
    @Transactional
    public void update(UpdateAttractionDTO updateDTO) throws AttractionException {
        Attraction attraction = attractionRepository.getById(updateDTO.getId());
        if (attraction == null) {
            logger.info("not found attraction with id {}", updateDTO.getId());
            throw new AttractionException("not found attraction with id ".concat(String.valueOf(updateDTO.getId())));
        }
        attraction.setName(updateDTO.getName());
        attractionRepository.merge(attraction);
    }
}