package com.example.ricardo.plandeestudios;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MateriaActivity extends AppCompatActivity {
    private EditText clave, nombre, creditos, calificacion, semestre, cursada;
    private TextView prueba;

    Bundle b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_materia);
        clave = (EditText) findViewById(R.id.etClave);
        //En caso de que le hayan mandado un bundle con la clave lo escribirá automáticamente en el EditText
        //En este caso, viene de la selección de las materias desplegadas
        if(this.getIntent().hasExtra("Clave")){
            clave.setText((String)this.getIntent().getExtras().get("Clave"));
        }
        nombre = (EditText) findViewById(R.id.etNombre);
        creditos = (EditText) findViewById(R.id.etCred);
        calificacion = (EditText) findViewById(R.id.etCalif);
        semestre = (EditText) findViewById(R.id.etSemestre);
        cursada = (EditText) findViewById(R.id.etCursada);
        prueba = (TextView) findViewById(R.id.tvPrueba);
        if(clave.getText().toString()!=""){
            consulta(findViewById(android.R.id.content));
        }
    }
    //Método que inserta en la tabla materia y la tabla vinculo los datos de activity_materia.xm
    public void agrega(View v){
        //Declaramos la clase derivada de SQLiteOpenHelper y la base de datos
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"administracion",null,6);
        SQLiteDatabase db = admin.getWritableDatabase();
        b = this.getIntent().getExtras();
        //Declaramos objeto Content Values para permitirnos insertar los datos en la tabla materia.
        ContentValues registro = new ContentValues();
        registro.put("clave",clave.getText().toString());
        registro.put("nombre",nombre.getText().toString());
        registro.put("creditos",creditos.getText().toString());
        registro.put("verano",0);

        if (agregaVinculo(v)) {
            if(db.insert("materia", null, registro) > 0){
                limpiar(v);
                prueba.setText("Se registro materia para el usuario: " + b.get("Usuario"));
            }else
                prueba.setText("La materia ya se encontraba registrada en el dispositivo. Quedó registrada para el usuario: " +b.get("Usuario"));
        }else
            prueba.setText("No se pudo dar de alta la materia");
        db.close();
    }
    //Función que agrega el vínculo y es llamada desde la función agregar. Regresa Boolean si se dio de alta con éxito.
    public boolean agregaVinculo(View v){
        //Declaramos la clase derivada de SQLiteOpenHelper y la base de datos
        Boolean res = false;
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"administracion",null,6);
        SQLiteDatabase db = admin.getWritableDatabase();
        //Declaramos objeto Content Values para permitirnos insertar los datos en la tabla materia.
        ContentValues registro = new ContentValues();
        b = this.getIntent().getExtras();
        registro.put("idUser",(String)b.get("Usuario"));
        registro.put("clave",clave.getText().toString());
        registro.put("calificacion",calificacion.getText().toString());
        registro.put("semestre",semestre.getText().toString());
        registro.put("cursada",cursada.getText().toString());

        if (db.insert("vinculo", null, registro) > 0) {
            res = true;
        }else
            prueba.setText("No se pudo dar de alta la materia");
        db.close();
        return res;
    }
    //Método que elimina la tupla que víncula la materia con el usuario. La materia no se da de baja
    public void baja(View v){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"administracion",null,6);
        SQLiteDatabase db = admin.getWritableDatabase();
        b = this.getIntent().getExtras();
        if(db.delete("vinculo","clave = '" + clave.getText().toString() +"' and idUser = '"+ b.get("Usuario") +"'",null)>0){
            prueba.setText("Se ha eliminado el registro de la materia con clave: " + clave.getText());
            limpiar(v);
        }else
            prueba.setText("No se pudo eliminar");
        db.close();
    }
    //Método que permite llenar los EditText con la información correspondiente a la clave escrita en el campo de 'clave'.
    public void consulta(View v){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"administracion",null,6);
        SQLiteDatabase db = admin.getWritableDatabase();
        b = this.getIntent().getExtras();
        Cursor cursor = db.rawQuery("select nombre, creditos, calificacion, semestre, cursada from materia inner join vinculo on materia.clave = vinculo.clave where idUser = '" + (String)b.get("Usuario") + "' and materia.clave = '" + clave.getText().toString() + "'",null);
        if(cursor.moveToNext()){
            prueba.setText("Se realizó la búsqueda para la clave: " + clave.getText().toString());
            nombre.setText(cursor.getString(0));
            creditos.setText(Integer.toString(cursor.getInt(1)));
            calificacion.setText(Integer.toString(cursor.getInt(2)));
            semestre.setText(Integer.toString(cursor.getInt(3)));
            cursada.setText(Integer.toString(cursor.getInt(4)));
        }else
            prueba.setText("No se encuentra la materia");
        db.close();
    }
    //metodo que permite modificar los datos de una tupla correspondientes a el dato escrito en el campo 'clave'
    public void modifica(View v){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"administracion",null,6);
        SQLiteDatabase db = admin.getWritableDatabase();
        ContentValues registro = new ContentValues();
        if(!nombre.getText().toString().equals("")){registro.put("nombre",nombre.getText().toString());}
        if(!creditos.getText().toString().equals("")){registro.put("creditos",creditos.getText().toString());}
        if(db.update("materia",registro,"clave = '" + clave.getText().toString()+ "'",null)>0){
            prueba.setText("Se ha modificado la información de la materia");
        }else
            prueba.setText("No se ha modificado la materia");
        db.close();
        modificaVinculo(v);
    }
    //Método que modifica únicamente la tabla vínculo
    public void modificaVinculo(View v){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"administracion",null,6);
        SQLiteDatabase db = admin.getWritableDatabase();
        ContentValues registro = new ContentValues();
        b = this.getIntent().getExtras();
        if(!calificacion.getText().toString().equals("")){registro.put("calificacion",calificacion.getText().toString());}
        if(!semestre.getText().toString().equals("")){registro.put("semestre",semestre.getText().toString());}
        if(!cursada.getText().toString().equals("")){registro.put("cursada",cursada.getText().toString());}
        if(db.update("vinculo",registro,"clave = '" + clave.getText().toString()+ "' and idUser = '"+ b.get("Usuario") +"'",null)>0){
            limpiar(v);
            prueba.setText("Se ha modificado la información de la materia para el usuario: "+ (String)b.get("Usuario"));
        }else
            prueba.setText("No se ha modificado la materia.");
        db.close();
    }

    public void limpiar(View v){
        clave.setText("");
        nombre.setText("");
        creditos.setText("");
        calificacion.setText("");
        semestre.setText("");
        cursada.setText("");
    }



}
