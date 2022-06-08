package com.example.finalproj;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.system.StructTimespec;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.finalproj.databinding.ActivityDashboardBinding;
import com.example.finalproj.databinding.ActivityTrainerTimeTableBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.StringTokenizer;

public class TrainerTimeTable extends TrainerDrawerBaseActivity implements View.OnClickListener {

    ActivityTrainerTimeTableBinding activityTrainerBinding;

    DatabaseReference rootRef;
    DatabaseReference orderRef;

    Button Trainer_TimeTable_A1,Trainer_TimeTable_A2,Trainer_TimeTable_A3,Trainer_TimeTable_A4,Trainer_TimeTable_A5,Trainer_TimeTable_A6,Trainer_TimeTable_A7,Trainer_TimeTable_A8,Trainer_TimeTable_A9,Trainer_TimeTable_A10;
    Button Trainer_TimeTable_B1,Trainer_TimeTable_B2,Trainer_TimeTable_B3,Trainer_TimeTable_B4,Trainer_TimeTable_B5,Trainer_TimeTable_B6,Trainer_TimeTable_B7,Trainer_TimeTable_B8,Trainer_TimeTable_B9,Trainer_TimeTable_B10;
    Button Trainer_TimeTable_C1,Trainer_TimeTable_C2,Trainer_TimeTable_C3,Trainer_TimeTable_C4,Trainer_TimeTable_C5,Trainer_TimeTable_C6,Trainer_TimeTable_C7,Trainer_TimeTable_C8,Trainer_TimeTable_C9,Trainer_TimeTable_C10;
    Button Trainer_TimeTable_D1,Trainer_TimeTable_D2,Trainer_TimeTable_D3,Trainer_TimeTable_D4,Trainer_TimeTable_D5,Trainer_TimeTable_D6,Trainer_TimeTable_D7,Trainer_TimeTable_D8,Trainer_TimeTable_D9,Trainer_TimeTable_D10;
    Button Trainer_TimeTable_E1,Trainer_TimeTable_E2,Trainer_TimeTable_E3,Trainer_TimeTable_E4,Trainer_TimeTable_E5,Trainer_TimeTable_E6,Trainer_TimeTable_E7,Trainer_TimeTable_E8,Trainer_TimeTable_E9,Trainer_TimeTable_E10;
    Button Trainer_TimeTable_F1,Trainer_TimeTable_F2,Trainer_TimeTable_F3,Trainer_TimeTable_F4,Trainer_TimeTable_F5,Trainer_TimeTable_F6,Trainer_TimeTable_F7,Trainer_TimeTable_F8,Trainer_TimeTable_F9,Trainer_TimeTable_F10;
    Button Trainer_TimeTable_G1,Trainer_TimeTable_G2,Trainer_TimeTable_G3,Trainer_TimeTable_G4,Trainer_TimeTable_G5,Trainer_TimeTable_G6,Trainer_TimeTable_G7,Trainer_TimeTable_G8,Trainer_TimeTable_G9,Trainer_TimeTable_G10;

    ArrayList<Button> Trainer_TimeTable_btnAry = new ArrayList<>();

    ArrayList<TimtTableUser> timtTableUserArrayList = new ArrayList<>();
    ArrayList<TimtTableUser> SureTimeTableUserArrayList = new ArrayList<>();
    ArrayList<String> timeTableUserPK = new ArrayList<>();

    String temp_name="";

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();

    String userPK=user.getUid();//수정 필요

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

//        userPK = user.getUid();
        super.onCreate(savedInstanceState);
        activityTrainerBinding = ActivityTrainerTimeTableBinding.inflate(getLayoutInflater());
        setContentView(activityTrainerBinding.getRoot());
        allocateActivityTitle("시간표 조회");
        TrainerTimetablebtnSave();
        userSearching();
        for(int i=0; i<Trainer_TimeTable_btnAry.size();i++){
            Trainer_TimeTable_btnAry.get(i).setOnClickListener(this);
            Trainer_TimeTable_btnAry.get(i).setBackgroundColor(Color.YELLOW);
        }
        rootRef = FirebaseDatabase.getInstance().getReference();
        orderRef = rootRef.child("Trainer_TimeTable");
        orderRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                for(DataSnapshot ds : task.getResult().getChildren()){
                    String ID = ds.getKey();
                    if(userPK.trim().equals(ID.trim())){
                        searching(ID);
                    }
                }
            }

            private void searching(String userkey) {
                rootRef = FirebaseDatabase.getInstance().getReference();
                orderRef = rootRef.child("Trainer_TimeTable").child(userkey);
                orderRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        for(DataSnapshot ds : task.getResult().getChildren()){
                            String ID = ds.getKey();
                            String value = ds.getValue(String.class);
                            for(int i=0;i<Trainer_TimeTable_btnAry.size();i++){
                                String text = getResources().getResourceName(Trainer_TimeTable_btnAry.get(i).getId());
                                StringTokenizer st = new StringTokenizer(text,"_");
                                while(st.hasMoreTokens()){
                                    text = st.nextToken();
                                }
                                //파이어베이스에 있는 키 값이랑, 버튼의 ID가 같다면 색을 바꿀 것
                                if(ID.equals(text)){
                                    if(value.equals("null")){
                                        Trainer_TimeTable_btnAry.get(i).setBackgroundColor(Color.YELLOW);
                                        Trainer_TimeTable_btnAry.get(i).setEnabled(false);
                                    }else {
                                        Trainer_TimeTable_btnAry.get(i).setBackgroundColor(Color.BLUE);
                                        Trainer_TimeTable_btnAry.get(i).setEnabled(true);
                                        for(int j=0;j<timtTableUserArrayList.size();j++){
                                            if(value.trim().equals(timtTableUserArrayList.get(j).getPK().trim())){
                                                //value값이랑 pk가 같은 유저를 찾아서 이름 바꾸기
                                                Trainer_TimeTable_btnAry.get(i).setText(timtTableUserArrayList.get(j).getName().trim());//value가 아닌 이름이 들어가야함
                                                Trainer_TimeTable_btnAry.get(i).setTextColor(Color.WHITE);

                                                SureTimeTableUserArrayList.add(new TimtTableUser(timtTableUserArrayList.get(j).getPK().trim(),timtTableUserArrayList.get(j).getName(),text));
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                });
            }
        });
    }//oncreate끝

    @Override
    public void onClick(View v){
        Button newButton = (Button) v;
        String UserPK = "";
        Calendar calendar = Calendar.getInstance();
        String Date;
        int tYear=calendar.get(Calendar.YEAR);
        int tMonth=calendar.get(Calendar.MONTH)+1;
        int tDay=calendar.get(Calendar.DAY_OF_MONTH);
        String stYear= String.valueOf(tYear);
        String stMonth= String.valueOf(tMonth);
        String stDay= String.valueOf(tDay);
        if(tMonth<10){
            stMonth="0"+stMonth;
        }
        if(tDay<10){
            stDay="0"+stDay;
        }
        Date = stYear+"-"+stMonth+"-"+stDay;



        for(Button button : Trainer_TimeTable_btnAry){
            if(button==newButton){
                //버튼 클릭 메소드
                ColorDrawable colorDrawable = (ColorDrawable) button.getBackground();
                int bg = colorDrawable.getColor();
                Log.d("타임 테이블","색상 0: "+String.valueOf(bg));
                Log.d("타임","버튼 클릭"+"/"+button.getText());
                Intent intent = new Intent(TrainerTimeTable.this,Dialog_HealthyList.class);
                for(int i=0;i<SureTimeTableUserArrayList.size();i++){
                    if(button.getText().equals(SureTimeTableUserArrayList.get(i).getName())){
                        //버튼에 등록된 이름과, 파란 버튼 ArrayList에 저장 된 이름이 같다면
                        UserPK = SureTimeTableUserArrayList.get(i).getPK();
                    }
                }
                intent.putExtra("PK",UserPK);
                intent.putExtra("Date",Date);
                startActivity(intent);
            }
        }

    }

    private void userSearching() {
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
                            }
                            timtTableUserArrayList.add(new TimtTableUser(s,temp_name));
                        } else {
                            Log.d("TAG", task.getException().getMessage()); //Don't ignore potential errors!
                        }
                    }
                });
            }
        });
    }

    private void TrainerTimetablebtnSave() {
        Trainer_TimeTable_A1 = (Button) findViewById(R.id.Trainer_A1);
        Trainer_TimeTable_A2 = (Button) findViewById(R.id.Trainer_A2);
        Trainer_TimeTable_A3 = (Button) findViewById(R.id.Trainer_A3);
        Trainer_TimeTable_A4 = (Button) findViewById(R.id.Trainer_A4);
        Trainer_TimeTable_A5 = (Button) findViewById(R.id.Trainer_A5);
        Trainer_TimeTable_A6 = (Button) findViewById(R.id.Trainer_A6);
        Trainer_TimeTable_A7 = (Button) findViewById(R.id.Trainer_A7);
        Trainer_TimeTable_A8 = (Button) findViewById(R.id.Trainer_A8);
        Trainer_TimeTable_A9 = (Button) findViewById(R.id.Trainer_A9);
        Trainer_TimeTable_A10= (Button) findViewById(R.id.Trainer_A10);

        Trainer_TimeTable_B1 = (Button) findViewById(R.id.Trainer_B1);
        Trainer_TimeTable_B2 = (Button) findViewById(R.id.Trainer_B2);
        Trainer_TimeTable_B3 = (Button) findViewById(R.id.Trainer_B3);
        Trainer_TimeTable_B4 = (Button) findViewById(R.id.Trainer_B4);
        Trainer_TimeTable_B5 = (Button) findViewById(R.id.Trainer_B5);
        Trainer_TimeTable_B6 = (Button) findViewById(R.id.Trainer_B6);
        Trainer_TimeTable_B7 = (Button) findViewById(R.id.Trainer_B7);
        Trainer_TimeTable_B8 = (Button) findViewById(R.id.Trainer_B8);
        Trainer_TimeTable_B9 = (Button) findViewById(R.id.Trainer_B9);
        Trainer_TimeTable_B10= (Button) findViewById(R.id.Trainer_B10);

        Trainer_TimeTable_C1 = (Button) findViewById(R.id.Trainer_C1);
        Trainer_TimeTable_C2 = (Button) findViewById(R.id.Trainer_C2);
        Trainer_TimeTable_C3 = (Button) findViewById(R.id.Trainer_C3);
        Trainer_TimeTable_C4 = (Button) findViewById(R.id.Trainer_C4);
        Trainer_TimeTable_C5 = (Button) findViewById(R.id.Trainer_C5);
        Trainer_TimeTable_C6 = (Button) findViewById(R.id.Trainer_C6);
        Trainer_TimeTable_C7 = (Button) findViewById(R.id.Trainer_C7);
        Trainer_TimeTable_C8 = (Button) findViewById(R.id.Trainer_C8);
        Trainer_TimeTable_C9 = (Button) findViewById(R.id.Trainer_C9);
        Trainer_TimeTable_C10= (Button) findViewById(R.id.Trainer_C10);

        Trainer_TimeTable_D1 = (Button) findViewById(R.id.Trainer_D1);
        Trainer_TimeTable_D2 = (Button) findViewById(R.id.Trainer_D2);
        Trainer_TimeTable_D3 = (Button) findViewById(R.id.Trainer_D3);
        Trainer_TimeTable_D4 = (Button) findViewById(R.id.Trainer_D4);
        Trainer_TimeTable_D5 = (Button) findViewById(R.id.Trainer_D5);
        Trainer_TimeTable_D6 = (Button) findViewById(R.id.Trainer_D6);
        Trainer_TimeTable_D7 = (Button) findViewById(R.id.Trainer_D7);
        Trainer_TimeTable_D8 = (Button) findViewById(R.id.Trainer_D8);
        Trainer_TimeTable_D9 = (Button) findViewById(R.id.Trainer_D9);
        Trainer_TimeTable_D10= (Button) findViewById(R.id.Trainer_D10);

        Trainer_TimeTable_E1 = (Button) findViewById(R.id.Trainer_E1);
        Trainer_TimeTable_E2 = (Button) findViewById(R.id.Trainer_E2);
        Trainer_TimeTable_E3 = (Button) findViewById(R.id.Trainer_E3);
        Trainer_TimeTable_E4 = (Button) findViewById(R.id.Trainer_E4);
        Trainer_TimeTable_E5 = (Button) findViewById(R.id.Trainer_E5);
        Trainer_TimeTable_E6 = (Button) findViewById(R.id.Trainer_E6);
        Trainer_TimeTable_E7 = (Button) findViewById(R.id.Trainer_E7);
        Trainer_TimeTable_E8 = (Button) findViewById(R.id.Trainer_E8);
        Trainer_TimeTable_E9 = (Button) findViewById(R.id.Trainer_E9);
        Trainer_TimeTable_E10= (Button) findViewById(R.id.Trainer_E10);

        Trainer_TimeTable_F1 = (Button) findViewById(R.id.Trainer_F1);
        Trainer_TimeTable_F2 = (Button) findViewById(R.id.Trainer_F2);
        Trainer_TimeTable_F3 = (Button) findViewById(R.id.Trainer_F3);
        Trainer_TimeTable_F4 = (Button) findViewById(R.id.Trainer_F4);
        Trainer_TimeTable_F5 = (Button) findViewById(R.id.Trainer_F5);
        Trainer_TimeTable_F6 = (Button) findViewById(R.id.Trainer_F6);
        Trainer_TimeTable_F7 = (Button) findViewById(R.id.Trainer_F7);
        Trainer_TimeTable_F8 = (Button) findViewById(R.id.Trainer_F8);
        Trainer_TimeTable_F9 = (Button) findViewById(R.id.Trainer_F9);
        Trainer_TimeTable_F10= (Button) findViewById(R.id.Trainer_F10);

        Trainer_TimeTable_G1 = (Button) findViewById(R.id.Trainer_G1);
        Trainer_TimeTable_G2 = (Button) findViewById(R.id.Trainer_G2);
        Trainer_TimeTable_G3 = (Button) findViewById(R.id.Trainer_G3);
        Trainer_TimeTable_G4 = (Button) findViewById(R.id.Trainer_G4);
        Trainer_TimeTable_G5 = (Button) findViewById(R.id.Trainer_G5);
        Trainer_TimeTable_G6 = (Button) findViewById(R.id.Trainer_G6);
        Trainer_TimeTable_G7 = (Button) findViewById(R.id.Trainer_G7);
        Trainer_TimeTable_G8 = (Button) findViewById(R.id.Trainer_G8);
        Trainer_TimeTable_G9 = (Button) findViewById(R.id.Trainer_G9);
        Trainer_TimeTable_G10= (Button) findViewById(R.id.Trainer_G10);
        //월 ~ 일 어레이리스트에 추가

        Trainer_TimeTable_btnAry.add(Trainer_TimeTable_A1); Trainer_TimeTable_btnAry.add(Trainer_TimeTable_A2); Trainer_TimeTable_btnAry.add(Trainer_TimeTable_A3); Trainer_TimeTable_btnAry.add(Trainer_TimeTable_A4); Trainer_TimeTable_btnAry.add(Trainer_TimeTable_A5);
        Trainer_TimeTable_btnAry.add(Trainer_TimeTable_A6); Trainer_TimeTable_btnAry.add(Trainer_TimeTable_A7); Trainer_TimeTable_btnAry.add(Trainer_TimeTable_A8); Trainer_TimeTable_btnAry.add(Trainer_TimeTable_A9); Trainer_TimeTable_btnAry.add(Trainer_TimeTable_A10);

        Trainer_TimeTable_btnAry.add(Trainer_TimeTable_B1); Trainer_TimeTable_btnAry.add(Trainer_TimeTable_B2); Trainer_TimeTable_btnAry.add(Trainer_TimeTable_B3); Trainer_TimeTable_btnAry.add(Trainer_TimeTable_B4); Trainer_TimeTable_btnAry.add(Trainer_TimeTable_B5);
        Trainer_TimeTable_btnAry.add(Trainer_TimeTable_B6); Trainer_TimeTable_btnAry.add(Trainer_TimeTable_B7); Trainer_TimeTable_btnAry.add(Trainer_TimeTable_B8); Trainer_TimeTable_btnAry.add(Trainer_TimeTable_B9); Trainer_TimeTable_btnAry.add(Trainer_TimeTable_B10);

        Trainer_TimeTable_btnAry.add(Trainer_TimeTable_C1); Trainer_TimeTable_btnAry.add(Trainer_TimeTable_C2); Trainer_TimeTable_btnAry.add(Trainer_TimeTable_C3); Trainer_TimeTable_btnAry.add(Trainer_TimeTable_C4); Trainer_TimeTable_btnAry.add(Trainer_TimeTable_C5);
        Trainer_TimeTable_btnAry.add(Trainer_TimeTable_C6); Trainer_TimeTable_btnAry.add(Trainer_TimeTable_C7); Trainer_TimeTable_btnAry.add(Trainer_TimeTable_C8); Trainer_TimeTable_btnAry.add(Trainer_TimeTable_C9); Trainer_TimeTable_btnAry.add(Trainer_TimeTable_C10);

        Trainer_TimeTable_btnAry.add(Trainer_TimeTable_D1); Trainer_TimeTable_btnAry.add(Trainer_TimeTable_D2); Trainer_TimeTable_btnAry.add(Trainer_TimeTable_D3); Trainer_TimeTable_btnAry.add(Trainer_TimeTable_D4); Trainer_TimeTable_btnAry.add(Trainer_TimeTable_D5);
        Trainer_TimeTable_btnAry.add(Trainer_TimeTable_D6); Trainer_TimeTable_btnAry.add(Trainer_TimeTable_D7); Trainer_TimeTable_btnAry.add(Trainer_TimeTable_D8); Trainer_TimeTable_btnAry.add(Trainer_TimeTable_D9); Trainer_TimeTable_btnAry.add(Trainer_TimeTable_D10);

        Trainer_TimeTable_btnAry.add(Trainer_TimeTable_E1); Trainer_TimeTable_btnAry.add(Trainer_TimeTable_E2); Trainer_TimeTable_btnAry.add(Trainer_TimeTable_E3); Trainer_TimeTable_btnAry.add(Trainer_TimeTable_E4); Trainer_TimeTable_btnAry.add(Trainer_TimeTable_E5);
        Trainer_TimeTable_btnAry.add(Trainer_TimeTable_E6); Trainer_TimeTable_btnAry.add(Trainer_TimeTable_E7); Trainer_TimeTable_btnAry.add(Trainer_TimeTable_E8); Trainer_TimeTable_btnAry.add(Trainer_TimeTable_E9); Trainer_TimeTable_btnAry.add(Trainer_TimeTable_E10);

        Trainer_TimeTable_btnAry.add(Trainer_TimeTable_F1); Trainer_TimeTable_btnAry.add(Trainer_TimeTable_F2); Trainer_TimeTable_btnAry.add(Trainer_TimeTable_F3); Trainer_TimeTable_btnAry.add(Trainer_TimeTable_F4); Trainer_TimeTable_btnAry.add(Trainer_TimeTable_F5);
        Trainer_TimeTable_btnAry.add(Trainer_TimeTable_F6); Trainer_TimeTable_btnAry.add(Trainer_TimeTable_F7); Trainer_TimeTable_btnAry.add(Trainer_TimeTable_F8); Trainer_TimeTable_btnAry.add(Trainer_TimeTable_F9); Trainer_TimeTable_btnAry.add(Trainer_TimeTable_F10);

        Trainer_TimeTable_btnAry.add(Trainer_TimeTable_G1); Trainer_TimeTable_btnAry.add(Trainer_TimeTable_G2); Trainer_TimeTable_btnAry.add(Trainer_TimeTable_G3); Trainer_TimeTable_btnAry.add(Trainer_TimeTable_G4); Trainer_TimeTable_btnAry.add(Trainer_TimeTable_G5);
        Trainer_TimeTable_btnAry.add(Trainer_TimeTable_G6); Trainer_TimeTable_btnAry.add(Trainer_TimeTable_G7); Trainer_TimeTable_btnAry.add(Trainer_TimeTable_G8); Trainer_TimeTable_btnAry.add(Trainer_TimeTable_G9); Trainer_TimeTable_btnAry.add(Trainer_TimeTable_G10);


    }
}