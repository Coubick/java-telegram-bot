package com.example.command;

import com.example.service.SendBotMessageService;
import com.google.common.collect.ImmutableMap;

import static com.example.command.CommandName.*;

public class CommandMap {
    private final ImmutableMap<String, Command> commandMap;

    public CommandMap(SendBotMessageService sendBotMessageService) {

        commandMap = ImmutableMap.<String, Command>builder()
                .put(START.getCommandName(), new StartCommand(sendBotMessageService))
                .put(HELP.getCommandName(), new HelpCommand(sendBotMessageService))
                .build();
    }

    public Command retrieveCommand(String commandIdentifier) {
        return commandMap.get(commandIdentifier);
    }

}
