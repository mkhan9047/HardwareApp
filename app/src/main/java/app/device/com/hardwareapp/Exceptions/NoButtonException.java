package app.device.com.hardwareapp.Exceptions;

public  class NoButtonException extends Exception {
    @Override
    public String getMessage() {
        return "No Button In This Relay!";

    }
}
