package com.example.finalproj;

import androidx.annotation.NonNull;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalproj.databinding.ActivityGuestPencilBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.StringTokenizer;

public class GuestPencil extends DrawerBaseActivity implements View.OnClickListener {

    ActivityGuestPencilBinding activityGuestPencilBinding;

    private DatabaseReference rootRef;
    private DatabaseReference orderRef;

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser user = mAuth.getCurrentUser();

    ArrayList<String> Firebasekey = new ArrayList<>();
    private String key;
    private DatabaseReference mDatabase;

    ArrayList<TrainerUser_guestpencil> trainerUser_guestpencilArrayList = new ArrayList<>();
    private String trainerPK="";


    Button btn_searchtrainer,btn_changeTime;
    TextView Gt_text_trainername;
    EditText edittext_trainername;

    Button A1,A2,A3,A4,A5,A6,A7,A8,A9,A10;
    Button B1,B2,B3,B4,B5,B6,B7,B8,B9,B10;
    Button C1,C2,C3,C4,C5,C6,C7,C8,C9,C10;
    Button D1,D2,D3,D4,D5,D6,D7,D8,D9,D10;
    Button E1,E2,E3,E4,E5,E6,E7,E8,E9,E10;
    Button F1,F2,F3,F4,F5,F6,F7,F8,F9,F10;
    Button G1,G2,G3,G4,G5,G6,G7,G8,G9,G10;

    RelativeLayout relativeLayout;
    ArrayList<Button> btnAry = new ArrayList<>();

    HashMap<String,Object> hashMap = new HashMap<>();

    ArrayList<String> timeTableUserPK = new ArrayList<>();
    private String temp_name="";
    private String temp_auth="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityGuestPencilBinding = ActivityGuestPencilBinding.inflate(getLayoutInflater());
        setContentView(activityGuestPencilBinding.getRoot());
        allocateActivityTitle("시간표");

        edittext_trainername = (EditText) findViewById(R.id.Gt_edittext_trainername);
        Gt_text_trainername = (TextView) findViewById(R.id.Gt_text_trainername);
        btn_searchtrainer = (Button) findViewById(R.id.Gt_btn_searchtrainer);
        btn_changeTime = (Button) findViewById(R.id.Gt_btn_changeTime);
        relativeLayout = (RelativeLayout) findViewById(R.id.RelativeLayout_searching);


        userSearching();
        btnsave();
        for(int i=0; i<btnAry.size();i++){
            btnAry.get(i).setBackgroundColor(Color.YELLOW);
            btnAry.get(i).setEnabled(true);
            btnAry.get(i).setOnClickListener(this);
        }
        searching();//

    }

    @Override
    public void onClick(View v){
        Button newButton = (Button) v;
        String UserPK = user.getUid();
//        String UserPK = "mOdjnMkh5NRxTenMNk29LYNy2xt1";
        String btnAdr = "";

        HashMap<String,Object> hashMap = new HashMap<>();

        for(Button button : btnAry){
            if(button==newButton){
                //버튼 클릭 메소드
                ColorDrawable colorDrawable = (ColorDrawable) button.getBackground();
                int bg = colorDrawable.getColor();
                btnAdr = getResources().getResourceName(button.getId());
                StringTokenizer st = new StringTokenizer(btnAdr,"/");
                while(st.hasMoreTokens()){
                    btnAdr = st.nextToken();
                }

                Log.d("서치",btnAdr+"의 색 : "+String.valueOf(bg));

                if(String.valueOf(bg).equals("-256")){
                    //색상이 yellow일 경우
                    AlertDialog.Builder dialog = new AlertDialog.Builder(GuestPencil.this);
                    dialog.setTitle("추가하기");
                    dialog.setMessage("해당 시간에 PT를 추가 하시겠 습니까?");
                    String finalBtnAdr = btnAdr;
                    dialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            mDatabase = FirebaseDatabase.getInstance().getReference();
                            hashMap.put(finalBtnAdr,UserPK);
                            mDatabase.child("Trainer_TimeTable").child(" "+trainerPK).updateChildren(hashMap);
                            Toast.makeText(GuestPencil.this,"추가 되었습니다.",Toast.LENGTH_SHORT).show();
                            button.setBackgroundColor(Color.BLUE);//파란 색으로
                        }
                    });
                    dialog.setNegativeButton("취소",null);
                    dialog.show();
                }
                if(String.valueOf(bg).equals("-16776961")){
                    //색상이 blue 일 경우
                    AlertDialog.Builder dialog = new AlertDialog.Builder(GuestPencil.this);
                    dialog.setTitle("삭제하기");
                    dialog.setMessage("해당 시간에 PT를 삭제 하시겠 습니까?");
                    String finalBtnAdr1 = btnAdr;
                    dialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            mDatabase = FirebaseDatabase.getInstance().getReference();
                            hashMap.put(finalBtnAdr1,"null");
                            mDatabase.child("Trainer_TimeTable").child(" "+trainerPK).updateChildren(hashMap);
                            Toast.makeText(GuestPencil.this,"삭제 되었습니다.",Toast.LENGTH_SHORT).show();
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
                    timeTableUserPK.add(ID);
                }
                for(int i=0; i<timeTableUserPK.size();i++){
                    userSearching2(timeTableUserPK.get(i));
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
                            trainerUser_guestpencilArrayList.add(new TrainerUser_guestpencil(temp_auth,temp_name,s));
                        } else {
                            Log.d("TAG", task.getException().getMessage()); //Don't ignore potential errors!
                        }
                    }
                });
            }
        });
    }

    private void searching() {
        rootRef = FirebaseDatabase.getInstance().getReference();
        orderRef = rootRef.child("Trainer_TimeTable");
//        String UserPK = user.getUid();
        String UserPK = "mOdjnMkh5NRxTenMNk29LYNy2xt1";
        orderRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                Boolean b=true;
                if (task.isSuccessful()) {
                    Firebasekey.clear();
                    for (DataSnapshot ds : task.getResult().getChildren()) {
                        key = ds.getKey();
                        Firebasekey.add(key);
                    }
                    for(int i=0; i<Firebasekey.size();i++){
                        searching2(Firebasekey.get(i));
                    }
                } else {
                    Log.d("TAG", task.getException().getMessage()); //Don't ignore potential errors!
                }
            }

            private void searching2(String s) {
                rootRef = FirebaseDatabase.getInstance().getReference();
                orderRef = rootRef.child("Trainer_TimeTable").child(s);
                orderRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DataSnapshot ds : task.getResult().getChildren()) {
                                String val = ds.getValue(String.class);
                                //user.getUid
                                //내가 해당 강사에게 포함 되어 있을 경우
                                if(val.equals(UserPK.trim())){
                                    searching3(s);
                                }else{
                                    //표현하지 않음
                                }
                            }
                        } else {
                            Log.d("TAG", task.getException().getMessage()); //Don't ignore potential errors!
                        }
                    }
                    private void searching3(String s) {
                        rootRef = FirebaseDatabase.getInstance().getReference();
                        orderRef = rootRef.child("Trainer_TimeTable").child(s);
                        orderRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DataSnapshot> task) {
                                for(DataSnapshot ds : task.getResult().getChildren()){
                                    String ID = ds.getKey();
                                    String value = ds.getValue(String.class);
                                    //user.getUid
                                    //내가 해당 강사에게 포함 되어 있을 경우
                                    if(value.equals(UserPK.trim())){
                                        //버튼 색 바꾸고, 내 버튼으로 해주기
                                        for(int i=0;i<btnAry.size();i++){
                                            String text = getResources().getResourceName(btnAry.get(i).getId());
                                            StringTokenizer st = new StringTokenizer(text,"/");
                                            while(st.hasMoreTokens()){
                                                text = st.nextToken();
                                            }
                                            //파이어베이스에 있는 키 값이랑, 버튼의 ID가 같다면 색을 바꿀 것
                                            if(ID.equals(text)){
                                                btnAry.get(i).setBackgroundColor(Color.BLUE);
                                                btnAry.get(i).setEnabled(true);//파란 버튼 클릭 가능하게
                                            }
                                        }
                                    }
                                    else if(value.equals("null")){
                                        for(int i=0;i<btnAry.size();i++){
                                            String text = getResources().getResourceName(btnAry.get(i).getId());
                                            StringTokenizer st = new StringTokenizer(text,"/");
                                            while(st.hasMoreTokens()){
                                                text = st.nextToken();
                                            }
                                            //파이어베이스에 있는 키 값이랑, 버튼의 ID가 같다면 색을 바꿀 것
                                            if(ID.equals(text)){
                                                btnAry.get(i).setBackgroundColor(Color.YELLOW);
                                                btnAry.get(i).setEnabled(true);//노란 버튼 클릭 가능하게
                                            }
                                        }
                                    }
                                    else {
                                        for(int i=0;i<btnAry.size();i++){
                                            String text = getResources().getResourceName(btnAry.get(i).getId());

                                            StringTokenizer st = new StringTokenizer(text,"/");
                                            while(st.hasMoreTokens()){
                                                text = st.nextToken();
                                            }

                                            //파이어베이스에 있는 키 값이랑, 버튼의 ID가 같다면 색을 바꿀 것
                                            if(ID.equals(text)){
                                                btnAry.get(i).setBackgroundColor(Color.BLACK);
                                                btnAry.get(i).setEnabled(false);
                                            }
                                        }
                                    }

                                }
                            }
                        });//searching3 끝
                        for(int z=0;z<trainerUser_guestpencilArrayList.size();z++){
                            if(trainerUser_guestpencilArrayList.get(z).getUid().trim().equals(s.trim())){
                                //검색하는 테이블의 키와,저장되어있는 유저의 키가 같다면
                                trainerPK=trainerUser_guestpencilArrayList.get(z).getUid();
                                Gt_text_trainername.setText(trainerUser_guestpencilArrayList.get(z).getName().trim());
                                Gt_text_trainername.setTextColor(Color.BLACK);
                            }
                        }
                    }
                });
            }
        });
    }
    private void btnsave() {
        A1 = (Button) findViewById(R.id.A1);
        A2 = (Button) findViewById(R.id.A2);
        A3 = (Button) findViewById(R.id.A3);
        A4 = (Button) findViewById(R.id.A4);
        A5 = (Button) findViewById(R.id.A5);
        A6 = (Button) findViewById(R.id.A6);
        A7 = (Button) findViewById(R.id.A7);
        A8 = (Button) findViewById(R.id.A8);
        A9 = (Button) findViewById(R.id.A9);
        A10= (Button) findViewById(R.id.A10);

        B1 = (Button) findViewById(R.id.B1);
        B2 = (Button) findViewById(R.id.B2);
        B3 = (Button) findViewById(R.id.B3);
        B4 = (Button) findViewById(R.id.B4);
        B5 = (Button) findViewById(R.id.B5);
        B6 = (Button) findViewById(R.id.B6);
        B7 = (Button) findViewById(R.id.B7);
        B8 = (Button) findViewById(R.id.B8);
        B9 = (Button) findViewById(R.id.B9);
        B10= (Button) findViewById(R.id.B10);

        C1 = (Button) findViewById(R.id.C1);
        C2 = (Button) findViewById(R.id.C2);
        C3 = (Button) findViewById(R.id.C3);
        C4 = (Button) findViewById(R.id.C4);
        C5 = (Button) findViewById(R.id.C5);
        C6 = (Button) findViewById(R.id.C6);
        C7 = (Button) findViewById(R.id.C7);
        C8 = (Button) findViewById(R.id.C8);
        C9 = (Button) findViewById(R.id.C9);
        C10= (Button) findViewById(R.id.C10);

        D1 = (Button) findViewById(R.id.D1);
        D2 = (Button) findViewById(R.id.D2);
        D3 = (Button) findViewById(R.id.D3);
        D4 = (Button) findViewById(R.id.D4);
        D5 = (Button) findViewById(R.id.D5);
        D6 = (Button) findViewById(R.id.D6);
        D7 = (Button) findViewById(R.id.D7);
        D8 = (Button) findViewById(R.id.D8);
        D9 = (Button) findViewById(R.id.D9);
        D10= (Button) findViewById(R.id.D10);

        E1 = (Button) findViewById(R.id.E1);
        E2 = (Button) findViewById(R.id.E2);
        E3 = (Button) findViewById(R.id.E3);
        E4 = (Button) findViewById(R.id.E4);
        E5 = (Button) findViewById(R.id.E5);
        E6 = (Button) findViewById(R.id.E6);
        E7 = (Button) findViewById(R.id.E7);
        E8 = (Button) findViewById(R.id.E8);
        E9 = (Button) findViewById(R.id.E9);
        E10= (Button) findViewById(R.id.E10);

        F1 = (Button) findViewById(R.id.F1);
        F2 = (Button) findViewById(R.id.F2);
        F3 = (Button) findViewById(R.id.F3);
        F4 = (Button) findViewById(R.id.F4);
        F5 = (Button) findViewById(R.id.F5);
        F6 = (Button) findViewById(R.id.F6);
        F7 = (Button) findViewById(R.id.F7);
        F8 = (Button) findViewById(R.id.F8);
        F9 = (Button) findViewById(R.id.F9);
        F10= (Button) findViewById(R.id.F10);

        G1 = (Button) findViewById(R.id.G1);
        G2 = (Button) findViewById(R.id.G2);
        G3 = (Button) findViewById(R.id.G3);
        G4 = (Button) findViewById(R.id.G4);
        G5 = (Button) findViewById(R.id.G5);
        G6 = (Button) findViewById(R.id.G6);
        G7 = (Button) findViewById(R.id.G7);
        G8 = (Button) findViewById(R.id.G8);
        G9 = (Button) findViewById(R.id.G9);
        G10= (Button) findViewById(R.id.G10);
        //월 ~ 일 어레이리스트에 추가
        btnAry.add(A1); btnAry.add(A2); btnAry.add(A3); btnAry.add(A4); btnAry.add(A5);
        btnAry.add(A6); btnAry.add(A7); btnAry.add(A8); btnAry.add(A9); btnAry.add(A10);

        btnAry.add(B1); btnAry.add(B2); btnAry.add(B3); btnAry.add(B4); btnAry.add(B5);
        btnAry.add(B6); btnAry.add(B7); btnAry.add(B8); btnAry.add(B9); btnAry.add(B10);

        btnAry.add(C1); btnAry.add(C2); btnAry.add(C3); btnAry.add(C4); btnAry.add(C5);
        btnAry.add(C6); btnAry.add(C7); btnAry.add(C8); btnAry.add(C9); btnAry.add(C10);

        btnAry.add(D1); btnAry.add(D2); btnAry.add(D3); btnAry.add(D4); btnAry.add(D5);
        btnAry.add(D6); btnAry.add(D7); btnAry.add(D8); btnAry.add(D9); btnAry.add(D10);

        btnAry.add(E1); btnAry.add(E2); btnAry.add(E3); btnAry.add(E4); btnAry.add(E5);
        btnAry.add(E6); btnAry.add(E7); btnAry.add(E8); btnAry.add(E9); btnAry.add(E10);

        btnAry.add(F1); btnAry.add(F2); btnAry.add(F3); btnAry.add(F4); btnAry.add(F5);
        btnAry.add(F6); btnAry.add(F7); btnAry.add(F8); btnAry.add(F9); btnAry.add(F10);

        btnAry.add(G1); btnAry.add(G2); btnAry.add(G3); btnAry.add(G4); btnAry.add(G5);
        btnAry.add(G6); btnAry.add(G7); btnAry.add(G8); btnAry.add(G9); btnAry.add(G10);

    }

    //강사 검색 버튼 클릭 시
    public void buttonSearching(View v){
        for(int i=0;i<trainerUser_guestpencilArrayList.size();i++){
            if(edittext_trainername.getText().toString().trim().equals(trainerUser_guestpencilArrayList.get(i).getName())){
                //입력 된 값과, user테이블에 저장되어 있는 이름이 같다면
                if(trainerUser_guestpencilArrayList.get(i).getAuth().equals("트레이너")){
                    //근데 그 유저가 트레이너 라면
                    Intent intent = new Intent(GuestPencil.this,Search_Timetable.class);
                    intent.putExtra("searchKey",trainerUser_guestpencilArrayList.get(i).getUid().trim());
                    //그 트레이너의 PK를 넘겨줘라
                    startActivity(intent);
                }
            }
        }
    }
    public void buttonChangeTime(View view){
        //어떻게 하지?
        for(int i=0;i<btnAry.size();i++){
            ColorDrawable color = (ColorDrawable) btnAry.get(i).getBackground();
            int bgColor = color.getColor();
            if(String.valueOf(bgColor).trim().equals("-5317")
                    ||String.valueOf(bgColor).trim().equals("-65536")
            ){
                //색이 yellow / red일 경우
                btnAry.get(i).setEnabled(true);
                //버튼 클릭 가능하게
            }
        }
    }
}
