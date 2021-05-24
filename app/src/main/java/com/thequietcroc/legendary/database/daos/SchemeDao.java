package com.thequietcroc.legendary.database.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.thequietcroc.legendary.database.entities.Scheme;

import java.util.List;

@Dao
public interface SchemeDao extends CardDao<Scheme> {

    @Query("SELECT * FROM tblSchemes WHERE name LIKE :name LIMIT 1")
    LiveData<Scheme> findByName(final String name);

    @Query("SELECT * FROM tblSchemes WHERE id = :id LIMIT 1")
    LiveData<Scheme> findById(final int id);

    @Query("SELECT * FROM tblSchemes ORDER BY name ASC")
    LiveData<List<Scheme>> getAllUnfiltered();

    @Query("SELECT * FROM tblSchemes WHERE isExcluded = 0 ORDER BY name ASC")
    LiveData<List<Scheme>> getAllFiltered();

    @Query("SELECT name FROM tblSchemes ORDER BY name ASC")
    LiveData<List<String>> getAllNames();

    @Query("SELECT name FROM tblSchemes WHERE isExcluded = 0 ORDER BY name ASC")
    LiveData<List<String>> getAllFilteredNames();
}
