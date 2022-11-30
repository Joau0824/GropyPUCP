package com.example.gropypucp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.gropypucp.Profesor.ListaEstudiantesProfesorActivity;

public class NuevaCuentaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_cuenta);
    }
    public void vista1(View view){
        Intent intent = new Intent(this, ListaEstudiantesProfesorActivity.class);
        startActivity(intent);

    }
}