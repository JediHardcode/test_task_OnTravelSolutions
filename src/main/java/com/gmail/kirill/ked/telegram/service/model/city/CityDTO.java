package com.gmail.kirill.ked.telegram.service.model.city;

import com.gmail.kirill.ked.telegram.service.model.attraction.AttractionDTO;
import com.gmail.kirill.ked.telegram.service.validation.UniqueName;
import com.gmail.kirill.ked.telegram.service.validation.ValidAttractions;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.ArrayList;
import java.util.List;

public class CityDTO {
    @Null
    private Long id;
    @NotNull
    @NotEmpty
    @UniqueName
    private String name;
    @ValidAttractions
    private List<AttractionDTO> attractions = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<AttractionDTO> getAttractions() {
        return attractions;
    }

    public void setAttractions(List<AttractionDTO> attractions) {
        this.attractions = attractions;
    }
}