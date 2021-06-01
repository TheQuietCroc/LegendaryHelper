package com.thequietcroc.legendary.database.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.thequietcroc.legendary.database.entities.SchemeEntity;

import java.util.List;

@Dao
public interface SchemeDao extends CardDao<SchemeEntity> {

    // findByName
    @Override
    @Query("SELECT * FROM SchemeEntity WHERE name LIKE :name LIMIT 1")
    LiveData<SchemeEntity> findByNameAsync(final String name);

    @Override
    @Query("SELECT * FROM SchemeEntity WHERE name LIKE :name LIMIT 1")
    SchemeEntity findByNameSync(final String name);

    // findById
    @Override
    @Query("SELECT * FROM SchemeEntity WHERE id = :id LIMIT 1")
    LiveData<SchemeEntity> findByIdAsync(final int id);

    @Override
    @Query("SELECT * FROM SchemeEntity WHERE id = :id LIMIT 1")
    SchemeEntity findByIdSync(final int id);

    // getAll
    @Override
    @Query("SELECT * FROM SchemeEntity ORDER BY id ASC")
    LiveData<List<SchemeEntity>> getAllAsync();

    @Override
    @Query("SELECT * FROM SchemeEntity ORDER BY id ASC")
    List<SchemeEntity> getAllSync();

    // getAllEnabled
    @Override
    @Query("SELECT * FROM SchemeEntity WHERE isEnabled = 1 ORDER BY id ASC")
    LiveData<List<SchemeEntity>> getAllEnabledAsync();

    @Override
    @Query("SELECT * FROM SchemeEntity WHERE isEnabled = 1 ORDER BY id ASC")
    List<SchemeEntity> getAllEnabledSync();

    // getAllBySetId
    @Override
    @Query("SELECT * FROM SchemeEntity WHERE setId = :setId ORDER BY id ASC")
    LiveData<List<SchemeEntity>> getAllBySetIdAsync(final int setId);

    @Override
    @Query("SELECT * FROM SchemeEntity WHERE setId = :setId ORDER BY id ASC")
    List<SchemeEntity> getAllBySetIdSync(final int setId);
}
