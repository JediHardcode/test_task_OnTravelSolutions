package com.gmail.kirill.ked.telegram.web.tbot.command.impl;

import com.gmail.kirill.ked.telegram.web.tbot.command.Command;
import com.gmail.kirill.ked.telegram.web.tbot.model.enums.CommandEnum;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component("showCommandsCommand")
public class ShowCommandsCommand implements Command {
    @Override
    public String execute() {
        return Arrays.toString(Arrays.stream(CommandEnum.values())
                .map(commandEnum -> "/".concat(commandEnum.toString()))
                .map(String::toLowerCase)
                .toArray());
    }
}