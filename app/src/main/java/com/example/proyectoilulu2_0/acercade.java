package com.example.proyectoilulu2_0;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class acercade extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acercade);

    }
    public void volver (View v){
        Intent intent = new Intent (acercade.this, Website.class);
        startActivity( intent );
    }

}