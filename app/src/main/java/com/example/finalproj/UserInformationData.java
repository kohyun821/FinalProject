package com.example.finalproj;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.HashMap;

public class UserInformationData {
    private DatabaseReference databaseReference;

    UserInformationData(){

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference(InBodyUser.class.getSimpleName());
    }

    //등록
    public Task<Void> add(User user){

        return databaseReference.push().setValue(user);
    }

    //수정
    public Task<Void> update(String key, HashMap<String,Object> hashMap){
        return databaseReference.child(key).updateChildren(hashMap);
    }
}
