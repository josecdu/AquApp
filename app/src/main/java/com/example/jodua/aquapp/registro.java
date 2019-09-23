package com.example.jodua.aquapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jodua.aquapp.OpenHelper.SQLite_OpenHelper;


public class registro extends AppCompatActivity {

    Button btnGrabarUsu;
    EditText txtNomUsu, txtApellido, txtCI,txtDireccion,txtUsuario, txtPass,txtConfirmar;
    SQLite_OpenHelper helper =new SQLite_OpenHelper(this,"AquaBD.db", null,1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        SQLite_OpenHelper conn =new SQLite_OpenHelper(this,"AquaBD.db", null,1);
        btnGrabarUsu=(Button)findViewById(R.id.btnRegistrarUsu);
        txtNomUsu=(EditText)findViewById(R.id.txtNomUsu);
        txtApellido=(EditText)findViewById(R.id.txtApellido);
        txtCI =(EditText)findViewById(R.id.txtCI);
        txtDireccion =(EditText)findViewById(R.id.txtDireccion);
        txtUsuario =(EditText)findViewById(R.id.txtUsuario);
        txtPass =(EditText)findViewById(R.id.txtContrasena);
        txtConfirmar =(EditText)findViewById(R.id.txtConfirmar);

        btnGrabarUsu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Contra = txtPass.getText().toString();
                String Contra2=txtConfirmar.getText().toString();

                if (txtNomUsu.getText().toString().isEmpty() || txtApellido.getText().toString().isEmpty() || txtCI.getText().toString().isEmpty()
                        || txtDireccion.getText().toString().isEmpty() || txtUsuario.getText().toString().isEmpty() | txtPass.getText().toString().isEmpty()
                    || txtConfirmar.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Favor complete todos los campos",Toast.LENGTH_LONG).show();
                }
                else if(Contra.trim().equals(Contra2.trim())){
                    SQLiteDatabase db= helper.getReadableDatabase();
                    Cursor cursor=db.rawQuery("select * from personal where per_ci='"+txtCI.getText()+"'",null);
                    if (cursor.moveToFirst()){
                        Toast.makeText(getApplicationContext(),"Ya existe un Personal con este número de Cédula",Toast.LENGTH_LONG).show();
                        txtCI.requestFocus();txtCI.selectAll();
                        helper.cerrar();
                    }else {
                        Cursor cursor2=db.rawQuery("select * from personal where per_log='"+txtUsuario.getText()+"'",null);
                        if (cursor2.moveToFirst()){
                            Toast.makeText(getApplicationContext(),"Ya existe un Personal con este Usuario",Toast.LENGTH_LONG).show();
                            txtCI.requestFocus();txtCI.selectAll();
                            helper.cerrar();
                        }else {
                            helper.abrir();
                            helper.insertarPersonal(String.valueOf(txtNomUsu.getText()),
                                    String.valueOf(txtApellido.getText()),
                                    String.valueOf(txtCI.getText()),
                                    String.valueOf(txtDireccion.getText()),
                                    String.valueOf(txtUsuario.getText()),
                                    String.valueOf(txtConfirmar.getText()),
                                    "Activo");
                            helper.cerrar();
                            Toast.makeText(getApplicationContext(),"Usuario creado con exito",Toast.LENGTH_LONG).show();
                            limpiar();
                        }
                    }


//                    Intent i= new Intent(getApplicationContext(),login.class);
//                    startActivity(i);
                }else {
                    Toast.makeText(getApplicationContext(),"Las contraseñas no coinciden",Toast.LENGTH_LONG).show();
                    txtPass.requestFocus();txtPass.selectAll();
                }

            /*    } else {
                    Toast.makeText(getApplicationContext(),"Las Contraseñas no coinciden",Toast.LENGTH_LONG).show();
                    txtPass.selectAll();
                    txtPass.requestFocus();
                };*/
            }
        });

    }

    private void limpiar() {
        txtNomUsu.setText("");
        txtApellido.setText("");
        txtCI.setText("");
        txtDireccion.setText("");
        txtUsuario.setText("");
        txtPass.setText("");
        txtConfirmar.setText("");
        txtNomUsu.requestFocus();
    }

    private void validarci(){

    }

}
