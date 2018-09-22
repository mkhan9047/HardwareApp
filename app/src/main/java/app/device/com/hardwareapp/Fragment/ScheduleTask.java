package app.device.com.hardwareapp.Fragment;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.kunzisoft.switchdatetime.SwitchDateTimeDialogFragment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import app.device.com.hardwareapp.LocalDatabase.DatabaseOperation;
import app.device.com.hardwareapp.Model.Button;
import app.device.com.hardwareapp.Exceptions.NoButtonException;
import app.device.com.hardwareapp.R;
import app.device.com.hardwareapp.Utility.Helper;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class ScheduleTask extends Fragment {

    EditText dateTimePicker;
    Spinner deviceSpinner;
    Spinner relay_spinner;
    RadioGroup ActionGroup;
    AppCompatButton save_schedule;
    TextView DeviceName;
    Button button;
    private int SelectedDeviceId;


    public ScheduleTask() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_schedule_task, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        InitViews();

        //date time picker
        dateTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DateTimeDialog();
            }
        });


        /*set device list */
        setDeviceList();

        /*selected device listener*/
        DeviceSelectedListener();

        /*relay selected listener*/
        RelaySelectedListener();




    }


    private void InitViews() {
        View view = getView();
        if (view != null) {
            dateTimePicker = view.findViewById(R.id.schedule_datetime_picker);
            relay_spinner = view.findViewById(R.id.relay_spinner);
            deviceSpinner = view.findViewById(R.id.spinner_device);
            DeviceName = view.findViewById(R.id.device_name);
            ActionGroup = view.findViewById(R.id.action_group);
            save_schedule = view.findViewById(R.id.save_schedule);


        }
    }

    private void DateTimeDialog() {

        SwitchDateTimeDialogFragment dialogFragment = SwitchDateTimeDialogFragment.newInstance("DateTime", "Ok", "Cancel");

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());

        // Assign values
        dialogFragment.startAtCalendarView();
        dialogFragment.set24HoursMode(false);
        dialogFragment.setMinimumDateTime(new GregorianCalendar(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).getTime());
        dialogFragment.setMaximumDateTime(new GregorianCalendar(2050, Calendar.DECEMBER, 31).getTime());
        dialogFragment.setDefaultDateTime(new GregorianCalendar(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), 8, 20).getTime());

        // Define new day and month format
        try {
            dialogFragment.setSimpleDateMonthAndDayFormat(new SimpleDateFormat("dd MMMM", Locale.getDefault()));
        } catch (SwitchDateTimeDialogFragment.SimpleDateMonthAndDayFormatException e) {
            Log.e(TAG, e.getMessage());
        }

        // Set listener
        dialogFragment.setOnButtonClickListener(new SwitchDateTimeDialogFragment.OnButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Date date) {
                // Date is get on positive button click
                // Do something
                dateTimePicker.setText(DateTimeParser(date));
            }

            @Override
            public void onNegativeButtonClick(Date date) {
                // Date is get on negative button click
                dateTimePicker.setText(DateTimeParser(date));
            }
        });

// Show
        dialogFragment.show(getChildFragmentManager(), "dialog_time");

    }

    private String DateTimeParser(Date date) {
        @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm");
        return dateFormat.format(date);

    }

    private void setDeviceList() {

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(Objects.requireNonNull(getContext()), android.R.layout.simple_spinner_dropdown_item, Helper.ReturnAllDeviceName(DatabaseOperation.returnAllDevices(getContext())));
            deviceSpinner.setAdapter(spinnerAdapter);
        }

    }

    private void setRelayList() {

    }

    private void DeviceSelectedListener() {

        deviceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                TriggerButton(Helper.getDeviceId(view.toString(), DatabaseOperation.returnAllDevices(getContext())),
                        Integer.parseInt(relay_spinner.getSelectedItem().toString())
                );

                List<String> relayList = Helper.ReturnRelayNumberFromDeviceId(1,

                        DatabaseOperation.getAllButtons(getContext()));

                for (String log : relayList) {

                    Log.d("relay: = ", log);
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }


    private void RelaySelectedListener() {

        relay_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                TextView selectedView = (TextView) view;

                TriggerButton(Helper.getDeviceId(deviceSpinner.getSelectedItem().toString(), DatabaseOperation.returnAllDevices(getContext())),
                        Integer.parseInt(selectedView.getText().toString())
                );

                Log.d("relay_no", String.valueOf(selectedView.getText().toString()));


            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    private void TriggerButton(int deviceId, int RelayNumber) {

        try {
            button = DatabaseOperation.getButtonByIdAndRelay(getContext(), deviceId, RelayNumber);

            DeviceName.setText(button.getButtonName());

        } catch (NoButtonException e) {

            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

            DeviceName.setText("");
        }
    }


    private void onScheduleSave() {

        save_schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }


}
