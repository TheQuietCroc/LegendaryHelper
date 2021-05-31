package com.thequietcroc.legendary.database.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.thequietcroc.legendary.utilities.GameSetup;

import java.util.List;

@Dao
public interface GameSetupDao extends BaseDao<GameSetup> {

    // findByName
    @Override
    @Query("SELECT * FROM tblGameSetups WHERE name = :name LIMIT 1")
    LiveData<GameSetup> findByNameAsync(final String name);

    @Override
    @Query("SELECT * FROM tblGameSetups WHERE name = :name LIMIT 1")
    GameSetup findByNameSync(final String name);

    // findById
    @Override
    @Query("SELECT * FROM tblGameSetups WHERE id = :id LIMIT 1")
    LiveData<GameSetup> findByIdAsync(final int id);

    @Override
    @Query("SELECT * FROM tblGameSetups WHERE id = :id LIMIT 1")
    GameSetup findByIdSync(final int id);

    // getAll
    @Override
    @Query("SELECT * FROM tblGameSetups ORDER BY dateLastPlayed DESC")
    LiveData<List<GameSetup>> getAllAsync();

    @Override
    @Query("SELECT * FROM tblGameSetups ORDER BY dateLastPlayed DESC")
    List<GameSetup> getAllSync();
}
