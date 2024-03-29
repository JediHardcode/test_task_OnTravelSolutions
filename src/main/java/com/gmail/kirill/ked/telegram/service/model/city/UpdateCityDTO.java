package com.gmail.kirill.ked.telegram.service.model.city;

import com.gmail.kirill.ked.telegram.service.validation.UniqueName;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class UpdateCityDTO {
    @NotNull
    private Long id;
    @NotNull
    @NotEmpty
    @UniqueName
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