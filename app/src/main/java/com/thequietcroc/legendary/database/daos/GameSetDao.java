package com.thequietcroc.legendary.database.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.thequietcroc.legendary.database.entities.GameSet;

import java.util.List;

@Dao
public interface GameSetDao extends BaseDao<GameSet> {

    @Override
    @Query("SELECT * FROM tblGameSets WHERE name LIKE :name LIMIT 1")
    LiveData<GameSet> findByNameAsync(final String name);

    @Override
    @Query("SELECT * FROM tblGameSets WHERE id = :id LIMIT 1")
    LiveData<GameSet> findByIdAsync(final int id);

    @Override
    @Query("SELECT * FROM tblGameSets ORDER BY name ASC")
    LiveData<List<GameSet>> getAllUnfilteredAsync();

    @Override
    @Query("SELECT * FROM tblGameSets WHERE isEnabled = 1")
    LiveData<List<GameSet>> getAllFilteredAsync();

    @Override
    @Query("SELECT * FROM tblGameSets WHERE name LIKE :name LIMIT 1")
    GameSet findByNameSync(final String name);

    @Override
    @Query("SELECT * FROM tblGameSets WHERE id = :id LIMIT 1")
    GameSet findByIdSync(final int id);

    @Override
    @Query("SELECT * FROM tblGameSets ORDER BY name ASC")
    List<GameSet> getAllUnfilteredSync();

    @Override
    @Query("SELECT * FROM tblGameSets WHERE isEnabled = 1")
    List<GameSet> getAllFilteredSync();
}
