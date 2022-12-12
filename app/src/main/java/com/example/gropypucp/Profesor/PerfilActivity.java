package com.example.gropypucp.Profesor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.gropypucp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageReference;

public class PerfilActivity extends AppCompatActivity {

    FirebaseFirestore firestore;
    FirebaseAuth mAuth;

    StorageReference miStorage;
    private TextView clientNombre,clientApellido,clientCodigo,clientTelefono,clientCorreo;
    private String IDcliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        mAuth=FirebaseAuth.getInstance();
        firestore=FirebaseFirestore.getInstance();
        IDcliente=mAuth.getCurrentUser().getUid();
        System.out.println(IDcliente);

        clientNombre=findViewById(R.id.textView16);
        clientApellido=findViewById(R.id.textView20);
        clientCodigo=findViewById(R.id.textView17);
        clientTelefono=findViewById(R.id.textView18);
        clientCorreo=findViewById(R.id.textView19);

        DocumentReference documentReference = firestore.collection("profesor").document(IDcliente);
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                System.out.println("se llegó hasta aqui");
                System.out.println("se llegó hasta aquilalalalala");
                if(task.isSuccessful()){
                    System.out.println("se llegó hasta aquix2");
                    DocumentSnapshot documentSnapshot = task.getResult();
                    System.out.println(documentSnapshot.getData());
                    if(documentSnapshot.exists()){
                        System.out.println("se llegó hasta aqui SEÑOR");

                        clientNombre.setText(documentSnapshot.getString("nombre"));
                        clientApellido.setText(documentSnapshot.getString("apellido"));
                        clientCodigo.setText(documentSnapshot.getString("codigo"));
                        clientTelefono.setText(documentSnapshot.getString("correo"));
                        clientCorreo.setText(documentSnapshot.getString("telefono"));

                        System.out.println(clientNombre);
                    }
                }
            }
        });
    }

    public void crearClase(View view){
        Intent intent = new Intent(this,CrearClasesProfesorActivity.class);
        startActivity(intent);

    }

    public void irListaClases(View view){
        Intent intent = new Intent(this,ListaClasesProfesorActivity.class);
        startActivity(intent);
    }


}