package com.example.ricardo.plandeestudios;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class SummaryActivity extends AppCompatActivity {
    TextView prom, porc, max,grad, bienvenida, prueba;
    Bundle b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        bienvenida = (TextView) findViewById(R.id.tvBienvenida);
        b = this.getIntent().getExtras();
        bienvenida.setText("Hola, "+ b.get("Nombre") +". Aquí te dejamos unos datos sobre tu carrera ...");

        prom = (TextView) findViewById(R.id.tvPromedio);
        porc = (TextView) findViewById(R.id.tvPorcentaje);
        max = (TextView) findViewById(R.id.tvMax);
        grad = (TextView) findViewById(R.id.tvGraduacion);
        prueba = (TextView) findViewById(R.id.prueba);

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"administracion", null,6);
        SQLiteDatabase db = admin.getWritableDatabase();

        //Consulta que te entrega el promedio del alumno
        Cursor cursor = db.rawQuery("select avg(calificacion) from vinculo where cursada = 1 and idUser = '" + b.get("Usuario") + "'",null);
        if(cursor.moveToNext())
            prom.setText("Promedio: " + Float.toString(cursor.getFloat(0)));
        //Consulta que te entrega el porcentaje de créditos
        Cursor cursor1 = db.rawQuery("select sum(creditos*cursada), sum(cursada) from materia inner join vinculo on materia.clave = vinculo.clave where cursada = 1 and idUser = '" + b.get("Usuario") + "'",null);
        if(cursor1.moveToNext()) {
            Float f = cursor1.getFloat(0);
            f = f/cursor1.getFloat(1);
            porc.setText(Float.toString(f*100)+"% de materias acreditadas.");
        }
        //Consulta que te regresa el promedio máximo que podrías tener
        Cursor cursor2 = db.rawQuery("select avg(calificacion) from vinculo where idUser = '" + b.get("Usuario") + "'",null);
        if(cursor2.moveToNext())
            max.setText("Tú máximo promedio posible: " + Float.toString(cursor.getFloat(0)));

        //Por implementar, debería regresar la fecha en la que podrías graduarte.
        Cursor cursor3 = db.rawQuery("select sum(creditos*cursada), sum(creditos) from materia inner join vinculo on materia.clave = vinculo.clave where cursada = 1 and idUser = '" + b.get("Usuario") + "'",null);
        if(cursor3.moveToNext()) {
            grad.setText("Graduación esperada Primavera 2019");
        }

        //Consulta que te regresa el número de créditos que haz acreditado
        Cursor cursor4 = db.rawQuery("select sum(creditos*cursada) from materia inner join vinculo on materia.clave = vinculo.clave where cursada = 1 and idUser = '" + b.get("Usuario") + "'",null);
        if(cursor4.moveToNext()) {
            porc.setText(Float.toString(cursor1.getFloat(0)) + " avance de créditos");
        }
        db.close();
    }
}
