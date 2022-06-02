package com.example.finalproj;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    InBodyUserAdapter adapter;

    //데이터 베이스 객체

    UserInBodyData dao;

    //키 변수
    String key = "";

    ArrayList<InBodyUser> list = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        recyclerView = findViewById(R.id.rv);
        recyclerView.setHasFixedSize(true);

        //화면 설정
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);

        //어뎁터 설정
        adapter = new InBodyUserAdapter(this, list);

        //리싸이클러뷰 어뎁터 설정
        recyclerView.setAdapter(adapter);

        //데이터 초기화
        dao = new UserInBodyData();

        //데이터 가져오기
        loadDate();
    }

    private void loadDate() {
        dao.get().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot data: snapshot.getChildren()){

                    InBodyUser user = data.getValue(InBodyUser.class);

                    //키 값 가져오기
                    key = data.getKey();

                    //키 값 담기
                    user.setUserday(key);

                    //리스트에 담기
                    list.add(user);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
