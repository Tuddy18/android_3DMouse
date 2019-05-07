package com.example.firstapp.execution;

import com.example.firstapp.command.Command;
import com.example.firstapp.interfaces.ICommandExecutor;


public class CommandReceiver {
    private static CommandReceiver instance;
    private ICommandExecutor executor;
    private Thread thread;
    private boolean stop= false;

    private CommandReceiver(){
    }

    public static CommandReceiver getInstance(){
        if(instance == null){
            instance = new CommandReceiver();
        }
        return  instance;
    }

    public void setExecutor(ICommandExecutor executor2){
        executor = executor2;
    }

    public void start(){
        if(executor != null){
            thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while(!stop){
                        synchronized (BufferManager.getInstance().getBufferLock()){
                            Command command = BufferManager.getInstance().getBuffer().poll();
                            executor.execute(command);
                        }
                    }
                }
            });
            thread.start();
        }
        else{
            throw new NullPointerException("Executor not ninitialized");
        }
    }

    public void stop(){
        stop = true;
    }
}
