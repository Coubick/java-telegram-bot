package com.example.command;

import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.example.command.CommandName.*;

@Component
public class CommandMap {
    private final ImmutableMap<String, Command> commandMap;

    @Autowired
    public CommandMap(
                      StartCommand startCommand,
                      DepCommand depCommand,
                      DeleteAccountCommand deleteAccountCommand,
                      RegisterCommand registerCommand,
                      StatisticsCommand staticsCommand,
                      RatingCommand ratingCommand,
                      RulesCommand rulesCommand,
                      EnableRemoveMessageCommand enableRemoveMessageCommand,
                      TurnOffRemoveMessageCommand turnOffRemoveMessageCommand
                      ) {

        commandMap = ImmutableMap.<String, Command>builder()
                .put(START.getCommandName(), startCommand)
                .put(REGISTER.getCommandName(), registerCommand)
                .put(DEP.getCommandName(), depCommand)
                .put(DELETE.getCommandName(), deleteAccountCommand)
                .put(STAT.getCommandName(), staticsCommand)
                .put(RATING.getCommandName(), ratingCommand)
                .put(RULES.getCommandName(), rulesCommand)
                .put(REMOVE_ON.getCommandName(), enableRemoveMessageCommand)
                .put(REMOVE_OFF.getCommandName(), turnOffRemoveMessageCommand)
                .build();
    }

    public Command retrieveCommand(String commandIdentifier) {
        return commandMap.get(commandIdentifier);
    }

}
