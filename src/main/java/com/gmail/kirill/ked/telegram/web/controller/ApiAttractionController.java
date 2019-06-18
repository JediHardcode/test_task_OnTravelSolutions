package com.gmail.kirill.ked.telegram.web.controller;

import com.gmail.kirill.ked.telegram.service.AttractionService;
import com.gmail.kirill.ked.telegram.service.exception.AttractionException;
import com.gmail.kirill.ked.telegram.service.model.attraction.UpdateAttractionDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Arrays;

@RestController
@RequestMapping("/api/v1")
public class ApiAttractionController {
    private final static Logger logger = LoggerFactory.getLogger(ApiAttractionController.class);
    private final AttractionService attractionService;

    public ApiAttractionController(AttractionService attractionService) {
        this.attractionService = attractionService;
    }

    @DeleteMapping("/attraction/{id}")
    public ResponseEntity deleteAttraction(@PathVariable(name = "id") Long id) {
        try {
            attractionService.delete(id);
            return new ResponseEntity(HttpStatus.OK);
        } catch (AttractionException e) {
            logger.error(e.getMessage(), e);
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/attraction")
    public ResponseEntity updateAttraction(@RequestBody @Valid UpdateAttractionDTO updateAttractionDTO,
                                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            logger.info("dto not valid cause {}", Arrays.toString(bindingResult.getAllErrors().toArray()));
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        try {
            attractionService.update(updateAttractionDTO);
            return new ResponseEntity(HttpStatus.OK);
        } catch (AttractionException e) {
            logger.error(e.getMessage(), e);
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }
}