package com.example.finalproj;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.finalproj.databinding.ActivityDashboardBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DashboardActivity extends DrawerBaseActivity {

    ActivityDashboardBinding activityDashboardBinding;
    TextView text_ID,text_Name;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();
    DatabaseReference rootRef;
    DatabaseReference orderRef;

//    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityDashboardBinding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(activityDashboardBinding.getRoot());
        allocateActivityTitle("Dashboard");
        text_ID = (TextView) findViewById(R.id.text_ID);
        text_Name = (TextView) findViewById(R.id.text_Name);
        DataSnapshot dataSnapshot = null;

        String ID = "" ;
        String Name="";

//        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        rootRef = FirebaseDatabase.getInstance().getReference();
        orderRef = rootRef.child("Users").child(user.getUid());
        orderRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    for (DataSnapshot ds : task.getResult().getChildren()) {
                        String key = ds.getKey();
                        String value = ds.getValue(String.class);
                        Log.d("TAG", key + ":" + value);

                        if(ds.getKey().equals("email")){
                            text_ID.setText(ds.getValue(String.class));
                        }
                        if(ds.getKey().equals("userName")){
                            text_Name.setText(ds.getValue(String.class));
                        }
                    }
                } else {
                    Log.d("TAG", task.getException().getMessage()); //Don't ignore potential errors!
                }
            }
        });


//        mDatabase.child("Users").child(ID).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DataSnapshot> task) {
//                if (!task.isSuccessful()) {
//                    //error
//                    Log.e("파이어베이스", "Error getting data", task.getException());
//                }
//                else {
//                    //정상 작동
//                    Log.d("파이어베이스", String.valueOf(task.getResult().getValue()));
//                }
//
//            }
//        });

    }
}