package com.example.tarea7;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

public class Utils {

    public static void guardarWeb(Context context , ArrayList<Web> webs) {
        Gson gson = new Gson();
        String json = gson.toJson(webs);
        SharedPreferences preferences = context.getSharedPreferences("Webs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("webs", json);
        editor.commit();
    }

    public static boolean validarUrl(String url){
        try {
            new URL(url).toURI();
            return true;
        }
        catch (URISyntaxException exception) {
            return false;
        }
        catch (MalformedURLException exception) {
            return false;
        }
    }

    public static boolean validacion_email(String email) {
        boolean arroba = false;
        boolean punto = false;
        boolean numeric = false;

        for (int i = 0; i<email.length(); i++) {
            for (int j = 0; j<email.length(); j++) {
                if(email.charAt(i)=='@' && email.charAt(j)=='.') {
                    arroba = true;
                    punto = true;
                }
            }
        }

        if(arroba == true && punto == true){
            String[] emailArray = email.split("\\.|[@]");
            String nombre = emailArray[0];
            String[] nombreSec = nombre.split("");
            String dominio = emailArray[1];
            String[] DominioSec = dominio.split("");
            String extension = emailArray[2];

            for(int i=0; i<dominio.length(); i++){
                if(esNumeroStr(DominioSec[i])){
                    numeric=true;
                }
            }

            if(nombre.length()<=10 && !nombre.isEmpty() && !dominio.isEmpty() && !extension.isEmpty() && extension.length()<=3 && !esNumeroStr(nombreSec[0]) && numeric==false){
                return true;
            }else{
                return false;
            }

        }else{
            return false;
        }
    }

    public static boolean validacionTelefono(String telefono){

        if(telefono.equals("")){

            return false;
        }else{
            String[] telfArray = telefono.split("");

            if(telfArray.length==9){
                if(telfArray[0].equals("9") || telfArray[0].equals("8") || telfArray[0].equals("6") || telfArray[0].equals("7")){
                    return true;
                }else{
                    return false;
                }

            }else{
                return false;
            }
        }
    }

    public static boolean esMayus(String Cadena){
        String CadenaMayus = Cadena.toUpperCase();

        if(Cadena.equals(CadenaMayus)){
            return true;
        }else{
            return false;
        }
    }

    public static boolean esNumeroStr(String Cadena){
        try {
            Integer.parseInt(Cadena);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
