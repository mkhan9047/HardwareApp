package app.device.com.hardwareapp.Fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.List;

import app.device.com.hardwareapp.LocalDatabase.DatabaseOperation;
import app.device.com.hardwareapp.Model.Device;
import app.device.com.hardwareapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddButton extends Fragment {


    MaterialSpinner selectDevice, relayNo;
    EditText buttonName, onCode, offcode;
    RadioGroup radioGroup;
    AppCompatButton button;

    public AddButton() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_button, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        InitView();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    private void InitView(){
        View view = getView();
        if(view != null){
            selectDevice = view.findViewById(R.id.spinner_device_add_btn);
            relayNo = view.findViewById(R.id.spiner_relay_add_btn);
            buttonName = view.findViewById(R.id.add_button_btn_name);
            onCode = view.findViewById(R.id.add_button_on_code);
            offcode = view.findViewById(R.id.add_button_off_code);
            radioGroup = view.findViewById(R.id.add_button_radio);
            button = view.findViewById(R.id.add_button_btn);
        }
    }

    private int getDeviceId(String deviceName , List<Device> devices){


        return 0;
    }









}
