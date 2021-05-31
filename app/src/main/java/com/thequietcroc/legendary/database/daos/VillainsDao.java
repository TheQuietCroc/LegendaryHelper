package com.thequietcroc.legendary.database.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.thequietcroc.legendary.database.entities.Villains;

import java.util.List;

@Dao
public interface VillainsDao extends CardDao<Villains> {

    // findByName
    @Override
    @Query("SELECT * FROM tblVillains WHERE name LIKE :name LIMIT 1")
    LiveData<Villains> findByNameAsync(final String name);

    @Override
    @Query("SELECT * FROM tblVillains WHERE name LIKE :name LIMIT 1")
    Villains findByNameSync(final String name);

    // findById
    @Override
    @Query("SELECT * FROM tblVillains WHERE id = :id LIMIT 1")
    LiveData<Villains> findByIdAsync(final int id);

    @Override
    @Query("SELECT * FROM tblVillains WHERE id = :id LIMIT 1")
    Villains findByIdSync(final int id);

    // getAll
    @Override
    @Query("SELECT * FROM tblVillains ORDER BY id ASC")
    LiveData<List<Villains>> getAllAsync();

    @Override
    @Query("SELECT * FROM tblVillains ORDER BY id ASC")
    List<Villains> getAllSync();

    // getAllEnabled
    @Override
    @Query("SELECT * FROM tblVillains WHERE isEnabled = 1 ORDER BY id ASC")
    LiveData<List<Villains>> getAllEnabledAsync();

    @Override
    @Query("SELECT * FROM tblVillains WHERE isEnabled = 1 ORDER BY id ASC")
    List<Villains> getAllEnabledSync();

    // getAllBySetId
    @Override
    @Query("SELECT * FROM tblVillains WHERE setId = :setId ORDER BY id ASC")
    LiveData<List<Villains>> getAllBySetIdAsync(final int setId);

    @Override
    @Query("SELECT * FROM tblVillains WHERE setId = :setId ORDER BY id ASC")
    List<Villains> getAllBySetIdSync(final int setId);
}
