package com.example.jodua.aquapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class Principal extends AppCompatActivity {
    Button bRegistrarLectura;
    Button bConsultarLetura;
    Button bRegistrarUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        bRegistrarLectura=findViewById(R.id.bRegistrarLectura);
        bConsultarLetura=findViewById(R.id.bConsultarLectura);
        bRegistrarUsuario=findViewById(R.id.bRegistrarUsuario);

        bRegistrarLectura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent((getApplicationContext()),RegistrarLectura.class);
                startActivity(i);
            }
        });
        bConsultarLetura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent((getApplicationContext()),consultar_lectura.class);
                startActivity(i);
            }
        });
        bConsultarLetura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent((getApplicationContext()),consultar_lectura.class);
                startActivity(i);
            }
        });
        bRegistrarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent((getApplicationContext()),registro.class);
                startActivity(i);
            }
        });
    }

}
