package com.example.gropypucp.Profesor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gropypucp.Entity.Clase;
import com.example.gropypucp.Entity.Profesor;
import com.example.gropypucp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ListaEstudiantesProfesorActivity extends AppCompatActivity {

    FirebaseFirestore firebaseFirestore;
    FirebaseAuth mAuth;
    TextView nombre,profesorname,correo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_estudiantes_profesor);

        String id =  getIntent().getStringExtra("idClase");

        firebaseFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        nombre=findViewById(R.id.textView23);
        profesorname=findViewById(R.id.textView26);
        correo=findViewById(R.id.textView27);

        obtenerValores(id);
    }

    private void obtenerValores(String id) {
        firebaseFirestore.collection("clase").document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Clase clase = documentSnapshot.toObject(Clase.class);
                nombre.setText(clase.getClase());

                firebaseFirestore.collection("profesor").document(clase.getUidProfesor()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Profesor profesor = documentSnapshot.toObject(Profesor.class);
                        profesorname.setText(profesor.getNombre());
                        correo.setText(profesor.getCorreo());

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ListaEstudiantesProfesorActivity.this,"Error al obtener los datos del profesor",Toast.LENGTH_SHORT).show();

                    }
                });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ListaEstudiantesProfesorActivity.this,"Error al obtener los datos",Toast.LENGTH_SHORT).show();

            }
        });

    }
}