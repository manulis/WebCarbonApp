package com.example.tarea7;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;
    ListView menu;
    MenuAdapter adapterMenu;
    static ArrayList<String> menuItems = new ArrayList<>() ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ItemsMenu();
        menu = findViewById(R.id.menu);
        drawerLayout = findViewById(R.id.drawer_layout);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.abrir_menu, R.string.cerrar_menu);
        drawerLayout.addDrawerListener(drawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        adapterMenu = new MenuAdapter(this, R.layout.row_menu, menuItems);
        menu.setAdapter(adapterMenu);

        cambiarFragment(0);

        menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cambiarFragment(position);
            }
        });
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void ItemsMenu(){
        menuItems.add("Inicio");
        menuItems.add("Perfil");
        menuItems.add("Datos");
    }

    private void cambiarFragment(int i){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Fragment fragment = null;

        switch (i){
            case 0:
            default:
                fragment = new InicioFragment();
                setTitle("Inicio");
                break;
            case 1:
                fragment = new PerfilFragment();
                setTitle("Perfil");
                break;
            case 2:
                fragment = new DatosFragment();
                setTitle("Datos");
                break;
        }

        transaction.replace(R.id.contenedorPantalla, fragment, "tag");
        transaction.commit();
        drawerLayout.closeDrawers();
    }
}