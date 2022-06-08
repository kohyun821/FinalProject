package com.example.finalproj;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalproj.databinding.ActivityUserInformationChangeBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class UserInformationChange extends DrawerBaseActivity {
    DatabaseReference mDatabase;
    EditText EditName , EditPhoneNum;
    Button EditUserEdit;
    TextView username,userphonenum;

    private DatabaseReference rootRef;
    private DatabaseReference orderRef;

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser user = mAuth.getCurrentUser();

    ActivityUserInformationChangeBinding activityUserInformationChangeBinding;
    private String aftername="",afterphonenum="";

    ArrayList<String> timeTableUserPK = new ArrayList<>();
    ArrayList<UserinformationSearchingList> userInformationData = new ArrayList<>();

    String temp_user_name="",temp_user_phonenum="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        activityUserInformationChangeBinding = ActivityUserInformationChangeBinding.inflate(getLayoutInflater());
        setContentView(activityUserInformationChangeBinding.getRoot());
        allocateActivityTitle("회원 정보 수정");

        EditName = (EditText) findViewById(R.id.EditName);
        EditPhoneNum = (EditText) findViewById(R.id.EditPhoneNum);
        EditUserEdit = (Button) findViewById(R.id.EditUserEdit);
        username = (TextView) findViewById(R.id.Read_Name);
        userphonenum = (TextView) findViewById(R.id.Read_Phonenum);
        userSearching();
    }

    private void userSearching() {
        //Users테이블에 있는 모든 값 저장
//        Log.d("인포메이션", "유저 서칭 시작");
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
        });

    }

    private void userSearching2(String s) {
        rootRef = FirebaseDatabase.getInstance().getReference();
        orderRef = rootRef.child("Users").child(s);
//        Log.d("인포메이션", "유저 서칭 시작2 + "+s);
        orderRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    for (DataSnapshot ds : task.getResult().getChildren()) {
                        String ID = ds.getKey();
                        String value = ds.getValue(String.class);
                        if(ID.equals("userName")){
//                            Log.d("인포메이션", "유저 서칭 시작2 + username : "+value);
                            temp_user_name=value;
                        }
                        if(ID.equals("phoneNo")){
//                            Log.d("인포메이션", "유저 서칭 시작2 + phoneNo : "+value);
                            temp_user_phonenum=value;
                        }
                    }
                    if(s.equals(user.getUid())){
                        username.setText("현재 로그인한 사용자의 이름 : "+temp_user_name);
                        userphonenum.setText("현재 로그인한 사용자의 전화번호 : "+temp_user_phonenum);
                    }
//                    userInformationData.add(new UserinformationSearchingList(s,temp_user_name,temp_user_phonenum));
                } else {
                    Log.d("TAG", task.getException().getMessage()); //Don't ignore potential errors!
                }
            }
        });
    }



    public void btnEditSure(View view){
        String userPK = user.getUid();
        aftername = EditName.getText().toString().trim();
        afterphonenum = EditPhoneNum.getText().toString().trim();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        HashMap<String,Object> nameHashMap = new HashMap<>();
        HashMap<String,Object> numHashMap = new HashMap<>();
        nameHashMap.put("userName",aftername);
        numHashMap.put("phoneNo",afterphonenum);

        if(afterphonenum.isEmpty()&&aftername.isEmpty()){
            //둘다 비어있다면 다시 하게 할 것
            Log.d("인포","여기가 실행 되는지");
            EditName.setError("입력해 주세요");
            EditPhoneNum.setError("입력해 주세요");
            Toast.makeText(UserInformationChange.this,"하나라도 입력 해 주세요!",Toast.LENGTH_LONG).show();
        }


        if(aftername.isEmpty()){
            //이름을 입력하지 않았다면 전화 번호만 바꿔 주기
            mDatabase.child("Users").child(userPK).updateChildren(numHashMap);
//            username.setText("현재 로그인한 사용자의 이름 : "+afterphonenum);
            userphonenum.setText("현재 로그인한 사용자의 전화번호 : "+afterphonenum);
            EditName.setText(null);
            EditPhoneNum.setText(null);
        }else if(afterphonenum.isEmpty()) {
            //전화번호를 입력하지 않았다면 이름만 바꿔주기
            mDatabase.child("Users").child(userPK).updateChildren(nameHashMap);
            username.setText("현재 로그인한 사용자의 이름 : "+afterphonenum);
//            userphonenum.setText("현재 로그인한 사용자의 전화번호 : "+afterphonenum);
            EditName.setText(null);
            EditPhoneNum.setText(null);
        }else{
            //둘 다 입력 했다면
            mDatabase.child("Users").child(userPK).updateChildren(nameHashMap);
            mDatabase.child("Users").child(userPK).updateChildren(numHashMap);
            username.setText("현재 로그인한 사용자의 이름 : "+afterphonenum);
            userphonenum.setText("현재 로그인한 사용자의 전화번호 : "+afterphonenum);
            EditName.setText(null);
            EditPhoneNum.setText(null);
        }

    }

}

