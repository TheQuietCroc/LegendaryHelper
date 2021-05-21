package com.thequietcroc.legendary.database.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.thequietcroc.legendary.database.entities.Henchmen;

import java.util.List;

@Dao
public interface HenchmenDao {

    @Query("SELECT * FROM tblHenchmen")
    LiveData<List<Henchmen>> getAll();

    @Query("SELECT * FROM tblHenchmen WHERE name LIKE :name LIMIT 1")
    LiveData<Henchmen> findByName(final String name);

    @Query("SELECT * FROM tblHenchmen WHERE setId = :setId")
    LiveData<List<Henchmen>> findAllInSet(final int setId);

    @Query("SELECT * FROM tblHenchmen WHERE isCustom = 1")
    LiveData<List<Henchmen>> getAllCustom();

    @Query("SELECT * FROM tblHenchmen WHERE isCustom = 0")
    LiveData<List<Henchmen>> getAllStock();

    @Query("SELECT name FROM tblHenchmen ORDER BY name ASC")
    LiveData<List<String>> getAllNames();

    @Query("SELECT name FROM tblHenchmen WHERE isExcluded = 0 ORDER BY name ASC")
    LiveData<List<String>> getAllFilteredNames();
}
