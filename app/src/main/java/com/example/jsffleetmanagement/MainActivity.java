package com.example.jsffleetmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.jsffleetmanagement.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding activityMainBinding;
    private FirebaseAuth mAuth;

    private FirebaseDatabase database;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = activityMainBinding.getRoot();
        setContentView(view);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        if(user != null)
        {
            //that means the user is already logged in
            database = FirebaseDatabase.getInstance();

            mDatabase = database.getReference().child("users").child(user.getUid());

            //name, email, address, and photo url
            String name = user.getDisplayName();
            String email = user.getEmail();
            //Url photoUrl = user.getPhotoUrl();
            //and open profile activity
            Intent i = new Intent(getApplicationContext(),DashboardActivity.class);
            i.putExtra("name",name);
            i.putExtra("Email",email);
            //i.putExtra("PhotoUrl",photoUrl);

            //so close this activity
            finish();
            startActivity(i);

        }


    }

    public void login(View view) {
        String email = activityMainBinding.edittxvUsername.getText().toString();
        String password = activityMainBinding.editTxvPassword.getText().toString();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter email", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_LONG).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(MainActivity.this,"Authentication Succeeded",Toast.LENGTH_SHORT).show();
                            finish();
                            startActivity(new Intent(getApplicationContext(),DashboardActivity.class));
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void addUser(View view) {
        Intent i = new Intent(getApplicationContext(), addUserActivity.class);
        startActivity(i);
    }
}