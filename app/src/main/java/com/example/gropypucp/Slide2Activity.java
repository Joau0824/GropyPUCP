package com.example.gropypucp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Slide2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide2);
    }
    public void slide3(View view){
        Intent intent = new Intent(this, Slide3Activity.class);
        startActivity(intent);

    }

    public void saltar2(View view){
        Intent intent = new Intent(this, InicioGropyActivity.class);
        startActivity(intent);
    }
}