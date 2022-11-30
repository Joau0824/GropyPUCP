package com.example.gropypucp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class InicioGropyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_gropy);

    }

    public void nuevaCuenta (View view){
        Intent intent = new Intent(this, NuevaCuentaActivity.class);
        startActivity(intent);
    }
}