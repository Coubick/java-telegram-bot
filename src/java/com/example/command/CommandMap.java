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
                      RegisterCommand registerCommand,
                      StatisticsCommand staticsCommand,
                      RatingCommand ratingCommand,
                      RulesCommand rulesCommand
                      ) {

        commandMap = ImmutableMap.<String, Command>builder()
                .put(START.getCommandName(), startCommand)
                .put(REGISTER.getCommandName(), registerCommand)
                .put(DEP.getCommandName(), depCommand)
                .put(STAT.getCommandName(), staticsCommand)
                .put(RATING.getCommandName(), ratingCommand)
                .put(RULES.getCommandName(), rulesCommand)
                .build();
    }

    public Command retrieveCommand(String commandIdentifier) {
        return commandMap.get(commandIdentifier);
    }

}
