package com.example.meau;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.support.v7.widget.Toolbar;

public class MenuActivity extends AppCompatActivity {

    Toolbar tb;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_drawer);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);   //Activity sera exibida em fullscreen

        tb =  findViewById(R.id.id_toolbarIntro);
        setSupportActionBar(tb);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, tb, R.string.open_drawer, R.string.close_drawer);
        drawerLayout.setDrawerListener(toggle);

        toggle.syncState();

        Button btnCadAni = findViewById(R.id.id_btnCadAnim);

        btnCadAni.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent it = new Intent(getApplicationContext(), CadastroAnimal.class);
                startActivity(it);
            }
        });

        Button btnAdotAni = findViewById(R.id.id_btnAdotar);

        btnAdotAni.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

            }
        });
    }

    public void login(View view){
        Intent it = new Intent(MenuActivity.this, Login.class);
        startActivity(it);
    }
}
