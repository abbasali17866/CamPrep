package com.technoweird.camprep;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfileActivity extends AppCompatActivity {
    private TextView name,email,contact;
    private DatabaseReference userInfo;
    private CircleImageView mDisplayImage;
    private ImageView editname,editemail,editContact;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        name=findViewById(R.id.name);
        email=findViewById(R.id.email);
        contact=findViewById(R.id.contact);
        mAuth=FirebaseAuth.getInstance();
        userInfo= FirebaseDatabase.getInstance().getReference("User");
        FirebaseUser user=mAuth.getCurrentUser();
        if(user!=null){
            String uname=user.getDisplayName().toString();
            String uemail=user.getEmail().toString().trim();
            name.setText(uname);
            email.setText(uemail);
        }

    }
}
