package com.example.finalproj;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.HashMap;

public class UserChange extends AppCompatActivity {
    /*EditText UpdateName, UpdateNumber;

    String Key, name, number;*/
    EditText et5, et6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_change);

        /*UpdateName = findViewById(R.id.EditView9);
        UpdateNumber = findViewById(R.id.EditView8);

        getAndSetIntentData();

        Button button8 = findViewById(R.id.button8);
        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //변경값
                String name = UpdateName.getText().toString();
                String number = UpdateNumber.getText().toString();

                //파라미터 셋팅
                HashMap<String,Object> hashMap =new HashMap<>();
                hashMap.put("username",name);
                hashMap.put("usernumber",number);

                UserInformationData.update(key,hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getApplicationContext(),"업데이트 완료", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),"업데이트 실패: "+e.getMessage()
                                ,Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }

    //데이터 보여주기
    private void getAndSetIntentData(){
        // 값 체크
        if(getIntent().hasExtra("key") && getIntent().
                hasExtra("name")&& getIntent().hasExtra("number")){
            //데이터 가져오기
            Key = getIntent().getStringExtra("key");
            name = getIntent().getStringExtra("name");
            number = getIntent().getStringExtra("number");

            //데이터 넣기
            UpdateName.setText(name);
            UpdateNumber.setText(number);


        }
    }*/
        Intent intent = getIntent(); // 실려온 인텐트 정보를 담음
        String name = intent.getStringExtra("name");
        String number = intent.getStringExtra("number");
        Button onButton9 = findViewById(R.id.onButton9);
        Button onButton10 = findViewById(R.id.button1);
        EditText EditView9 = findViewById(R.id.EditView9);//이름
        EditText EditView8 = findViewById(R.id.EditView8);//번호

        et5 = findViewById(R.id.EditView8);
        et5.setText(name);

        et6 = findViewById(R.id.EditView9);
        et6.setText(number);

        onButton9.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                Intent data = new Intent();

                String name = et5.getText().toString(); // 읽어와서 변수에 담음
                String number = et6.getText().toString();

                data.putExtra("name", name); // 인텐트에 내용 실음
                data.putExtra("number", number);

                 //현재 액티비티 넘기고 메인 메소드 호출하며 종료
                setResult(Activity.RESULT_OK, data);
                finish();
            }
        });

        onButton10.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                finish(); // 현재 화면 종료
            }
        });
       /* public void onButton9Click (View view){ // 확인 버튼
            Intent data = new Intent();

            String name = E8.getText().toString(); // 읽어와서 변수에 담음
            String number = E9.getText().toString();

            data.putExtra("name", name); // 인텐트에 내용 실음
            data.putExtra("number", number);

            *//**//* 현재 액티비티 넘기고 메인 메소드 호출하며 종료 *//**//*
            setResult(Activity.RESULT_OK, data);
            finish();
        }*//*
         *//*public void onButton10Click (View view){ // 취소 버튼
            finish(); // 현재 화면 종료
        }*//*
    }*/

    }
}