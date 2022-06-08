package com.example.finalproj;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalproj.databinding.ActivityTrainerPlusBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.StringTokenizer;


public class TrainerPlus extends TrainerDrawerBaseActivity {

    ActivityTrainerPlusBinding activityTrainerPlusBinding;

    private DatabaseReference mDatabase;
    DatabaseReference rootRef;
    DatabaseReference orderRef;

    EditText txt_email;
    TextView txt_name;
    TextView txt_phonemum;
    TextView txt_term;
    Button btnsave;
    Button btnsearching;

    String date;
    String finaluserkey="null";
    Calendar calendar = Calendar.getInstance();
    int pYear = calendar.get(Calendar.YEAR);//오늘 연도
    int pMonth = calendar.get(Calendar.MONTH);//오늘 월
    int pDay = calendar.get(Calendar.DAY_OF_MONTH);//오늘 일

    private String user_name = "", user_auth="",user_phonenum="";
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
        activityTrainerPlusBinding = ActivityTrainerPlusBinding.inflate(getLayoutInflater());
        setContentView(activityTrainerPlusBinding.getRoot());
        allocateActivityTitle("회원 등록");

        txt_email = (EditText) findViewById(R.id.user_id);
        txt_name = (TextView) findViewById(R.id.user_name);
        txt_phonemum = (TextView) findViewById(R.id.user_phonenum);
        txt_term = (TextView) findViewById(R.id.user_term);
        btnsave = (Button) findViewById(R.id.savebtn);
        btnsearching = (Button)findViewById(R.id.searchingbtn);
        btnsave.setEnabled(false);
        btnsearching.setEnabled(false);
    }

    public void buttonSearching(View view){
        String email=txt_email.getText()+"";
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
                Log.d("버튼클릭", String.valueOf(txt_email.getText()));
                Log.d("버튼클릭", "email : "+email);
                Log.d("버튼클릭",s);
                orderRef = rootRef.child("Users").child(s);
                orderRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        Log.d("파이어베이스","2번째 검색의 key : "+ s +"로 검색");
                        if (task.isSuccessful()) {
                            for (DataSnapshot ds : task.getResult().getChildren()) {
                                String ID = ds.getKey();
                                String value = ds.getValue(String.class);
                                if(ID.equals("email")) {
                                    Log.d("버튼클릭","ID : "+ID+" / value : "+value);
                                    if(value.equals(email)){
                                        Log.d("버튼클릭","같아요");
                                        Log.d("버튼클릭","같을때의 키는 : "+s);
                                        searching3(s);
                                    }
                                }
                            }

                        } else {
                            Log.d("TAG", task.getException().getMessage()); //Don't ignore potential errors!
                        }
                    }

                    private void searching3(String s) {
                        orderRef = rootRef.child("Users").child(s);
                        finaluserkey = s;
                        orderRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DataSnapshot> task) {
                                Log.d("파이어베이스","2번째 검색의 key : "+ s +"로 검색");
                                if (task.isSuccessful()) {
                                    for (DataSnapshot ds : task.getResult().getChildren()) {
                                        String ID = ds.getKey();
                                        String value = ds.getValue(String.class);
                                        if(ID.equals("userName")){
                                            txt_name.setText(value);
                                        }
                                        if(ds.getKey().equals("phoneNo")){
                                            txt_phonemum.setText(value);
                                        }
                                    }
                                    btnsearching.setEnabled(true);
                                } else {
                                    Log.d("TAG", task.getException().getMessage()); //Don't ignore potential errors!
                                }
                            }
                        });
                    }

                });
            }
        });
    }

    public void buttonDate(View view){

        Log.d("버튼클릭","날짜 검색 버튼 클릭");
        DatePickerDialog datePicker = new DatePickerDialog(TrainerPlus.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month+1;

                date = year + "-"+month+"-"+day;
                txt_term.setText(date);
            }
        },pYear,pMonth,pDay);
        datePicker.show();
        btnsave.setEnabled(true);
    }
    public void buttonSave(View view){

        Calendar today = Calendar.getInstance();
        Calendar d_day = Calendar.getInstance();

        int year = 0;
        int month=0;
        int day=0;

        StringTokenizer st = new StringTokenizer(date,"-");
        while(st.hasMoreTokens()){
            year = Integer.parseInt(st.nextToken());
            month = Integer.parseInt(st.nextToken());
            day = Integer.parseInt(st.nextToken());
            month = month-1;
        }
        d_day.set(year,month,day);

        long l_dday = d_day.getTimeInMillis()/(24*60*60*1000);
        long l_today = today.getTimeInMillis()/(24*60*60*1000);

        long sub = l_today-l_dday;
        sub = sub*(-1);
        if(sub<=0){
            Log.d("버튼클릭","날짜가 0보다 작아서 실패");
            Toast.makeText(this,"그 날짜까지는 "+sub+"일 로 이상합니다!",Toast.LENGTH_LONG).show();
        }else {
            Log.d("버튼클릭","날짜가 0보다 커서 실행");
            if(!(finaluserkey.equals(null))){
                Log.d("버튼클릭","유저 키가 널이 아니라 성공");
                Toast.makeText(this,"잘 되었습니다.",Toast.LENGTH_LONG).show();
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("term",date);
                mDatabase.child("Users").child(finaluserkey).updateChildren(hashMap);
                finish();
                startActivity(new Intent(TrainerPlus.this,TrainerActivity.class));
            }else{
                Log.d("버튼클릭","유저 키가 널이라서 실패");
            }
        }
    }
}