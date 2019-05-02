package com.example.meau;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class CadastroPessoa extends AppCompatActivity{

    public static final int PICK_IMAGE = 1234;

    private FirebaseFirestore mFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_pessoa);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);

        mFirestore = FirebaseFirestore.getInstance();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //Botão adicional na ToolBar
        switch (item.getItemId()) {
            case android.R.id.home:  //ID do seu botão (gerado automaticamente pelo android, usando como está, deve funcionar
                startActivity(new Intent(this, MenuActivity.class));  //O efeito ao ser pressionado do botão (no caso abre a activity)
                finishAffinity();  //Método para matar a activity e não deixa-lá indexada na pilhagem
                break;
            default:break;
        }
        return true;
    }

    public void abrirGaleria(View view){
        Intent i = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(i, "Selecione uma imagem"), PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != Activity.RESULT_CANCELED){
            if(requestCode == PICK_IMAGE){
                Uri selectedImage = data.getData();
                Toast.makeText(getApplicationContext(), selectedImage.toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void cadastrarCliente(View view){
        EditText nomeTxt = findViewById(R.id.id_nome_user);
        String nome = nomeTxt.getText().toString();

        EditText idadelTxt = findViewById(R.id.id_idade_user);
        String idade = idadelTxt.getText().toString();

        EditText emailTxt = findViewById(R.id.id_email_user);
        String email = emailTxt.getText().toString();

        EditText estadoTxt = findViewById(R.id.id_estado_user);
        String estado = estadoTxt.getText().toString();

        EditText cidadeTxt = findViewById(R.id.id_cidade_user);
        String cidade = cidadeTxt.getText().toString();

        EditText endTxt = findViewById(R.id.id_end_user);
        String endereco = endTxt.getText().toString();

        EditText telefoneTxt = findViewById(R.id.id_telefone_user);
        String telefone = telefoneTxt.getText().toString();

        EditText loginTxt = findViewById(R.id.id_login_user);
        String login = loginTxt.getText().toString();

        EditText senhaTxt = findViewById(R.id.id_senha_user);
        String senha = senhaTxt.getText().toString();

        Map<String, Object> user = new HashMap<>();
        user.put("nome", nome);
        user.put("idade", idade);
        user.put("email", email);
        user.put("estado", estado);
        user.put("cidade", cidade);
        user.put("endereco", endereco);
        user.put("telefone", telefone);
        user.put("login", login);
        user.put("senha", senha);

        // Run snippets
        DocSnippets docSnippets = new DocSnippets(mFirestore);
        docSnippets.runCadastroCliente(user);
    }
}
