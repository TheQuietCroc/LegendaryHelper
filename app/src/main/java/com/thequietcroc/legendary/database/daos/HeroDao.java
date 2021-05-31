package com.thequietcroc.legendary.database.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.thequietcroc.legendary.database.entities.Hero;

import java.util.List;

@Dao
public interface HeroDao extends CardDao<Hero> {

    // findByName
    @Override
    @Query("SELECT * FROM tblHeroes WHERE name LIKE :name LIMIT 1")
    LiveData<Hero> findByNameAsync(final String name);

    @Override
    @Query("SELECT * FROM tblHeroes WHERE name LIKE :name LIMIT 1")
    Hero findByNameSync(final String name);

    // findById
    @Override
    @Query("SELECT * FROM tblHeroes WHERE id = :id LIMIT 1")
    LiveData<Hero> findByIdAsync(final int id);

    @Override
    @Query("SELECT * FROM tblHeroes WHERE id = :id LIMIT 1")
    Hero findByIdSync(final int id);

    // getAll
    @Override
    @Query("SELECT * FROM tblHeroes ORDER BY id ASC")
    LiveData<List<Hero>> getAllAsync();

    @Override
    @Query("SELECT * FROM tblHeroes ORDER BY id ASC")
    List<Hero> getAllSync();

    // getAllEnabled
    @Override
    @Query("SELECT * FROM tblHeroes WHERE isEnabled = 1 ORDER BY id ASC")
    LiveData<List<Hero>> getAllEnabledAsync();

    @Override
    @Query("SELECT * FROM tblHeroes WHERE isEnabled = 1 ORDER BY id ASC")
    List<Hero> getAllEnabledSync();

    // getAllBySetId
    @Override
    @Query("SELECT * FROM tblHeroes WHERE setId = :setId ORDER BY id ASC")
    LiveData<List<Hero>> getAllBySetIdAsync(final int setId);

    @Override
    @Query("SELECT * FROM tblHeroes WHERE setId = :setId ORDER BY id ASC")
    List<Hero> getAllBySetIdSync(final int setId);
}
