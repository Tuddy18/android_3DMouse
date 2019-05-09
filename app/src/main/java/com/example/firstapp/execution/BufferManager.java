package com.example.firstapp.execution;

import com.example.firstapp.command.Command;
import com.example.firstapp.interfaces.IBufferManager;

import java.util.ArrayDeque;
import java.util.Queue;

public class BufferManager implements IBufferManager {
    private Queue<Command> buffer;
    private Object lock;
    private static IBufferManager instance;

    private BufferManager(){
        buffer = new ArrayDeque<>();
    }

    public static IBufferManager getInstance(){
        if(instance == null){
            instance = new BufferManager();
        }
        return instance;
    }

    @Override
    public void addCommand(Command command) {
        synchronized (lock){
            buffer.offer(command);
        }
    }

    @Override
    public Queue<Command> getBuffer() {
        return buffer;
    }

    @Override
    public Object getBufferLock() {
        if(lock == null){
            lock = new Object();
        }
        return lock;
    }
}
