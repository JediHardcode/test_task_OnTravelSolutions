package com.gmail.kirill.ked.telegram.service.converter.impl;

import com.gmail.kirill.ked.telegram.repository.model.Attraction;
import com.gmail.kirill.ked.telegram.repository.model.City;
import com.gmail.kirill.ked.telegram.service.converter.Converter;
import com.gmail.kirill.ked.telegram.service.model.attraction.AttractionDTO;
import com.gmail.kirill.ked.telegram.service.model.city.CityDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component("cityConverter")
public class CityConverterImpl implements Converter<CityDTO, City> {
    private final Converter<AttractionDTO, Attraction> attractionConverter;

    public CityConverterImpl(@Qualifier("attractionConverter") Converter<AttractionDTO, Attraction> attractionConverter) {
        this.attractionConverter = attractionConverter;
    }

    @Override
    public CityDTO toDTO(City city) {
        CityDTO cityDTO = new CityDTO();
        cityDTO.setId(city.getId());
        cityDTO.setName(city.getName());
        cityDTO.setAttractions(convertAttractionsDTO(city.getAttractions()));
        return cityDTO;
    }

    @Override
    public City toEntity(CityDTO cityDTO) {
        City city = new City();
        city.setName(cityDTO.getName());
        if (cityDTO.getAttractions() != null) {
            city.setAttractions(convertAttractions(cityDTO.getAttractions()));
        }
        return city;
    }

    private List<Attraction> convertAttractions(List<AttractionDTO> attractions) {
        return attractions.stream()
                .map(attractionConverter::toEntity)
                .collect(Collectors.toList());
    }

    private List<AttractionDTO> convertAttractionsDTO(List<Attraction> attractions) {
        return attractions.stream()
                .map(attractionConverter::toDTO)
                .collect(Collectors.toList());
    }
}