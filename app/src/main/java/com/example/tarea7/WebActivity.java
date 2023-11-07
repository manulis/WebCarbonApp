package com.example.tarea7;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import pl.droidsonroids.gif.GifImageView;

public class WebActivity extends AppCompatActivity {
    TextView Url;
    TextView Isgreen;
    TextView CleanerThan;
    TextView Energia;
    TextView Co2Grid;
    TextView Co2Renewbable;
    Button guardar;
    GifImageView Cargando;
    TextView errorMessage;
    ImageButton atras;

    Web web;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_urlinfo);


        Intent intent = getIntent();
        String json = intent.getStringExtra("Webs");
        web = Web.convertirDesdeJson(json);

        String url = web.getUrl();
        boolean isgreen = web.isIsgreen();
        double cleanerThanPercent = web.getCleanerThan();
        double energia = web.getEnergia();
        double gridGramsCo2 = web.getGridGramCo2();
        double renewableGramsCo2 = web.getRenewableGramsCo2();

        Url = findViewById(R.id.URL);
        Isgreen = findViewById(R.id.Green);
        CleanerThan = findViewById(R.id.cleanerthan);
        Energia = findViewById(R.id.energia);
        Co2Grid = findViewById(R.id.Co2Grid);
        Co2Renewbable = findViewById(R.id.Co2Renewbable);
        Cargando = findViewById(R.id.cargando);
        errorMessage = findViewById(R.id.errormessage);
        atras = findViewById(R.id.atras);
        guardar = findViewById(R.id.guardar);

        guardar.setVisibility(View.GONE);
        Cargando.setVisibility(View.GONE);
        errorMessage.setVisibility(View.GONE);

        Url.setText(url);

        CleanerThan.setText("Está pagina esta más limpia que el " + cleanerThanPercent + "% de las páginas ");

        Energia.setText("Consumo energetico: " +energia + " kWh");

        if(isgreen==true){
            Isgreen.setText("Esta página web parece funcionar con energía sostenible");
        }else{
            Isgreen.setText("Oh no, parece que esta página web no fuciona con energia sostenible");
        }

        Co2Grid.setText("Gramos de C02 emitidos por red electrica: " + gridGramsCo2 + " Gramos");
        Co2Renewbable.setText("Gramos de Co2 emitidos por la fuente de energia usada: " + renewableGramsCo2 + " Gramos");

        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}