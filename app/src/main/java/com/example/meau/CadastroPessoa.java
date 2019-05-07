package com.example.meau;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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

import pub.devrel.easypermissions.EasyPermissions;

public class CadastroPessoa extends AppCompatActivity{

    public static final int PICK_IMAGE = 1234;

    private FirebaseFirestore mFirestore;

    private DatabaseReference mDatabase;

    private String[] galleryPermissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_pessoa);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);

        mFirestore = FirebaseFirestore.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
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
        if (EasyPermissions.hasPermissions(this, galleryPermissions)) {
            pickImageFromGallery();
        } else {
            EasyPermissions.requestPermissions(this, "Access for storage",
                    101, galleryPermissions);
        }
    }

    public void pickImageFromGallery(){
        Intent i = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(i, "Selecione uma imagem"), PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_CANCELED) {
            if (requestCode == PICK_IMAGE) {
                Uri selectedImage = data.getData();

                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();

                Bitmap bitmap = BitmapFactory.decodeFile(picturePath);
                ImageView imageView = findViewById(R.id.id_imagem_perfil);
                imageView.setImageBitmap(bitmap);
            }
        }
    }

    public String retornValor(int id){
        EditText nomeTxt = findViewById(id);
        return nomeTxt.getText().toString();
    }

    public void cadastrarCliente(View view){

        Users user = new Users();
        user.setNome(retornValor(R.id.id_nome_user));
        user.setIdade(retornValor(R.id.id_idade_user));
        user.setEmail(retornValor(R.id.id_email_user));
        user.setEstado(retornValor(R.id.id_estado_user));
        user.setCidade(retornValor(R.id.id_cidade_user));
        user.setEndereco(retornValor(R.id.id_end_user));
        user.setTelefone(retornValor(R.id.id_telefone_user));
        user.setLogin(retornValor(R.id.id_login_user));
        user.setSenha(retornValor(R.id.id_senha_user));

        // Run snippets
        DocSnippets docSnippets = new DocSnippets(mFirestore, mDatabase);

        if(docSnippets.runCadastroCliente(user, "users")){
            Toast.makeText(getApplicationContext(), "Cadastro Realizado com sucesso", Toast.LENGTH_SHORT).show();
            Intent it = new Intent(CadastroPessoa.this, MenuActivity.class);
            startActivity(it);
        }else{
            Toast.makeText(getApplicationContext(), "Erro ao realizar cadastro", Toast.LENGTH_SHORT).show();
        }
    }
}
