package com.example.finalproj;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;

import com.airbnb.lottie.L;
import com.example.finalproj.databinding.ActivityGuestDumbbellBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

public class GuestDumbbell extends DrawerBaseActivity implements GuestHealthyAdapter.MyRecyclerViewClickListener {

    public CalendarView calendarView;
    public Button button;
    public TextView textView;
    ActivityGuestDumbbellBinding activityGuestDumbbellBinding;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();
    DatabaseReference rootRef;
    DatabaseReference orderRef;
    DatabaseReference mDatabase;

    private String name="",count="",set="",KG="";
    public String Date="";
    ArrayList<Listitem_healthy> dataList = new ArrayList<>();
    RecyclerView recyclerView;
    GuestHealthyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityGuestDumbbellBinding = ActivityGuestDumbbellBinding.inflate(getLayoutInflater());
        setContentView(activityGuestDumbbellBinding.getRoot());
        allocateActivityTitle("운동 기록 조회 및 등록");

        mDatabase = FirebaseDatabase.getInstance().getReference();

        calendarView = (CalendarView) findViewById(R.id.dumbbell_calview);
        button = (Button) findViewById(R.id.Btn_plus);
        textView = (TextView) findViewById(R.id.textDate);

        recyclerView = (RecyclerView) findViewById(R.id.guest_dumbbell_RecyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new GuestHealthyAdapter(dataList);


        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            //달력 클릭 날에 추가 하기
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i2, int i3) {
                button.setVisibility(View.VISIBLE);
                textView.setVisibility(View.VISIBLE);

                int year=i;
                int month=i2+1;
                String m="";
                if(month<10){
                    m="0"+month;
                }else {
                    m=String.valueOf(month);
                }
                int day=i3;
                String d="";
                if(day<10){
                    d="0"+day;
                }else{
                    d=String.valueOf(day);
                }

                //달력 날
                Date = year+"-"+m+"-"+d;
                textView.setText(Date+" 운동 일지");
                rootRef = FirebaseDatabase.getInstance().getReference();
//                user.getUid();
                orderRef = rootRef.child("Guest_Healthy").child(user.getUid()).child(Date);

                orderRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if(task.isSuccessful()){
                            dataList.clear();
                            for(DataSnapshot ds : task.getResult().getChildren()){
                                String key = ds.getKey();
                                String value = ds.getValue(String.class);
                                Log.d("덤벨",Date);
                                st(key,value);
                            }
                            FinishAdd();
                        }else{
                            Log.d("TAG", task.getException().getMessage()); //Don't ignore potential errors!
                        }

                    }

                    private void st(String key, String value) {
                        StringTokenizer st = new StringTokenizer(value,",");
                        name = key;
                        while (st.hasMoreTokens()){
                            KG=st.nextToken();
                            count = st.nextToken();
                            set = st.nextToken();
                        }
                        dataList.add(new Listitem_healthy(name,count,set,KG));
                    }

                    private void FinishAdd() {
                        recyclerView.setAdapter(adapter);
                    }
                });
            }
        });

        adapter.setOnClickListener(this);

        //추가하기 버튼이 눌렸을 경우
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GuestDumbbell.this,Dialog_HealthyList.class);
                intent.putExtra("PK",user.getUid());
                intent.putExtra("Date",Date);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBtnClicked(int position) {
        //수정하기
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_custom,null,false);
        EditText ET_kg = (EditText) view.findViewById(R.id.DC_KG);
        EditText ET_count = (EditText) view.findViewById(R.id.DC_count);
        EditText ET_set = (EditText) view.findViewById(R.id.DC_set);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("운동 상세 정보 기록");
        builder.setView(R.layout.dialog_custom);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.guest_dumbbell_RecyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        GuestHealthyAdapter adapter = new GuestHealthyAdapter(dataList);
        builder.setPositiveButton("저장", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String totalString = "";
                totalString = ET_kg.getText()+","+ET_count.getText()+","+ET_set.getText();
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put(name,totalString);
                mDatabase.child("Guest_Healthy").child(user.getUid()).child(Date).updateChildren(hashMap);
                dataList.remove(position);
                count = ET_kg.getText().toString().trim();
                set = ET_set.getText().toString().trim();
                KG=ET_kg.getText().toString().trim();
                dataList.add(position,new Listitem_healthy(name,count,set,KG));

            }
        });
        builder.setNegativeButton("취소", null);
        builder.setView(view);
        builder.show();
    }
}