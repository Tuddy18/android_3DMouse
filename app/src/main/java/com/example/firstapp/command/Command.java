package com.example.firstapp.command;

import com.example.firstapp.pointer.Position;

public class Command {
    private Position position;
    private CommandType commandType;
    private String color;

    public Command(Position position, CommandType commandType) {
        this.position = position;
        this.commandType = commandType;
    }

    public Command(Position position, CommandType commandType, String color) {
        this.position = position;
        this.commandType = commandType;
        this.color = color;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public CommandType getCommandType() {
        return commandType;
    }

    public void setCommandType(CommandType commandType) {
        this.commandType = commandType;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "Command{" +
                "position=" + position +
                ", commandType=" + commandType +
                ", color='" + color + '\'' +
                '}';
    }
}
