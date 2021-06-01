package com.thequietcroc.legendary.database.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.thequietcroc.legendary.database.entities.HenchmenEntity;

import java.util.List;

@Dao
public interface HenchmenDao extends CardDao<HenchmenEntity> {

    // findByName
    @Override
    @Query("SELECT * FROM HenchmenEntity WHERE name LIKE :name LIMIT 1")
    LiveData<HenchmenEntity> findByNameAsync(final String name);

    @Override
    @Query("SELECT * FROM HenchmenEntity WHERE name LIKE :name LIMIT 1")
    HenchmenEntity findByNameSync(final String name);

    // findById
    @Override
    @Query("SELECT * FROM HenchmenEntity WHERE id = :id LIMIT 1")
    LiveData<HenchmenEntity> findByIdAsync(final int id);

    @Override
    @Query("SELECT * FROM HenchmenEntity WHERE id = :id LIMIT 1")
    HenchmenEntity findByIdSync(final int id);

    // getAll
    @Override
    @Query("SELECT * FROM HenchmenEntity ORDER BY id ASC")
    LiveData<List<HenchmenEntity>> getAllAsync();

    @Override
    @Query("SELECT * FROM HenchmenEntity ORDER BY id ASC")
    List<HenchmenEntity> getAllSync();

    // getAllEnabled
    @Override
    @Query("SELECT * FROM HenchmenEntity WHERE isEnabled = 1 ORDER BY id ASC")
    LiveData<List<HenchmenEntity>> getAllEnabledAsync();

    @Override
    @Query("SELECT * FROM HenchmenEntity WHERE isEnabled = 1 ORDER BY id ASC")
    List<HenchmenEntity> getAllEnabledSync();

    // getAllBySetId
    @Override
    @Query("SELECT * FROM HenchmenEntity WHERE setId = :setId ORDER BY id ASC")
    LiveData<List<HenchmenEntity>> getAllBySetIdAsync(final int setId);

    @Override
    @Query("SELECT * FROM HenchmenEntity WHERE setId = :setId ORDER BY id ASC")
    List<HenchmenEntity> getAllBySetIdSync(final int setId);
}
