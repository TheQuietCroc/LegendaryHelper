package com.thequietcroc.legendary.database.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.thequietcroc.legendary.database.entities.Scheme;

import java.util.List;

@Dao
public interface SchemeDao extends CardDao<Scheme> {

    // findByName
    @Override
    @Query("SELECT * FROM tblSchemes WHERE name LIKE :name LIMIT 1")
    LiveData<Scheme> findByNameAsync(final String name);

    @Override
    @Query("SELECT * FROM tblSchemes WHERE name LIKE :name LIMIT 1")
    Scheme findByNameSync(final String name);

    // findById
    @Override
    @Query("SELECT * FROM tblSchemes WHERE id = :id LIMIT 1")
    LiveData<Scheme> findByIdAsync(final int id);

    @Override
    @Query("SELECT * FROM tblSchemes WHERE id = :id LIMIT 1")
    Scheme findByIdSync(final int id);

    // getAll
    @Override
    @Query("SELECT * FROM tblSchemes ORDER BY id ASC")
    LiveData<List<Scheme>> getAllAsync();

    @Override
    @Query("SELECT * FROM tblSchemes ORDER BY id ASC")
    List<Scheme> getAllSync();

    // getAllEnabled
    @Override
    @Query("SELECT * FROM tblSchemes WHERE isEnabled = 1 ORDER BY id ASC")
    LiveData<List<Scheme>> getAllEnabledAsync();

    @Override
    @Query("SELECT * FROM tblSchemes WHERE isEnabled = 1 ORDER BY id ASC")
    List<Scheme> getAllEnabledSync();

    // getAllBySetId
    @Override
    @Query("SELECT * FROM tblSchemes WHERE setId = :setId ORDER BY id ASC")
    LiveData<List<Scheme>> getAllBySetIdAsync(final int setId);

    @Override
    @Query("SELECT * FROM tblSchemes WHERE setId = :setId ORDER BY id ASC")
    List<Scheme> getAllBySetIdSync(final int setId);
}
