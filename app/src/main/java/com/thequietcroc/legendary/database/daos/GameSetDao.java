package com.thequietcroc.legendary.database.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.thequietcroc.legendary.database.entities.GameSet;

import java.util.List;

@Dao
public interface GameSetDao extends GameItemDao<GameSet> {

    // findByName
    @Override
    @Query("SELECT * FROM tblGameSets WHERE name LIKE :name LIMIT 1")
    LiveData<GameSet> findByNameAsync(final String name);

    @Override
    @Query("SELECT * FROM tblGameSets WHERE name LIKE :name LIMIT 1")
    GameSet findByNameSync(final String name);

    // findById
    @Override
    @Query("SELECT * FROM tblGameSets WHERE id = :id LIMIT 1")
    LiveData<GameSet> findByIdAsync(final int id);

    @Override
    @Query("SELECT * FROM tblGameSets WHERE id = :id LIMIT 1")
    GameSet findByIdSync(final int id);

    // getAll
    @Override
    @Query("SELECT * FROM tblGameSets ORDER BY id ASC")
    LiveData<List<GameSet>> getAllAsync();

    @Override
    @Query("SELECT * FROM tblGameSets ORDER BY id ASC")
    List<GameSet> getAllSync();

    // getAllEnabled
    @Override
    @Query("SELECT * FROM tblGameSets WHERE isEnabled = 1 ORDER BY id ASC")
    LiveData<List<GameSet>> getAllEnabledAsync();

    @Override
    @Query("SELECT * FROM tblGameSets WHERE isEnabled = 1 ORDER BY id ASC")
    List<GameSet> getAllEnabledSync();
}
