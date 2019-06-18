package com.gmail.kirill.ked.telegram.service.validation.contraint;

import com.gmail.kirill.ked.telegram.service.model.attraction.AttractionDTO;
import com.gmail.kirill.ked.telegram.service.validation.ValidAttractions;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class ValidAttractionsValidator implements ConstraintValidator<ValidAttractions, List<AttractionDTO>> {
    public boolean isValid(List<AttractionDTO> attractionDTOS, ConstraintValidatorContext context) {
        if (attractionDTOS != null) {
            for (AttractionDTO attractionDTO : attractionDTOS) {
                if (!validAttraction(attractionDTO)) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean validAttraction(AttractionDTO attractionDTO) {
        return attractionDTO.getName() != null && !attractionDTO.getName().isEmpty();
    }
}