package com.thequietcroc.legendary.database.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.thequietcroc.legendary.database.entities.Hero;

import java.util.List;

@Dao
public interface HeroDao extends CardDao<Hero> {

    @Override
    @Query("SELECT * FROM tblHeroes WHERE name LIKE :name LIMIT 1")
    LiveData<Hero> findByNameAsync(final String name);

    @Override
    @Query("SELECT * FROM tblHeroes WHERE id = :id LIMIT 1")
    LiveData<Hero> findByIdAsync(final int id);

    @Override
    @Query("SELECT * FROM tblHeroes ORDER BY name ASC")
    LiveData<List<Hero>> getAllUnfilteredAsync();

    @Override
    @Query("SELECT * FROM tblHeroes WHERE isEnabled = 1 ORDER BY name ASC")
    LiveData<List<Hero>> getAllFilteredAsync();

    @Override
    @Query("SELECT * FROM tblHeroes WHERE name LIKE :name LIMIT 1")
    Hero findByNameSync(final String name);

    @Override
    @Query("SELECT * FROM tblHeroes WHERE id = :id LIMIT 1")
    Hero findByIdSync(final int id);

    @Override
    @Query("SELECT * FROM tblHeroes ORDER BY name ASC")
    List<Hero> getAllUnfilteredSync();

    @Override
    @Query("SELECT * FROM tblHeroes WHERE isEnabled = 1 ORDER BY name ASC")
    List<Hero> getAllFilteredSync();
}
