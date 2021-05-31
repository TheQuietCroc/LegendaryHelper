package com.thequietcroc.legendary.database.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.thequietcroc.legendary.database.entities.Henchmen;

import java.util.List;

@Dao
public interface HenchmenDao extends CardDao<Henchmen> {

    // findByName
    @Override
    @Query("SELECT * FROM tblHenchmen WHERE name LIKE :name LIMIT 1")
    LiveData<Henchmen> findByNameAsync(final String name);

    @Override
    @Query("SELECT * FROM tblHenchmen WHERE name LIKE :name LIMIT 1")
    Henchmen findByNameSync(final String name);

    // findById
    @Override
    @Query("SELECT * FROM tblHenchmen WHERE id = :id LIMIT 1")
    LiveData<Henchmen> findByIdAsync(final int id);

    @Override
    @Query("SELECT * FROM tblHenchmen WHERE id = :id LIMIT 1")
    Henchmen findByIdSync(final int id);

    // getAll
    @Override
    @Query("SELECT * FROM tblHenchmen ORDER BY id ASC")
    LiveData<List<Henchmen>> getAllAsync();

    @Override
    @Query("SELECT * FROM tblHenchmen ORDER BY id ASC")
    List<Henchmen> getAllSync();

    // getAllEnabled
    @Override
    @Query("SELECT * FROM tblHenchmen WHERE isEnabled = 1 ORDER BY id ASC")
    LiveData<List<Henchmen>> getAllEnabledAsync();

    @Override
    @Query("SELECT * FROM tblHenchmen WHERE isEnabled = 1 ORDER BY id ASC")
    List<Henchmen> getAllEnabledSync();

    // getAllBySetId
    @Override
    @Query("SELECT * FROM tblHenchmen WHERE setId = :setId ORDER BY id ASC")
    LiveData<List<Henchmen>> getAllBySetIdAsync(final int setId);

    @Override
    @Query("SELECT * FROM tblHenchmen WHERE setId = :setId ORDER BY id ASC")
    List<Henchmen> getAllBySetIdSync(final int setId);
}
