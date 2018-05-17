package com.example.ricardo.plandeestudios;

import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView bienvenida;
    Bundle b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Recibe los datos de la Activity donde se inició sesión para darte la bienvenida.
        bienvenida = (TextView) findViewById(R.id.tvBienvenida);
        b = this.getIntent().getExtras();
        bienvenida.setText("Hola, "+(String)b.get("Nombre") + ". ¿Qué quieres hacer?");
    }
    //Abre una ventana distinta para cada uno de los 4 botones
    public void verLista(View v){
        startActivity(new Intent(this, WebViewActivity.class).putExtras(b));
    }

    public void verResumen(View v){
        Intent i = new Intent(this,SummaryActivity.class);
        i.putExtras(b);
        startActivity(i);
    }

    public void verMateria(View v){
        Intent i = new Intent(this,MateriaActivity.class);
        i.putExtras(b);
        startActivity(i);
    }

    public void verMaterias(View v){
        Intent i = new Intent(this,MateriaListActivity.class);
        i.putExtras(b);
        startActivity(i);
    }
}
