package com.example.meau;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class CadastroAnimal extends AppCompatActivity {
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_animal);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);   //Activity sera exibida em fullscreen

        toolbar = (Toolbar) findViewById(R.id.id_toolbarCadastroAnimal);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button btnAdocao = findViewById(R.id.id_btnAdocao);
        Button btnAjuda = findViewById(R.id.id_btnAjuda);
        Button btnApad = findViewById(R.id.id_btnApadrinhar);

        final TextView textoPorBtn = findViewById(R.id.id_textEscolha);

        btnApad.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //criar o bagui aqui
                textoPorBtn.setText("Apadrinhar");
            }
        });

        btnAjuda.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //criar o bagui aqui
                textoPorBtn.setText("Ajudar");
            }
        });

        btnAdocao.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                textoPorBtn.setText("Adoção");
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.id_LayFrag, new FragmentCadAdocAnim())
                        .commit();
            }
        });
    }
}
