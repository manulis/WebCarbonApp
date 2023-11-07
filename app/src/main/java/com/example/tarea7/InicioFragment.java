package com.example.tarea7;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class InicioFragment extends Fragment {
    EditText urlInput;
    Button enviar;
    public static  ArrayList<Web> webs = new ArrayList<>();
    final int requestCode = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inicio, container, false);

        urlInput = view.findViewById(R.id.UrlInput);
        enviar = view.findViewById(R.id.enviar);

        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String UrlWeb = urlInput.getText().toString();

                if(UrlWeb!="" && Utils.validarUrl("https://"+ UrlWeb)){
                    enviarUrl(UrlWeb);
                }else{
                    urlInput.setError("Url Invalida");
                }
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode==-1){
            System.out.println("recibido");

            String url = data.getStringExtra("Url");
            boolean isgreen = data.getBooleanExtra("isgreen", true);
            double cleanerthan = data.getDoubleExtra("cleanerthan", 0.0);
            double energia = data.getDoubleExtra("energia", 0.0);
            double gridGramsCo2 = data.getDoubleExtra("gridGramsCo2", 0.0);
            double renewableGramsCo2 = data.getDoubleExtra("renewbableGramsCo2", 0.0);

            System.out.println(url);
            System.out.println(isgreen);
            System.out.println(cleanerthan);

            webs.add(new Web(url, isgreen, cleanerthan, energia, gridGramsCo2, renewableGramsCo2));

            Utils.guardarWeb(getContext(), webs);
        }
    }

    private void enviarUrl(String url){
        Intent intent = new Intent(getContext(), UrlinfoActivity.class);
        intent.putExtra("UrlWeb", url);
        startActivityForResult(intent, requestCode);
    }
}
