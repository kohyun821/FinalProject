package com.example.finalproj;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.view.MotionEvent;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.ZoomControls;
import android.widget.LinearLayout;
import android.view.View.OnClickListener;
import android.util.DisplayMetrics;
import android.view.View;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.app.Activity;

import com.example.finalproj.databinding.ActivityInBody1Binding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class InBody1 extends DrawerBaseActivity {

    ActivityInBody1Binding activityInBody1Binding;
    DatabaseReference mDatabase;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();
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
    protected void onCreate(Bundle savedInstanceState) { //값 입력

        super.onCreate(savedInstanceState);
        activityInBody1Binding = ActivityInBody1Binding.inflate(getLayoutInflater());
        setContentView(activityInBody1Binding.getRoot());
        allocateActivityTitle("Inbody");

        EditText editTextDate = findViewById(R.id.editTextDate);//골격근량
        EditText editTextDate2 = findViewById(R.id.editTextDate2);//체중
        EditText editTextDate3 = findViewById(R.id.editTextDate3);//체지방량
        Button IFIP = findViewById(R.id.IFIP);//기록 버튼

        mDatabase = FirebaseDatabase.getInstance().getReference();
        //기록 버튼이 눌렸을 시
        IFIP.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                //입력값 변수에 담기
                String skeletal_mass = editTextDate.getText().toString().trim();//골격근량
                String fat = editTextDate2.getText().toString().trim();//체중
                String fat_mass = editTextDate3.getText().toString().trim();//체지방량
                Calendar calendar = Calendar.getInstance();
                int tYear=calendar.get(Calendar.YEAR);
                int tMonth=calendar.get(Calendar.MONTH)+1;
                int tDay=calendar.get(Calendar.DAY_OF_MONTH);

                String y=String.valueOf(tYear);
                String m=String.valueOf(tMonth);
                String d=String.valueOf(tDay);

                Log.d("인바디",String.valueOf(tYear)+"/"+String.valueOf(tMonth)+"/"+tDay);
                if(tMonth<10){
                    m="0"+m;
                }
                if(tDay<10){
                    d="0"+d;
                }

                //오늘 날
                String date = y+"-"+m+"-"+d;

                if(skeletal_mass.isEmpty()||fat.isEmpty()||fat_mass.isEmpty()){
                    if(skeletal_mass.isEmpty()){
                        editTextDate.setError("골격근량을 제대로 입력 해 주세요");
                        editTextDate.requestFocus();
                    }
                    if(fat.isEmpty()){
                        editTextDate2.setError("체중을 제대로 입력 해 주세요");
                        editTextDate2.requestFocus();
                    }
                    if(fat_mass.isEmpty()){
                        editTextDate3.setError("체지방량을 제대로 입력 해 주세요");
                        editTextDate3.requestFocus();
                    }
                }else {
                    //공백이 없다면
                    HashMap<String,Object> hashmap_skeletal_mass = new HashMap<>();
                    HashMap<String,Object> hashmap_fat = new HashMap<>();
                    HashMap<String,Object> hashmap_fat_mass = new HashMap<>();

//                    hashmap_skeletal_mass.put(date,skeletal_mass);
//                    hashmap_fat.put(date,fat);
//                    hashmap_fat_mass.put(date,fat_mass);

                    hashmap_skeletal_mass.put("skeletal_mass",skeletal_mass);
                    hashmap_fat.put("fat",fat);
                    hashmap_fat_mass.put("fat_mass",fat_mass);
                    mDatabase.child("InBodyUser")
                            .child(user.getUid()/*<-- user.getUid로 변경 해 주어야 함*/)
                            .child(date).updateChildren(hashmap_skeletal_mass);//골격근량 저장
                    mDatabase.child("InBodyUser")
                            .child(user.getUid())
                            .child(date).updateChildren(hashmap_fat);//체중 저장
                    mDatabase.child("InBodyUser")
                            .child(user.getUid())
                            .child(date).updateChildren(hashmap_fat_mass);//체지방량 저장
                    editTextDate.setText(null);
                    editTextDate2.setText(null);
                    editTextDate3.setText(null);

                }
            }
        });

        Button LookList = findViewById(R.id.LookList);
        LookList.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InBody1.this, ListActivity.class);
                startActivity(intent);
            }
        });
    }
}