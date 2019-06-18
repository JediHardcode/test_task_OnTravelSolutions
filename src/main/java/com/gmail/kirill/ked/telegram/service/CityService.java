package com.gmail.kirill.ked.telegram.service;

import com.gmail.kirill.ked.telegram.service.exception.CityServiceException;
import com.gmail.kirill.ked.telegram.service.model.attraction.AttractionDTO;
import com.gmail.kirill.ked.telegram.service.model.city.CityDTO;
import com.gmail.kirill.ked.telegram.service.model.city.UpdateCityDTO;

import java.util.List;

public interface CityService {
    void addCity(CityDTO city);

    CityDTO getByName(String name);

    void updateCity(UpdateCityDTO updateCityDTO) throws CityServiceException;

    void deleteCity(Long id) throws CityServiceException;

    List<CityDTO> getAll();

    void addAttraction(AttractionDTO attractionDTO, Long cityId) throws CityServiceException;
}