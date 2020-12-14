package com.example.andreacarballidop2di.database;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.room.Room;

import com.example.andreacarballidop2di.core.Planta;

import java.util.ArrayList;

public class PlantaLab {
    @SuppressLint("StaticFieldLeak")
    private static PlantaLab sPlantaLab;

    private static PlantaDao mPlantaDao;

    private PlantaLab(Context context) {
        Context appContext = context.getApplicationContext();
        PlantaDataBase database = Room.databaseBuilder(appContext, PlantaDataBase.class, "plantas")
                .allowMainThreadQueries().build();
        mPlantaDao = database.getPlantaDao();
    }

    public static PlantaLab get(Context context) {
        if (sPlantaLab == null) {
            sPlantaLab = new PlantaLab(context);
        }
        return sPlantaLab;
    }

    public static ArrayList<Planta> getPlantas() {
        return (ArrayList<Planta>) mPlantaDao.getPlantas();
    }

    public Planta getPlanta(String id) {
        return mPlantaDao.getPlanta(id);
    }

    public void addPlanta(Planta planta) {
        mPlantaDao.addPlanta(planta);
    }

    public void updatePlanta(Planta planta) {
        mPlantaDao.updatePlanta(planta);
    }

    public static void deletePlanta(Planta planta) {
        mPlantaDao.deletePlanta(planta);
    }
}
