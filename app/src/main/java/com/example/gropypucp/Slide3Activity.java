package com.example.gropypucp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Slide3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide3);
    }

    public void Inicio(View view){
        Intent intent = new Intent(this, InicioGropyActivity.class);
        startActivity(intent);
    }
}