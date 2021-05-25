package com.thequietcroc.legendary.database.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.thequietcroc.legendary.database.entities.Scheme;

import java.util.List;

@Dao
public interface SchemeDao extends CardDao<Scheme> {

    @Override
    @Query("SELECT * FROM tblSchemes WHERE name LIKE :name LIMIT 1")
    LiveData<Scheme> findByNameAsync(final String name);

    @Override
    @Query("SELECT * FROM tblSchemes WHERE id = :id LIMIT 1")
    LiveData<Scheme> findByIdAsync(final int id);

    @Override
    @Query("SELECT * FROM tblSchemes ORDER BY name ASC")
    LiveData<List<Scheme>> getAllUnfilteredAsync();

    @Override
    @Query("SELECT * FROM tblSchemes WHERE isEnabled = 1 ORDER BY name ASC")
    LiveData<List<Scheme>> getAllFilteredAsync();

    @Override
    @Query("SELECT * FROM tblSchemes WHERE name LIKE :name LIMIT 1")
    Scheme findByNameSync(final String name);

    @Override
    @Query("SELECT * FROM tblSchemes WHERE id = :id LIMIT 1")
    Scheme findByIdSync(final int id);

    @Override
    @Query("SELECT * FROM tblSchemes ORDER BY name ASC")
    List<Scheme> getAllUnfilteredSync();

    @Override
    @Query("SELECT * FROM tblSchemes WHERE isEnabled = 1 ORDER BY name ASC")
    List<Scheme> getAllFilteredSync();
}
