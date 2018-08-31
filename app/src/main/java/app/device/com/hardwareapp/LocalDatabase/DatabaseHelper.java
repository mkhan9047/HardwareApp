package app.device.com.hardwareapp.LocalDatabase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String databaseName = "HardwareDatabase";
    private static final int databaseVersion = 1;

    public DatabaseHelper(Context context) {
        super(context, databaseName, null, databaseVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String device_table = "CREATE TABLE Device (\n" +
                "device_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\n" +
                "device_name TEXT UNIQUE, \n" +
                "phone_number TEXT UNIQUE\n" +
                ");";

        String button_table = "CREATE TABLE button (\n" +
                "button_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\n" +
                "device_id INTEGER, \n" +
                "relay_no INTEGER UNIQUE, \n" +
                "btn_name TEXT UNIQUE , \n" +
                "ON_CODE TEXT, \n" +
                "OFF_CODE TEXT, \n" +
                "btn_type TEXT, \n" +
                "status INTEGER \n" +
                ");";

        sqLiteDatabase.execSQL(device_table);
        sqLiteDatabase.execSQL(button_table);

    }

    @Override
    public void onUpgrade(SQLiteDatabase  database, int i, int i1) {


    }
}
