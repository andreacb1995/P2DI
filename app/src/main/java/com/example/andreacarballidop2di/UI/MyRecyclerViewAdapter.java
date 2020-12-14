package com.example.andreacarballidop2di.UI;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.andreacarballidop2di.R;
import com.example.andreacarballidop2di.core.Planta;

import java.lang.reflect.Field;
import java.nio.charset.MalformedInputException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {
    private Context context;
    private List<Planta> items;
    private AccionesPlanta accionesPlanta;

    MyRecyclerViewAdapter(List<Planta> items,AccionesPlanta accionesPlanta){
        this.items = items;
        this.accionesPlanta=accionesPlanta;
        this.context = context;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final AccionesPlanta accionesPlanta;
        // Campos respectivos de un item
        CardView cv;
        private final ImageView imagenPlanta;
        private final TextView tvNombrePlanta;

        public ViewHolder(View v,AccionesPlanta accionesPlanta) {
            super(v);

            cv= (CardView) v.findViewById(R.id.cv);
            imagenPlanta = itemView.findViewById(R.id.imagenViewPlanta);
            tvNombrePlanta = itemView.findViewById(R.id.tvNombrePlanta);
            this.accionesPlanta = accionesPlanta;


        }



        public void mostrarDatosPlanta(final Planta planta, final Context context){

            imagenPlanta.setImageResource(planta.getImagen());
            tvNombrePlanta.setText(planta.getNombre());

            cv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    accionesPlanta.informacionPlantaPulsada(planta);
                }


            });

            cv.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v){
                    accionesPlanta.eliminarPlanta(planta);
                    return false;
                }
            });
        }
    }

    public MyRecyclerViewAdapter(MainActivity mainActivity, List<Planta> items) {
        this.items = items;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.planta_card, viewGroup, false);
        ViewHolder pvh = new ViewHolder(v,accionesPlanta);
        return pvh;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {

        Planta planta= items.get(i);
        SharedPreferences preferences = context.getSharedPreferences("lista", Context.MODE_PRIVATE);
        Set<String> set = preferences.getStringSet("datos", null);
        for(String e : set){
            if(e.equals(String.valueOf(planta.getId()))){
                viewHolder.imagenPlanta.setImageResource(items.get(i).getImagen());
                viewHolder.tvNombrePlanta.setText(items.get(i).getNombre());
            }
        }

        viewHolder.mostrarDatosPlanta(planta,context);
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

}
