package com.example.jodua.aquapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jodua.aquapp.Entidades.Lectura;
import com.example.jodua.aquapp.Entidades.Personal;
import com.example.jodua.aquapp.OpenHelper.SQLite_OpenHelper;
import com.example.jodua.aquapp.Utilidades.Utilidades;

public class login extends AppCompatActivity {

    TextView tvRegistrese;
    Button btnIngresar;
    SQLite_OpenHelper conn =new SQLite_OpenHelper(this,"AquaBD.db", null,1);
    EditText txtusu,txtpas;
    int contador =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        txtusu=(EditText)findViewById(R.id.txtusuario);
        txtpas=(EditText)findViewById(R.id.txtpassword);
        tvRegistrese = (TextView) findViewById(R.id.tvRegistrese);
        tvRegistrese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent((getApplicationContext()),registro.class);
                startActivity(i);
            }
        });

        btnIngresar=findViewById(R.id.btningresar);
        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validaracceso();
            }
        });

    }
    private void validaracceso() {
        SQLiteDatabase db= conn.getReadableDatabase();
        Cursor cursor=db.rawQuery("select * from personal where per_log='"+txtusu.getText()+"'",null);
        String Contra = "";
        String Contra2 = "";
        String log ="";
        String log2 =""; String per_cod=""; String usu_estado="";
        if (cursor.moveToFirst()){
            DatabaseUtils.dumpCursor(cursor);
            Contra=txtpas.getText().toString();
            Contra2=cursor.getString(6);
            log=cursor.getString(5);
            per_cod=cursor.getString(0);
            usu_estado=cursor.getString(7);
            Personal.per_codigo=cursor.getString(0);
            if(usu_estado.equals("bloqueado")) {
                Toast.makeText(getApplicationContext(),"Usuario bloqueado", Toast.LENGTH_SHORT).show();
                Limpiar();
            }else if(Contra.trim().equals(Contra2.trim())) {
                Intent i = new Intent(getApplicationContext(), Principal.class);
                startActivity(i);
            }else {
                Toast.makeText(getApplicationContext(),"Usuario o contraseña incorrecto bloquear", Toast.LENGTH_SHORT).show();
                contador++;  txtpas.requestFocus();
                System.out.println("Contador "+contador);
                if (contador == 3)
                {
                    SQLiteDatabase db2= conn.getWritableDatabase();
                    ContentValues values=new ContentValues();
                    values.put(Utilidades.PERSONAL_PER_ESTADO,"bloqueado");
                    db2.update("personal",values,"per_cod="+per_cod,null);
                    Toast.makeText(getApplicationContext(),"Su Usuario ha sido bloqueado", Toast.LENGTH_SHORT).show();
                    Limpiar();
                } else
                {
                    txtpas.requestFocus();
                }
            }
            cursor.close();
        }else {
            Toast.makeText(getApplicationContext(),"El Usuario no existe", Toast.LENGTH_SHORT).show();

        }
    }

    private void Limpiar() {
        txtpas.setText("");
        txtusu.setText("");
    }
}
