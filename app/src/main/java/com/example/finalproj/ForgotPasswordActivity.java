package com.example.finalproj;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {

    EditText editTextEmail;
    FirebaseAuth mAuth;
    ProgressBar progressBar;;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        editTextEmail = (EditText) findViewById(R.id.editTextForgotPasswordEmail);
        mAuth = FirebaseAuth.getInstance();
        progressBar = (ProgressBar) findViewById(R.id.forgotPasswordActivity);
    }

    public void forgotPasswordResetBtnPressed(View v){
        resetPassword();

    }

    private void resetPassword(){
        String txtEmail = editTextEmail.getText().toString().trim();
        if(!Patterns.EMAIL_ADDRESS.matcher(txtEmail).matches()){
            editTextEmail.setError("올바른 이메일을 입력해 주세요.");
            editTextEmail.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        mAuth.sendPasswordResetEmail(txtEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(ForgotPasswordActivity.this,"이메일을 확인 바랍니다.",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(ForgotPasswordActivity.this, SignInActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(ForgotPasswordActivity.this,"비밀번호 초기화에 실패하였습니다.",Toast.LENGTH_LONG).show();
                }
                progressBar.setVisibility(View.GONE);
            }
        });

    }
}