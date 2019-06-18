package com.gmail.kirill.ked.telegram.service.model.attraction;

import com.gmail.kirill.ked.telegram.service.validation.UniqueAttractionName;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class UpdateAttractionDTO {
    @NotNull
    private Long id;
    @NotNull
    @NotEmpty
    @UniqueAttractionName
    private String name;

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
}