package app.device.com.hardwareapp.Utility;

import java.util.ArrayList;
import java.util.List;

import app.device.com.hardwareapp.Model.Button;
import app.device.com.hardwareapp.Model.Device;

public class Helper {

    public static int getDeviceId(String deviceName, List<Device> devices) {

        for (Device device : devices) {

            if (deviceName.contains(device.getDeviceName())) {

                return device.getDeviceID();

            }

        }
        return 0;
    }


    public static List<String> ReturnAllDeviceName(List<Device> devices) {

        List<String> strings = new ArrayList<>();

        for (Device device : devices) {

            strings.add(device.getDeviceName());

        }

        return strings;
    }


    public static List<String> ReturnRelayNumberFromDeviceId(int deviceId, List<Button> buttons) {

        List<String> relayHolder = new ArrayList<>();

        for (Button button : buttons) {

            if (button.getDeviceID() == deviceId) {

                relayHolder.add(String.valueOf(button.getRelayNo()));
            }
        }

        return relayHolder;
    }
}
