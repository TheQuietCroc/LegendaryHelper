package com.thequietcroc.legendary.database.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.thequietcroc.legendary.database.entities.Mastermind;

import java.util.List;
@Dao
public interface MastermindDao {

    @Query("SELECT * FROM tblMasterminds")
    LiveData<List<Mastermind>> getAll();

    @Query("SELECT * FROM tblMasterminds WHERE name LIKE :name LIMIT 1")
    LiveData<Mastermind> findByName(final String name);

    @Query("SELECT * FROM tblMasterminds WHERE setId = :setId")
    LiveData<List<Mastermind>> findAllInSet(final int setId);

    @Query("SELECT * FROM tblMasterminds WHERE isCustom = 1")
    LiveData<List<Mastermind>> getAllCustom();

    @Query("SELECT * FROM tblMasterminds WHERE isCustom = 0")
    LiveData<List<Mastermind>> getAllStock();
}
