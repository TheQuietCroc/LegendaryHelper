package com.thequietcroc.legendary.database.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.thequietcroc.legendary.database.entities.Mastermind;

import java.util.List;
@Dao
public interface MastermindDao extends BaseDao<Mastermind> {

    @Query("SELECT * FROM tblMasterminds ORDER BY name ASC")
    LiveData<List<Mastermind>> getAll();

    @Query("SELECT * FROM tblMasterminds WHERE name LIKE :name LIMIT 1")
    LiveData<Mastermind> findByName(final String name);

    @Query("SELECT * FROM tblMasterminds WHERE setId = :setId ORDER BY name ASC")
    LiveData<List<Mastermind>> findAllInSet(final int setId);

    @Query("SELECT * FROM tblMasterminds WHERE isCustom = 1 ORDER BY name ASC")
    LiveData<List<Mastermind>> getAllCustom();

    @Query("SELECT * FROM tblMasterminds WHERE isCustom = 0 ORDER BY name ASC")
    LiveData<List<Mastermind>> getAllStock();

    @Query("SELECT name FROM tblMasterminds ORDER BY name ASC")
    LiveData<List<String>> getAllNames();

    @Query("SELECT name FROM tblMasterminds WHERE isExcluded = 0 ORDER BY name ASC")
    LiveData<List<String>> getAllFilteredNames();
}
