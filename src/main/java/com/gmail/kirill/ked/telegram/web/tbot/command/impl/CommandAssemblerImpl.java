package com.gmail.kirill.ked.telegram.web.tbot.command.impl;

import com.gmail.kirill.ked.telegram.web.tbot.command.Command;
import com.gmail.kirill.ked.telegram.web.tbot.command.CommandAssembler;
import com.gmail.kirill.ked.telegram.web.tbot.model.enums.CommandEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Component
public class CommandAssemblerImpl implements CommandAssembler {
    private static final Logger logger = LoggerFactory.getLogger(CommandAssemblerImpl.class);
    private static final Map<CommandEnum, Command> commands = new HashMap<>();
    private final Command showCitiesCommand;
    private final Command showCommandsCommand;
    private final Command startCommand;

    public CommandAssemblerImpl(@Qualifier("showCitiesCommand") Command showCitiesCommand,
                                @Qualifier("showCommandsCommand") Command showCommandsCommand,
                                @Qualifier("startCommand") Command startCommand) {
        this.showCitiesCommand = showCitiesCommand;
        this.showCommandsCommand = showCommandsCommand;
        this.startCommand = startCommand;
    }

    @Override
    public Command getCommand(String message) {
        return commands.get(transferToCommandEnum(message));
    }

    @PostConstruct
    public void init() {
        commands.put(CommandEnum.SHOWCITIES, showCitiesCommand);
        commands.put(CommandEnum.SHOWCOMMANDS, showCommandsCommand);
        commands.put(CommandEnum.START, startCommand);
    }

    private CommandEnum transferToCommandEnum(String message) {
        String commandMessage = message.replace("/", "");
        try {
            return CommandEnum.valueOf(commandMessage.toUpperCase());
        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }
}