package com.technoweird.camprep.Login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.technoweird.camprep.CheckConnection;
import com.technoweird.camprep.MainActivity;
import com.technoweird.camprep.R;

public class LoginActivity extends AppCompatActivity {
    private EditText user_email,user_password;
    private TextView forgotpassword;
    private Button btn_login,btn_register;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        user_email=findViewById(R.id.input_email);
        user_password=findViewById(R.id.input_password);
        forgotpassword=findViewById(R.id.action_forgot);
        btn_login=findViewById(R.id.btn_login);
        btn_register=findViewById(R.id.link_signup);
        mAuth=FirebaseAuth.getInstance();
        FirebaseUser user=mAuth.getCurrentUser();
        progressDialog=new ProgressDialog(this);

        CheckConnection ch=new CheckConnection();
        boolean status=ch.isNetworkAvailable(getApplicationContext());
        if(status){

        }else{
            Toast.makeText(getApplicationContext(),"Kindly connect to the Internet",Toast.LENGTH_SHORT).show();
        }


        if(user!=null){
            finish();
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()){
                    String email=user_email.getText().toString();
                    String password=user_password.getText().toString();
                    progressDialog.setMessage("Authenticating...");
                    progressDialog.show();
                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                progressDialog.dismiss();
                                checkEmailVarification();
                            }else{
                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(),"Authentication Failed",Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

                }
            }
        });
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),RegisterActivity.class);
                startActivity(intent);
            }
        });


        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Forgot password reset mail varifiacation
                startActivity(new Intent(getApplicationContext(),ResetPasswordActivity.class));
            }
        });



    }
    private boolean validate(){
        boolean result=false;
        String email=user_email.getText().toString();
        String pass=user_password.getText().toString();
        if(email.isEmpty()||pass.isEmpty()){
            Toast.makeText(getApplicationContext(),"Please fill the details",Toast.LENGTH_SHORT).show();
        }
        else{
            result=true;
        }
        return result;
    }
    private void checkEmailVarification(){
        FirebaseUser user=mAuth.getCurrentUser();
        boolean emailflag=user.isEmailVerified();
        if(emailflag){
            finish();
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }else{
            Toast.makeText(getApplicationContext(),"Varify your email first",Toast.LENGTH_SHORT).show();
            mAuth.signOut();
        }
    }
}
