package com.example.finalproj;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.finalproj.databinding.ActivityDashboardBinding;
import com.example.finalproj.databinding.ActivityTrainerBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TrainerActivity extends TrainerDrawerBaseActivity {
    @NonNull ActivityDashboardBinding activityTrainerBinding;
    TextView text_ID,text_Name;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();
    DatabaseReference rootRef;
    DatabaseReference orderRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityTrainerBinding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(activityTrainerBinding.getRoot());
        allocateActivityTitle("I'm Trainer");
        text_ID = (TextView) findViewById(R.id.text_ID);
        text_Name = (TextView) findViewById(R.id.text_Name);
        DataSnapshot dataSnapshot = null;

        String ID = "" ;
        String Name="";

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

    }
}