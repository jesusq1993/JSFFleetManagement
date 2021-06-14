package com.example.jsffleetmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.example.jsffleetmanagement.databinding.ActivityAddUserBinding;
import com.example.jsffleetmanagement.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class addUserActivity extends AppCompatActivity {

    //Declare view binding
    ActivityAddUserBinding activityAddUserBinding;

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_add_user);

        //Set View Binding
        activityAddUserBinding = ActivityAddUserBinding.inflate(getLayoutInflater());
        View view = activityAddUserBinding.getRoot();
        setContentView(view);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        if(user != null){
            startActivity(new Intent(getApplicationContext(),DashboardActivity.class));
            finish();
        }

    }

    public void registerUser(View view) {

        String firstName = activityAddUserBinding.editTextTxvFirstName.getText().toString();
        String lastName = activityAddUserBinding.editTexttxvLastName.getText().toString();
        String email = activityAddUserBinding.editTextTxvEmail.getText().toString();
        String password = activityAddUserBinding.editTextTxvPassword.getText().toString();

        if (TextUtils.isEmpty(firstName)) {
            Toast.makeText(this, "Please enter first name.", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(lastName)) {
            Toast.makeText(this, "Please enter last name.", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter email", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter password.", Toast.LENGTH_LONG).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {

                    //use User class to write to user section in realtime database
                    User newUser = new User(firstName, lastName, email);

                    //gets database instance and reference to location to write data
                    database = FirebaseDatabase.getInstance();
                    mDatabase = database.getReference("Users");
                    mDatabase.child(mAuth.getCurrentUser().getUid()).setValue(newUser);


                    Toast.makeText(addUserActivity.this,"User Created.",Toast.LENGTH_SHORT).show();
                    finish();
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                }else {
                    Toast.makeText(addUserActivity.this,"Error! "+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}