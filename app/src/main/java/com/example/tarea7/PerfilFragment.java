package com.example.tarea7;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.Objects;

public class PerfilFragment extends Fragment {
    TextView nombreUser;
    TextView correoUser;
    TextView telefonoUser;
    ImageView fotoPerfil;
    ImageButton editar;
    Button crearPerfil;
    final int requestCode =1;
    User user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view =  inflater.inflate(R.layout.fragment_perfil, container, false);
       nombreUser =  view.findViewById(R.id.nombre);
       correoUser = view.findViewById(R.id.correo);
       telefonoUser = view.findViewById(R.id.telefono);
       fotoPerfil = view.findViewById(R.id.fotoPerfil);
       editar = view.findViewById(R.id.editar);
       crearPerfil = view.findViewById(R.id.crearPerfil);

        cargarInfo();

       if(nombreUser.getText().toString().equals("")){
           nombreUser.setVisibility(View.GONE);
           correoUser.setVisibility(View.GONE);
           telefonoUser.setVisibility(View.GONE);
           fotoPerfil.setVisibility(View.GONE);
           editar.setVisibility(View.GONE);

           crearPerfil.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   crearPerfil();
               }
           });
       }

       editar.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               crearPerfil();
           }
       });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode==-1){

            System.out.println("Recibido");
            String Nombre = data.getStringExtra("Nombre");
            String Telefono = data.getStringExtra("Telefono");
            String Correo = data.getStringExtra("Correo");

            user = new User(Nombre,Correo, Telefono);

            guardarPerfil();
        }
    }

    void guardarPerfil(){
        Gson gson = new Gson();
        String json = gson.toJson(user);
        SharedPreferences preferences = getContext().getSharedPreferences("User", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("user", json);
        editor.commit();
        cargarInfo();
    }

    void cargarInfo(){
        SharedPreferences preferences = getContext().getSharedPreferences("User", Context.MODE_PRIVATE);
        String json = preferences.getString("user", "");
        System.out.println(json);

        if (json.isEmpty()){
            return;
        }

        try {
            JSONObject objetoJson = new JSONObject(json);
            String Nombre = objetoJson.getString("name");
            String Correo = objetoJson.getString("Email");
            String Telefono = objetoJson.getString("telf");

            nombreUser.setText(Nombre);
            correoUser.setText(Correo);
            telefonoUser.setText(Telefono);

            nombreUser.setVisibility(View.VISIBLE);
            correoUser.setVisibility(View.VISIBLE);
            telefonoUser.setVisibility(View.VISIBLE);
            fotoPerfil.setVisibility(View.VISIBLE);
            editar.setVisibility(View.VISIBLE);
            crearPerfil.setVisibility(View.GONE);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void crearPerfil(){
        Intent intent = new Intent(getContext(), FormularioActivity.class);
        startActivityForResult(intent, requestCode);
    }
}