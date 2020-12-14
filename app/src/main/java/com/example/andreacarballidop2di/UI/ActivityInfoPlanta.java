package com.example.andreacarballidop2di.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.andreacarballidop2di.R;
import com.example.andreacarballidop2di.core.Planta;

public class ActivityInfoPlanta extends AppCompatActivity {
    private static final String PLANTA = "planta";
    private Planta planta;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacion_planta);

        Intent intent = getIntent();

        planta=(Planta) intent.getSerializableExtra(PLANTA);

        ImageView imagenPlanta = findViewById(R.id.imageViewPlanta);
        TextView nombrePlanta = findViewById(R.id.tvNombrePlanta);
        TextView descripcionPlanta = findViewById(R.id.tvDescripcionPlanta);

        imagenPlanta.setImageResource(planta.getImagen());
        nombrePlanta.setText(planta.getNombre());
        descripcionPlanta.setText(planta.getDescripcion());


    }
}