package com.thequietcroc.legendary.database.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.thequietcroc.legendary.database.entities.Henchmen;

import java.util.List;

@Dao
public interface HenchmenDao extends CardDao<Henchmen> {

    @Override
    @Query("SELECT * FROM tblHenchmen WHERE name LIKE :name LIMIT 1")
    LiveData<Henchmen> findByNameAsync(final String name);

    @Override
    @Query("SELECT * FROM tblHenchmen WHERE id = :id LIMIT 1")
    LiveData<Henchmen> findByIdAsync(final int id);

    @Override
    @Query("SELECT * FROM tblHenchmen ORDER BY name ASC")
    LiveData<List<Henchmen>> getAllUnfilteredAsync();

    @Override
    @Query("SELECT * FROM tblHenchmen WHERE isEnabled = 1 ORDER BY name ASC")
    LiveData<List<Henchmen>> getAllFilteredAsync();

    @Override
    @Query("SELECT * FROM tblHenchmen WHERE name LIKE :name LIMIT 1")
    Henchmen findByNameSync(final String name);

    @Override
    @Query("SELECT * FROM tblHenchmen WHERE id = :id LIMIT 1")
    Henchmen findByIdSync(final int id);

    @Override
    @Query("SELECT * FROM tblHenchmen ORDER BY name ASC")
    List<Henchmen> getAllUnfilteredSync();

    @Override
    @Query("SELECT * FROM tblHenchmen WHERE isEnabled = 1 ORDER BY name ASC")
    List<Henchmen> getAllFilteredSync();
}
