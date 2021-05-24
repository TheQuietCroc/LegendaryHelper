package com.thequietcroc.legendary.database.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.thequietcroc.legendary.database.entities.Henchmen;

import java.util.List;

@Dao
public interface HenchmenDao extends CardDao<Henchmen> {

    @Query("SELECT * FROM tblHenchmen WHERE name LIKE :name LIMIT 1")
    LiveData<Henchmen> findByName(final String name);

    @Query("SELECT * FROM tblHenchmen WHERE id = :id LIMIT 1")
    LiveData<Henchmen> findById(final int id);

    @Query("SELECT * FROM tblHenchmen ORDER BY name ASC")
    LiveData<List<Henchmen>> getAllUnfiltered();

    @Query("SELECT * FROM tblHenchmen WHERE isExcluded = 0 ORDER BY name ASC")
    LiveData<List<Henchmen>> getAllFiltered();

    @Query("SELECT name FROM tblHenchmen ORDER BY name ASC")
    LiveData<List<String>> getAllNames();

    @Query("SELECT name FROM tblHenchmen WHERE isExcluded = 0 ORDER BY name ASC")
    LiveData<List<String>> getAllFilteredNames();
}
