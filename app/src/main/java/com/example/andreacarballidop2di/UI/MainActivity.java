package com.example.andreacarballidop2di.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.andreacarballidop2di.R;
import com.example.andreacarballidop2di.core.Planta;
import com.example.andreacarballidop2di.database.PlantaLab;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.IDN;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static android.widget.Toast.makeText;


public class MainActivity extends AppCompatActivity implements AccionesPlanta {
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    RecyclerView recyclerView;
    private static final String PLANTA = "planta";
    private MyRecyclerViewAdapter adapter;
    private ArrayList<Planta> listaPlantasAñadidas;
    private ArrayList<Planta> plantas;
    private PlantaLab mPlantaLab;
    ArrayList<Planta> plantasDialogo;
    ArrayList<String> idsPlantasRecycler;
    public static final String Id = "id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FloatingActionButton btAdd = findViewById(R.id.btAdd);


        listaPlantasAñadidas = new ArrayList<>();
        plantas = new ArrayList<>();
        plantasDialogo = new ArrayList<>();
        idsPlantasRecycler = new ArrayList<>();
        mPlantaLab = PlantaLab.get(this);

        if(plantas.isEmpty()){
            añadirPlantasBD();
            plantas = mPlantaLab.getPlantas();
            for (Planta planta : plantas) {
                plantasDialogo.add(planta);
            }
        }


        recyclerView = findViewById(R.id.rvPlantas);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        adapter = new MyRecyclerViewAdapter(listaPlantasAñadidas, this);
        recyclerView.setAdapter(adapter);

        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(plantasDialogo.isEmpty()){
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setMessage("No hay elementos para añadir");
                    builder.create().show();

                }else{
                    añadirPlantasActividad();
                }
            }
        });
    }

    public void guardarpreferencias() {

        SharedPreferences preferences = getSharedPreferences("lista", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        Set<String> set = new HashSet<>();
        set.addAll(idsPlantasRecycler);
        editor.putStringSet("datos", set).commit();
        editor.apply();

    }
    public void cargarpreferencias() {

        SharedPreferences preferences = getSharedPreferences("lista", Context.MODE_PRIVATE);
        Set<String> set = preferences.getStringSet("datos", null);
//        int id= preferences.getInt()

        idsPlantasRecycler.addAll(set);


        //list.addAll(set);

        //Convert HashSet to List.
//        idsPlantasRecycler = (Set<String>) new ArrayList<String>(set);

    }

    @Override
    public void onResume() {
        super.onResume();
        cargarpreferencias();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu_planta, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem menuItem) {

        boolean toret = false;

        switch (menuItem.getItemId()) {
            case R.id.añadirPlanta:
                añadirPlantasActividad();
                toret = true;
                break;
            case R.id.eliminarPlanta:
                eliminarPlantasMenu();
                toret = true;
                break;

        }
        return toret;
    }




    private void añadirPlantasBD() {

        Planta aloeVera = new Planta(R.drawable.aloevera, "Aloe Vera", getString(R.string.descripcionAloe));
        Planta crisantemo = new Planta(R.drawable.crisantemo, "Crisantemo", getString(R.string.descripcionCrisantemo));
        Planta azalea = new Planta(R.drawable.azalea, "Azalea", getString(R.string.descripcionAzalea));
        Planta calatea = new Planta(R.drawable.calatea, "Calatea", getString(R.string.descripcionCalatea));
        Planta calendula = new Planta(R.drawable.calatea, "Calendula", getString(R.string.descripcionCalendula));
        Planta campanilla = new Planta(R.drawable.campanillas, "Campanilla", getString(R.string.descripcionCampanilla));
        Planta viola = new Planta(R.drawable.viola, "Viola", getString(R.string.descripcionViola));
        Planta girasol = new Planta(R.drawable.girasol, "Girasol", getString(R.string.descripcionGirasol));

        mPlantaLab.addPlanta(aloeVera);
        mPlantaLab.addPlanta(crisantemo);
        mPlantaLab.addPlanta(azalea);
        mPlantaLab.addPlanta(calatea);
        mPlantaLab.addPlanta(calendula);
        mPlantaLab.addPlanta(campanilla);
        mPlantaLab.addPlanta(viola);
        mPlantaLab.addPlanta(girasol);

    }

    public void añadirPlantasActividad() {

        for (Planta plantaAñadida : listaPlantasAñadidas) {

            if (plantasDialogo.contains(plantaAñadida)) {
                plantasDialogo.remove(plantaAñadida);

            } else {
                continue;
            }
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Añadir Planta");
        String[] arrayMostrarPlantas = new String[plantasDialogo.size()];
        final boolean[] plantasSeleccion = new boolean[plantasDialogo.size()];
        for (
                int i = 0; i < plantasDialogo.size(); i++) {

            arrayMostrarPlantas[i] = plantasDialogo.get(i).getNombre();
        }

        builder.setMultiChoiceItems(arrayMostrarPlantas, plantasSeleccion, (dialog, i, isChecked) -> plantasSeleccion[i] = isChecked);

        builder.setPositiveButton("Añadir", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(final DialogInterface dialog, int which) {

                for (int i = plantasSeleccion.length - 1; i >= 0; i--) {
                    if (plantasSeleccion[i]) {
                        Planta plantaElegida = plantasDialogo.get(i);
                        idsPlantasRecycler.add(String.valueOf(plantaElegida.getId()));
                        listaPlantasAñadidas.add(plantaElegida);
                        guardarpreferencias();
                        adapter.notifyDataSetChanged();
                    }
                }
                Toast.makeText(MainActivity.this, "Planta añadida", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancelar", null);
        builder.create().show();
    }

    @Override
    public void eliminarPlanta(Planta planta) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Borrar Elemento");
        builder.setMessage("Está seguro de que desea eliminar este elemento?");
        builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                idsPlantasRecycler.remove(planta.getId());
                listaPlantasAñadidas.remove(planta);
                plantasDialogo.add(planta);
                adapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("No", null);
        builder.create().show();
    }

    @Override
    public void informacionPlantaPulsada(Planta planta) {
        Intent intent = new Intent(MainActivity.this, ActivityInfoPlanta.class);
        intent.putExtra(PLANTA, planta);
        startActivity(intent);
    }

    private void eliminarPlantasMenu() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Eliminar plantas");

        String[] stringPlantas = new String[listaPlantasAñadidas.size()];
        final boolean[] plantasSeleccion = new boolean[listaPlantasAñadidas.size()];
        for (int i = 0; i < listaPlantasAñadidas.size(); i++) {
                stringPlantas[i] = listaPlantasAñadidas.get(i).getNombre();
        }
        builder.setMultiChoiceItems(stringPlantas, plantasSeleccion, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i, boolean isChecked) {
                plantasSeleccion[i] = isChecked;
            }
        });
        builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialog, int which) {
                AlertDialog.Builder buildereliminar = new AlertDialog.Builder(MainActivity.this);
                buildereliminar.setMessage("¿Está seguro de que desea eliminar los elementos?");
                buildereliminar.setNegativeButton("No", null);
                buildereliminar.setPositiveButton("Sí", new DialogInterface.OnClickListener() {


                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        for (int i = 0 ; i<listaPlantasAñadidas.size() ; i++)
                            if (plantasSeleccion[i]) {
                                idsPlantasRecycler.remove(listaPlantasAñadidas.get(i).getId());
                                listaPlantasAñadidas.remove(i);
                                adapter.notifyDataSetChanged();
                            }

                        Toast.makeText(MainActivity.this, "Plantas eliminadas correctamente", Toast.LENGTH_SHORT).show();
                        MainActivity.this.adapter.notifyDataSetChanged();
                    }
                });
                buildereliminar.create().show();
            }
        });
        builder.setNegativeButton("No", null);
        builder.create().show();
    }
}