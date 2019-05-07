package com.example.firstapp.interfaces;

import com.example.firstapp.command.Command;

import java.io.IOException;

public interface IBTConnection {
    void porcessCommand(Command command);

    void init() throws IOException;
}
