package com.gmail.kirill.ked.telegram.service.validation.contraint;

import com.gmail.kirill.ked.telegram.repository.AttractionRepository;
import com.gmail.kirill.ked.telegram.repository.model.Attraction;
import com.gmail.kirill.ked.telegram.service.model.attraction.AttractionDTO;
import com.gmail.kirill.ked.telegram.service.validation.ValidAttractions;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class ValidAttractionsValidator implements ConstraintValidator<ValidAttractions, List<AttractionDTO>> {
    private final AttractionRepository attractionRepository;

    public ValidAttractionsValidator(AttractionRepository attractionRepository) {
        this.attractionRepository = attractionRepository;
    }

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
        if (attractionDTO.getName() == null || attractionDTO.getName().isEmpty()) {
            return false;
        }
        Attraction attraction = attractionRepository.getByName(attractionDTO.getName());
        return attraction == null;
    }
}