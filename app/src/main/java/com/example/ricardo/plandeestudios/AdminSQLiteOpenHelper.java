package com.example.ricardo.plandeestudios;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.security.cert.CRLException;

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper{

    //Creación de tablas
    String CREATE_USUARIO = "create table usuario (idUser text primary key, pwd text, nombre text, carrera text, inicio text)";
    String CREATE_MATERIA = "create table materia (clave text primary key, nombre text, creditos integer, verano integer)";
    //Esta tabla es necesaria por que la relación entre la materia y el usuario es de m, n, y hay información característica del vínculo, como es el semestre en el que llevas la materia y tu calificación
    String CREATE_VINCULO = "create table vinculo (idUser text references usuario, clave text references materia, calificacion integer, semestre integer, cursada integer, primary key(idUser,clave))";
    String CREATE_PROGRAMA = "create table programa (idPrograma text primary key, programa text)";
    String CREATE_PROG_MAT = "create table progMat (idPrograma text references programa, clave text references materia, optativa integer, primary key (idPrograma,clave))";
    String CREATE_PRE = "create table prerrequisito (materia text references materia, pre text references materia, idPrograma text references programa, primary key (materia, pre, idPrograma))";

    public AdminSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_PRE);
        db.execSQL(CREATE_VINCULO);
        db.execSQL(CREATE_MATERIA);
        db.execSQL(CREATE_USUARIO);
        db.execSQL(CREATE_PROGRAMA);
        db.execSQL(CREATE_PROG_MAT);
        //Por implementar
        //insertaTuplas();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists prerrequisito");
        db.execSQL("drop table if exists vinculo");
        db.execSQL("drop table if exists materia");
        db.execSQL("drop table if exists usuario");
        db.execSQL("drop table if exists programa");
        db.execSQL("drop table if exists progMat");
        db.execSQL(CREATE_PRE);
        db.execSQL(CREATE_PROGRAMA);
        db.execSQL(CREATE_PROG_MAT);
        db.execSQL(CREATE_VINCULO);
        db.execSQL(CREATE_MATERIA);
        db.execSQL(CREATE_USUARIO);
    }

    /* Por implementar
    public void insertaTuplas(SQLiteDatabase db){
        for(int i = 0; i<5;i++) {
            String tupla = "insert into programa (" + + ","+  +")";
            db.execSQL(tupla);
        }
    }

    }*/
}
