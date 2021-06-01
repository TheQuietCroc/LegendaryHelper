package com.thequietcroc.legendary.database.daos.gamecomponents;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.thequietcroc.legendary.database.entities.gamecomponents.GameSetEntity;

import java.util.List;

@Dao
public interface GameSetDao extends GameComponentDao<GameSetEntity> {

    // findByName
    @Override
    @Query("SELECT * FROM tblGameSets WHERE name LIKE :name LIMIT 1")
    LiveData<GameSetEntity> findByNameAsync(final String name);

    @Override
    @Query("SELECT * FROM tblGameSets WHERE name LIKE :name LIMIT 1")
    GameSetEntity findByNameSync(final String name);

    // findById
    @Override
    @Query("SELECT * FROM tblGameSets WHERE id = :id LIMIT 1")
    LiveData<GameSetEntity> findByIdAsync(final int id);

    @Override
    @Query("SELECT * FROM tblGameSets WHERE id = :id LIMIT 1")
    GameSetEntity findByIdSync(final int id);

    // getAll
    @Override
    @Query("SELECT * FROM tblGameSets ORDER BY id ASC")
    LiveData<List<GameSetEntity>> getAllAsync();

    @Override
    @Query("SELECT * FROM tblGameSets ORDER BY id ASC")
    List<GameSetEntity> getAllSync();

    // getAllEnabled
    @Override
    @Query("SELECT * FROM tblGameSets WHERE isEnabled = 1 ORDER BY id ASC")
    LiveData<List<GameSetEntity>> getAllEnabledAsync();

    @Override
    @Query("SELECT * FROM tblGameSets WHERE isEnabled = 1 ORDER BY id ASC")
    List<GameSetEntity> getAllEnabledSync();
}
