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
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        final Button btnAdocao = findViewById(R.id.id_btnAdocao);
        final Button btnAjuda = findViewById(R.id.id_btnAjuda);
        final Button btnApad = findViewById(R.id.id_btnApadrinhar);

        final TextView textoPorBtn = findViewById(R.id.id_textEscolha);

        btnApad.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Mudar o texto para um TextView que fica abaixo dos botões iniciais
                textoPorBtn.setText("Apadrinhar");
                //Mudar a cor dos botões inicais conforme for selecionado
                btnAjuda.setBackgroundColor(Color.parseColor("#f1f2f2"));
                btnAdocao.setBackgroundColor(Color.parseColor("#f1f2f2"));
                btnApad.setBackgroundColor(Color.parseColor("#ffd358"));
                //iniciar o fragment daquele botão
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.id_LayFrag, new FragmentCadApadrAnim())
                        .commit();
            }
        });

        btnAjuda.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Mudar o texto para um TextView que fica abaixo dos botões iniciais
                textoPorBtn.setText("Ajudar");
                //Mudar a cor dos botões inicais conforme for selecionado
                btnAjuda.setBackgroundColor(Color.parseColor("#ffd358"));
                btnAdocao.setBackgroundColor(Color.parseColor("#f1f2f2"));
                btnApad.setBackgroundColor(Color.parseColor("#f1f2f2"));
                //iniciar o fragment daquele botão
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.id_LayFrag, new FragmentCadAjdAnim())
                        .commit();
            }
        });

        btnAdocao.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                //Mudar o texto para um TextView que fica abaixo dos botões iniciais
                textoPorBtn.setText("Adoção");
                //Mudar a cor dos botões inicais conforme for selecionado
                btnAjuda.setBackgroundColor(Color.parseColor("#f1f2f2"));
                btnAdocao.setBackgroundColor(Color.parseColor("#ffd358"));
                btnApad.setBackgroundColor(Color.parseColor("#f1f2f2"));
                //iniciar o fragment daquele botão
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.id_LayFrag, new FragmentCadAdocAnim())
                        .commit();
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: //caso clique na seta para voltar
                finish(); // Finaliza a Activity atual e assim volta para a tela anterior
                break;
            default:
                break;
        }
        return true;
    }
}
