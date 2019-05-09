package com.example.firstapp.bt;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.ParcelUuid;
import android.util.Log;

import com.example.firstapp.command.Command;
import com.example.firstapp.interfaces.IBTConnection;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Set;

public class BTConnection implements IBTConnection {
    @Override
    public void porcessCommand(Command command) {
        try {
            write(command.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private OutputStream outputStream;

    public void init() throws IOException {
        BluetoothAdapter blueAdapter = BluetoothAdapter.getDefaultAdapter();
        if (blueAdapter != null) {
            if (blueAdapter.isEnabled()) {
                Set<BluetoothDevice> bondedDevices = blueAdapter.getBondedDevices();

                if(bondedDevices.size() > 0) {
                    Object[] devices = (Object []) bondedDevices.toArray();
                    BluetoothDevice device = (BluetoothDevice) devices[0]; //First device i guess...
                    ParcelUuid[] uuids = device.getUuids();
//                    BluetoothSocket socket = device.createRfcommSocketToServiceRecord(uuids[0].getUuid());
                    BluetoothSocket socket = null;
                    try {
                        socket = (BluetoothSocket) device.getClass().getMethod("createRfcommSocket", new Class[] {int.class}).invoke(device,8);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                    socket.connect();
                    outputStream = socket.getOutputStream();
                }

                Log.e("error", "No appropriate paired devices.");
            } else {
                Log.e("error", "Bluetooth is disabled.");
            }
        }
    }

    public void write(String s) throws IOException {
        outputStream.write(s.getBytes());
    }
}
