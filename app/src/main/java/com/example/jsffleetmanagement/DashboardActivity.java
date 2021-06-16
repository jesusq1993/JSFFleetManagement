package com.example.jsffleetmanagement;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.jsffleetmanagement.databinding.ActivityDashboardBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DashboardActivity extends AppCompatActivity {

    String TAG = "Message";

    ActivityDashboardBinding activityDashboardBinding;

    FirebaseAuth mAuth;
    FirebaseUser user;
    FirebaseDatabase database;
    DatabaseReference mdatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_dashboard);
        activityDashboardBinding = activityDashboardBinding.inflate(getLayoutInflater());
        View view = activityDashboardBinding.getRoot();
        setContentView(view);

        database = FirebaseDatabase.getInstance();
        mdatabase = database.getReference();
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();


        setNameAndEmail();


    }

    private void setNameAndEmail() {

        mdatabase = mdatabase.child("Users");

        mdatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                activityDashboardBinding.textviewName.setText(dataSnapshot.child(user.getUid()).child("first_name").getValue().toString()+" "+dataSnapshot.child(user.getUid()).child("last_name").getValue());
                activityDashboardBinding.textviewEmail.setText(user.getEmail());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }


    public void logout(View view) {
        mAuth.signOut();
        finish();
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
    }
}