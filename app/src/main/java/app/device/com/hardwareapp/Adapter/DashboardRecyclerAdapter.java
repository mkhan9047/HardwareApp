package app.device.com.hardwareapp.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import app.device.com.hardwareapp.LocalDatabase.DatabaseOperation;
import app.device.com.hardwareapp.Model.Button;
import app.device.com.hardwareapp.Model.Device;
import app.device.com.hardwareapp.R;

public class DashboardRecyclerAdapter extends RecyclerView.Adapter<DashboardRecyclerAdapter.DashboardHolder> {

    private List<Button> buttons = new ArrayList<>();
    private Context context;

    public DashboardRecyclerAdapter(Context context, List<Button> buttons) {

        this.context = context;
        this.buttons = buttons;
    }

    @Override
    public DashboardHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = null;

        if(viewType == 0 ){

            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dashboard_recyler_row_fan, parent, false);

        }else if(viewType == 1){

            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dashboard_recycler_row, parent, false);
        }

        return new DashboardHolder(view);
    }

    @Override
    public void onBindViewHolder(DashboardHolder holder, int position) {

        holder.device.setText(DatabaseOperation.getDeviceByID(buttons.get(position).getDeviceID(), context).getDeviceName());
        holder.button_name.setText(buttons.get(position).getButtonName());
        if(buttons.get(position).getStatus() == 1){
            holder.aSwitch.setChecked(true);
        }else if(buttons.get(position).getStatus() == 0){
            holder.aSwitch.setChecked(false);
        }
    }

    @Override
    public int getItemCount() {

        return buttons.size();
    }

    @Override
    public int getItemViewType(int position) {

        if(buttons.get(position).getType().contains("Fan")){

            return 0;

        }else if(buttons.get(position).getType().contains("Light")){

            return 1;
        }


        return 1;
    }

    class DashboardHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView device, button_name;
        Switch aSwitch;
        CardView onButton, offButton;

        public DashboardHolder(View itemView) {

            super(itemView);
            device = itemView.findViewById(R.id.das_device);
            button_name = itemView.findViewById(R.id.das_button_name);
            aSwitch = itemView.findViewById(R.id.status);
            onButton = itemView.findViewById(R.id.das_onbutton);
            offButton = itemView.findViewById(R.id.das_offbutton);
            itemView.setOnClickListener(this);

            onButton.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("DefaultLocale")
                @Override
                public void onClick(View view) {
                    Device device = DatabaseOperation.getDeviceByID(buttons.get(getAdapterPosition()).getDeviceID(), context);
                    Toast.makeText(context, String.format("Sending sms to %s and code is %s relay No: %d", device.getDevicePhone(), buttons.get(getAdapterPosition()).getOnButtonCode(), buttons.get(getAdapterPosition()).getRelayNo()), Toast.LENGTH_LONG).show();
                }
            });

            offButton.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("DefaultLocale")
                @Override
                public void onClick(View view) {
                    Device device = DatabaseOperation.getDeviceByID(buttons.get(getAdapterPosition()).getDeviceID(), context);
                    Toast.makeText(context, String.format("Sending sms to %s and code is %s relay No: %d", device.getDevicePhone(), buttons.get(getAdapterPosition()).getOfButtonCode(), buttons.get(getAdapterPosition()).getRelayNo()), Toast.LENGTH_LONG).show();
                }
            });
        }

        @Override
        public void onClick(View view) {

        }
    }
}
