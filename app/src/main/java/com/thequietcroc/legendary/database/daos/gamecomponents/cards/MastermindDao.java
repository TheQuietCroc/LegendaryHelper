package com.thequietcroc.legendary.database.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.thequietcroc.legendary.database.entities.MastermindEntity;

import java.util.List;
@Dao
public interface MastermindDao extends CardDao<MastermindEntity> {

    // findByName
    @Override
    @Query("SELECT * FROM MastermindEntity WHERE name LIKE :name LIMIT 1")
    LiveData<MastermindEntity> findByNameAsync(final String name);

    @Override
    @Query("SELECT * FROM MastermindEntity WHERE name LIKE :name LIMIT 1")
    MastermindEntity findByNameSync(final String name);

    // findById
    @Override
    @Query("SELECT * FROM MastermindEntity WHERE id = :id LIMIT 1")
    LiveData<MastermindEntity> findByIdAsync(final int id);

    @Override
    @Query("SELECT * FROM MastermindEntity WHERE id = :id LIMIT 1")
    MastermindEntity findByIdSync(final int id);

    // getAll
    @Override
    @Query("SELECT * FROM MastermindEntity ORDER BY id ASC")
    LiveData<List<MastermindEntity>> getAllAsync();

    @Override
    @Query("SELECT * FROM MastermindEntity ORDER BY id ASC")
    List<MastermindEntity> getAllSync();

    // getAllEnabled
    @Override
    @Query("SELECT * FROM MastermindEntity WHERE isEnabled = 1 ORDER BY id ASC")
    LiveData<List<MastermindEntity>> getAllEnabledAsync();

    @Override
    @Query("SELECT * FROM MastermindEntity WHERE isEnabled = 1 ORDER BY id ASC")
    List<MastermindEntity> getAllEnabledSync();

    // getAllBySetId
    @Override
    @Query("SELECT * FROM MastermindEntity WHERE setId = :setId ORDER BY id ASC")
    LiveData<List<MastermindEntity>> getAllBySetIdAsync(final int setId);

    @Override
    @Query("SELECT * FROM MastermindEntity WHERE setId = :setId ORDER BY id ASC")
    List<MastermindEntity> getAllBySetIdSync(final int setId);
}
