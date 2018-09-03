package app.device.com.hardwareapp.Fragment;


import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import app.device.com.hardwareapp.Adapter.DashboardRecyclerAdapter;
import app.device.com.hardwareapp.LocalDatabase.DatabaseOperation;
import app.device.com.hardwareapp.Model.Button;
import app.device.com.hardwareapp.Model.Device;
import app.device.com.hardwareapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DashboardFragment extends Fragment {

    RecyclerView dashRecyler;

    public DashboardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        View view = getView();
        if(view != null){
            dashRecyler = view.findViewById(R.id.dash_recyler);
        }

        dashRecyler.setHasFixedSize(true);
        dashRecyler.setLayoutManager(new LinearLayoutManager(getActivity()));
        DashboardRecyclerAdapter adapter = new DashboardRecyclerAdapter(getActivity(), DatabaseOperation.getAllButtons(getActivity()));
        dashRecyler.setAdapter(adapter);

      //  List<Button> button = DatabaseOperation.getAllButtons(getActivity());

    }
}
