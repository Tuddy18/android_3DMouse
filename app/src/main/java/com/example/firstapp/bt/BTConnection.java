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
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class BTConnection implements IBTConnection {
    private BluetoothAdapter blueAdapter;
    private OutputStream outputStream;

    public BTConnection(){
        blueAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    @Override
    public void porcessCommand(Command command) {
        try {
            write(command.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init() throws IOException {
        throw new UnsupportedOperationException();
    }


    public HashMap<String, String> getBluetoothDevices(){
        HashMap<String, String> map = new HashMap<>();
        if (blueAdapter != null) {
            if (blueAdapter.isEnabled()) {
                Set<BluetoothDevice> bondedDevices = blueAdapter.getBondedDevices();

                if(bondedDevices.size() > 0) {
                    for(BluetoothDevice device: bondedDevices){
                        map.put(device.getName(), device.getAddress());
                    }
                }

                Log.e("error", "No appropriate paired devices.");
            } else {
                Log.e("error", "Bluetooth is disabled.");
            }
        }
        return map;
    }

    public void connect(String address) throws IOException {
        if (blueAdapter != null) {
            if (blueAdapter.isEnabled()) {
                Set<BluetoothDevice> bondedDevices = blueAdapter.getBondedDevices();

                if(bondedDevices.size() > 0) {

                    for(BluetoothDevice device: bondedDevices){
                        if(device.getAddress().equals(address)){
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
                            return;
                        }
                    }
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
