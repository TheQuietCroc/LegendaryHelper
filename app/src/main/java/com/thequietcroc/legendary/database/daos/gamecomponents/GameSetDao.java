package com.thequietcroc.legendary.database.daos.gamecomponents;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.thequietcroc.legendary.database.entities.gamecomponents.GameSetEntity;

import java.util.List;

@Dao
public interface GameSetDao extends BaseGameComponentDao<GameSetEntity, GameSetEntity.Minimal> {

    // findByName
    @Override
    @Query("SELECT * FROM tblGameSets WHERE name LIKE :name LIMIT 1")
    LiveData<GameSetEntity> findByNameAsync(final String name);

    @Override
    @Query("SELECT * FROM tblGameSets WHERE name LIKE :name LIMIT 1")
    GameSetEntity findByNameSync(final String name);

    @Override
    @Query("SELECT id, name FROM tblGameSets WHERE name = :name LIMIT 1")
    LiveData<GameSetEntity.Minimal> findByNameAsyncMinimal(final String name);

    @Override
    @Query("SELECT id, name FROM tblGameSets WHERE name = :name LIMIT 1")
    GameSetEntity.Minimal findByNameSyncMinimal(final String name);

    // findById
    @Override
    @Query("SELECT * FROM tblGameSets WHERE id = :id LIMIT 1")
    LiveData<GameSetEntity> findByIdAsync(final int id);

    @Override
    @Query("SELECT * FROM tblGameSets WHERE id = :id LIMIT 1")
    GameSetEntity findByIdSync(final int id);

    @Override
    @Query("SELECT id, name FROM tblGameSets WHERE id = :id LIMIT 1")
    LiveData<GameSetEntity.Minimal> findByIdAsyncMinimal(final int id);

    @Override
    @Query("SELECT id, name FROM tblGameSets WHERE id = :id LIMIT 1")
    GameSetEntity.Minimal findByIdSyncMinimal(final int id);

    // getAll
    @Override
    @Query("SELECT * FROM tblGameSets ORDER BY id ASC")
    LiveData<List<GameSetEntity>> getAllAsync();

    @Override
    @Query("SELECT * FROM tblGameSets ORDER BY id ASC")
    List<GameSetEntity> getAllSync();

    @Override
    @Query("SELECT id, name FROM tblGameSets ORDER BY id DESC")
    LiveData<List<GameSetEntity.Minimal>> getAllAsyncMinimal();

    @Override
    @Query("SELECT id, name FROM tblGameSets ORDER BY id DESC")
    List<GameSetEntity.Minimal> getAllSyncMinimal();

    // getAllEnabled
    @Override
    @Query("SELECT * FROM tblGameSets WHERE isEnabled = 1 ORDER BY id ASC")
    LiveData<List<GameSetEntity>> getAllEnabledAsync();

    @Override
    @Query("SELECT * FROM tblGameSets WHERE isEnabled = 1 ORDER BY id ASC")
    List<GameSetEntity> getAllEnabledSync();

    @Override
    @Query("SELECT id, name FROM tblGameSets WHERE isEnabled = 1 ORDER BY id ASC")
    LiveData<List<GameSetEntity.Minimal>> getAllEnabledAsyncMinimal();

    @Override
    @Query("SELECT id, name FROM tblGameSets WHERE isEnabled = 1 ORDER BY id ASC")
    List<GameSetEntity.Minimal> getAllEnabledSyncMinimal();
}
