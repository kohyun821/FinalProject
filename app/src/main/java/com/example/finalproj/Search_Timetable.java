package com.example.finalproj;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

public class Search_Timetable extends AppCompatActivity implements View.OnClickListener {

    private String searchkey = "";
    private String searchTimeTableAuth = "";

    private DatabaseReference rootRef;
    private DatabaseReference orderRef;

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser user = mAuth.getCurrentUser();

    Button Dialog_A1,Dialog_A2,Dialog_A3,Dialog_A4,Dialog_A5,Dialog_A6,Dialog_A7,Dialog_A8,Dialog_A9,Dialog_A10;
    Button Dialog_B1,Dialog_B2,Dialog_B3,Dialog_B4,Dialog_B5,Dialog_B6,Dialog_B7,Dialog_B8,Dialog_B9,Dialog_B10;
    Button Dialog_C1,Dialog_C2,Dialog_C3,Dialog_C4,Dialog_C5,Dialog_C6,Dialog_C7,Dialog_C8,Dialog_C9,Dialog_C10;
    Button Dialog_D1,Dialog_D2,Dialog_D3,Dialog_D4,Dialog_D5,Dialog_D6,Dialog_D7,Dialog_D8,Dialog_D9,Dialog_D10;
    Button Dialog_E1,Dialog_E2,Dialog_E3,Dialog_E4,Dialog_E5,Dialog_E6,Dialog_E7,Dialog_E8,Dialog_E9,Dialog_E10;
    Button Dialog_F1,Dialog_F2,Dialog_F3,Dialog_F4,Dialog_F5,Dialog_F6,Dialog_F7,Dialog_F8,Dialog_F9,Dialog_F10;
    Button Dialog_G1,Dialog_G2,Dialog_G3,Dialog_G4,Dialog_G5,Dialog_G6,Dialog_G7,Dialog_G8,Dialog_G9,Dialog_G10;

    TextView tv;

    private ArrayList<Button> dialogbtnAry = new ArrayList<>();

    private ArrayList<String> FirebaseTimetablekey = new ArrayList<>();
    private ArrayList<String> DialogTrainerPK = new ArrayList<>();
    private ArrayList<TrainerUser_guestpencil> DialogTrainer = new ArrayList<>();
    private String temp_name="";
    private String temp_auth="";

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_timetable);

        Intent secIntent = getIntent();
//        searchkey = "W4Gx8BI9FpdoTJEBg3MboEgF65c2";
        searchkey = secIntent.getStringExtra("searchKey").trim();
        searchTimeTableAuth = secIntent.getStringExtra("timetableauth").trim();
        
        dialogbtnsave();
        for(int i=0;i<dialogbtnAry.size();i++)
        {
            if(searchTimeTableAuth.equals("false")){
                dialogbtnAry.get(i).setEnabled(false);
            }
            dialogbtnAry.get(i).setBackgroundColor(Color.YELLOW);
            dialogbtnAry.get(i).setOnClickListener(this);
        }
        tv = (TextView) findViewById(R.id.dialog_timetable_textview);
        userSearching();
        searchTimetable();
    }
    @Override
    public void onClick(View v){
        Button newButton = (Button) v;
//        String UserPK = user.getUid();
        String UserPK = "mOdjnMkh5NRxTenMNk29LYNy2xt1";
        String btnAdr = "";

        HashMap<String,Object> hashMap = new HashMap<>();

        for(Button button : dialogbtnAry){
            if(button==newButton){
                //버튼 클릭 메소드
                ColorDrawable colorDrawable = (ColorDrawable) button.getBackground();
                int bg = colorDrawable.getColor();

                btnAdr = getResources().getResourceName(button.getId());
                StringTokenizer st = new StringTokenizer(btnAdr,"_");
                while(st.hasMoreTokens()){
                    btnAdr = st.nextToken();
                }

                Log.d("서치",btnAdr+"의 색 : "+String.valueOf(bg));
                if(String.valueOf(bg).equals("-256")){
                    //색상이 yellow일 경우
                    AlertDialog.Builder dialog = new AlertDialog.Builder(Search_Timetable.this);
                    dialog.setTitle("추가하기");
                    dialog.setMessage("해당 시간에 PT를 추가 하시겠 습니까?");
                    String finalBtnAdr = btnAdr;
                    dialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            mDatabase = FirebaseDatabase.getInstance().getReference();
                            hashMap.put(finalBtnAdr,UserPK);
                            mDatabase.child("Trainer_TimeTable").child(" "+searchkey).updateChildren(hashMap);
                            Toast.makeText(Search_Timetable.this,"추가 되었습니다.",Toast.LENGTH_SHORT).show();
                            button.setBackgroundColor(Color.BLUE);//파란 색으로
                        }
                    });
                    dialog.setNegativeButton("취소",null);
                    dialog.show();
                }
                if(String.valueOf(bg).equals("-16776961")){
                    //색상이 blue 일 경우
                    AlertDialog.Builder dialog = new AlertDialog.Builder(Search_Timetable.this);
                    dialog.setTitle("삭제하기");
                    dialog.setMessage("해당 시간에 PT를 삭제 하시겠 습니까?");
                    String finalBtnAdr1 = btnAdr;
                    dialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            mDatabase = FirebaseDatabase.getInstance().getReference();
                            hashMap.put(finalBtnAdr1,"null");
                            mDatabase.child("Trainer_TimeTable").child(" "+searchkey).updateChildren(hashMap);
                            Toast.makeText(Search_Timetable.this,"삭제 되었습니다.",Toast.LENGTH_SHORT).show();
                            button.setBackgroundColor(Color.YELLOW);//노란 색으로
                        }
                    });
                    dialog.setNegativeButton("취소",null);
                    dialog.show();
                }
            }
        }

    }

    private void userSearching() {
        //Users테이블에 있는 모든 값 저장
        rootRef = FirebaseDatabase.getInstance().getReference();
        orderRef = rootRef.child("Users");
        orderRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                for(DataSnapshot ds : task.getResult().getChildren()){
                    String ID = ds.getKey();
                    DialogTrainerPK.add(ID);
                }
                for(int i=0; i<DialogTrainerPK.size();i++){
                    userSearching2(DialogTrainerPK.get(i));
                }
            }

            private void userSearching2(String s) {
                orderRef = rootRef.child("Users").child(s);
                orderRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DataSnapshot ds : task.getResult().getChildren()) {
                                String ID = ds.getKey();
                                String value = ds.getValue(String.class);
                                if(ID.equals("userName")){
                                    temp_name=value;
                                }
                                if(ID.equals("auth")){
                                    temp_auth=value;
                                }
                            }
                            DialogTrainer.add(new TrainerUser_guestpencil(temp_auth,temp_name,s));
                        } else {
                            Log.d("TAG", task.getException().getMessage()); //Don't ignore potential errors!
                        }
                    }
                });
            }
        });
    }
    private void searchTimetable() {
        rootRef = FirebaseDatabase.getInstance().getReference();
        orderRef = rootRef.child("Trainer_TimeTable");
//        Log.d("서치","key is : "+secIntent.getStringExtra("searchKey").trim());
        orderRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()){
                    FirebaseTimetablekey.clear();
                    for(DataSnapshot ds : task.getResult().getChildren()){
                        String key = ds.getKey();
                        FirebaseTimetablekey.add(key);
                    }
                    for(int i=0; i<FirebaseTimetablekey.size();i++){
                        if((FirebaseTimetablekey.get(i).trim()).equalsIgnoreCase(searchkey)){
                            searching2(FirebaseTimetablekey.get(i));
                        }
                    }
                }else{
                    Log.d("TAG", task.getException().getMessage()); //Don't ignore potential errors!
                }

            }

            private void searching2(String s) {
                rootRef = FirebaseDatabase.getInstance().getReference();
                orderRef = rootRef.child("Trainer_TimeTable").child(s);
                Log.d("서치","성공!  searching2 in");
                orderRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (task.isSuccessful()) {
                            Log.d("서치","성공! 서치 시작");
                            for (DataSnapshot ds : task.getResult().getChildren()) {
                                Log.d("서치","성공! 포문 in");
                                String key = ds.getKey();
                                String value = ds.getValue(String.class);
                                Log.d("서치","key is : "+key);
                                if(value.equals("null")){
                                    for(int i=0;i<dialogbtnAry.size();i++){
                                        String text = getResources().getResourceName(dialogbtnAry.get(i).getId());
                                        StringTokenizer st = new StringTokenizer(text,"_");
                                        while(st.hasMoreTokens()){
                                            text = st.nextToken();
                                        }
                                        //파이어베이스에 있는 키 값이랑, 버튼의 ID가 같다면 색을 바꿀 것
                                        if(key.equals(text)){
                                            dialogbtnAry.get(i).setBackgroundColor(Color.YELLOW);
                                            dialogbtnAry.get(i).setEnabled(true);
                                        }
                                    }
                                }else{
                                    for(int i=0;i<dialogbtnAry.size();i++){
                                        String text = getResources().getResourceName(dialogbtnAry.get(i).getId());
                                        StringTokenizer st = new StringTokenizer(text,"_");
                                        while(st.hasMoreTokens()){
                                            text = st.nextToken();
                                        }
                                        //파이어베이스에 있는 키 값이랑, 버튼의 ID가 같다면 색을 바꿀 것
                                        if(key.equals(text)){
                                            dialogbtnAry.get(i).setBackgroundColor(Color.BLACK);
                                            dialogbtnAry.get(i).setEnabled(false);
                                        }
                                    }
                                }
                            }
                        } else {
                            Log.d("TAG", task.getException().getMessage()); //Don't ignore potential errors!
                        }
                        Log.d("서치테이블",s);
                        for(int b=0;b<DialogTrainer.size();b++){
                            if(DialogTrainer.get(b).getUid().trim().equals(s.trim())){
                                tv.setText(DialogTrainer.get(b).getName());
                                tv.setTextColor(Color.BLACK);
                            }
                        }
                    }
                });
            }
        });
    }

    private void dialogbtnsave() {
        
            Dialog_A1 = (Button) findViewById(R.id.Dialog_A1);
            Dialog_A2 = (Button) findViewById(R.id.Dialog_A2);
            Dialog_A3 = (Button) findViewById(R.id.Dialog_A3);
            Dialog_A4 = (Button) findViewById(R.id.Dialog_A4);
            Dialog_A5 = (Button) findViewById(R.id.Dialog_A5);
            Dialog_A6 = (Button) findViewById(R.id.Dialog_A6);
            Dialog_A7 = (Button) findViewById(R.id.Dialog_A7);
            Dialog_A8 = (Button) findViewById(R.id.Dialog_A8);
            Dialog_A9 = (Button) findViewById(R.id.Dialog_A9);
            Dialog_A10= (Button) findViewById(R.id.Dialog_A10);

            Dialog_B1 = (Button) findViewById(R.id.Dialog_B1);
            Dialog_B2 = (Button) findViewById(R.id.Dialog_B2);
            Dialog_B3 = (Button) findViewById(R.id.Dialog_B3);
            Dialog_B4 = (Button) findViewById(R.id.Dialog_B4);
            Dialog_B5 = (Button) findViewById(R.id.Dialog_B5);
            Dialog_B6 = (Button) findViewById(R.id.Dialog_B6);
            Dialog_B7 = (Button) findViewById(R.id.Dialog_B7);
            Dialog_B8 = (Button) findViewById(R.id.Dialog_B8);
            Dialog_B9 = (Button) findViewById(R.id.Dialog_B9);
            Dialog_B10= (Button) findViewById(R.id.Dialog_B10);

            Dialog_C1 = (Button) findViewById(R.id.Dialog_C1);
            Dialog_C2 = (Button) findViewById(R.id.Dialog_C2);
            Dialog_C3 = (Button) findViewById(R.id.Dialog_C3);
            Dialog_C4 = (Button) findViewById(R.id.Dialog_C4);
            Dialog_C5 = (Button) findViewById(R.id.Dialog_C5);
            Dialog_C6 = (Button) findViewById(R.id.Dialog_C6);
            Dialog_C7 = (Button) findViewById(R.id.Dialog_C7);
            Dialog_C8 = (Button) findViewById(R.id.Dialog_C8);
            Dialog_C9 = (Button) findViewById(R.id.Dialog_C9);
            Dialog_C10= (Button) findViewById(R.id.Dialog_C10);

            Dialog_D1 = (Button) findViewById(R.id.Dialog_D1);
            Dialog_D2 = (Button) findViewById(R.id.Dialog_D2);
            Dialog_D3 = (Button) findViewById(R.id.Dialog_D3);
            Dialog_D4 = (Button) findViewById(R.id.Dialog_D4);
            Dialog_D5 = (Button) findViewById(R.id.Dialog_D5);
            Dialog_D6 = (Button) findViewById(R.id.Dialog_D6);
            Dialog_D7 = (Button) findViewById(R.id.Dialog_D7);
            Dialog_D8 = (Button) findViewById(R.id.Dialog_D8);
            Dialog_D9 = (Button) findViewById(R.id.Dialog_D9);
            Dialog_D10= (Button) findViewById(R.id.Dialog_D10);

            Dialog_E1 = (Button) findViewById(R.id.Dialog_E1);
            Dialog_E2 = (Button) findViewById(R.id.Dialog_E2);
            Dialog_E3 = (Button) findViewById(R.id.Dialog_E3);
            Dialog_E4 = (Button) findViewById(R.id.Dialog_E4);
            Dialog_E5 = (Button) findViewById(R.id.Dialog_E5);
            Dialog_E6 = (Button) findViewById(R.id.Dialog_E6);
            Dialog_E7 = (Button) findViewById(R.id.Dialog_E7);
            Dialog_E8 = (Button) findViewById(R.id.Dialog_E8);
            Dialog_E9 = (Button) findViewById(R.id.Dialog_E9);
            Dialog_E10= (Button) findViewById(R.id.Dialog_E10);

            Dialog_F1 = (Button) findViewById(R.id.Dialog_F1);
            Dialog_F2 = (Button) findViewById(R.id.Dialog_F2);
            Dialog_F3 = (Button) findViewById(R.id.Dialog_F3);
            Dialog_F4 = (Button) findViewById(R.id.Dialog_F4);
            Dialog_F5 = (Button) findViewById(R.id.Dialog_F5);
            Dialog_F6 = (Button) findViewById(R.id.Dialog_F6);
            Dialog_F7 = (Button) findViewById(R.id.Dialog_F7);
            Dialog_F8 = (Button) findViewById(R.id.Dialog_F8);
            Dialog_F9 = (Button) findViewById(R.id.Dialog_F9);
            Dialog_F10= (Button) findViewById(R.id.Dialog_F10);

            Dialog_G1 = (Button) findViewById(R.id.Dialog_G1);
            Dialog_G2 = (Button) findViewById(R.id.Dialog_G2);
            Dialog_G3 = (Button) findViewById(R.id.Dialog_G3);
            Dialog_G4 = (Button) findViewById(R.id.Dialog_G4);
            Dialog_G5 = (Button) findViewById(R.id.Dialog_G5);
            Dialog_G6 = (Button) findViewById(R.id.Dialog_G6);
            Dialog_G7 = (Button) findViewById(R.id.Dialog_G7);
            Dialog_G8 = (Button) findViewById(R.id.Dialog_G8);
            Dialog_G9 = (Button) findViewById(R.id.Dialog_G9);
            Dialog_G10= (Button) findViewById(R.id.Dialog_G10);
            
            //월 ~ 일 어레이리스트에 추가

            dialogbtnAry.add(Dialog_A1);  dialogbtnAry.add(Dialog_A2);  dialogbtnAry.add(Dialog_A3);  dialogbtnAry.add(Dialog_A4);  dialogbtnAry.add(Dialog_A5);
            dialogbtnAry.add(Dialog_A6);  dialogbtnAry.add(Dialog_A7);  dialogbtnAry.add(Dialog_A8);  dialogbtnAry.add(Dialog_A9);  dialogbtnAry.add(Dialog_A10);
    
            dialogbtnAry.add(Dialog_B1);  dialogbtnAry.add(Dialog_B2);  dialogbtnAry.add(Dialog_B3);  dialogbtnAry.add(Dialog_B4);  dialogbtnAry.add(Dialog_B5);
            dialogbtnAry.add(Dialog_B6);  dialogbtnAry.add(Dialog_B7);  dialogbtnAry.add(Dialog_B8);  dialogbtnAry.add(Dialog_B9);  dialogbtnAry.add(Dialog_B10);
    
            dialogbtnAry.add(Dialog_C1);  dialogbtnAry.add(Dialog_C2);  dialogbtnAry.add(Dialog_C3);  dialogbtnAry.add(Dialog_C4);  dialogbtnAry.add(Dialog_C5);
            dialogbtnAry.add(Dialog_C6);  dialogbtnAry.add(Dialog_C7);  dialogbtnAry.add(Dialog_C8);  dialogbtnAry.add(Dialog_C9);  dialogbtnAry.add(Dialog_C10);
    
            dialogbtnAry.add(Dialog_D1);  dialogbtnAry.add(Dialog_D2);  dialogbtnAry.add(Dialog_D3);  dialogbtnAry.add(Dialog_D4);  dialogbtnAry.add(Dialog_D5);
            dialogbtnAry.add(Dialog_D6);  dialogbtnAry.add(Dialog_D7);  dialogbtnAry.add(Dialog_D8);  dialogbtnAry.add(Dialog_D9);  dialogbtnAry.add(Dialog_D10);
    
            dialogbtnAry.add(Dialog_E1);  dialogbtnAry.add(Dialog_E2);  dialogbtnAry.add(Dialog_E3);  dialogbtnAry.add(Dialog_E4);  dialogbtnAry.add(Dialog_E5);
            dialogbtnAry.add(Dialog_E6);  dialogbtnAry.add(Dialog_E7);  dialogbtnAry.add(Dialog_E8);  dialogbtnAry.add(Dialog_E9);  dialogbtnAry.add(Dialog_E10);
    
            dialogbtnAry.add(Dialog_F1);  dialogbtnAry.add(Dialog_F2);  dialogbtnAry.add(Dialog_F3);  dialogbtnAry.add(Dialog_F4);  dialogbtnAry.add(Dialog_F5);
            dialogbtnAry.add(Dialog_F6);  dialogbtnAry.add(Dialog_F7);  dialogbtnAry.add(Dialog_F8);  dialogbtnAry.add(Dialog_F9);  dialogbtnAry.add(Dialog_F10);
    
            dialogbtnAry.add(Dialog_G1);  dialogbtnAry.add(Dialog_G2);  dialogbtnAry.add(Dialog_G3);  dialogbtnAry.add(Dialog_G4);  dialogbtnAry.add(Dialog_G5);
            dialogbtnAry.add(Dialog_G6);  dialogbtnAry.add(Dialog_G7);  dialogbtnAry.add(Dialog_G8);  dialogbtnAry.add(Dialog_G9);  dialogbtnAry.add(Dialog_G10);

        
    }
}