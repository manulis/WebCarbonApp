package com.example.tarea7;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MenuAdapter extends ArrayAdapter {
    Context contexto;
    int layoutfila;
    ArrayList<String> menuItems;

    public MenuAdapter(@NonNull Context context, int resource, ArrayList objects ){
        super(context, resource, objects);
        this.contexto = context;
        this.layoutfila = resource;
        this.menuItems = objects;
    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Crear la vista desde el xml de la fila
        LayoutInflater inflater = LayoutInflater.from(contexto);
        convertView = inflater.inflate(layoutfila, parent, false);
        TextView nombreItemMenu = convertView.findViewById(R.id.nombreItemMenu);
        ImageView icon = convertView.findViewById(R.id.icon);
        String Item = menuItems.get(position);
        nombreItemMenu.setText(Item);

        switch (Item){
            case "Inicio":
                icon.setImageResource(R.drawable.home);
                break;
            case "Perfil":
                icon.setImageResource(R.drawable.perfil);
                break;
            case "Datos":
                icon.setImageResource(R.drawable.world);
                break;
        }
        return convertView;
    }
}
