package com.example.gropypucp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gropypucp.Entity.Alumno;
import com.example.gropypucp.Profesor.ListaEstudiantesProfesorActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NuevaCuentaActivity extends AppCompatActivity {


    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    List<String> listaDocuments = new ArrayList<>();
    boolean exist = false;
    Button btnregistrar;
    EditText correo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_cuenta);

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        btnregistrar = findViewById(R.id.buttonRegistrar);
        EditText nombre = findViewById(R.id.editTextTextPersonName);
        EditText apellido = findViewById(R.id.editTextTextPersonName2);
        EditText codigo = findViewById(R.id.editTextNumber2);
        EditText telefono = findViewById(R.id.editTextNumber3);
        EditText rol = findViewById(R.id.editTextTextPersonName3);
        EditText correo = findViewById(R.id.editTextTextEmailAddress);
        EditText password = findViewById(R.id.editTextTextPassword2);
        EditText password1 = findViewById(R.id.editTextTextPassword3);

        btnregistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String rol1= rol.getText().toString();
                String nombreString = nombre.getText().toString();
                String apellidoString = apellido.getText().toString();
                String telefonoString = telefono.getText().toString();
                String codigoString = codigo.getText().toString();
                String correoString = correo.getText().toString().trim();
                String passwordString = password.getText().toString();
                String password1String = password1.getText().toString();

                if(nombreString.isEmpty() || apellidoString.isEmpty() || codigoString.isEmpty() || correoString.isEmpty() || passwordString.isEmpty() || password1String.isEmpty() || rol1.isEmpty()){
                    Toast.makeText(NuevaCuentaActivity.this,"Los campos no pueden ser vacios",Toast.LENGTH_SHORT).show();
                }else{
                    registerUsuario(nombreString,apellidoString,telefonoString,codigoString,correoString,passwordString,password1String,rol1);

                }


            }
        });

        firebaseFirestore.collection("profesor").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error!=null){
                    return;
                }
                for (QueryDocumentSnapshot document : value){
                    listaDocuments.add(document.getId());
                }
            }
        });

        firebaseFirestore.collection("alumno").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error!=null){
                    return;
                }
                for (QueryDocumentSnapshot document : value){
                    listaDocuments.add(document.getId());
                }
            }
        });

        TextView logueo = findViewById(R.id.textView5);
        logueo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NuevaCuentaActivity.this,InicioGropyActivity.class));
                finish();
            }


        });

    }

    public void registerUsuario(String nombre,String apellido,String dni,String codigo,String correo,String password,String password1,String rol){

        if(listaDocuments.contains(correo)){
            exist = true;
        }
        if(exist){
            Toast.makeText(NuevaCuentaActivity.this,"Este correo ya tiene una cuenta asociada",Toast.LENGTH_SHORT).show();
        }else if(codigo.length()!=8){
            Toast.makeText(NuevaCuentaActivity.this,"El codigo debe contener 8 carácteres",Toast.LENGTH_SHORT).show();
        }else if(rol.equals("rol")){
            Toast.makeText(NuevaCuentaActivity.this,"Eliga su rol",Toast.LENGTH_SHORT).show();
        }else if(!password.equals(password1)){
            Toast.makeText(NuevaCuentaActivity.this,"Las contraseñas deben ser iguales",Toast.LENGTH_SHORT).show();
        }else{

            firebaseAuth.createUserWithEmailAndPassword(correo,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    firebaseAuth.getCurrentUser().sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            String id = firebaseAuth.getCurrentUser().getUid();
                            Map<String,Object> map =new HashMap<>();
                            map.put("id",id);
                            map.put("nombre",nombre);
                            map.put("apellido",apellido);
                            map.put("correo",correo);
                            map.put("telefono",dni);
                            map.put("codigo",codigo);
                            map.put("password",password);
                            map.put("rol",rol);

                            System.out.println(rol);

                            if(rol.equalsIgnoreCase("alumno")){
                                firebaseFirestore.collection("alumno").document(id).set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        finish();
                                        startActivity(new Intent(NuevaCuentaActivity.this,InicioGropyActivity.class));
                                        Toast.makeText(NuevaCuentaActivity.this, "Alumno creado exitosamente", Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(NuevaCuentaActivity.this,"error al hacer el registro",Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                            if(rol.equalsIgnoreCase("profesor")){
                                firebaseFirestore.collection("profesor").document(id).set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        finish();
                                        startActivity(new Intent(NuevaCuentaActivity.this,InicioGropyActivity.class));
                                        Toast.makeText(NuevaCuentaActivity.this, "Profesor creado exitosamente", Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(NuevaCuentaActivity.this,"error al hacer el registro",Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
        }
    }
}