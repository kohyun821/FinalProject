package com.example.finalproj;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    InBodyUserAdapter adapter;

    private DatabaseReference rootRef;
    private DatabaseReference orderRef;
    private DatabaseReference mDatabase;

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser user = mAuth.getCurrentUser();

    private ArrayList<InBodyUser> dataList = new ArrayList<>();
    private String UserPK="";
    String SearchDate="", SearchFat="", SearchFatmass="",SearchSkeletalmass="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.Inbody_List_RV);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        InBodyUserAdapter adapter = new InBodyUserAdapter(dataList);

        UserPK = user.getUid();
//        UserPK="mOdjnMkh5NRxTenMNk29LYNy2xt1";

        rootRef = FirebaseDatabase.getInstance().getReference();
        orderRef = rootRef.child("InBodyUser").child(UserPK);
        orderRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    for (DataSnapshot ds : task.getResult().getChildren()) {
                        String key = ds.getKey();
                        searching2(key);

                    }
                    Log.d("체크", String.valueOf(dataList.size()));
//                    FinishAdd();
                } else {
                    Log.d("TAG", task.getException().getMessage()); //Don't ignore potential errors!
                }
            }

            private void searching2(String key) {
                rootRef = FirebaseDatabase.getInstance().getReference();
                orderRef = rootRef.child("InBodyUser").child(UserPK).child(key);
                orderRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DataSnapshot ds : task.getResult().getChildren()) {
                                String key = ds.getKey();
                                String value = ds.getValue(String.class);
                                if(key.equals("fat")){
                                    SearchFat=value;
                                }
                                if(key.equals("fat_mass")){
                                    SearchFatmass=value;
                                }
                                if(key.equals("skeletal_mass")){
                                    SearchSkeletalmass=value;
                                }

                            }
                            dataList.add(new InBodyUser(key,SearchFat,SearchFatmass,SearchSkeletalmass));
                            FinishAdd();
//                    FinishAdd();
                        } else {
                            Log.d("TAG", task.getException().getMessage()); //Don't ignore potential errors!
                        }
                    }

                    private void FinishAdd() {
                        recyclerView.setAdapter(adapter);
                    }
                });
            }
        });

    }
}
