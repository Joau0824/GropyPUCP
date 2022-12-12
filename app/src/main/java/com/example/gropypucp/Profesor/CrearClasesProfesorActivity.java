package com.example.gropypucp.Profesor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gropypucp.Entity.Clase;
import com.example.gropypucp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class CrearClasesProfesorActivity extends AppCompatActivity {


    FirebaseFirestore firebaseFirestore;
    FirebaseAuth mAuth;

    EditText nameClase,nameProyecto,descripcion,numeroGrupos,numeroIntegrantes,tiempo;
    String uidUsuario;
    Button crear;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_clases_profesor);

        firebaseFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        uidUsuario=mAuth.getCurrentUser().getUid();

        nameClase=findViewById(R.id.editTextTextPersonName4);
        nameProyecto=findViewById(R.id.editTextTextPersonName5);
        descripcion=findViewById(R.id.editTextTextPersonName6);
        numeroGrupos=findViewById(R.id.editTextNumber5);
        numeroIntegrantes=findViewById(R.id.editTextNumber6);
        tiempo=findViewById(R.id.editTextDate);

        crear=findViewById(R.id.buttonCrear);

        crear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameClase2=nameClase.getText().toString().trim();
                String nameProyecto2=nameProyecto.getText().toString().trim();
                String descripcion2=descripcion.getText().toString().trim();
                String numeroGrupos2=numeroGrupos.getText().toString().trim();
                String numeroIntegrantes2=numeroIntegrantes.getText().toString().trim();
                String tiempo2=tiempo.getText().toString().trim();

                if(nameClase2.isEmpty() || nameProyecto2.isEmpty() || descripcion2.isEmpty() || numeroGrupos2.isEmpty() || numeroIntegrantes2.isEmpty() || tiempo2.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Ingresar los datos", Toast.LENGTH_SHORT).show();
                }else{
                    putSolicitud(nameClase2,nameProyecto2,descripcion2,numeroGrupos2,numeroIntegrantes2,tiempo2);

                }
            }
        });
    }

    private void putSolicitud(String nameClase2, String nameProyecto2, String descripcion2, String numeroGrupos2, String numeroIntegrantes2, String tiempo2) {

        Clase clase = new Clase();

        clase.setClase(nameClase2);
        clase.setProyecto(nameProyecto2);
        clase.setDescripcion(descripcion2);
        clase.setNumeroGrupos(numeroGrupos2);
        clase.setNumeroIntegrantes(numeroIntegrantes2);
        clase.setTiempo(tiempo2);
        clase.setUidProfesor(uidUsuario);
        firebaseFirestore.collection("clase").add(clase).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(CrearClasesProfesorActivity.this,"Clase creada exitosamente exitosamente",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(CrearClasesProfesorActivity.this,PerfilActivity.class));
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(CrearClasesProfesorActivity.this,"Error al agregar",Toast.LENGTH_SHORT).show();
            }
        });

    }
}