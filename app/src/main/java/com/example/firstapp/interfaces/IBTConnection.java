package com.example.firstapp.interfaces;

import com.example.firstapp.command.Command;

import java.io.IOException;
import java.util.HashMap;

public interface IBTConnection {
    void porcessCommand(Command command);

    void init() throws IOException;

    HashMap<String, String> getBluetoothDevices();

    void connect(String address) throws IOException;
}
