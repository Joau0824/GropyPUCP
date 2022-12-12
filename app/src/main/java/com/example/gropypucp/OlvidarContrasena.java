package com.example.gropypucp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

public class OlvidarContrasena extends AppCompatActivity {

    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;

    EditText correo;
    Button btnCambiarContra;
    private String email = "" ;

    private ProgressDialog mDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_olvidar_contrasena);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        correo = findViewById(R.id.editTextTextPersonName8);
        btnCambiarContra = findViewById(R.id.button2);

        mDialog = new ProgressDialog(this);

        btnCambiarContra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                email = correo.getText().toString();

                if(!email.isEmpty()){
                    mDialog.setMessage("Espere un momento...");
                    mDialog.setCanceledOnTouchOutside(false);
                    mDialog.show();
                    resetPassword();
                }
                else{
                    Toast.makeText( OlvidarContrasena.this, "Debe ingresar su correo", Toast.LENGTH_SHORT).show();
                }

                mDialog.dismiss();


            }
        });
    }

    private void resetPassword() {
        firebaseAuth.setLanguageCode("es");
        firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){

                    Toast.makeText( OlvidarContrasena.this, "Se ha enviado un correo para reestablecer tu contraseña", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(OlvidarContrasena.this,InicioGropyActivity.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText( OlvidarContrasena.this, "No se pudo enviar el correo de reestablecer contraseña", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

    public void volverLogin(View view) {
        Intent intent = new Intent(this, InicioGropyActivity.class);
        startActivity(intent);
    }


}