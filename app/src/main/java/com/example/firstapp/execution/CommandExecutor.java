package com.example.firstapp.execution;

import com.example.firstapp.command.Command;
import com.example.firstapp.interfaces.IBTConnection;
import com.example.firstapp.interfaces.ICommandExecutor;

import java.io.IOException;

public class CommandExecutor implements ICommandExecutor {
    private IBTConnection btConnection;

    public CommandExecutor(IBTConnection btConnection) {
        this.btConnection = btConnection;
        try {
            btConnection.init();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void execute(Command command) {
        btConnection.porcessCommand(command);
    }
}
