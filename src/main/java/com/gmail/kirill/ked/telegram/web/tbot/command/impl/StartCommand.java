package com.gmail.kirill.ked.telegram.web.tbot.command.impl;

import com.gmail.kirill.ked.telegram.web.tbot.command.Command;
import org.springframework.stereotype.Component;

@Component("startCommand")
public class StartCommand implements Command {
    @Override
    public String execute() {
        return "Hello, my name is City Guide bot, type some city and I will give you useless information about it";
    }
}