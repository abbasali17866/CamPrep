package com.technoweird.camprep.Login;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.technoweird.camprep.R;

public class ResetPasswordActivity extends AppCompatActivity {
    private TextView email;
    private Button resetpass;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        email=findViewById(R.id.reset_email);
        mAuth=FirebaseAuth.getInstance();
        resetpass=findViewById(R.id.btn_resetpass);


        resetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmail=email.getText().toString().trim();
                if(userEmail.equals("")){
                    Toast.makeText(getApplicationContext(),"Please enter your registered email address",Toast.LENGTH_SHORT).show();
                }
                else{
                    mAuth.sendPasswordResetEmail(userEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(getApplicationContext(),"Password reset link has been send to the registered email",Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(getApplicationContext(),LoginActivity.class));

                            }
                            else{
                                Toast.makeText(getApplicationContext(),"Something went wrong",Toast.LENGTH_LONG);
                            }
                        }
                    });
                }
            }
        });
    }
}
