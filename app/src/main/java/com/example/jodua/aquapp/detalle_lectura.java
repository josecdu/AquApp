package com.example.jodua.aquapp;

import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jodua.aquapp.Entidades.Lectura;
import com.example.jodua.aquapp.OpenHelper.SQLite_OpenHelper;

public class detalle_lectura extends AppCompatActivity {
    TextView tciclo,tUsuario, tMedidor, tLecAct, tLecAnt, tFLecAct, tFlecAnt,tConsumo;
    ImageView imagen;
    String ciclo, mCurrentPhotoPath;
    SQLiteOpenHelper conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        conn =new SQLite_OpenHelper(this,"AquaBD.db", null,1);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_lectura);


        tciclo=findViewById(R.id.tciclo);
        tUsuario=findViewById(R.id.tUsuario);
        tMedidor=findViewById(R.id.tmedidor);
       // tFlecAnt=findViewById(R.id.tFechaAnt);
        //tLecAnt=findViewById(R.id.tLecAnt);
        tFLecAct=findViewById(R.id.tFechaAct);
        tLecAct=findViewById(R.id.tLecAct);
        tConsumo=findViewById(R.id.tConsumo);
        imagen=findViewById(R.id.iFoto);
        
        
        Bundle objetoEnviado=getIntent().getExtras();
        Lectura lectura=null;
        if (objetoEnviado!=null){
            lectura= (Lectura) objetoEnviado.getSerializable("lectura");
            ciclo=(lectura.getCic_cod().toString());
            mostrarCiclo(lectura.getCic_cod());
            mostarUsuarioMedidor(lectura.getMed_cod());
            tFLecAct.setText(lectura.getFlec_act());
            tLecAct.setText(lectura.getLec_act().toString());
            tConsumo.setText(String.valueOf(lectura.getLec_act()-lectura.getLec_ant()));
            mCurrentPhotoPath=(lectura.getLec_foto().toString());
            System.out.println(mCurrentPhotoPath);
            Bitmap bitmap= BitmapFactory.decodeFile(mCurrentPhotoPath);
            imagen.setImageBitmap(bitmap);
           //setPic();
        }

    }

    private void mostarUsuarioMedidor(Integer med_cod) {
        SQLiteDatabase db = conn.getReadableDatabase();
        String[] parametros = {med_cod.toString()};
        try{
            Cursor cursor = db.rawQuery("select a.usu_nom || a.usu_ape as nombre, b.med_serie\n" +
                    "from usuario a, medidor b \n" +
                    "where a.usu_cod=b.usu_cod   and b.med_cod= ?", parametros);

            if (cursor.moveToFirst()) {
                tUsuario.setText(cursor.getString(0));
                tMedidor.setText(cursor.getString(1));
        }
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"Hubo un error al buscar los datos",Toast.LENGTH_LONG);

        }
    }
    private void mostrarCiclo(Integer cic_cod) {
        if(String.valueOf(ciclo).equals("1")){ tciclo.setText("Enero");}else if(String.valueOf(ciclo).equals("2")){tciclo.setText("Febrero");} else if(String.valueOf(ciclo).equals("3")){tciclo.setText("Marzo");}
        else if(String.valueOf(ciclo).equals("4")){tciclo.setText("Abril");} else if(String.valueOf(ciclo).equals("5")){tciclo.setText("Mayo");}
        else if(String.valueOf(ciclo).equals("6")){tciclo.setText("Junio");} else if(String.valueOf(ciclo).equals("7")){tciclo.setText("Julio");}
        else if(String.valueOf(ciclo).equals("8")){tciclo.setText("Agosto");} else if(String.valueOf(ciclo).equals("9")){tciclo.setText("Septiembre");}
        else if(String.valueOf(ciclo).equals("10")){tciclo.setText("Octubre");} else if(String.valueOf(ciclo).equals("11")){tciclo.setText("Noviembre");}
        else if(String.valueOf(ciclo).equals("12")){tciclo.setText("Dicimbre");}

    }

    private void setPic() {
        // Get the dimensions of the View
        int targetW = imagen.getWidth(); System.out.println(targetW+" Width");
        int targetH = imagen.getHeight();System.out.println(targetH+" Height");

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        imagen.setImageBitmap(bitmap);

    }
}
