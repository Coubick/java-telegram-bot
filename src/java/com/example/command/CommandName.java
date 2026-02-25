package com.example.command;

public enum CommandName {

    START("/start"),
    HELP("/help"),
    ENTRANCE("/entrance"),
    DEP("/dep"),
    RENAME("/rename"),
    STAT("/my_stat"),
    RATING("/rating"),
    RULES("/rules");

    private final String commandName;

    CommandName(String commandName) {
        this.commandName = commandName;
    }

    public String getCommandName() {
        return commandName;
    }

}