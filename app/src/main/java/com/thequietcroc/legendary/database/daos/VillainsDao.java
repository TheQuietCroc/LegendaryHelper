package com.thequietcroc.legendary.database.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.thequietcroc.legendary.database.entities.Villains;

import java.util.List;

@Dao
public interface VillainsDao {

    @Query("SELECT * FROM tblVillains")
    LiveData<List<Villains>> getAll();

    @Query("SELECT * FROM tblVillains WHERE name LIKE :name LIMIT 1")
    LiveData<Villains> findByName(final String name);

    @Query("SELECT * FROM tblVillains WHERE setId = :setId")
    LiveData<List<Villains>> findAllInSet(final int setId);

    @Query("SELECT * FROM tblVillains WHERE isCustom = 1")
    LiveData<List<Villains>> getAllCustom();

    @Query("SELECT * FROM tblVillains WHERE isCustom = 0")
    LiveData<List<Villains>> getAllStock();

    @Query("SELECT name FROM tblVillains ORDER BY name ASC")
    LiveData<List<String>> getAllNames();

    @Query("SELECT name FROM tblVillains WHERE isExcluded = 0 ORDER BY name ASC")
    LiveData<List<String>> getAllFilteredNames();
}
