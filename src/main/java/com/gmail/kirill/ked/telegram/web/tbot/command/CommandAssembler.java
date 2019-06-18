package com.gmail.kirill.ked.telegram.web.tbot.command;

public interface CommandAssembler {
    Command getCommand(String message);
}