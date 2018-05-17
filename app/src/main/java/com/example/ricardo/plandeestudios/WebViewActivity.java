package com.example.ricardo.plandeestudios;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.webkit.WebView;

public class WebViewActivity extends AppCompatActivity {
    TextView bienvenida;
    WebView wb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        bienvenida = (TextView) findViewById(R.id.tvBienvenida);
        wb = (WebView) findViewById(R.id.wbPlan);
        Bundle b = this.getIntent().getExtras();
        bienvenida.setText("Plan de "+ b.get("Carrera"));
        String carrera = "NEG-A";
        //El siguiente switch busca que se cargue la página propia a tu carrera.
        switch ((String)b.get("Carrera")){
            case "Ing. en Negocios": carrera = "http://ingnegocios.itam.mx/";
            break;
            case "Ing. en Computación": carrera ="http://computacion.itam.mx/";
                break;
            case "Ing. en Mecatrónica": carrera ="http://mecatronica.itam.mx/";
                break;
            case "Ing. en Telecomunicaciones": carrera ="http://telecomunicaciones.itam.mx/";
                break;
            default: carrera = "http://industrial.itam.mx/";
            break;
        }

        //Estos métodos nos permiten cargar la página específica en el WebView
        wb.loadUrl(carrera);
        wb.getSettings().setJavaScriptEnabled(true);
        wb.setWebViewClient(new WebViewClient());
    }
}
