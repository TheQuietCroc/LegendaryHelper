package com.thequietcroc.legendary.database.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.thequietcroc.legendary.database.entities.GameSet;

import java.util.List;

@Dao
public interface GameSetDao extends BaseDao<GameSet> {

    @Query("SELECT * FROM tblGameSets WHERE name LIKE :name LIMIT 1")
    LiveData<GameSet> findByName(final String name);

    @Query("SELECT * FROM tblGameSets WHERE id = :id LIMIT 1")
    LiveData<GameSet> findById(final int id);

    @Query("SELECT * FROM tblGameSets ORDER BY name ASC")
    LiveData<List<GameSet>> getAllUnfiltered();

    @Query("SELECT * FROM tblGameSets WHERE isExcluded = 0")
    LiveData<List<GameSet>> getAllFiltered();

    @Query("SELECT name FROM tblGameSets ORDER BY name ASC")
    LiveData<List<String>> getAllNames();

    @Query("SELECT name FROM tblGameSets WHERE isExcluded = 0 ORDER BY name ASC")
    LiveData<List<String>> getAllFilteredNames();
}
