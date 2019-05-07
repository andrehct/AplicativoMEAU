package com.example.meau;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

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

import java.util.Arrays;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class DocSnippets extends AppCompatActivity implements DocSnippetsInterface{

    private static final String TAG = "DocSnippets";

    private static final ThreadPoolExecutor EXECUTOR = new ThreadPoolExecutor(2, 4,
            60, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());

    private final FirebaseFirestore db;

    private DatabaseReference mDatabase;// ...


    DocSnippets(FirebaseFirestore db, DatabaseReference mDatabase) {
        this.db = db;
        this.mDatabase = mDatabase;
    }

    @Override
    public void setup() {
        // [START get_firestore_instance]
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        // [END get_firestore_instance]

        // [START set_firestore_settings]
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .build();
        db.setFirestoreSettings(settings);
        // [END set_firestore_settings]
    }


    public boolean runCadastroCliente(Users user, String colecao) {
        Log.d(TAG, "================= BEGIN RUN ALL ===============");
        return addCliente(user, colecao);
    }

    public boolean addCliente(Users user, String colecao) {


        mDatabase.child(colecao).push().setValue(user);
        // Add a new document with a generated ID
        try {
            db.collection(colecao)
                    .add(user)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Erro ao tentar persistir os dados", e);
                        }
                    });
            return true;
        }catch(Exception e){
            return false;
        }
    }

    public void inserirUsuarioGoogle(Users user) {
        db.collection("users").document(user.getId()).set(user);
    }

    public void cadastrarAnimal(Animal animal){
        SharedPreferences shared = getSharedPreferences("info",MODE_PRIVATE);
        String idDono = shared.getString("id", "");
        animal.setIdDono(idDono);
    }

    public void inserirAnimal(Animal animal) {
        db.collection("pets").document(animal.getIdDono()).set(animal);
    }

    public void getAllUsers() {
        // [START get_all_users]
        db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
        // [END get_all_users]
    }
}
