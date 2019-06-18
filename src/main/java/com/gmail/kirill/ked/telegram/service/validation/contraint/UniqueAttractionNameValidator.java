package com.gmail.kirill.ked.telegram.service.validation.contraint;

import com.gmail.kirill.ked.telegram.repository.AttractionRepository;
import com.gmail.kirill.ked.telegram.repository.model.Attraction;
import com.gmail.kirill.ked.telegram.service.validation.UniqueAttractionName;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueAttractionNameValidator implements ConstraintValidator<UniqueAttractionName, String> {
    private final AttractionRepository attractionRepository;

    public UniqueAttractionNameValidator(AttractionRepository attractionRepository) {
        this.attractionRepository = attractionRepository;
    }

    public boolean isValid(String name, ConstraintValidatorContext context) {
        Attraction attraction = attractionRepository.getByName(name);
        return attraction == null;
    }
}