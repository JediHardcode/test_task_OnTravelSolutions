package com.gmail.kirill.ked.telegram.web.controller;

import com.gmail.kirill.ked.telegram.service.CityService;
import com.gmail.kirill.ked.telegram.service.exception.CityServiceException;
import com.gmail.kirill.ked.telegram.service.model.attraction.AttractionDTO;
import com.gmail.kirill.ked.telegram.service.model.city.CityDTO;
import com.gmail.kirill.ked.telegram.service.model.city.UpdateCityDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ApiCityController {
    private final static Logger logger = LoggerFactory.getLogger(ApiCityController.class);
    private final CityService cityService;

    public ApiCityController(CityService cityService) {
        this.cityService = cityService;
    }

    @PostMapping("/city")
    public ResponseEntity addCity(@RequestBody @Valid CityDTO city,
                                  BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            logger.info(" cityDTO nov valid cause : {}", Arrays.toString(bindingResult.getAllErrors().toArray()));
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        cityService.addCity(city);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PatchMapping("/city")
    public ResponseEntity updateCity(@RequestBody @Valid UpdateCityDTO updateCityDTO,
                                     BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            logger.info("update dto not valid cause {}", Arrays.toString(bindingResult.getAllErrors().toArray()));
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        try {
            cityService.updateCity(updateCityDTO);
        } catch (CityServiceException e) {
            logger.error(e.getMessage(), e);
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/city/{id}")
    public ResponseEntity deleteCity(@PathVariable(name = "id") Long id) {
        try {
            cityService.deleteCity(id);
            return new ResponseEntity(HttpStatus.OK);
        } catch (CityServiceException e) {
            logger.error(e.getMessage(), e);
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/cities")
    public ResponseEntity<List<CityDTO>> getCities() {
        List<CityDTO> cities = cityService.getAll();
        return new ResponseEntity<>(cities, HttpStatus.OK);
    }

    @PostMapping("/city/attraction")
    public ResponseEntity addAttraction(@RequestBody @Valid AttractionDTO attractionDTO,
                                        BindingResult bindingResult,
                                        @RequestParam(name = "cityId") Long id) {
        if (bindingResult.hasErrors()) {
            logger.info("attraction not valid cause {}", Arrays.toString(bindingResult.getAllErrors().toArray()));
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        try {
            cityService.addAttraction(attractionDTO, id);
            return new ResponseEntity(HttpStatus.CREATED);
        } catch (CityServiceException e) {
            logger.error(e.getMessage(), e);
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }
}