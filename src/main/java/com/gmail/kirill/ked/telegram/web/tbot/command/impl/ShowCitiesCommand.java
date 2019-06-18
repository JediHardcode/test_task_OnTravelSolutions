package com.gmail.kirill.ked.telegram.web.tbot.command.impl;

import com.gmail.kirill.ked.telegram.service.CityService;
import com.gmail.kirill.ked.telegram.service.model.city.CityDTO;
import com.gmail.kirill.ked.telegram.web.tbot.command.Command;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component("showCitiesCommand")
public class ShowCitiesCommand implements Command {
    private final CityService cityService;

    public ShowCitiesCommand(CityService cityService) {
        this.cityService = cityService;
    }

    @Override
    public String execute() {
        List<CityDTO> cityDTOS = cityService.getAll();
        List<String> cityNames = getNames(cityDTOS);
        return cityNames.toString();
    }

    private List<String> getNames(List<CityDTO> cityDTOS) {
        return cityDTOS.stream()
                .map(CityDTO::getName)
                .collect(Collectors.toList());
    }
}