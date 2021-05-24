package com.thequietcroc.legendary.database.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.thequietcroc.legendary.database.entities.Hero;

import java.util.List;

@Dao
public interface HeroDao extends CardDao<Hero> {

    @Query("SELECT * FROM tblHeroes WHERE name LIKE :name LIMIT 1")
    LiveData<Hero> findByName(final String name);

    @Query("SELECT * FROM tblHeroes WHERE id = :id LIMIT 1")
    LiveData<Hero> findById(final int id);

    @Query("SELECT * FROM tblHeroes ORDER BY name ASC")
    LiveData<List<Hero>> getAllUnfiltered();

    @Query("SELECT * FROM tblHeroes WHERE isExcluded = 0 ORDER BY name ASC")
    LiveData<List<Hero>> getAllFiltered();

    @Query("SELECT name FROM tblHeroes ORDER BY name ASC")
    LiveData<List<String>> getAllNames();

    @Query("SELECT name FROM tblHeroes WHERE isExcluded = 0 ORDER BY name ASC")
    LiveData<List<String>> getAllFilteredNames();


}
