package com.example.finalproj;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.firestore.FirebaseFirestore;
//import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {

    EditText editTextUserName;
    EditText editTextPassword;
    EditText editTextPhoneNo;
    EditText editTextEmail;
    ProgressBar progressBar;

    private FirebaseAuth mAuth;
//    private FirebaseFirestore mStore = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        editTextUserName = (EditText) findViewById(R.id.editTextUserName);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextPhoneNo = (EditText) findViewById(R.id.editTextPhoneNo);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        mAuth = FirebaseAuth.getInstance();
    }

    public void signupButtonClicked(View v){
        String txtUserName = editTextUserName.getText().toString().trim();
        String txtPassword = editTextPassword.getText().toString().trim();
        String txtEmail = editTextEmail.getText().toString().trim();
        String txtPhoneNo = editTextPhoneNo.getText().toString().trim();

        if(txtUserName.isEmpty() || txtPassword.isEmpty() || txtPassword.length() < 6 || txtPhoneNo.length() != 11 || txtEmail.isEmpty()){
            if(txtUserName.isEmpty()){
                editTextEmail.setError("유저 이름을 입력해 주세요");
                editTextUserName.requestFocus();
            }
            if(txtPassword.isEmpty() || txtPassword.length() < 6){
                editTextPassword.setError("암호는 6자 이상이어야 합니다.");
                editTextPassword.requestFocus();
            }
            if(txtPhoneNo.length() != 11){
                editTextPhoneNo.setError("핸드폰 번호를 11자로 입력해 주세요");
                editTextPhoneNo.requestFocus();
            }
            if(txtEmail.isEmpty()) {
                editTextEmail.setError("이메일을 입력해 주세요");
                editTextEmail.requestFocus();
            }
        }
        else{
            progressBar.setVisibility(View.VISIBLE);
            //DB에 저장되는 메소드
            mAuth.createUserWithEmailAndPassword(txtEmail,txtPassword)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){

                                User user = new User(txtUserName,txtPassword,txtPhoneNo,txtEmail);

                                FirebaseDatabase.getInstance().getReference("Users")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
//                                            FirebaseUser user = mAuth.getCurrentUser();
//                                            if(user !=null){
//                                                Map<String, Object> userMap = new HashMap<>();
//                                                userMap.put(FirebaseID.documentId,user.getUid());
//                                                userMap.put(FirebaseID.email,txtEmail);
//                                                userMap.put(FirebaseID.password,txtPassword);
//                                                userMap.put(FirebaseID.phoneNo,txtPhoneNo);
//                                                userMap.put(FirebaseID.nicname,txtUserName);
//                                                mStore.collection(FirebaseID.user).document(user.getUid()).set(userMap, SetOptions.merge());
//                                                finish();
//                                            }
                                            Toast.makeText(SignUpActivity.this,"회원가입이 성공적으로 되었습니다!!!",Toast.LENGTH_LONG).show();;
                                            progressBar.setVisibility(View.GONE);
                                            finish();
                                        }else{
                                            Toast.makeText(SignUpActivity.this,"회원가입에 실패하였습니다 :(",Toast.LENGTH_LONG).show();
                                            progressBar.setVisibility(View.GONE);
                                        }
                                    }
                                });
                            }
                            else{
                                Toast.makeText(SignUpActivity.this,"회원가입에 실패하였습니다 :(",Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                    });
        }

    }
}