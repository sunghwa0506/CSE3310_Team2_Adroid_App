package com.example.team2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SingupActivity extends AppCompatActivity {
    private static final String TAG = "SignUPActivity";
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        findViewById(R.id.loginbutton).setOnClickListener(onClickListener);
        findViewById(R.id.gotploginbutton).setOnClickListener(onClickListener);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }


    private void startLoginActivity(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }


    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.loginbutton:
                    signUp();
                    break;
                case R.id.gotploginbutton:
                    startLoginActivity();
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + v.getId());
            }
        }
    };


    private void startToast(String msg){
        Toast.makeText(this, msg,Toast.LENGTH_SHORT).show();
    }


    private void signUp(){
        String email = ((EditText)findViewById(R.id.LoginID)).getText().toString();
        String password = ((EditText)findViewById(R.id.LoginPW)).getText().toString();
        String passwordCheck = ((EditText)findViewById(R.id.editTextTextPassword2)).getText().toString();
        if(email.length()>0&&password.length()>0&&passwordCheck.length()>0){
            if(password.equals(passwordCheck))
            {
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    startToast( "Success");
                                } else {
                                    // If sign in fails, display a message to the user.
                                    if(task.getException()!=null){
                                        startToast( task.getException().toString());
                                    }
                                }// ...
                            }
                        });
            }
            else { startToast( "Password not match"); } }
        else{
            startToast( "Please enter Email or Password");
        }



    }


}

