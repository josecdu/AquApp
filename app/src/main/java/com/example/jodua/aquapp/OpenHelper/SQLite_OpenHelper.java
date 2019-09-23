package com.example.jodua.aquapp.OpenHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.jodua.aquapp.Utilidades.Utilidades;

public class SQLite_OpenHelper extends SQLiteOpenHelper {



    public SQLite_OpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Utilidades.CREAR_TABLA_USUARIO);
        db.execSQL(Utilidades.CREAR_TABLA_PERSONAL);
        db.execSQL(Utilidades.CREAR_TABLA_MEDIDOR);
        db.execSQL(Utilidades.CREAR_TABLA_LECTURA);



        String InsertarUsuario = "INSERT INTO `usuario` (usu_cod,usu_nom,usu_ape,usu_ci,usu_dir,usu_casanro,usu_tel,usu_correo) VALUES (1,'Tyson','Duarte','3212333','Chile','111','0981234567','tyson@gmail.com'),\n" +
                " (2,'Dylan','Coronel','3456890','Sargent Ovelar','222','0971998076','dylan@hotmail.com'),\n" +
                " (3,'Kaiser','Mendez','3876909','Iturbe','333','0961897456','Kaiser@gmail.com'),\n" +
                " (4,'Leon','Ullon','4548984','25 de Mayo','444','0991098123','leon@hotmail.com');";

        db.execSQL(InsertarUsuario);

        String InsertarMedidor = "INSERT INTO `medidor` (med_cod,usu_cod,med_marca,med_serie,med_estado,med_ultlec) VALUES " +
                " (1,1,'AVP','a111','Activo',1),\n" +
                " (2,2,'AVP','a222','Activo',0),\n" +
                " (3,3,'AVP','a333','Activo',0),\n" +
                " (4,4,'AVP','a444','Activo',0);";
        db.execSQL(InsertarMedidor);

        String InsertarPersonal = "insert into personal values(null,'Valerio','Mendez',123456,'Capiata','vale',1234,'Activo');";
        db.execSQL(InsertarPersonal);

        String InsertarLectura = "INSERT INTO `lectura`\n" +
                "(`lec_cod`, `flec_act`, `lec_act`, `flec_ant`, `lec_ant`,\n" +
                "`lec_estado`, `lec_foto`, `per_cod`, `med_cod`, `cic_cod`)\n" +
                "VALUES (\n" +
                "null,'2018-06-14',0,'2018-06-14', 0,'No facturado','foto',1,1,1);";
        db.execSQL(InsertarLectura);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
    //METODO QUE PERMITE ABRIR LA BD
    public void abrir(){
        this.getWritableDatabase();
    }

    //PERMITE CERRAR LA BD
    public void cerrar(){
        this.close();
    }

    //METODO QUE PERMITE INSERTAR REGISTROS EN LA TABLA USUARIOS
    public void insertarPersonal(String per_nom, String per_ape, String per_ci, String per_dir, String per_log,
                                 String per_pass, String per_estado){
        ContentValues valores=new ContentValues();

        valores.put("per_nom",per_nom);
        valores.put("per_ape",per_ape);
        valores.put("per_ci",per_ci);
        valores.put("per_dir",per_dir);
        valores.put("per_log",per_log);
        valores.put("per_pass",per_pass);
        valores.put("per_estado",per_estado);
        this.getWritableDatabase().insert("personal",null,valores);
    }

    //METODO QUE PERMITE VALIDAR SI EL USUARIO EXITE
    public Cursor ConsultarUsuPas(String usu, String pas) throws SQLException{
        Cursor mcursor=null; String nombre;
        mcursor=this.getReadableDatabase().query("personal",new String[]{"per_log","per_pass"},
                "per_log like '"+usu+"' and per_pass like '"+pas+"'", null,null,null,null);
                return mcursor;
    };



}
