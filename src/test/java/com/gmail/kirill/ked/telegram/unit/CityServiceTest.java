package com.gmail.kirill.ked.telegram.unit;

import com.gmail.kirill.ked.telegram.repository.CityRepository;
import com.gmail.kirill.ked.telegram.repository.model.Attraction;
import com.gmail.kirill.ked.telegram.repository.model.City;
import com.gmail.kirill.ked.telegram.service.CityService;
import com.gmail.kirill.ked.telegram.service.converter.Converter;
import com.gmail.kirill.ked.telegram.service.exception.CityServiceException;
import com.gmail.kirill.ked.telegram.service.impl.CityServiceImpl;
import com.gmail.kirill.ked.telegram.service.model.attraction.AttractionDTO;
import com.gmail.kirill.ked.telegram.service.model.city.CityDTO;
import com.gmail.kirill.ked.telegram.service.model.city.UpdateCityDTO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collections;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
public class CityServiceTest {
    private CityService cityService;
    @Mock
    private CityRepository cityRepository;
    @Mock
    private Converter<CityDTO, City> cityConverter;
    @Mock
    private Converter<AttractionDTO, Attraction> attractionConverter;

    @Before
    public void init() {
        cityService = new CityServiceImpl(cityRepository, cityConverter, attractionConverter);
    }

    @Test
    public void shouldReturnNullIfCityDoesntExistInDatabase() {
        String cityName = "test";
        Mockito.when(cityRepository.getByName(cityName)).thenReturn(null);
        CityDTO cityDTO = cityService.getByName(cityName);
        Assert.assertNull(cityDTO);
    }

    @Test
    public void shouldReturnCityIfItExistInDatabase() {
        String cityName = "test";
        City city = new City();
        city.setName(cityName);
        city.setId(1L);
        CityDTO cityDTO = new CityDTO();
        cityDTO.setName(cityName);
        cityDTO.setId(1L);
        Mockito.when(cityRepository.getByName(cityName)).thenReturn(city);
        Mockito.when(cityConverter.toDTO(city)).thenReturn(cityDTO);
        CityDTO foundCity = cityService.getByName(cityName);
        Assert.assertNotNull(foundCity);
    }

    @Test(expected = CityServiceException.class)
    public void shouldThrowCityServiceExceptionIfCityDoesntExistInDB() throws CityServiceException {
        UpdateCityDTO updateCityDTO = new UpdateCityDTO();
        updateCityDTO.setId(1L);
        Mockito.when(cityRepository.getById(updateCityDTO.getId())).thenReturn(null);
        cityService.updateCity(updateCityDTO);
    }

    @Test(expected = CityServiceException.class)
    public void shouldThrowCityServiceExceptionIfCityDoestExistInDatabaseWhenTryToDelete() throws CityServiceException {
        Long id = 1L;
        Mockito.when(cityRepository.getById(id)).thenReturn(null);
        cityService.deleteCity(id);
    }

    @Test
    public void shouldReturnEmptyListIfNoCitiesInDatabase() {
        Mockito.when(cityRepository.findAll()).thenReturn(Collections.emptyList());
        List<CityDTO> cityDTOList = cityService.getAll();
        Assert.assertNotNull(cityDTOList);
        Assert.assertEquals(0, cityDTOList.size());
    }
}