package com.example.firstapp.pointer;

import com.example.firstapp.command.Command;
import com.example.firstapp.command.CommandType;
import com.example.firstapp.interfaces.IBufferManager;
import com.example.firstapp.interfaces.IPointerRecorder;
import com.example.firstapp.interfaces.IPointerSettings;

public class PointerRecorder implements IPointerRecorder {
    private boolean drawing;
    private boolean moving;
    private IPointerSettings pointerSettings;
    private IBufferManager bufferManager;

    public PointerRecorder(IPointerSettings pointerSettings, IBufferManager bufferManager) {
        this.pointerSettings = pointerSettings;
        this.bufferManager = bufferManager;
    }

    @Override
    public void recordPosition(double x, double y, double z) {
        String color = pointerSettings.getColor();

        if(isDrawing()){
            Command command = createCommand(x, y, z, CommandType.DRAW, color);
            bufferManager.addCommand(command);
        }
        if(isMoving()){
            Command command = createCommand(x, y, z, CommandType.MOVE, color);
            bufferManager.addCommand(command);
        }
    }

    @Override
    public boolean isDrawing() {
        return drawing;
    }

    @Override
    public boolean isMoving() {
        return moving;
    }

    @Override
    public void setIsDrawing(boolean value) {
        this.drawing = value;
    }

    @Override
    public void setIsMoving(boolean value) {
        this.moving = value;
    }

    @Override
    public void reposition() {
        Command command = createCommand(0, 0, 0, CommandType.REPOSITION, "");
        bufferManager.addCommand(command);
    }

    @Override
    public Position getCurrentPosition() {
        return null;
    }

    protected Command createCommand(double x, double y, double z, CommandType type, String color){
        Position p = new Position(x, y, z);
        Command command = new Command(p, type, color);

        return command;
    }
}
