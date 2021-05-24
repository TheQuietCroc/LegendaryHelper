package com.thequietcroc.legendary.database.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.thequietcroc.legendary.database.entities.Mastermind;

import java.util.List;
@Dao
public interface MastermindDao extends CardDao<Mastermind> {

    @Query("SELECT * FROM tblMasterminds WHERE name LIKE :name LIMIT 1")
    LiveData<Mastermind> findByName(final String name);

    @Query("SELECT * FROM tblMasterminds WHERE id = :id LIMIT 1")
    LiveData<Mastermind> findById(final int id);

    @Query("SELECT * FROM tblMasterminds ORDER BY name ASC")
    LiveData<List<Mastermind>> getAllUnfiltered();

    @Query("SELECT * FROM tblMasterminds WHERE isExcluded = 0 ORDER BY name ASC")
    LiveData<List<Mastermind>> getAllFiltered();

    @Query("SELECT name FROM tblMasterminds ORDER BY name ASC")
    LiveData<List<String>> getAllNames();

    @Query("SELECT name FROM tblMasterminds WHERE isExcluded = 0 ORDER BY name ASC")
    LiveData<List<String>> getAllFilteredNames();
}
