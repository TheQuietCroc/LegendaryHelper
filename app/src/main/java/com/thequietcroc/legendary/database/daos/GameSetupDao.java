package com.thequietcroc.legendary.database.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.thequietcroc.legendary.database.entities.GameSetupEntity;

import java.util.List;

@Dao
public interface GameSetupDao extends BaseDao<GameSetupEntity> {

    // findByName
    @Override
    @Query("SELECT * FROM tblGameSetups WHERE name = :name LIMIT 1")
    LiveData<GameSetupEntity> findByNameAsync(final String name);

    @Override
    @Query("SELECT * FROM tblGameSetups WHERE name = :name LIMIT 1")
    GameSetupEntity findByNameSync(final String name);

    // findById
    @Override
    @Query("SELECT * FROM tblGameSetups WHERE id = :id LIMIT 1")
    LiveData<GameSetupEntity> findByIdAsync(final int id);

    @Override
    @Query("SELECT * FROM tblGameSetups WHERE id = :id LIMIT 1")
    GameSetupEntity findByIdSync(final int id);

    // getAll
    @Override
    @Query("SELECT * FROM tblGameSetups WHERE id > 0 ORDER BY dateLastPlayed DESC")
    LiveData<List<GameSetupEntity>> getAllAsync();

    @Override
    @Query("SELECT * FROM tblGameSetups WHERE id > 0 ORDER BY dateLastPlayed DESC")
    List<GameSetupEntity> getAllSync();
}
