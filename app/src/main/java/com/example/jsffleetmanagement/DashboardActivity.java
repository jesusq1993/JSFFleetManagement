package com.example.jsffleetmanagement;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.jsffleetmanagement.databinding.ActivityDashboardBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class DashboardActivity extends AppCompatActivity {

    String TAG = "Message";

    ActivityDashboardBinding activityDashboardBinding;

    FirebaseAuth mAuth;
    FirebaseUser user;
    FirebaseDatabase database;
    DatabaseReference mdatabase;
    StorageReference storageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityDashboardBinding = activityDashboardBinding.inflate(getLayoutInflater());
        View view = activityDashboardBinding.getRoot();
        setContentView(view);

        database = FirebaseDatabase.getInstance();
        mdatabase = database.getReference();
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        storageRef = storageRef.getStorage().getReference();

        setProfilePhoto();
        setNameAndEmail();

    }

    private void setProfilePhoto(){
        StorageReference imageRef = storageRef.child("Profile Images/"+user.getUid()+"headshot_image.jpg");
        String url = imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {

            }
        });

        Picasso.get().load(url).placeholder(R.drawable.ic_baseline_perm_identity_24).error(R.drawable.ic_baseline_perm_identity_24).into(activityDashboardBinding.imgViewProfilePic);
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

    public void gotoVehicles(View view) {
        Intent intent = new Intent(this,VehicleActivity.class);
    }

    public void photoAction(View view) {

    }
}