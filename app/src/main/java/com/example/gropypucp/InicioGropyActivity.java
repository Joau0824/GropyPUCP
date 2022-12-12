package com.example.gropypucp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gropypucp.Profesor.CrearClasesProfesorActivity;
import com.example.gropypucp.Profesor.ListaClasesProfesorActivity;
import com.example.gropypucp.Profesor.ListaEstudiantesProfesorActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class InicioGropyActivity extends AppCompatActivity {

    TextView btnloguear;
    EditText correoLogueo;
    EditText passwordLogueo;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    String rolLogueo="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_gropy);

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        btnloguear = findViewById(R.id.textViewSesion);
        correoLogueo = findViewById(R.id.editTextTextPersonName7);
        passwordLogueo = findViewById(R.id.editTextTextPasswordS);




        TextView registrar = findViewById(R.id.textView2Register);
        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InicioGropyActivity.this,NuevaCuentaActivity.class);
                startActivity(intent);
            }
        });

        btnloguear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email =  correoLogueo.getText().toString().trim();
                String password = passwordLogueo.getText().toString();

                if(email.isEmpty() || password.isEmpty() ){
                    Toast.makeText(InicioGropyActivity.this,"El correo o la contraseña no pueden ser vacias",Toast.LENGTH_SHORT).show();
                }else{
                    loginUsuario(email,password);
                }

            }
        });




    }

    public void loginUsuario(String email,String password){

        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(firebaseAuth.getCurrentUser().isEmailVerified()){
                    System.out.println(email);
                    System.out.println(password);
                    if(task.isSuccessful()){
                        firebaseFirestore.collection("alumno").whereEqualTo("correo",email).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if(task.isSuccessful()){
                                    System.out.println(task.getResult().isEmpty());
                                    if(task.getResult().isEmpty()){
                                        firebaseFirestore.collection("profesor").whereEqualTo("correo",email).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                if(task.isSuccessful()){
                                                    System.out.println(task.getResult().isEmpty());
                                                    if(task.getResult().isEmpty()){
                                                        Toast.makeText(InicioGropyActivity.this,"No esta asociado este correo",Toast.LENGTH_SHORT).show();
                                                    }else {
                                                        startActivity(new Intent(InicioGropyActivity.this, ListaClasesProfesorActivity.class));
                                                        Toast.makeText(InicioGropyActivity.this,"Bienvenido Profesor",Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            }
                                        });
                                    }else{
                                        startActivity(new Intent(InicioGropyActivity.this, ListaEstudiantesProfesorActivity.class));
                                        finish();
                                        Toast.makeText(InicioGropyActivity.this,"Bienvenido Alumno",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        });
                    }else{
                        Toast.makeText(InicioGropyActivity.this,"Error en las credenciales",Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

    }
    public void olvidarContrasena(View view) {
        Intent intent = new Intent(this, OlvidarContrasena.class);
        startActivity(intent);
    }

}