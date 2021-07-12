package com.thequietcroc.legendary.database.daos.gamecomponents;

import androidx.room.Dao;
import androidx.room.Query;

import com.thequietcroc.legendary.database.entities.gamecomponents.GameSetEntity;

import java.util.List;

@Dao
public interface GameSetDao extends BaseGameComponentDao<GameSetEntity> {

    @Override
    @Query("SELECT * FROM tblGameSets WHERE name LIKE :name LIMIT 1")
    GameSetEntity findByName(final String name);

    @Override
    @Query("SELECT * FROM tblGameSets WHERE id = :id LIMIT 1")
    GameSetEntity findById(final int id);

    @Override
    @Query("SELECT * FROM tblGameSets WHERE id > 0 ORDER BY id ASC")
    List<GameSetEntity> getAll();

    @Override
    @Query("SELECT * FROM tblGameSets WHERE isEnabled = 1 AND id > 0 ORDER BY id ASC")
    List<GameSetEntity> getAllEnabled();

    @Override
    @Query("SELECT * FROM tblGameSets WHERE isCustom = 1 AND id > 0 ORDER BY id ASC")
    List<GameSetEntity> getAllCustom();
}
