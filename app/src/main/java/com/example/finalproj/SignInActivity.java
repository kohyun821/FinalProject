package com.example.finalproj;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

public class SignInActivity extends AppCompatActivity {
    EditText editTextUserName, editTextPassword;
    TextView textViewForgotPassword, textViewRegister;
    ProgressBar progressBar;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();
    DatabaseReference rootRef;
    DatabaseReference orderRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_activity);

        editTextUserName = (EditText) findViewById(R.id.editTextSignInUserName);
        editTextPassword = (EditText) findViewById(R.id.editTextSignInPassword);

        textViewForgotPassword = (TextView) findViewById(R.id.txtSignInForgotPassword);
        textViewRegister = (TextView) findViewById(R.id.txtSignInRegister);
        progressBar = (ProgressBar) findViewById(R.id.progressBarSignIn);
    }

    public void txtSignInForgotPasswordClicked(View v){
        Intent intent = new Intent(this,ForgotPasswordActivity.class);
        startActivity(intent);
    }

    public void txtSignInRegisterClicked(View v){
        Intent intent = new Intent(this,SignUpActivity.class);
        startActivity(intent);
    }

//    //자동 로그인
//    @Override
//    protected void onStart() {
//        super.onStart();
//        FirebaseUser user = mAuth.getCurrentUser();
//        if(user !=null){
//            Toast.makeText(this,"자동 로그인 되었습니다.",Toast.LENGTH_SHORT).show();
//            startActivity(new Intent(SignInActivity.this,DashboardActivity.class));
//        }
//    }

    public void buttonSignInScrSignInClicked(View v){
        String userName = editTextUserName.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();


//        //mysql
//        Response.Listener<String> responseListener = new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                try {
//                    JSONObject jsonObject = new JSONObject(response);
//                    boolean success = jsonObject.getBoolean("success");
//                    if(success){//성공
//                        progressBar.setVisibility(View.GONE);
//
//                        String userID = jsonObject.getString("userID");
//                        String userPass = jsonObject.getString("userPassword");
//                        Toast.makeText(getApplicationContext(), "로그인에 성공 했습니다.",Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(SignInActivity.this,DashboardActivity.class);
//                        intent.putExtra("userID",userID);
//                        intent.putExtra("userPass",userPass);
//                        startActivity(intent);
//                        finish();
//                    }else{
//                        progressBar.setVisibility(View.GONE);
//                        Toast.makeText(SignInActivity.this,"로그인에 실패하였습니다 :(",Toast.LENGTH_LONG).show();
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        };
//        LoginRequest loginRequest = new LoginRequest(userName, password, responseListener);
//        RequestQueue queue = Volley.newRequestQueue(SignInActivity.this);
//        queue.add(loginRequest);


        //이메일이 다르다면
        if(!Patterns.EMAIL_ADDRESS.matcher(userName).matches() || editTextPassword.length() < 6){
            if(!Patterns.EMAIL_ADDRESS.matcher(userName).matches()){
                editTextUserName.setError("정확한 이메일 입력바랍니다.");
                editTextUserName.requestFocus();
            }//비밀번호가 이상하다면
            if(editTextPassword.length() < 6){
                editTextPassword.setError("비밀번호는 6자 이상이어야 합니다.");
                editTextPassword.requestFocus();
            }
            //제대로 입력
        }else{
            progressBar.setVisibility(View.VISIBLE);
//            firebaseDB로 로그인
            mAuth.signInWithEmailAndPassword(userName, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){

                        progressBar.setVisibility(View.GONE);
                        FirebaseUser user = mAuth.getCurrentUser();
                        if(user!=null){
                            Toast.makeText(SignInActivity.this,"로그인에 성공 했습니다.", Toast.LENGTH_LONG).show();
                            rootRef = FirebaseDatabase.getInstance().getReference();
                            orderRef = rootRef.child("Users").child(user.getUid());
                            orderRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DataSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (DataSnapshot ds : task.getResult().getChildren()) {
                                            String key = ds.getKey();
                                            String value = ds.getValue(String.class);
                                            Log.d("TAG", key + ":" + value);

                                            //userAuth가 회원이라면 DashboardActivity를 시행
                                            if(key.equals("userAuth") && value.equals("회원")){
                                                startActivity(new Intent(SignInActivity.this,DashboardActivity.class));
                                            }
                                        }
                                    } else {
                                        Log.d("TAG", task.getException().getMessage()); //Don't ignore potential errors!
                                    }
                                }
                            });
                        }

                    }else{
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(SignInActivity.this,"로그인에 실패 했습니다.", Toast.LENGTH_LONG).show();
                    }

                }
            });
        }
    }
}