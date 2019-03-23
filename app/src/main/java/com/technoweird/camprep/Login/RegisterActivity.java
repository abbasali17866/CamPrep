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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.technoweird.camprep.R;
import com.technoweird.camprep.UserProfile;

public class RegisterActivity extends AppCompatActivity {
    private EditText name,email,password;
    private Button btnSignup;
    private TextView linkLogin;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;
    String uname,uemail;
    DatabaseReference userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        name=findViewById(R.id.input_name);
        email=findViewById(R.id.input_email);
        password=findViewById(R.id.input_password);
        btnSignup=findViewById(R.id.btn_signup);
        linkLogin=findViewById(R.id.link_login);
        mAuth = FirebaseAuth.getInstance();
        userInfo=FirebaseDatabase.getInstance().getReference("User");
        progressDialog=new ProgressDialog(this);
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String upass=password.getText().toString().trim();
                uname=name.getText().toString();

                uemail=email.getText().toString().trim();
                if(validate()){
                    progressDialog.setMessage("Signing Up...");
                    saveUSerInfo();
                    progressDialog.show();
                    //save data to firebase
                    mAuth.createUserWithEmailAndPassword(uemail, upass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()){
                                sendEmailVarification();
                            }else {
                                Toast.makeText(getApplicationContext(),"Signup error ",Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }

                        }
                    });
                }

            }
        });
        linkLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private boolean validate(){
        boolean result=false;
        uname=name.getText().toString();
        String upass=password.getText().toString();
        uemail=email.getText().toString();
        if(uname.isEmpty()||uemail.isEmpty()||upass.isEmpty()){
            Toast.makeText(getApplicationContext(),"Please fill all the details",Toast.LENGTH_SHORT).show();
        }
        else
            result=true;
        return result;
    }
    private void sendEmailVarification(){
        FirebaseUser user=mAuth.getCurrentUser();
        if(user!=null){
             user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                 @Override
                 public void onComplete(@NonNull Task<Void> task) {
                     if(task.isSuccessful()){
                         Toast.makeText(getApplicationContext(),"Successfully registered and a verification mail has been send",Toast.LENGTH_LONG).show();
                         mAuth.signOut();
                         progressDialog.dismiss();
                         finish();
                         startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                     }else{
                         Toast.makeText(getApplicationContext(),"Verification mail has'nt been send",Toast.LENGTH_SHORT).show();
                         progressDialog.dismiss();
                     }
                 }
             });
        }
    }
    public void saveUSerInfo(){
        UserProfile user=new UserProfile(uname,uemail);
        String id=userInfo.push().getKey();
        userInfo.child(id).setValue(user);
    }
}
