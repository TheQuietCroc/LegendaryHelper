package com.thequietcroc.legendary.database.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.thequietcroc.legendary.database.entities.Villains;

import java.util.List;

@Dao
public interface VillainsDao extends CardDao<Villains> {

    @Override
    @Query("SELECT * FROM tblVillains WHERE name LIKE :name LIMIT 1")
    LiveData<Villains> findByNameAsync(final String name);

    @Override
    @Query("SELECT * FROM tblVillains WHERE id = :id LIMIT 1")
    LiveData<Villains> findByIdAsync(final int id);

    @Override
    @Query("SELECT * FROM tblVillains ORDER BY name ASC")
    LiveData<List<Villains>> getAllUnfilteredAsync();

    @Override
    @Query("SELECT * FROM tblVillains WHERE isEnabled = 1 ORDER BY name ASC")
    LiveData<List<Villains>> getAllFilteredAsync();

    @Override
    @Query("SELECT * FROM tblVillains WHERE name LIKE :name LIMIT 1")
    Villains findByNameSync(final String name);

    @Override
    @Query("SELECT * FROM tblVillains WHERE id = :id LIMIT 1")
    Villains findByIdSync(final int id);

    @Override
    @Query("SELECT * FROM tblVillains ORDER BY name ASC")
    List<Villains> getAllUnfilteredSync();

    @Override
    @Query("SELECT * FROM tblVillains WHERE isEnabled = 1 ORDER BY name ASC")
    List<Villains> getAllFilteredSync();
}
