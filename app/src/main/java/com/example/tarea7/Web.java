package com.example.tarea7;

import com.google.gson.Gson;

public class Web {
    String url;
    boolean isgreen;
    double cleanerThan;
    double energia;
    double gridGramsCo2;
    double renewableGramsCo2;

    public Web(String url, boolean isgreen, double cleanerThan, double energia , double gridGramsCo2, double renewableGramsCo2 ) {
        this.url = url;
        this.isgreen = isgreen;
        this.cleanerThan = cleanerThan;
        this.energia = energia;
        this.gridGramsCo2 = gridGramsCo2;
        this.renewableGramsCo2 = renewableGramsCo2;
    }

    public String getUrl() {
        return url;
    }

    public boolean isIsgreen() {return isgreen;}

    public double getCleanerThan() {
        return cleanerThan;
    }

    public double getEnergia() {return energia;}

    public double getGridGramCo2() {return gridGramsCo2;}

    public double getRenewableGramsCo2() {
        return renewableGramsCo2;
    }

    public String convertirAJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public static Web convertirDesdeJson(String json){
        Gson gson = new Gson();
        return gson.fromJson(json, Web.class);
    }

}
