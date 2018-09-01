package app.device.com.hardwareapp.Fragment;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.List;

import app.device.com.hardwareapp.LocalDatabase.DatabaseOperation;
import app.device.com.hardwareapp.Model.Device;
import app.device.com.hardwareapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddButton extends Fragment {


    MaterialSpinner selectDevice;
    Spinner relayNo;
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

          boolean what =  DatabaseOperation.AddButton(getActivity(), getDeviceId(selectDevice.getItems().get(selectDevice.getSelectedIndex()).toString(), DatabaseOperation.returnAllDevices(getActivity())),
                    Integer.parseInt(relayNo.getSelectedItem().toString()),  buttonName.getText().toString(),
                    onCode.getText().toString(), offcode.getText().toString(), getRadioGruopText(), 1
                    );

          if(what){
              Toast.makeText(getActivity(), "Save Success", Toast.LENGTH_SHORT).show();
          }else{
              Toast.makeText(getActivity(), "Not Save Success", Toast.LENGTH_SHORT).show();
          }

            }
        });


        //select device
        selectDevice.setItems(ReturnAllDeviceName(DatabaseOperation.returnAllDevices(getActivity())));
        selectDevice.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {

                Toast.makeText(getContext(), String.format("ID: %d", getDeviceId(item, DatabaseOperation.returnAllDevices(getContext()))), Toast.LENGTH_SHORT).show();

            }
        });


     relayNo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
         @Override
         public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

         }

         @Override
         public void onNothingSelected(AdapterView<?> adapterView) {

         }
     });

     if(DatabaseOperation.isDeviceNull(getActivity())){
         selectDevice.setEnabled(false);
         Toast.makeText(getActivity(), "No device added! please add device first", Toast.LENGTH_SHORT).show();
     }

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

        for(Device device : devices){

            if(deviceName.contains(device.getDeviceName())){

                return  device.getDeviceID();

            }

        }
     return 0;
    }

    private List<String> ReturnAllDeviceName(List<Device> devices){

        List<String> strings = new ArrayList<>();

        for(Device device : devices){

            strings.add(device.getDeviceName());

        }

        return strings;
    }




    private String getRadioGruopText(){
        int selectedID = radioGroup.getCheckedRadioButtonId();
        if(getView()!= null){

            if(selectedID == R.id.radio_light){
                return "Light";
            }else if(selectedID == R.id.radio_fan){
                return "Fan";
            }

        }

        return "";

    }





}
