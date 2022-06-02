package com.example.finalproj;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
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

    RadioButton radio_chest,radio_back,radio_arm,radio_shoulder,radio_leg;
    RadioGroup radioGroup;
    TextView tv_name;
    ImageView imageView;
    DatabaseReference rootRef;
    DatabaseReference orderRef;
    DatabaseReference mDatabase;
    ArrayList<HealthyList> dataList = new ArrayList<>();

    public String dating="";

    GuestDumbbell guestDumbbell = new GuestDumbbell();


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
                        //가슴 운동 선택 시
                        Log.d("체크","가슴 선택");
                        dataList.clear();
                        tv_name.setText(radio_chest.getText()+"운동 리스트");
                        rootRef = FirebaseDatabase.getInstance().getReference();
                        orderRef = rootRef.child("Healthy_List").child("가슴");
                        orderRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DataSnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (DataSnapshot ds : task.getResult().getChildren()) {
                                        String key = ds.getKey();
                                        String value = ds.getValue(String.class);
                                        Log.d("체크","key"+key);
                                        Log.d("체크","key value"+value);
                                        dataList.add(new HealthyList(key, value));
                                    }
                                    Log.d("체크", String.valueOf(dataList.size()));
                                    FinishAdd();
                                } else {
                                    Log.d("TAG", task.getException().getMessage()); //Don't ignore potential errors!
                                }
                            }
                        });
                        break;
                    case R.id.RB_back:
                        //등 운동 선택 시
                        dataList.clear();
                        tv_name.setText(radio_back.getText()+"운동 리스트");
                        rootRef = FirebaseDatabase.getInstance().getReference();
                        orderRef = rootRef.child("Healthy_List").child("등");
                        orderRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DataSnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (DataSnapshot ds : task.getResult().getChildren()) {
                                        String key = ds.getKey();
                                        String value = ds.getValue(String.class);
                                        Log.d("체크","key"+key);
                                        Log.d("체크","key value"+value);
                                        dataList.add(new HealthyList(key, value));
                                    }
                                    Log.d("체크", String.valueOf(dataList.size()));
                                    FinishAdd();
                                } else {
                                    Log.d("TAG", task.getException().getMessage()); //Don't ignore potential errors!
                                }
                            }
                        });
                        break;
                    case R.id.RB_arm:
                        //팔 운동 선택 시
                        dataList.clear();
                        tv_name.setText(radio_arm.getText()+"운동 리스트");
                        rootRef = FirebaseDatabase.getInstance().getReference();
                        orderRef = rootRef.child("Healthy_List").child("팔");
                        orderRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DataSnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (DataSnapshot ds : task.getResult().getChildren()) {
                                        String key = ds.getKey();
                                        String value = ds.getValue(String.class);
                                        Log.d("체크","key"+key);
                                        Log.d("체크","key value"+value);
                                        dataList.add(new HealthyList(key, value));
                                    }
                                    Log.d("체크", String.valueOf(dataList.size()));
                                    FinishAdd();
                                } else {
                                    Log.d("TAG", task.getException().getMessage()); //Don't ignore potential errors!
                                }
                            }
                        });
                        break;
                    case R.id.RB_leg:
                        //하체 운동 선택 시
                        dataList.clear();
                        tv_name.setText(radio_leg.getText()+"운동 리스트");
                        rootRef = FirebaseDatabase.getInstance().getReference();
                        orderRef = rootRef.child("Healthy_List").child("하체");
                        orderRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DataSnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (DataSnapshot ds : task.getResult().getChildren()) {
                                        String key = ds.getKey();
                                        String value = ds.getValue(String.class);
                                        Log.d("체크","key"+key);
                                        Log.d("체크","key value"+value);
                                        dataList.add(new HealthyList(key, value));
                                    }
                                    Log.d("체크", String.valueOf(dataList.size()));
                                    FinishAdd();
                                } else {
                                    Log.d("TAG", task.getException().getMessage()); //Don't ignore potential errors!
                                }
                            }
                        });
                        break;
                    case R.id.RB_shoulder:
                        //어깨 운동 선택 시
                        dataList.clear();
                        tv_name.setText(radio_shoulder.getText()+"운동 리스트");
                        rootRef = FirebaseDatabase.getInstance().getReference();
                        orderRef = rootRef.child("Healthy_List").child("어깨");
                        orderRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DataSnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (DataSnapshot ds : task.getResult().getChildren()) {
                                        String key = ds.getKey();
                                        String value = ds.getValue(String.class);
                                        Log.d("체크","key"+key);
                                        Log.d("체크","key value"+value);
                                        dataList.add(new HealthyList(key, value));
                                    }
                                    Log.d("체크", String.valueOf(dataList.size()));
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
        builder.setTitle("운동 상세 정보 기록");
        builder.setView(R.layout.dialog_custom);
        builder.setPositiveButton("저장", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String totalString = "";
                totalString = ET_kg.getText()+","+ET_count.getText()+","+ET_set.getText();
                dating= guestDumbbell.Date;
                Log.d("클릭","date : " + dating);

                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put(dataList.get(position).getHealthyList_name(),totalString);
                mDatabase.child("Guest_Healthy").child("vo4Q22g6OKV82MmVM4bVCMFtUtv1").child(dating).updateChildren(hashMap);
                finish();
            }
        });
        builder.setNegativeButton("취소", null);
        builder.setView(view);
        builder.show();
    }
}