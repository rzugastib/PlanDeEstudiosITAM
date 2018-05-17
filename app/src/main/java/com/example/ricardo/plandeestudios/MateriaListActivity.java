package com.example.ricardo.plandeestudios;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
//Activity que me permite desplegar las materias registradas en un ListView
public class MateriaListActivity extends AppCompatActivity {
    AdminSQLiteOpenHelper admin;
    ListView lvMaterias;
    TextView prueba;
    Bundle b;
    ArrayList<String> informacion;
    ArrayList<Materia> materias;
    String CLAVE;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_materia_list);
        prueba = (TextView) findViewById(R.id.prueba);
        prueba.setText("Sí funciona la base de datos");
        admin = new AdminSQLiteOpenHelper(this,"administracion",null,6);
        lvMaterias = (ListView) findViewById(R.id.lvMaterias);

        //Este método realiza el query
        consultaListaMaterias();

        ArrayAdapter adaptador = new ArrayAdapter(this,android.R.layout.simple_list_item_1,informacion);
        lvMaterias.setAdapter(adaptador);

        lvMaterias.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CLAVE = materias.get(position).getClave();
            }
        });
    }

    private void consultaListaMaterias() {
        SQLiteDatabase db= admin.getReadableDatabase();

        b = this.getIntent().getExtras();
        Materia materia = null;
        materias = new ArrayList<Materia>();
        Cursor cursor = db.rawQuery("select clave, nombre, creditos from materia",null);
        cursor.moveToNext();
        prueba.setText(cursor.getString(0).toString());
        //se crean la clase materia, se instancia y se modifican sus atributos para cada tupla que arroje el cursor.
        while (cursor.moveToNext()){
            materia = new Materia();
            materia.setClave(cursor.getString(0));
            materia.setNombre(cursor.getString(1));
            materia.setCreditos(cursor.getInt(2));
            materias.add(materia);
        }
        //Método que Genera la lista de información que se insertará en los List View.
        obtenerLista();
    }

    private void obtenerLista() {
        informacion = new ArrayList<String>();
        for(int i = 0; i < materias.size();i++){
            informacion.add(materias.get(i).getClave() + " - " + materias.get(i).getNombre() + " - " + materias.get(i).getCreditos());
        }
    }

    public void modificar(View v){
        b.putString("Clave",CLAVE);
        startActivity(new Intent(this,MateriaActivity.class).putExtras(b));
    }
}
