package com.thequietcroc.legendary.database.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.thequietcroc.legendary.database.entities.HeroEntity;

import java.util.List;

@Dao
public interface HeroDao extends CardDao<HeroEntity> {

    // findByName
    @Override
    @Query("SELECT * FROM HeroEntity WHERE name LIKE :name LIMIT 1")
    LiveData<HeroEntity> findByNameAsync(final String name);

    @Override
    @Query("SELECT * FROM HeroEntity WHERE name LIKE :name LIMIT 1")
    HeroEntity findByNameSync(final String name);

    // findById
    @Override
    @Query("SELECT * FROM HeroEntity WHERE id = :id LIMIT 1")
    LiveData<HeroEntity> findByIdAsync(final int id);

    @Override
    @Query("SELECT * FROM HeroEntity WHERE id = :id LIMIT 1")
    HeroEntity findByIdSync(final int id);

    // getAll
    @Override
    @Query("SELECT * FROM HeroEntity ORDER BY id ASC")
    LiveData<List<HeroEntity>> getAllAsync();

    @Override
    @Query("SELECT * FROM HeroEntity ORDER BY id ASC")
    List<HeroEntity> getAllSync();

    // getAllEnabled
    @Override
    @Query("SELECT * FROM HeroEntity WHERE isEnabled = 1 ORDER BY id ASC")
    LiveData<List<HeroEntity>> getAllEnabledAsync();

    @Override
    @Query("SELECT * FROM HeroEntity WHERE isEnabled = 1 ORDER BY id ASC")
    List<HeroEntity> getAllEnabledSync();

    // getAllBySetId
    @Override
    @Query("SELECT * FROM HeroEntity WHERE setId = :setId ORDER BY id ASC")
    LiveData<List<HeroEntity>> getAllBySetIdAsync(final int setId);

    @Override
    @Query("SELECT * FROM HeroEntity WHERE setId = :setId ORDER BY id ASC")
    List<HeroEntity> getAllBySetIdSync(final int setId);
}
