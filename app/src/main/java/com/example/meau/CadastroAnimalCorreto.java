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
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import pub.devrel.easypermissions.EasyPermissions;

public class CadastroAnimalCorreto extends AppCompatActivity {

    private FirebaseFirestore mFirestore;
    private DatabaseReference mDatabase;
    private String[] galleryPermissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    public static final int PICK_IMAGE = 1234;
    private SharedPreferences pref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_animal_correto);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mFirestore = FirebaseFirestore.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public void adotar(View view){
        findViewById(R.id.id_caixa_adocao).setVisibility(View.VISIBLE);
    }

    public void apadrinhar(View view){
        findViewById(R.id.id_caixa_adocao).setVisibility(View.GONE);
    }

    public void ajudar(View view){
        findViewById(R.id.id_caixa_adocao).setVisibility(View.GONE);
    }

    public void cadastroAnimal(View view){
        Intent it = new Intent(CadastroAnimalCorreto.this, CadastroPessoa.class);
        startActivity(it);
    }

    public void colocarPraAdocao(View view){
        SharedPreferences shared = getSharedPreferences("info",MODE_PRIVATE);
        String idDono = shared.getString("id", "");

        SharedPreferences sharedFoto = getSharedPreferences("info",MODE_PRIVATE);
        String caminhoFoto = sharedFoto.getString("caminhoFotoDog", "");

        Animal animal = new Animal();
        animal.setNomeAnimal(retornValor(R.id.id_nomeCorreto_animal));
        animal.setIdDono(idDono);
        animal.setRaca(retornValorRadioButton(R.id.id_cachorro));
        animal.setCaminhoFoto(caminhoFoto);

        DocSnippets docSnippets = new DocSnippets(mFirestore, mDatabase);

        docSnippets.cadastrarAnimal(animal);
    }

    public String retornValor(int id) {
        EditText nomeTxt = findViewById(id);
        return nomeTxt.getText().toString();
    }

    public String retornValorRadioButton(int id) {
        RadioButton nomeTxt = findViewById(id);
        return nomeTxt.getText().toString();
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

                pref = getSharedPreferences("info", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("caminhoFotoDog", selectedImage.toString());
                editor.commit();

                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();

                Bitmap bitmap = BitmapFactory.decodeFile(picturePath);
                ImageView imageView = findViewById(R.id.id_fotos_animal);
                imageView.setImageBitmap(bitmap);
            }
        }
    }
}
