package com.example.finalproj;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.airbnb.lottie.L;
import com.example.finalproj.databinding.ActivityAdminUserEditBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

public class AdminUserEdit extends AdminDrawerBaseActivity {

    ActivityAdminUserEditBinding activityAdminUserEditBinding;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();
    DatabaseReference rootRef;
    DatabaseReference orderRef;
    DatabaseReference rootRef2;
    DatabaseReference orderRef2;
    private DatabaseReference mDatabase;

    private int tYear;//오늘 연 월 일
    private int tMonth;
    private int tDay;

    private int dYear=1; //디에이 연월일
    private int dMonth=1;
    private int dDay=1;

    private long d;
    private long t;
    private long r;

    private int resultNumber = 0;

    static final int DATE_DIALOG_iD=0;

    private String temp_name = "", temp_auth="",temp_term="",temp_lostterm="";

    private String key;

    List<ListItem> dataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        activityAdminUserEditBinding = ActivityAdminUserEditBinding.inflate(getLayoutInflater());
        setContentView(activityAdminUserEditBinding.getRoot());
        allocateActivityTitle("유저 정보 변경");

        RecyclerView recyclerView = findViewById(R.id.admin_RecyclerView);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        rootRef = FirebaseDatabase.getInstance().getReference();
        orderRef = rootRef.child("Users");

        Log.d("파이어베이스", "에러찾기");


        orderRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    for (DataSnapshot ds : task.getResult().getChildren()) {
                        key = ds.getKey();
                        Log.d("파이어베이스", key);
                        rootRef = FirebaseDatabase.getInstance().getReference();
                        orderRef = rootRef.child("Users").child(key);
                        orderRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DataSnapshot> task) {
                                Log.d("파이어베이스","key : "+ key+"로 검색");
                                if (task.isSuccessful()) {
                                    for (DataSnapshot ds : task.getResult().getChildren()) {
                                        temp_name = ""; temp_auth=""; temp_term=""; temp_lostterm="";
                                        int i=0;
                                        i++;
                                        String ID = ds.getKey();
                                        String value = ds.getValue(String.class);
                                        if(ID.equals("userName")){
                                            temp_name=value;
                                            Log.d("파이어베이스", i+"번째"+"temp_name : "+ temp_name);
                                        }
                                        if(ds.getKey().equals("auth")){
                                            temp_auth=value;
                                            Log.d("파이어베이스", i+"번째"+"temp_auth : "+ temp_auth);
                                        }
                                        if(ds.getKey().equals("term")){
                                            if(value.equals("")){

                                            }
                                            temp_term=value;

                                            StringTokenizer st = new StringTokenizer(value,"-");
                                            while(st.hasMoreTokens()){
                                                dYear=Integer.parseInt(st.nextToken());
                                                dMonth=Integer.parseInt(st.nextToken());
                                                dDay=Integer.parseInt(st.nextToken());
                                            }
                                            Calendar calendar = Calendar.getInstance();
                                            tYear=calendar.get(Calendar.YEAR);
                                            tMonth=calendar.get(Calendar.MONTH);
                                            tDay=calendar.get(Calendar.DAY_OF_MONTH);
                                            Calendar dCalender = Calendar.getInstance();
                                            dCalender.set(dYear,dMonth,dDay);
                                            t=calendar.getTimeInMillis();
                                            d=dCalender.getTimeInMillis();
                                            r=(d-t)/(24*60*60*1000);
                                            resultNumber=(int)r+1;
                                            if(resultNumber>=0){
                                                temp_lostterm=String.valueOf(resultNumber);
                                            }else{
                                                temp_lostterm="기간 만료";
                                            }
                                            Log.d("파이어베이스",i+"번째"+"temp_term : "+ temp_term);
                                            Log.d("파이어베이스", i+"번째"+"temp_lostterm : "+ temp_lostterm);
                                        }
                                    }
                                } else {
                                    Log.d("TAG", task.getException().getMessage()); //Don't ignore potential errors!
                                }
                            }
                        });
                        dataList.add(new ListItem(temp_name,temp_auth,temp_term,temp_lostterm));
                        Log.d("파이어베이스", "add이후");
                    }
                } else {
                    Log.d("TAG", task.getException().getMessage()); //Don't ignore potential errors!
                }
            }
        });

        //어댑터 연결
        UserEditListAdapter adapter = new UserEditListAdapter(dataList);
        recyclerView.setAdapter(adapter);
    }
}