package app.device.com.hardwareapp.Fragment;


import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import app.device.com.hardwareapp.LocalDatabase.DatabaseOperation;
import app.device.com.hardwareapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddDevices extends Fragment {

    EditText deviceName, phoneNumber;
    AppCompatButton button;

    public AddDevices() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_devices, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        InitView();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    if(DatabaseOperation.AddDevice(getActivity(), deviceName.getText().toString(), phoneNumber.getText().toString())) {
                        Toast.makeText(getActivity(), "Save Success!", Toast.LENGTH_SHORT).show();

                    }else{
                        Toast.makeText(getActivity(), "Not Save Success!", Toast.LENGTH_SHORT).show();
                    }
            }
        });
    }

    private void InitView(){
        View view = getView();
        if(view != null){
            deviceName = view.findViewById(R.id.add_device_name);
            phoneNumber = view.findViewById(R.id.add_device_phone_number);
            button = view.findViewById(R.id.add_device_btn);
        }
    }

}
