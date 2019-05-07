package com.example.firstapp.interfaces;

import com.example.firstapp.command.Command;

public interface ICommandExecutor {
    void execute(Command command);
}
