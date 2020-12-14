package com.example.andreacarballidop2di.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.andreacarballidop2di.core.Planta;

import java.util.List;

@Dao
interface PlantaDao {

    @Query("SELECT * FROM plantas")
    List<Planta> getPlantas();

    @Query("SELECT * FROM plantas WHERE id LIKE :uuid")
    Planta getPlanta(String uuid);

    @Insert
    void addPlanta(Planta book);

    @Delete
    void deletePlanta(Planta book);

    @Update
    void updatePlanta(Planta book);

}
