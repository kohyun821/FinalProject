package com.example.finalproj;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import androidx.appcompat.app.AlertDialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.airbnb.lottie.L;
import com.example.finalproj.databinding.ActivityAdminUserEditBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.StringTokenizer;

import javax.security.auth.callback.Callback;

public class AdminUserEdit extends AdminDrawerBaseActivity implements UserEditListAdapter.MyRecyclerViewClickListener {

    ActivityAdminUserEditBinding activityAdminUserEditBinding;
    DatabaseReference rootRef;
    DatabaseReference orderRef;
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

    ArrayList<String> Firebasekey = new ArrayList<>();

    ArrayList<ListItem> dataList = new ArrayList<>();

    private BackKeyHandler backKeyHandler = new BackKeyHandler(this);
    @Override
    public void onBackPressed() {
        /* 다음 4가지 형태 중 하나 선택해서 사용 */

        //backKeyHandler.onBackPressed();
        //backKeyHandler.onBackPressed("\'뒤로\' 버튼을 두 번 누르면 종료됩니다.\n입력한 내용이 지워집니다.");
        //backKeyHandler.onBackPressed(5);
        backKeyHandler.onBackPressed("5초 내로 한번 더 누르세요", 5);
    }

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
        UserEditListAdapter adapter = new UserEditListAdapter(dataList);

        Log.d("파이어베이스", "에러찾기");

        rootRef = FirebaseDatabase.getInstance().getReference();
        orderRef = rootRef.child("Users");
        orderRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {

                if (task.isSuccessful()) {
                    dataList.clear();
                    Firebasekey.clear();
                    for (DataSnapshot ds : task.getResult().getChildren()) {
                        key = ds.getKey();
                        Firebasekey.add(key);
                        Log.d("파이어베이스", key);
                    }
                    Log.d("파이어베이스","size : "+Firebasekey.size());
                    rootRef = FirebaseDatabase.getInstance().getReference();
                    for(int i=0; i<Firebasekey.size();i++){
                        Log.d("파이어베이스","key : "+ Firebasekey.get(i) +"로 검색");
                        searching2(Firebasekey.get(i));
                    }
                } else {
                    Log.d("TAG", task.getException().getMessage()); //Don't ignore potential errors!
                }
            }

            private void searching2(String s) {
                orderRef = rootRef.child("Users").child(s);
                orderRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        Log.d("파이어베이스","2번째 검색의 key : "+ s +"로 검색");
                        if (task.isSuccessful()) {
                            for (DataSnapshot ds : task.getResult().getChildren()) {
                                String ID = ds.getKey();
                                String value = ds.getValue(String.class);
                                if(ID.equals("userName")){
                                    temp_name=value;
                                }
                                if(ds.getKey().equals("auth")){
                                    temp_auth=value;
                                }
                                if(ds.getKey().equals("term")){
                                    temp_term=value;

                                    StringTokenizer st = new StringTokenizer(value,"-");
                                    while(st.hasMoreTokens()){
                                        dYear=Integer.parseInt(st.nextToken());
                                        dMonth=Integer.parseInt(st.nextToken());
                                        dDay=Integer.parseInt(st.nextToken());
                                        dMonth=dMonth-1;
                                    }
                                    Calendar calendar = Calendar.getInstance();
                                    tYear=calendar.get(Calendar.YEAR);
                                    tMonth=calendar.get(Calendar.MONTH)+1;
                                    tDay=calendar.get(Calendar.DAY_OF_MONTH);

                                    Calendar today = Calendar.getInstance();
                                    Calendar d_day = Calendar.getInstance();

                                    d_day.set(dYear,dMonth,dDay);

                                    long l_dday = d_day.getTimeInMillis()/(24*60*60*1000);
                                    long l_today = today.getTimeInMillis()/(24*60*60*1000);

                                    long sub = l_today-l_dday;
                                    sub = sub*(-1);

                                    resultNumber=(int)sub;
                                    Log.d("파이어베이스","남은 날"+resultNumber);
                                    if(resultNumber>=0){
                                        temp_lostterm=String.valueOf(resultNumber);
                                    }else{
                                        temp_lostterm="기간 만료";
                                    }
                                }
                            }
                            dataList.add(new ListItem(temp_name,temp_auth,temp_term,temp_lostterm,s));
                            FinishAdd();
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
        adapter.setOnClickListener(this);

    }
    @Override
    public void onBtnClicked(int position) {

        RecyclerView recyclerView = findViewById(R.id.admin_RecyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        UserEditListAdapter adapter = new UserEditListAdapter(dataList);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        Log.d("파이어베이스","버튼 클릭 뒤"+position);
        Log.d("파이어베이스","버튼 클릭 뒤"+dataList.get(position).getUID());
        HashMap<String, Object> hashMap = new HashMap<>();
        if(dataList.get(position).getListitem_userauth().equals("회원")){
            builder.setTitle("사용자 권한 변경").setMessage("사용자의 권한을 \""+dataList.get(position).getListitem_userauth()+"\" 에서 \n \"트레이너\" 로 변경 하겠습니까?")
                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //디비에 내용이 수정 되는 메소드
                            hashMap.put("auth","트레이너");
                            mDatabase.child("Users").child(dataList.get(position).getUID()).updateChildren(hashMap);
                            String aftname = dataList.get(position).getListitem_name();
                            String aftauth = "트레이너";
                            String aftterm = dataList.get(position).getListitem_term();
                            String aftlosterm = dataList.get(position).getListitem_lostterm();
                            String aftuid = dataList.get(position).getUID();
                            dataList.remove(position);
                            dataList.add(position,new ListItem(aftname, aftauth, aftterm, aftlosterm, aftuid));
                            Finish_BtnClicked();
//                            FinishAdd();
                        }

                        private void Finish_BtnClicked() {
                            recyclerView.setAdapter(adapter);
                        }
                    })
                    .setNeutralButton("취소",null)
                    .show();
        }else if(dataList.get(position).getListitem_userauth().equals("트레이너")){
            builder.setTitle("사용자 권한 변경").setMessage("사용자의 권한을 \""+dataList.get(position).getListitem_userauth()+"\" 에서 \n \"회원\" 로 변경 하겠습니까?")
                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //디비에 내용이 수정 되는 메소드
                            hashMap.put("auth","회원");
                            mDatabase.child("Users").child(dataList.get(position).getUID()).updateChildren(hashMap);
                            String aftname = dataList.get(position).getListitem_name();
                            String aftauth = "회원";
                            String aftterm = dataList.get(position).getListitem_term();
                            String aftlosterm = dataList.get(position).getListitem_lostterm();
                            String aftuid = dataList.get(position).getUID();
                            dataList.remove(position);
                            dataList.add(position,new ListItem(aftname, aftauth, aftterm, aftlosterm, aftuid));
                            Finish_BtnClicked();
//                            FinishAdd();
                        }
                        private void Finish_BtnClicked() {
                            recyclerView.setAdapter(adapter);
                        }
                    })
                    .setNeutralButton("취소",null)
                    .show();
        }else{
            builder.setTitle("사용자 권한 변경").setMessage("해당 기능을 사용 할 수 없는 사용자 입니다.")
                    .setPositiveButton("확인",null)
                    .show();
        }
        adapter.setOnClickListener(this);
    }
}