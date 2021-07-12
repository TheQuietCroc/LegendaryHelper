package com.thequietcroc.legendary.database.daos;

import androidx.room.Dao;
import androidx.room.Query;

import com.thequietcroc.legendary.database.entities.GameSetupEntity;

import java.util.List;

@Dao
public interface GameSetupDao extends BaseDao<GameSetupEntity> {

    @Override
    @Query("SELECT * FROM tblGameSetups WHERE name = :name LIMIT 1")
    GameSetupEntity findByName(final String name);

    @Override
    @Query("SELECT * FROM tblGameSetups WHERE id = :id LIMIT 1")
    GameSetupEntity findById(final int id);

    @Override
    @Query("SELECT * FROM tblGameSetups WHERE id > 0 ORDER BY dateLastPlayed DESC")
    List<GameSetupEntity> getAll();
}
