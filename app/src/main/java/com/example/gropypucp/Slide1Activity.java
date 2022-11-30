package com.example.gropypucp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Slide1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide1);
    }

    public void slide2(View view){
        Intent intent = new Intent(this,Slide2Activity.class);
        startActivity(intent);

    }

    public void Saltar1(View view){
        Intent intent = new Intent(this, InicioGropyActivity.class);
        startActivity(intent);
    }
}