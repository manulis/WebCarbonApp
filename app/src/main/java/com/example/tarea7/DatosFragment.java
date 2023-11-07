package com.example.tarea7;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class DatosFragment extends Fragment {
    ListView listaWebs;

    websAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_datos, container, false);

        InicioFragment.webs = new ArrayList<>();
        listaWebs = view.findViewById(R.id.listaWebs);

        cargarInfo();

        adapter = (new websAdapter(getContext(), R.layout.row_web, InicioFragment.webs));
        listaWebs.setAdapter(adapter);

        listaWebs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                abrirWeb(i);
            }
        });

        listaWebs.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                borrarWeb(i);
                return true;
            }
        });

        return view;
    }

    void cargarInfo(){
        SharedPreferences preferences = getContext().getSharedPreferences("Webs", Context.MODE_PRIVATE);
        String json = preferences.getString("webs", "");
        System.out.println(json);
        if (json.isEmpty()){
            return;
        }
        Gson gson = new Gson();
        Type ArraylistOfwebs = new TypeToken<ArrayList<Web>>() {
        }.getType();
        InicioFragment.webs = gson.fromJson(json, ArraylistOfwebs);
        System.out.println(InicioFragment.webs);
    }
    void abrirWeb(int i){
        Web webSeleccionada = InicioFragment.webs.get(i);
        Intent intent = new Intent(getContext(), WebActivity.class);
        intent.putExtra("Webs", webSeleccionada.convertirAJson());
        startActivity(intent);
    }

    void borrarWeb(int i){
        InicioFragment.webs.remove(i);
        adapter.notifyDataSetChanged();
        Utils.guardarWeb(getContext(), InicioFragment.webs);
    }
}