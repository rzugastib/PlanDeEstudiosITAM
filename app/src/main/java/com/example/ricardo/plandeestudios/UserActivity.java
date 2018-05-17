package com.example.ricardo.plandeestudios;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class UserActivity extends AppCompatActivity {

    EditText nombre, usuario, contra, inicio;
    Spinner spCarrera;
    TextView prueba;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        nombre = (EditText) findViewById(R.id.etNombre);
        usuario = (EditText) findViewById(R.id.etUser);
        contra = (EditText) findViewById(R.id.etContra);
        inicio = (EditText) findViewById(R.id.etInicio);
        prueba = (TextView) findViewById(R.id.tvPrueba);
        //Carga los datos de las ingenierías como muestra de todas las carreras del ITAM
        List<String> spinnerArray =  new ArrayList<String>();
        spinnerArray.add("Ing. en Negocios");
        spinnerArray.add("Ing. en Computación");
        spinnerArray.add("Ing. en Telecomunicaciones");
        spinnerArray.add("Ing. en Mecatrónica");
        spinnerArray.add("Ing. Industrial");

        //El adaptador nos permite llenar el Spinner, que funciona como un DropDownList de Visual.
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCarrera = (Spinner) findViewById(R.id.spCarrera);
        spCarrera.setAdapter(adapter);
    }
    //Método que permite registrar a nuevos usuarios para usar la app.
    public void registro(View v){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"administracion",null,6);
        SQLiteDatabase db = admin.getWritableDatabase();
        ContentValues registro = new ContentValues();
        registro.put("nombre",nombre.getText().toString());
        registro.put("idUser",usuario.getText().toString());
        registro.put("pwd",contra.getText().toString());
        registro.put("carrera",spCarrera.getSelectedItem().toString());
        registro.put("inicio",inicio.getText().toString());
        //Manda información relevante para la usabilidad de la app.
        if(db.insert("usuario",null,registro)>0){
            Intent i = new Intent(this,LoginActivity.class);
            Bundle b = new Bundle();
            b.putLong("Id",spCarrera.getSelectedItemPosition());
            b.putString("Usuario",usuario.getText().toString());
            i.putExtras(b);
            startActivity(i);
        }else
            prueba.setText("Error al registrar el usuario. Intenta otro usuario");
        db.close();
    }
}
