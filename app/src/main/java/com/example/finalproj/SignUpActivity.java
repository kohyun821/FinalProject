package com.example.finalproj;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
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
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
//import com.google.firebase.firestore.FirebaseFirestore;
//import com.google.firebase.firestore.SetOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
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
        String userAuth = "??????";
        Date date = new Date();

        SimpleDateFormat getYearFormat = new SimpleDateFormat("yyyy");
        String currentYear = getYearFormat.format(date);

        SimpleDateFormat getMmFormat = new SimpleDateFormat("MM");
        String currentMm = getMmFormat.format(date);

        SimpleDateFormat getDdFormat = new SimpleDateFormat("dd");
        String currentDd = getDdFormat.format(date);


        String userterm = currentYear+"-"+currentMm+"-"+currentDd;

        if(txtUserName.isEmpty() || txtPassword.isEmpty() || txtPassword.length() < 6 || txtPhoneNo.length() != 11 || txtEmail.isEmpty()){
            if(txtUserName.isEmpty()){
                editTextUserName.setError("?????? ????????? ????????? ?????????");
                editTextUserName.requestFocus();
            }
            if(txtPassword.isEmpty() || txtPassword.length() < 6){
                editTextPassword.setError("????????? 6??? ??????????????? ?????????.");
                editTextPassword.requestFocus();
            }
            if(txtPhoneNo.length() != 11){
                editTextPhoneNo.setError("????????? ????????? 11?????? ????????? ?????????");
                editTextPhoneNo.requestFocus();
            }
            if(txtEmail.isEmpty()) {
                editTextEmail.setError("???????????? ????????? ?????????");
                editTextEmail.requestFocus();
            }
        }
        else{
            progressBar.setVisibility(View.VISIBLE);

            Log.e("????????????","?????????????????????");
            //firebaseDB??? ???????????? ?????????
            mAuth.createUserWithEmailAndPassword(txtEmail,txtPassword)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){

                                User user = new User(txtUserName,txtPassword,txtPhoneNo,txtEmail,userAuth,userterm);

                                FirebaseDatabase.getInstance().getReference("Users")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            Toast.makeText(SignUpActivity.this,"??????????????? ??????????????? ???????????????!!!",Toast.LENGTH_LONG).show();;
                                            progressBar.setVisibility(View.GONE);
                                            finish();
                                        }else{
                                            Toast.makeText(SignUpActivity.this,"??????????????? ????????????????????? :(",Toast.LENGTH_LONG).show();
                                            progressBar.setVisibility(View.GONE);
                                        }
                                    }
                                });
                            }
                            else{
                                Toast.makeText(SignUpActivity.this,"??????????????? ????????????????????? :(",Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                    });
        }

    }
}