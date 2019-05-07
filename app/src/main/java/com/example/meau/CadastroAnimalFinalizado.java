package com.example.meau;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import static android.app.PendingIntent.getActivity;

public class CadastroAnimalFinalizado extends AppCompatActivity {

    private FirebaseFirestore mFirestore;

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_animal_finalizado);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);   //Activity sera exibida em fullscreen
        mFirestore = FirebaseFirestore.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        Toolbar toolbar = (Toolbar) findViewById(R.id.id_toolbarFimCadastroAnimal);

        DocSnippets docSnippets = new DocSnippets(mFirestore, mDatabase);

        EditText nomeTxt = findViewById(R.id.id_usuNomAnimal);
        Animal animal = new Animal();

        animal.setNomeAnimal(nomeTxt.getText().toString());
        docSnippets.cadastrarAnimal(animal);


        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_preto);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

    }
}
