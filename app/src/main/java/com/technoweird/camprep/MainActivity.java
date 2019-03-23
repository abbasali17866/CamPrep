package com.technoweird.camprep;


import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.technoweird.camprep.Login.LoginActivity;
import com.technoweird.camprep.Model.Model;

import java.util.ArrayList;
import java.util.List;

public class MainActivity<status> extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private FirebaseAuth mAuth;
    private TextView userName,userEmail;
    private ImageView userImage;
    SwipeRefreshLayout refresh;
    private ProgressDialog dialog;


    DatabaseReference dbword;
    List<Model>  list;
    ListView listwords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mAuth=FirebaseAuth.getInstance();
        FirebaseUser user=mAuth.getCurrentUser();
        dbword= FirebaseDatabase.getInstance().getReference("words");
        list=new ArrayList<Model>();
        refresh=findViewById(R.id.refresh);
        listwords=findViewById(R.id.listword);
        dialog=new ProgressDialog(this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View userView=navigationView.getHeaderView(0);
        userName=userView.findViewById(R.id.name);
        userEmail=userView.findViewById(R.id.email);
        userImage=userView.findViewById(R.id.imageView);


        if(user!=null){
            String name=user.getDisplayName();
            String email=user.getEmail();
            Uri photoUrl=user.getPhotoUrl();
            Log.d("msg",name);
            userName.setText(name);
            userEmail.setText(email);
            userImage.setImageURI(photoUrl);
        }


        dbword.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){

                    Model model=postSnapshot.getValue(Model.class);
                    list.add(model);
                }

                CostomAdapter wordAdapter=new CostomAdapter(MainActivity.this,list);
                listwords.setAdapter(wordAdapter);



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Toast.makeText(getApplicationContext(),String.valueOf(databaseError),Toast.LENGTH_SHORT).show();

            }
        });



        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                dbword.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        list.clear();

                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){

                            Model model=postSnapshot.getValue(Model.class);
                            list.add(model);
                        }

                        CostomAdapter wordAdapter=new CostomAdapter(MainActivity.this,list);
                        listwords.setAdapter(wordAdapter);



                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                        Toast.makeText(getApplicationContext(),String.valueOf(databaseError),Toast.LENGTH_SHORT).show();

                    }
                });

                refresh.setRefreshing(false);

            }
        });



    }
    @Override
    protected void onStart() {
        super.onStart();


        dbword.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){

                    Model model=postSnapshot.getValue(Model.class);
                    list.add(model);
                }

                CostomAdapter wordAdapter=new CostomAdapter(MainActivity.this,list);
                listwords.setAdapter(wordAdapter);



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Toast.makeText(getApplicationContext(),String.valueOf(databaseError),Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if(id==R.id.action_logout){
            mAuth.signOut();
            finish();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            return true;
        }
        if(id==R.id.action_editProfile){
            //Edit profile section
            startActivity(new Intent(getApplicationContext(),EditProfileActivity.class));
            return true;
        }
        if(id==R.id.action_refresh){
            //refresh the activity
            dialog.setMessage("Refreshing...");
            dialog.show();
            dbword.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    list.clear();

                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){

                        Model model=postSnapshot.getValue(Model.class);
                        list.add(model);
                    }

                    CostomAdapter wordAdapter=new CostomAdapter(MainActivity.this,list);
                    listwords.setAdapter(wordAdapter);
                    dialog.dismiss();



                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                    Toast.makeText(getApplicationContext(),String.valueOf(databaseError),Toast.LENGTH_SHORT).show();

                }
            });
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.addWords) {
            // Handle the camera action
            startActivity(new Intent(getApplicationContext(),AddWordActivity.class));
        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



}