package app.device.com.hardwareapp.Model;

public class Button {

    private int deviceID;
    private String buttonName;
    private String onButtonCode;
    private String ofButtonCode;
    private String type;
    private int relayNo;
    private int status;

    public Button(int deviceID, String buttonName, String onButtonCode, String ofButtonCode, int relayNo, int status , String type) {
        this.deviceID = deviceID;
        this.buttonName = buttonName;
        this.onButtonCode = onButtonCode;
        this.ofButtonCode = ofButtonCode;
        this.relayNo = relayNo;
        this.status = status;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public int getDeviceID() {
        return deviceID;
    }

    public String getButtonName() {
        return buttonName;
    }

    public String getOnButtonCode() {
        return onButtonCode;
    }

    public String getOfButtonCode() {
        return ofButtonCode;
    }

    public int getRelayNo() {
        return relayNo;
    }

    public int getStatus() {
        return status;
    }
}
