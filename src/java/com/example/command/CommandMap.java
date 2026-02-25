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
                      HelpCommand helpCommand,
                      DepCommand depCommand,
                      EntranceCommand entranceCommand,
                      RenameCommand renameCommand,
                      StatisticsCommand staticsCommand,
                      RatingCommand ratingCommand,
                      RulesCommand rulesCommand
                      ) {

        commandMap = ImmutableMap.<String, Command>builder()
                .put(START.getCommandName(), startCommand)
                .put(HELP.getCommandName(), helpCommand)
                .put(ENTRANCE.getCommandName(), entranceCommand)
                .put(DEP.getCommandName(), depCommand)
                .put(RENAME.getCommandName(), renameCommand)
                .put(STAT.getCommandName(), staticsCommand)
                .put(RATING.getCommandName(), ratingCommand)
                .put(RULES.getCommandName(), rulesCommand)
                .build();
    }

    public Command retrieveCommand(String commandIdentifier) {
        return commandMap.get(commandIdentifier);
    }

}
