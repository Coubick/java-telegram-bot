package com.example.command;

public enum CommandName {

    START("/start"),
    REGISTER("/register"),
    DEP("/dep"),
    DELETE("/delete"),
    STAT("/my_stat"),
    RATING("/rating"),
    RULES("/rules"),
    REMOVE_ON("/remove_messages_on"),
    REMOVE_OFF("/remove_messages_off");

    private final String commandName;

    CommandName(String commandName) {
        this.commandName = commandName;
    }

    public String getCommandName() {
        return commandName;
    }

}