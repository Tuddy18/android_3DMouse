package com.example.firstapp.interfaces;

import com.example.firstapp.command.Command;
import com.example.firstapp.execution.BufferManager;

import java.util.Queue;

public interface IBufferManager {
    void addCommand(Command command);

    Queue<Command> getBuffer();

    Object getBufferLock();
}
