package app.device.com.hardwareapp.LocalDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import app.device.com.hardwareapp.Model.Button;
import app.device.com.hardwareapp.Model.Device;
import app.device.com.hardwareapp.Exceptions.NoButtonException;

public class DatabaseOperation {


    public static boolean AddDevice(Context context, String DeviceName, String phoneNumber) {

        boolean saveSuccess = false;
        ContentValues contentValues = new ContentValues();
        contentValues.put("device_name", DeviceName);
        contentValues.put("phone_number", phoneNumber);


        try {
            SQLiteOpenHelper helper = new DatabaseHelper(context);

            SQLiteDatabase database = helper.getWritableDatabase();

            database.insertOrThrow("Device", null, contentValues);

            saveSuccess = true;

        } catch (SQLiteConstraintException e) {


            if (e.getMessage().contains("UNIQUE") && e.getMessage().contains("phone_number")) {

                Toast.makeText(context, "Phone Number Used, Try new one!", Toast.LENGTH_SHORT).show();
                saveSuccess = false;

            } else if (e.getMessage().contains("UNIQUE") && e.getMessage().contains("device_name")) {

                Toast.makeText(context, "Device Name Used, Try new one!", Toast.LENGTH_SHORT).show();
                saveSuccess = false;

            }

        }

        return saveSuccess;


    }


    public static boolean AddButton(Context context, int deviceId, int relay_no, String btn_name, String onCode, String offCode, String btnType, int status) {

        boolean saveSuccess = false;

        ContentValues contentValues = new ContentValues();
        contentValues.put("device_id", deviceId);
        contentValues.put("relay_no", relay_no);
        contentValues.put("btn_name", btn_name);
        contentValues.put("ON_CODE", onCode);
        contentValues.put("OFF_CODE", offCode);
        contentValues.put("btn_type", btnType);
        contentValues.put("status", status);


        try {

            SQLiteOpenHelper helper = new DatabaseHelper(context);
            SQLiteDatabase database = helper.getWritableDatabase();
            database.insertOrThrow("button", null, contentValues);
            saveSuccess = true;

        } catch (SQLiteException e) {

            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.e("databaseError", e.getMessage());
            saveSuccess = false;

        }


        return saveSuccess;
    }

    public static List<Device> returnAllDevices(Context context) {

        List<Device> devices = new ArrayList<>();

        Cursor cursor = null;

        try {
            SQLiteOpenHelper helper = new DatabaseHelper(context);
            SQLiteDatabase database = helper.getReadableDatabase();

            cursor = database.query("Device", new String[]{"device_name", "phone_number", "device_id"}, null, null, null, null, null, null);

            while (cursor.moveToNext()) {

                devices.add(new Device(cursor.getString(0), cursor.getString(1), cursor.getInt(2)));

            }

        } catch (SQLiteException e) {

            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();

        }
        if (cursor != null) {

            cursor.close();

        }

        return devices;
    }

    public static boolean isDeviceNull(Context context) {
        Cursor cursor = null;

        try {

            SQLiteOpenHelper helper = new DatabaseHelper(context);
            SQLiteDatabase database = helper.getReadableDatabase();

            cursor = database.query("Device", new String[]{"device_name", "phone_number", "device_id"}, null, null, null, null, null, null);

            if (cursor.getCount() == 0) {
                return true;
            }

        } catch (SQLiteException e) {

            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();

        }
        if (cursor != null) {

            cursor.close();

        }

        return false;
    }

    public static Device getDeviceByID(int id, Context context) {

        Device device = null;
        Cursor cursor = null;
        try {

            SQLiteOpenHelper helper = new DatabaseHelper(context);
            SQLiteDatabase database = helper.getReadableDatabase();

            cursor = database.query("Device", new String[]{"device_name", "phone_number", "device_id"}, "device_id=?", new String[]{String.valueOf(id)}, null, null, null, null);

            cursor.moveToFirst();

            device = new Device(cursor.getString(0), cursor.getString(1), cursor.getInt(2));

        } catch (SQLiteException e) {

            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();

        }

        if (cursor != null) {

            cursor.close();

        }

        return device;

    }


    public static List<Button> getAllButtons(Context context) {
        List<Button> buttons = new ArrayList<>();
        Cursor cursor = null;
        try {

            SQLiteOpenHelper helper = new DatabaseHelper(context);
            SQLiteDatabase database = helper.getReadableDatabase();
            cursor = database.query("button", new String[]{"device_id", "relay_no", "btn_name", "ON_CODE", "OFF_CODE", "btn_type", "status"}, null, null,
                    null, null, null
            );

            while (cursor.moveToNext()) {
                buttons.add(new Button(cursor.getInt(0), cursor.getString(2), cursor.getString(3), cursor.getString(4),
                        cursor.getInt(1), cursor.getInt(6), cursor.getString(5)
                ));
            }


        } catch (SQLiteException e) {
            Log.e("button_error", e.getMessage());
        }

        if (cursor != null) {
            cursor.close();
        }

        return buttons;
    }


    public static Button getButtonByIdAndRelay(Context context, int device_id, int relay) throws NoButtonException {

        Button buttons = null;
        Cursor cursor;

        SQLiteOpenHelper helper = new DatabaseHelper(context);

        SQLiteDatabase database = helper.getReadableDatabase();


        cursor = database.query("button", new String[]{"device_id", "relay_no", "btn_name", "ON_CODE", "OFF_CODE", "btn_type", "status"}, "device_id=? AND relay_no=?", new String[]{String.valueOf(device_id), String.valueOf(relay)},
                null, null, null
        );

        if (cursor != null && cursor.moveToFirst()) {

            buttons = new Button(cursor.getInt(0), cursor.getString(2), cursor.getString(3), cursor.getString(4),
                    cursor.getInt(1), cursor.getInt(6), cursor.getString(5)
            );

        }

        if (cursor != null) {
            cursor.close();
        }

        if (buttons == null) {

            throw new NoButtonException();
        }

        return buttons;
    }

    public static List<String> getRelayList(Context context, int deviceId) {

        List<String> holder = new ArrayList<>();

        Cursor cursor = null;

        try {
            SQLiteOpenHelper helper = new DatabaseHelper(context);

            SQLiteDatabase database = helper.getReadableDatabase();


            cursor = database.query("button", new String[]{"relay_no"}, "device_id=?", new String[]{String.valueOf(deviceId)},

                    null, null, null
            );


            if(cursor != null && cursor.moveToFirst()){


                while (cursor.moveToNext()) {

                    holder.add(String.valueOf(cursor.getInt(0)));

                }

            }


        } catch (SQLiteException e) {

            e.printStackTrace();
        }


        if (cursor != null) {

            cursor.close();
        }

        return holder;
    }

}