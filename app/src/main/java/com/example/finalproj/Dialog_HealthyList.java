package com.example.finalproj;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class Dialog_HealthyList extends AppCompatActivity implements Dialog_Healthy_Adapter.MyRecyclerViewClickListener {

    private RadioButton radio_chest,radio_back,radio_arm,radio_shoulder,radio_leg;
    private RadioGroup radioGroup;
    private TextView tv_name;
    private ImageView imageView;
    private DatabaseReference rootRef;
    private DatabaseReference orderRef;
    private DatabaseReference mDatabase;
    private ArrayList<HealthyList> dataList = new ArrayList<>();

    public String dating="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_healthy_list);
        radioGroup = (RadioGroup) findViewById(R.id.radiogroup);
        radio_chest = (RadioButton) findViewById(R.id.RB_chest);
        radio_back = (RadioButton) findViewById(R.id.RB_back);
        radio_arm = (RadioButton) findViewById(R.id.RB_arm);
        radio_shoulder = (RadioButton) findViewById(R.id.RB_shoulder);
        radio_leg = (RadioButton) findViewById(R.id.RB_leg);
        tv_name = (TextView) findViewById(R.id.TV_name);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.Healthy_List_RecyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        Dialog_Healthy_Adapter adapter = new Dialog_Healthy_Adapter(dataList);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.RB_chest:
                        //?????? ?????? ?????? ???
                        Log.d("??????","?????? ??????");
                        dataList.clear();
                        tv_name.setText(radio_chest.getText()+"?????? ?????????");
                        rootRef = FirebaseDatabase.getInstance().getReference();
                        orderRef = rootRef.child("Healthy_List").child("??????");
                        orderRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DataSnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (DataSnapshot ds : task.getResult().getChildren()) {
                                        String key = ds.getKey();
                                        String value = ds.getValue(String.class);
                                        Log.d("??????","key"+key);
                                        Log.d("??????","key value"+value);
                                        dataList.add(new HealthyList(key, value));
                                    }
                                    Log.d("??????", String.valueOf(dataList.size()));
                                    FinishAdd();
                                } else {
                                    Log.d("TAG", task.getException().getMessage()); //Don't ignore potential errors!
                                }
                            }
                        });
                        break;
                    case R.id.RB_back:
                        //??? ?????? ?????? ???
                        dataList.clear();
                        tv_name.setText(radio_back.getText()+"?????? ?????????");
                        rootRef = FirebaseDatabase.getInstance().getReference();
                        orderRef = rootRef.child("Healthy_List").child("???");
                        orderRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DataSnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (DataSnapshot ds : task.getResult().getChildren()) {
                                        String key = ds.getKey();
                                        String value = ds.getValue(String.class);
                                        Log.d("??????","key"+key);
                                        Log.d("??????","key value"+value);
                                        dataList.add(new HealthyList(key, value));
                                    }
                                    Log.d("??????", String.valueOf(dataList.size()));
                                    FinishAdd();
                                } else {
                                    Log.d("TAG", task.getException().getMessage()); //Don't ignore potential errors!
                                }
                            }
                        });
                        break;
                    case R.id.RB_arm:
                        //??? ?????? ?????? ???
                        dataList.clear();
                        tv_name.setText(radio_arm.getText()+"?????? ?????????");
                        rootRef = FirebaseDatabase.getInstance().getReference();
                        orderRef = rootRef.child("Healthy_List").child("???");
                        orderRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DataSnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (DataSnapshot ds : task.getResult().getChildren()) {
                                        String key = ds.getKey();
                                        String value = ds.getValue(String.class);
                                        Log.d("??????","key"+key);
                                        Log.d("??????","key value"+value);
                                        dataList.add(new HealthyList(key, value));
                                    }
                                    Log.d("??????", String.valueOf(dataList.size()));
                                    FinishAdd();
                                } else {
                                    Log.d("TAG", task.getException().getMessage()); //Don't ignore potential errors!
                                }
                            }
                        });
                        break;
                    case R.id.RB_leg:
                        //?????? ?????? ?????? ???
                        dataList.clear();
                        tv_name.setText(radio_leg.getText()+"?????? ?????????");
                        rootRef = FirebaseDatabase.getInstance().getReference();
                        orderRef = rootRef.child("Healthy_List").child("??????");
                        orderRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DataSnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (DataSnapshot ds : task.getResult().getChildren()) {
                                        String key = ds.getKey();
                                        String value = ds.getValue(String.class);
                                        Log.d("??????","key"+key);
                                        Log.d("??????","key value"+value);
                                        dataList.add(new HealthyList(key, value));
                                    }
                                    Log.d("??????", String.valueOf(dataList.size()));
                                    FinishAdd();
                                } else {
                                    Log.d("TAG", task.getException().getMessage()); //Don't ignore potential errors!
                                }
                            }
                        });
                        break;
                    case R.id.RB_shoulder:
                        //?????? ?????? ?????? ???
                        dataList.clear();
                        tv_name.setText(radio_shoulder.getText()+"?????? ?????????");
                        rootRef = FirebaseDatabase.getInstance().getReference();
                        orderRef = rootRef.child("Healthy_List").child("??????");
                        orderRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DataSnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (DataSnapshot ds : task.getResult().getChildren()) {
                                        String key = ds.getKey();
                                        String value = ds.getValue(String.class);
                                        Log.d("??????","key"+key);
                                        Log.d("??????","key value"+value);
                                        dataList.add(new HealthyList(key, value));
                                    }
                                    Log.d("??????", String.valueOf(dataList.size()));
                                    FinishAdd();
                                } else {
                                    Log.d("TAG", task.getException().getMessage()); //Don't ignore potential errors!
                                }
                            }
                        });
                        break;
                }
            }

            private void FinishAdd() {
                recyclerView.setAdapter(adapter);
            }
        });

        adapter.setOnClickListener(this);
    }

    @Override
    public void onItemClicked(int position) {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_custom,null,false);
        EditText ET_kg = (EditText) view.findViewById(R.id.DC_KG);
        EditText ET_count = (EditText) view.findViewById(R.id.DC_count);
        EditText ET_set = (EditText) view.findViewById(R.id.DC_set);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        Intent Dialog_HealthyList_Intent = getIntent();
        String PK = Dialog_HealthyList_Intent.getStringExtra("PK").trim();
        dating = Dialog_HealthyList_Intent.getStringExtra("Date").trim();
        builder.setTitle("?????? ?????? ?????? ??????");
        builder.setView(R.layout.dialog_custom);
        builder.setPositiveButton("??????", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String totalString = "";
                totalString = ET_kg.getText()+","+ET_count.getText()+","+ET_set.getText();

//                dating= guestDumbbell.Date;
                Log.d("??????","date : " + dating);

                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put(dataList.get(position).getHealthyList_name(),totalString);
                mDatabase.child("Guest_Healthy").child(PK).child(dating).updateChildren(hashMap);
                finish();
            }
        });
        builder.setNegativeButton("??????", null);
        builder.setView(view);
        builder.show();
    }
}