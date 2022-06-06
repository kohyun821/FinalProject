package com.example.finalproj;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class UserInBodyData {
    private DatabaseReference databaseReference;

    UserInBodyData(){

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference(InBodyUser.class.getSimpleName());
    }

    //등록
    public Task<Void> add(InBodyUser InBodyUser){

        return databaseReference.push().setValue(InBodyUser);
    }

    //조회
   public Query get(){

        return databaseReference;
    }

    //삭제
    public Task<Void> remove(String key){
        return databaseReference.child(key).removeValue();
    }

}
