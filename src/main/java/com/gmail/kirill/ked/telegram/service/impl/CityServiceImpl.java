package com.gmail.kirill.ked.telegram.service.impl;

import com.gmail.kirill.ked.telegram.repository.CityRepository;
import com.gmail.kirill.ked.telegram.repository.model.Attraction;
import com.gmail.kirill.ked.telegram.repository.model.City;
import com.gmail.kirill.ked.telegram.service.CityService;
import com.gmail.kirill.ked.telegram.service.converter.Converter;
import com.gmail.kirill.ked.telegram.service.exception.CityServiceException;
import com.gmail.kirill.ked.telegram.service.model.attraction.AttractionDTO;
import com.gmail.kirill.ked.telegram.service.model.city.CityDTO;
import com.gmail.kirill.ked.telegram.service.model.city.UpdateCityDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CityServiceImpl implements CityService {
    private final static Logger logger = LoggerFactory.getLogger(CityServiceImpl.class);
    private final static String ERROR_MESSAGE = "city with id {} doesnt exist in database";
    private final CityRepository cityRepository;
    private final Converter<CityDTO, City> cityConverter;
    private final Converter<AttractionDTO, Attraction> attractionConverter;

    public CityServiceImpl(CityRepository cityRepository,
                           @Qualifier("cityConverter") Converter<CityDTO, City> cityConverter,
                           @Qualifier("attractionConverter") Converter<AttractionDTO, Attraction> attractionConverter) {
        this.cityRepository = cityRepository;
        this.cityConverter = cityConverter;
        this.attractionConverter = attractionConverter;
    }

    @Override
    @Transactional
    public void addCity(CityDTO city) {
        City savedCity = cityConverter.toEntity(city);
        cityRepository.persist(savedCity);
        logger.info("city with name {} successfully saved in database", city.getName());
    }

    @Override
    @Transactional
    public CityDTO getByName(String name) {
        City city = cityRepository.getByName(name);
        if (city == null) {
            logger.info("not found city with this name {}", name);
            return null;
        } else {
            logger.info("city with name {} found", name);
            return cityConverter.toDTO(city);
        }
    }

    @Override
    @Transactional
    public void updateCity(UpdateCityDTO updateCityDTO) throws CityServiceException {
        City city = cityRepository.getById(updateCityDTO.getId());
        if (city == null) {
            logger.info(ERROR_MESSAGE, updateCityDTO.getId());
            throw new CityServiceException("not found city with current id");
        }
        city.setName(updateCityDTO.getName());
        cityRepository.merge(city);
        logger.info(" update successfully, city with id {} have name {}", city.getId(), city.getName());
    }

    @Override
    @Transactional
    public void deleteCity(Long id) throws CityServiceException {
        City city = cityRepository.getById(id);
        if (city == null) {
            logger.info(ERROR_MESSAGE, id);
            throw new CityServiceException("not found city with current id");
        }
        cityRepository.remove(city);
        logger.info("city with id {} deleted from database ", id);
    }

    @Override
    @Transactional
    public List<CityDTO> getAll() {
        List<City> cityList = cityRepository.findAll();
        logger.info("count of founded cities is {}", cityList.size());
        return getListOfDTOs(cityList);
    }

    @Override
    @Transactional
    public void addAttraction(AttractionDTO attractionDTO, Long cityId) throws CityServiceException {
        City city = cityRepository.getById(cityId);
        if (city == null) {
            logger.info(ERROR_MESSAGE, cityId);
            throw new CityServiceException("not found city with current id");
        }
        city.getAttractions().add(attractionConverter.toEntity(attractionDTO));
        cityRepository.merge(city);
    }

    private List<CityDTO> getListOfDTOs(List<City> cityList) {
        return cityList.stream()
                .map(cityConverter::toDTO)
                .collect(Collectors.toList());
    }
}