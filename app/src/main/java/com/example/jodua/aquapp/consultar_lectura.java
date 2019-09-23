package com.example.jodua.aquapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.jodua.aquapp.Entidades.Lectura;
import com.example.jodua.aquapp.Entidades.Medidor;
import com.example.jodua.aquapp.OpenHelper.SQLite_OpenHelper;
import com.example.jodua.aquapp.Utilidades.Utilidades;

import java.util.ArrayList;
import java.util.List;

public class consultar_lectura extends AppCompatActivity {

    Button bConsultar;
    AutoCompleteTextView medidores;
    ArrayList<String> listMedidores;
    ArrayList<Medidor> medidoresList;
    SQLiteOpenHelper conn;
    Spinner sCiclo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        conn =new SQLite_OpenHelper(this,"AquaBD.db", null,1);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_lectura);
        sCiclo=findViewById(R.id.spinCiclo);
        bConsultar= findViewById(R.id.bConsultarLectura);

        final Spinner spinner = (Spinner) findViewById(R.id.spinCiclo);
        List<String> meses = new ArrayList<String>();
        meses.add("Enero"); meses.add("Febrero"); meses.add("Marzo"); meses.add("Abril");
        meses.add("Mayo"); meses.add("Junio"); meses.add("Julio"); meses.add("Agosto");
        meses.add("Septiembre"); meses.add("Octubre"); meses.add("Noviembre"); meses.add("Diciembre");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, meses);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        spinner.setSelection(5);

        medidores =(AutoCompleteTextView) findViewById(R.id.autoCompleteMedidor);
        consultarAdapterMedidor();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, listMedidores);
        AutoCompleteTextView AutoCompleteMedidor = (AutoCompleteTextView) findViewById(R.id.autoCompleteMedidor);
        AutoCompleteMedidor.setThreshold(1);
        AutoCompleteMedidor.setAdapter(adapter);
        AutoCompleteMedidor.getOnItemClickListener();

        medidores.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });

        bConsultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                traerlectura();
            }
        });

    }
    private void consultarAdapterMedidor() {
        SQLiteDatabase db= conn.getReadableDatabase();
        Medidor medidor = null;
        medidoresList = new ArrayList<Medidor>();
        Cursor cursor=db.rawQuery("SELECT * FROM "+ Utilidades.TABLA_MEDIDOR,null);
        while (cursor.moveToNext()){
            medidor =new Medidor();
            medidor.setMed_cod(cursor.getInt(0));
            medidor.setMed_serie(cursor.getString(3));
            medidor.setUsu_cod(cursor.getInt(1));
            Log.i("Usu_cod ",medidor.getUsu_cod().toString());

            medidoresList.add(medidor);
        }

        obtenerLista();
        cursor.close();
        conn.close();
    }
    private void obtenerLista() {
        listMedidores =new ArrayList<String>();
        for(int i=0; i<medidoresList.size(); i++){
            listMedidores.add(medidoresList.get(i).getMed_serie());
        }
    }

    private void traerlectura() {
        SQLiteDatabase db= conn.getReadableDatabase();
        Lectura lectura=null;
        String [] parametros={medidores.getText().toString()};
        //   try {
        Cursor cursor=db.rawQuery("select c.*\n, a.usu_nom || a.usu_ape as nombre, b.med_cod " +
                "from usuario a, medidor b, lectura c \n" +
                "where a.usu_cod=b.usu_cod and b.med_cod=c.med_cod and c.lec_estado='No facturado' and " +
                "c.cic_cod="+(sCiclo.getSelectedItemPosition()+1)+" and "+
                "b.med_serie=? ",parametros);

        if (cursor.moveToFirst()){
            DatabaseUtils.dumpCursor(cursor);
            lectura=new Lectura();
            lectura.setLec_cod(cursor.getInt(0));
            lectura.setFlec_act(cursor.getString(1));
            lectura.setLec_act(cursor.getInt(2));
            lectura.setFlec_ant(cursor.getString(3));
            lectura.setLec_ant(cursor.getInt(4));
            lectura.setLec_estado(cursor.getString(5));
            lectura.setLec_foto(cursor.getString(6));
            lectura.setPer_cod(cursor.getInt(7));
            lectura.setMed_cod(cursor.getInt(8));
            lectura.setCic_cod(cursor.getInt(9));

            cursor.close();

            Intent intent = new Intent(consultar_lectura.this,detalle_lectura.class);
            Bundle bundle=new Bundle();
            bundle.putSerializable("lectura",lectura);
            intent.putExtras(bundle);
            startActivity(intent);

        }else {
            Toast.makeText(getApplicationContext(),"Esta lectura no existe", Toast.LENGTH_SHORT).show();

        }

    }
}
