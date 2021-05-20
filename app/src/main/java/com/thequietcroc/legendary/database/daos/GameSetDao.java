package com.thequietcroc.legendary.database.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.thequietcroc.legendary.database.entities.GameSet;

@Dao
public interface GameSetDao {

    @Query("SELECT * FROM tblGameSets WHERE name LIKE :name LIMIT 1")
    LiveData<GameSet> findByName(final String name);
}
