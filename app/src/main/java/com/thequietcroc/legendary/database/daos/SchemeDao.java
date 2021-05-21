package com.thequietcroc.legendary.database.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.thequietcroc.legendary.database.entities.Scheme;

import java.util.List;

@Dao
public interface SchemeDao {

    @Query("SELECT * FROM tblSchemes")
    LiveData<List<Scheme>> getAll();

    @Query("SELECT * FROM tblSchemes WHERE name LIKE :name LIMIT 1")
    LiveData<Scheme> findByName(final String name);

    @Query("SELECT * FROM tblSchemes WHERE setId = :setId")
    LiveData<List<Scheme>> findAllInSet(final int setId);

    @Query("SELECT * FROM tblSchemes WHERE isCustom = 1")
    LiveData<List<Scheme>> getAllCustom();

    @Query("SELECT * FROM tblSchemes WHERE isCustom = 0")
    LiveData<List<Scheme>> getAllStock();

    @Query("SELECT name FROM tblSchemes ORDER BY name ASC")
    LiveData<List<String>> getAllNames();

    @Query("SELECT name FROM tblSchemes WHERE isExcluded = 0 ORDER BY name ASC")
    LiveData<List<String>> getAllFilteredNames();
}
