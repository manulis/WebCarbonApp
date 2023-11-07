package com.example.tarea7;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class FormularioActivity extends AppCompatActivity {
    EditText nombre;
    EditText telefono;
    EditText correo;
    Button  aceptar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);
        setTitle("Crear Perfil");

        nombre = findViewById(R.id.Nombre);
        telefono = findViewById(R.id.Telefono);
        correo = findViewById(R.id.Correo);
        aceptar = findViewById(R.id.Aceptar);

        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Nombre = nombre.getText().toString();
                String Telefono = telefono.getText().toString();
                String Correo = correo.getText().toString();

                if(Utils.validacion_email(Correo) && Utils.validacionTelefono(Telefono) && !Nombre.isEmpty()){
                    Intent intent = new Intent();

                    intent.putExtra("Nombre", Nombre);
                    intent.putExtra("Telefono", Telefono);
                    intent.putExtra("Correo", Correo);

                    setResult(RESULT_OK, intent);
                    finish();

                }else{
                    if (!Utils.validacion_email(Correo)){
                        correo.setError("Correo Invalido");
                    }
                    if(!Utils.validacionTelefono(Telefono)){
                        telefono.setError("Telefono Invalido");
                    }
                    if(Nombre.isEmpty()){
                        nombre.setError("Nombre Invalido");
                    }
                    return;
                }
            }
        });
    }
}