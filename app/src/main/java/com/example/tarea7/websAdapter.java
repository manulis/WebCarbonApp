package com.example.tarea7;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class websAdapter extends ArrayAdapter {
    Context contexto;
    int layoutfila;
    ArrayList<Web> webs;

    public websAdapter(@NonNull Context context, int resource, ArrayList objects) {
        super(context, resource, objects);
        this.contexto = context;
        this.layoutfila = resource;
        this.webs = objects;
    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(contexto);
        convertView = inflater.inflate(layoutfila, parent, false);

        TextView nombreItemWeb = convertView.findViewById(R.id.WebNombre);

        Web web = webs.get(position);

        nombreItemWeb.setText(web.getUrl());



        if(web.isIsgreen()){

            nombreItemWeb.setBackgroundColor(Color.parseColor("#338926"));
        }else{

            nombreItemWeb.setBackgroundColor(Color.parseColor("#FF2216"));
        }




        return convertView;
    }
}
