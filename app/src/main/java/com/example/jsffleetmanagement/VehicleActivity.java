package com.example.jsffleetmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.jsffleetmanagement.databinding.ActivityVehicleBinding;

import java.util.ArrayList;

public class VehicleActivity extends AppCompatActivity {

    ActivityVehicleBinding activityVehicleBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_vehicle);
        activityVehicleBinding = activityVehicleBinding.inflate(getLayoutInflater());
        View view = activityVehicleBinding.getRoot();
        setContentView(view);

        ArrayList<String> vehicleList = new ArrayList<String>();

        vehicleList.add("Subaru");
        vehicleList.add("Toyota");
        vehicleList.add("Chevrolet");
        vehicleList.add("Dodge");
        vehicleList.add("Mitsubishi");
        vehicleList.add("Nissan");


        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,vehicleList);

        activityVehicleBinding.listViewVehicles.setAdapter(arrayAdapter);

        activityVehicleBinding.listViewVehicles.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("Vehicle Name", vehicleList.get(position));
                Toast.makeText(VehicleActivity.this, vehicleList.get(position),Toast.LENGTH_LONG).show();
            }
        });
    }
}