package com.example.finalproj;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
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

    private String name="",count="",set="";
    ArrayList<Listitem_healthy> dataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityGuestDumbbellBinding = ActivityGuestDumbbellBinding.inflate(getLayoutInflater());
        setContentView(activityGuestDumbbellBinding.getRoot());
        allocateActivityTitle("운동 기록 조회 및 등록");

        calendarView = (CalendarView) findViewById(R.id.dumbbell_calview);
        button = (Button) findViewById(R.id.Btn_plus);
        textView = (TextView) findViewById(R.id.textDate);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.guest_dumbbell_RecyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        GuestHealthyAdapter adapter = new GuestHealthyAdapter(dataList);


        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i2, int i3) {
                button.setVisibility(View.VISIBLE);
                textView.setVisibility(View.VISIBLE);

                int year=i;
                int month=i2+1;
                int day=i3;

                String Date = year+"-"+month+"-"+day;
                textView.setText(Date+" 운동 일지");
                rootRef = FirebaseDatabase.getInstance().getReference();
                orderRef = rootRef.child("Guest_Healthy").child("vo4Q22g6OKV82MmVM4bVCMFtUtv1");

                orderRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if(task.isSuccessful()){
                            for(DataSnapshot ds : task.getResult().getChildren()){
                                String date = ds.getKey();
                                String value = ds.getValue(String.class);
                                Log.d("덤벨",Date);
                                if(Date.equals(date)){
                                    Log.d("덤벨",date+"일에"+value+"운동을");
                                    StringTokenizer st = new StringTokenizer(value,",");
                                    Log.d("덤벨","st");
                                    while (st.hasMoreTokens()){
                                        name = st.nextToken();
                                        Log.d("덤벨","name : "+name);
                                        count = st.nextToken();
                                        Log.d("덤벨","count : "+count);
                                        set = st.nextToken();
                                        Log.d("덤벨","set : "+set);
                                    }
                                    dataList.add(new Listitem_healthy(name,count,set));
                                    Log.d("덤벨",String.valueOf(dataList.size()));
                                }else{
                                    Log.d("덤벨","같지 않아서 출력될 께 없음.");
                                }
                            }
                            FinishAdd();
                        }else{
                            Log.d("TAG", task.getException().getMessage()); //Don't ignore potential errors!
                        }

                    }

                    private void FinishAdd() {
                        Log.d("덤벨","finishadd 메소드 실행");
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
                Log.d("덤벨",user.getUid());
            }
        });
    }

    @Override
    public void onBtnClicked(int position) {

    }
}