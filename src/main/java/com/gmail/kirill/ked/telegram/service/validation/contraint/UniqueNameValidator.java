package com.gmail.kirill.ked.telegram.service.validation.contraint;

import com.gmail.kirill.ked.telegram.service.CityService;
import com.gmail.kirill.ked.telegram.service.model.city.CityDTO;
import com.gmail.kirill.ked.telegram.service.validation.UniqueName;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueNameValidator implements ConstraintValidator<UniqueName, String> {
    private final CityService cityService;

    public UniqueNameValidator(CityService cityService) {
        this.cityService = cityService;
    }

    public boolean isValid(String name, ConstraintValidatorContext context) {
        CityDTO cityDTO = cityService.getByName(name);
        return cityDTO == null;
    }
}