package com.example.andreacarballidop2di.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.andreacarballidop2di.core.Planta;

@Database(entities = {Planta.class}, version = 1)
public abstract class PlantaDataBase extends RoomDatabase {
    public abstract PlantaDao getPlantaDao();
}
