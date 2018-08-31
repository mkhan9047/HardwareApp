package app.device.com.hardwareapp.Model;

public class Device {

    private String deviceName;
    private String devicePhone;
    private int deviceID;

    public Device(String deviceName, String devicePhone, int deviceID) {
        this.deviceName = deviceName;
        this.devicePhone = devicePhone;
        this.deviceID = deviceID;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public String getDevicePhone() {
        return devicePhone;
    }

    public int getDeviceID() {
        return deviceID;
    }


}
