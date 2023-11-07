package com.example.tarea7;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;

public class UrlinfoActivity extends AppCompatActivity {
    TextView Url;
    TextView isgreen;
    TextView CleanerThan;
    TextView Energia;
    TextView Co2Grid;
    TextView Co2Renewbable;
    Button guardar;
    GifImageView Cargando;
    TextView errorMessage;
    ImageButton atras;
    Web web;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_urlinfo);
        setTitle("Información");

        Url = findViewById(R.id.URL);
        isgreen = findViewById(R.id.Green);
        CleanerThan = findViewById(R.id.cleanerthan);
        Energia = findViewById(R.id.energia);
        Co2Grid = findViewById(R.id.Co2Grid);
        Co2Renewbable = findViewById(R.id.Co2Renewbable);
        Cargando = findViewById(R.id.cargando);
        errorMessage = findViewById(R.id.errormessage);
        atras = findViewById(R.id.atras);
        guardar = findViewById(R.id.guardar);

        Url.setVisibility(View.GONE);
        isgreen.setVisibility(View.GONE);
        CleanerThan.setVisibility(View.GONE);
        Energia.setVisibility(View.GONE);
        Co2Grid.setVisibility(View.GONE);
        Co2Renewbable.setVisibility(View.GONE);
        errorMessage.setVisibility(View.GONE);
        guardar.setVisibility(View.GONE);

        Intent intent = getIntent();
        String Urlweb = intent.getStringExtra("UrlWeb");
        final String Endpoint = "https://api.websitecarbon.com/site?url=";

        getInfoWeb(Urlweb,Endpoint);

        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {finish();}
        });

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guardarInfo();
            }
        });

    }

    void getInfoWeb(String UrlWeb, String Endpoint){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = Endpoint+UrlWeb;

        System.out.println(url);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest


            (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        webResult(response);
                    } catch (JSONException e) {
                        e.printStackTrace();

                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Cargando.setImageResource(R.drawable.error);
                    errorMessage.setVisibility(View.VISIBLE);
                    System.out.println(error);
                }
            });

        int tiempoEspera = 100000;
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(tiempoEspera,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(jsonObjectRequest);
    }

    @SuppressLint("SetTextI18n")
    void webResult(JSONObject result) throws JSONException {
        System.out.println(result);
        parseJsonResult(result);
        web=parseJsonResult(result);

        System.out.println(web);

        Cargando.setVisibility(View.GONE);
        Url.setVisibility(View.VISIBLE);
        isgreen.setVisibility(View.VISIBLE);
        CleanerThan.setVisibility(View.VISIBLE);
        Energia.setVisibility(View.VISIBLE);
        Co2Renewbable.setVisibility(View.VISIBLE);
        Co2Grid.setVisibility(View.VISIBLE);
        guardar.setVisibility(View.VISIBLE);

        double cleanerThanPercent = (web.cleanerThan);
        boolean Isgreen = web.isgreen;
        String webUrl = web.url;
        double energia = web.energia;
        double co2Grid = web.gridGramsCo2;
        double co2Renewbable = web.renewableGramsCo2;

        System.out.println(energia);

        Url.setText(webUrl);

        Energia.setText("Consumo energetico: " +energia + " kWh");

        CleanerThan.setText("Está pagina esta más limpia que el " + cleanerThanPercent + "% de las páginas ");

        if(Isgreen==true){
            isgreen.setText("Esta página web parece funcionar con energía sostenible");
        }else{
            isgreen.setText("Oh no, parece que esta página web no fuciona con energia sostenible");
        }

        Co2Grid.setText("Gramos de C02 emitidos por red electrica: " + co2Grid + " Gramos");
        Co2Renewbable.setText("Gramos de Co2 emitidos por la fuente de energia usada: " + co2Renewbable + " Gramos");
    }

    Web parseJsonResult(JSONObject json) throws JSONException {

        boolean isgreen;
        String url = json.getString("url");
        double cleanerThan =  json.getDouble("cleanerThan")*100.0;
        JSONObject statistics = json.getJSONObject("statistics");
        double energia = statistics .getDouble("energy")*100000.0;
        JSONObject co2 = statistics.getJSONObject("co2");
        JSONObject grid = co2.getJSONObject("grid");
        double gridGramsCo2 = grid.getDouble("grams");
        JSONObject renewable = co2.getJSONObject("renewable");
        double renewableGramsCo2 = renewable.getDouble("grams");

        if(json.getString("green").equals("unknown")){
            isgreen = false;
        }else{
            isgreen =  json.getBoolean("green");
        }

        Web web = new Web(url,isgreen, cleanerThan,energia, gridGramsCo2, renewableGramsCo2);

        return web;
    }

    void guardarInfo(){
        Intent intent = new Intent();

        intent.putExtra("Url", web.url);
        intent.putExtra("isgreen", web.isgreen);
        intent.putExtra("cleanerthan", web.cleanerThan);
        intent.putExtra("energia", web.energia);
        intent.putExtra("gridGramsCo2", web.gridGramsCo2);
        intent.putExtra("renewbableGramsCo2", web.renewableGramsCo2);

        setResult(RESULT_OK, intent);

        finish();
    }

}