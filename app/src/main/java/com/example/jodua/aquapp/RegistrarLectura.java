package com.example.jodua.aquapp;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jodua.aquapp.Entidades.Lectura;
import com.example.jodua.aquapp.Entidades.Medidor;
import com.example.jodua.aquapp.Entidades.Personal;
import com.example.jodua.aquapp.OpenHelper.SQLite_OpenHelper;
import com.example.jodua.aquapp.Utilidades.Utilidades;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class RegistrarLectura extends AppCompatActivity {
    private final String CARPETA_RAIZ="imagenes/";
    private final String RUTA_IMAGEN=CARPETA_RAIZ+"fotos";

    AutoCompleteTextView medidores;
    //Adapter que contiene lo que se va a mostrar en el AutocompleteTextView
    ArrayList<String> listMedidores;
    //Adapter con la estrucura de la tabla Medidor, trae la estructura de "Entidades/Medidor"
    ArrayList<Medidor> medidoresList;
    //llama a la conexión
    SQLiteOpenHelper conn;
    String usu_cod, path="";
    EditText tUsuario, tFecAnt, tFecAct, tLecAct, tLecAnt ;
    TextView tconsumo;
    String fechaActual = new SimpleDateFormat("YYYY-MM-dd", Locale.getDefault()).format(new Date());
    String med_cod,lec_cod;
    int lecturaActual, lecturaAnterior, consumo;
    Button bRegistrar;
    ImageView imagen;
    final int COD_FOTO=20;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        conn =new SQLite_OpenHelper(this,"AquaBD.db", null,1);
        System.out.println("Codigo de personal: "+ Personal.per_codigo);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_lectura);
        tUsuario =(EditText)findViewById(R.id.tUsuario);
        tFecAct =(EditText)findViewById(R.id.tFecAct); tFecAct.setEnabled(false);
        tLecAct=(EditText)findViewById(R.id.tLecAct); tLecAct.setEnabled(false);
        tFecAnt =(EditText)findViewById(R.id.tFecAnt); tFecAnt.setEnabled(false);
        tLecAnt=(EditText)findViewById(R.id.tLecAnt); tLecAnt.setEnabled(false);
        tconsumo= (TextView) findViewById(R.id.tConsumo);
        bRegistrar= findViewById(R.id.bRegistrar);
        imagen =findViewById(R.id.camara);
        if (validarPermisos()){
            System.out.println("Permisos true");
            imagen.setEnabled(true);
        }else{
            System.out.println("Permisos false");
            imagen.setEnabled(false);

        }


            //Spinner
            spinner = (Spinner) findViewById(R.id.spinCiclo);
            List<String> meses = new ArrayList<String>();
            meses.add("Enero"); meses.add("Febrero"); meses.add("Marzo"); meses.add("Abril");
            meses.add("Mayo"); meses.add("Junio"); meses.add("Julio"); meses.add("Agosto");
            meses.add("Septiembre"); meses.add("Octubre"); meses.add("Noviembre"); meses.add("Diciembre");
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, meses);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(dataAdapter);
            spinner.setSelection(6);

            //Autocomplete
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
            traerlectura();
            }
        });

        tLecAct.setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if(!tLecAct.getText().toString().isEmpty() & tLecAct.getText().toString().matches("\\d+(?:\\.\\d+)?") ){
                    lecturaActual = Integer.parseInt(tLecAct.getText().toString());
                    lecturaAnterior = Integer.parseInt(tLecAnt.getText().toString());
                    calcularConsumo();
                }
                return false;
            }
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                limpiar();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        bRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (medidores.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Seleccione un Medidor y cargue la lectura actual", Toast.LENGTH_LONG).show();
                }
                else if (consumo<=0){
                    Toast.makeText(getApplicationContext(),"El consumo no puede ser menor a 0", Toast.LENGTH_LONG).show();
                }
                else if (path.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Debe tomar una fotografía del Medidor", Toast.LENGTH_LONG).show();

                }else {
                    SQLiteDatabase db= conn.getWritableDatabase();
                    ContentValues valores=new ContentValues();

                    valores.put("flec_act",tFecAct.getText().toString());
                    valores.put("lec_act",tLecAct.getText().toString());
                    valores.put("flec_ant",tFecAnt.getText().toString());
                    valores.put("lec_ant",tLecAnt.getText().toString());
                    valores.put("lec_estado","No facturado");
                    valores.put("lec_foto", path.toString());
                    valores.put("per_cod",Personal.per_codigo);
                    valores.put("med_cod",String.valueOf(med_cod));
                    valores.put("cic_cod",spinner.getSelectedItemPosition()+1);
                    db.insert("lectura",null,valores);
                    Toast.makeText(getApplicationContext(),"La lectura se registró correctamente", Toast.LENGTH_LONG).show();
                    limpiar(); deshabilitar(); path="";
                }

            }
        });
    }

    private boolean validarPermisos() {
        if (Build.VERSION.SDK_INT<Build.VERSION_CODES.M){
            System.out.println("Si es menor a M");
            return true;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if ((checkSelfPermission(CAMERA) == PackageManager.PERMISSION_GRANTED) &&
                    (checkSelfPermission(WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)) {
                return true;
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if((shouldShowRequestPermissionRationale(CAMERA)) ||
                    (shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE))){
                cargarDialogoRecomendacion();

            }else {
                requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE,CAMERA},100);
            }
        }
        return false;
    }

    private void cargarDialogoRecomendacion() {
       AlertDialog.Builder dialogo=new AlertDialog.Builder(RegistrarLectura.this);
        dialogo.setTitle("Permisos Desactivados");
        dialogo.setMessage("Debe aceptar los permisos para el correcto funcionamiento de la APP");
        dialogo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    System.out.println("Request Permission");
                    requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE,CAMERA},100);
                }else {
                    solicitarPermisosManual();
                }
            }
        });
        dialogo.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==100){
            if(grantResults.length==2 && grantResults[0]==PackageManager.PERMISSION_GRANTED &&
                    grantResults[1]==PackageManager.PERMISSION_GRANTED){
                imagen.setEnabled(true);
            }else {
                solicitarPermisosManual();
            }
        }
    }

    private void solicitarPermisosManual() {
        final CharSequence[] opciones={"Si","No"};
        final AlertDialog.Builder alertOpciones=new AlertDialog.Builder(RegistrarLectura.this);
        alertOpciones.setTitle("Desea configurar los permisos de forma Manual?");
        alertOpciones.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (opciones[which].equals("Si")){
                    Intent intent=new Intent();
                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri=Uri.fromParts("package",getPackageName(),null);
                    intent.setData(uri);
                    startActivity(intent);
                }else {
                    Toast.makeText(getApplicationContext(),"Los permisos no fueron aceptados",Toast.LENGTH_LONG);
                    dialog.dismiss();
                }
            }
        });
        alertOpciones.show();
    }

    private void deshabilitar() {
        tFecAct =(EditText)findViewById(R.id.tFecAct); tFecAct.setEnabled(false);
        tLecAct=(EditText)findViewById(R.id.tLecAct); tLecAct.setEnabled(false);
        tFecAnt =(EditText)findViewById(R.id.tFecAnt); tFecAnt.setEnabled(false);
        tLecAnt=(EditText)findViewById(R.id.tLecAnt); tLecAnt.setEnabled(false);
    }

    private void limpiar() {
        medidores.setText(""); medidores.requestFocus();
        tLecAnt.setText("");
        tLecAct.setText("");
        tFecAnt.setText("");
        tFecAct.setText("");
        tconsumo.setText("Consumo");
        tUsuario.setText("");
        imagen.setImageResource(R.drawable.camera);
    }

    private void calcularConsumo() {

        System.out.println("La resta es= "+lecturaActual+"-"+lecturaAnterior);

        consumo=lecturaActual - lecturaAnterior;
        tconsumo.setText("El consumo es "+String.valueOf(consumo));
        System.out.println("El consumo es "+ consumo);

    }

    private void traerlectura() {
        SQLiteDatabase db= conn.getReadableDatabase();
        String [] parametros={medidores.getText().toString()};
            //
            Cursor cursor=db.rawQuery("select a.usu_nom || \" \" || a.usu_ape as nombre, b.med_cod, b.med_ultlec, c.lec_cod, c.lec_act, c.flec_act, c.lec_ant, c.flec_ant\n" +
                    "from usuario a, medidor b, lectura c \n" +
                    "where a.usu_cod=b.usu_cod and b.med_cod=c.med_cod and c.lec_estado='No facturado' and c.cic_cod="+(spinner.getSelectedItemPosition()+1)+" " +
                    "and b.med_serie=? ",parametros);
            //si ya existe una lectura para el ciclo seleccionado pregunta si se desea anular esta lectura
            if (cursor.moveToLast()){
                lec_cod=cursor.getString(3);
                db.close();
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                //Yes button clicked
                                anularlectura();
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                limpiar();
                                break;
                        }
                    }
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(RegistrarLectura.this);
                builder.setMessage("Ya existe una lectura para este ciclo. Desea reemplazarla?");
                builder.setPositiveButton("Si", dialogClickListener);
                builder.setNegativeButton("No", dialogClickListener);
                builder.show();
            }else {
                traerultimalectura();
            }
    }

    private void anularlectura() {
        //anula la lectura para el ciclo seleccionado
        SQLiteDatabase db= conn.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("lec_estado","anulado");
        db.update("lectura",values,"lec_cod="+lec_cod,null);
        db.close();
        Toast.makeText(getApplicationContext(),"La lectura ha sido anulada", Toast.LENGTH_SHORT).show();
        traerultimalectura();
    }

    private void traerultimalectura() {
        //trae la ultima lectura con estado facturado, si no existe, crea una lectura
        SQLiteDatabase db= conn.getReadableDatabase();
        Cursor cursor=db.rawQuery("select a.usu_nom || \" \" || a.usu_ape as nombre, b.med_cod, b.med_ultlec, c.lec_cod, c.lec_act, c.flec_act, c.lec_ant, c.flec_ant\n" +
                "from usuario a, medidor b, lectura c \n" +
                "where a.usu_cod=b.usu_cod and b.med_cod=c.med_cod and c.lec_estado='No facturado' " +
                "and b.med_serie='"+medidores.getText()+"'",null);
        if (cursor.moveToLast()){
            DatabaseUtils.dumpCursor(cursor);
            tUsuario.setText(cursor.getString(0));
            med_cod=(cursor.getString(1));
            tLecAnt.setText(cursor.getString(4));
            tFecAct.setText(fechaActual);
            tFecAnt.setText(cursor.getString(5));
            tLecAct.setEnabled(true); tLecAct.requestFocus();
            cursor.close();

    }else {
            Cursor cursor2=db.rawQuery("select a.usu_nom ||  \" \" || a.usu_ape as nombre, b.med_cod, b.med_ultlec\n" +
                    "from usuario a, medidor b\n" +
                    "where a.usu_cod=b.usu_cod  and b.med_serie='"+medidores.getText()+"'",null);
            cursor2.moveToFirst();
            tUsuario.setText(cursor2.getString(0));
            tLecAnt.setText("0");
            med_cod=(cursor2.getString(1));
            tFecAct.setText(fechaActual);
            tFecAnt.setText(fechaActual);
            tLecAct.setEnabled(true); tLecAct.requestFocus();
            cursor2.close();
        }
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

    public void onclick(View view) {
        tomarFotografia();
        System.out.println(path.toString());

    }

    static final int REQUEST_TAKE_PHOTO = 1;

    private void tomarFotografia() {
        File fileImagen=new File(Environment.getExternalStorageDirectory(),RUTA_IMAGEN);
        boolean isCreada=fileImagen.exists();
        String nombreImagen="";
        if(isCreada==false){
            isCreada=fileImagen.mkdirs();
        }

        if(isCreada==true){
            nombreImagen=(System.currentTimeMillis()/1000)+".jpg";
        }


        path=Environment.getExternalStorageDirectory()+
                File.separator+RUTA_IMAGEN+File.separator+nombreImagen;

        File imagen=new File(path);

        Intent intent=null;
        intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        ////
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N)
        {
            String authorities=getApplicationContext().getPackageName()+".provider";
            Uri imageUri=FileProvider.getUriForFile(this,authorities,imagen);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        }else
        {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imagen));
        }
        startActivityForResult(intent,COD_FOTO);

        ////
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){

            switch (requestCode){
                case COD_FOTO:
                    MediaScannerConnection.scanFile(this, new String[]{path}, null,
                            new MediaScannerConnection.OnScanCompletedListener() {
                                @Override
                                public void onScanCompleted(String path, Uri uri) {
                                    Log.i("Ruta de almacenamiento","Path: "+path);
                                }
                            });

                    Bitmap bitmap= BitmapFactory.decodeFile(path);
                    imagen.setImageBitmap(bitmap);
                    break;
            }


        }
    }
    }
