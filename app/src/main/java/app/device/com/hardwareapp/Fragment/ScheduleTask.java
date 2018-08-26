package app.device.com.hardwareapp.Fragment;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.kunzisoft.switchdatetime.SwitchDateTimeDialogFragment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import app.device.com.hardwareapp.R;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class ScheduleTask extends Fragment {

    EditText dateTimePicker;

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
    }


    private void InitViews(){
        View view = getView();
        if(view != null){
            dateTimePicker = view.findViewById(R.id.schedule_datetime_picker);
        }
    }

    private void DateTimeDialog(){
        SwitchDateTimeDialogFragment dialogFragment = SwitchDateTimeDialogFragment.newInstance("DateTime","Ok", "Cancel");

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

    private String DateTimeParser(Date date){
        @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm");
        return  dateFormat.format(date);

    }


}
