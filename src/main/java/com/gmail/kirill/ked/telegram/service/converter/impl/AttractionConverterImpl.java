package com.gmail.kirill.ked.telegram.service.converter.impl;

import com.gmail.kirill.ked.telegram.repository.model.Attraction;
import com.gmail.kirill.ked.telegram.service.converter.Converter;
import com.gmail.kirill.ked.telegram.service.model.attraction.AttractionDTO;
import org.springframework.stereotype.Component;

@Component("attractionConverter")
public class AttractionConverterImpl implements Converter<AttractionDTO, Attraction> {
    @Override
    public AttractionDTO toDTO(Attraction attraction) {
        AttractionDTO attractionDTO = new AttractionDTO();
        attractionDTO.setId(attraction.getId());
        attractionDTO.setName(attraction.getName());
        return attractionDTO;
    }

    @Override
    public Attraction toEntity(AttractionDTO attractionDTO) {
        Attraction attraction = new Attraction();
        attraction.setName(attractionDTO.getName());
        return attraction;
    }
}