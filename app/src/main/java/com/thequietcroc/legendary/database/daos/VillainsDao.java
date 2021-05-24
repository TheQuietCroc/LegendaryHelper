package com.thequietcroc.legendary.database.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.thequietcroc.legendary.database.entities.Villains;

import java.util.List;

@Dao
public interface VillainsDao extends CardDao<Villains> {

    @Query("SELECT * FROM tblVillains WHERE name LIKE :name LIMIT 1")
    LiveData<Villains> findByName(final String name);

    @Query("SELECT * FROM tblVillains WHERE id = :id LIMIT 1")
    LiveData<Villains> findById(final int id);

    @Query("SELECT * FROM tblVillains ORDER BY name ASC")
    LiveData<List<Villains>> getAllUnfiltered();

    @Query("SELECT * FROM tblVillains WHERE isExcluded = 0 ORDER BY name ASC")
    LiveData<List<Villains>> getAllFiltered();

    @Query("SELECT name FROM tblVillains ORDER BY name ASC")
    LiveData<List<String>> getAllNames();

    @Query("SELECT name FROM tblVillains WHERE isExcluded = 0 ORDER BY name ASC")
    LiveData<List<String>> getAllFilteredNames();
}
