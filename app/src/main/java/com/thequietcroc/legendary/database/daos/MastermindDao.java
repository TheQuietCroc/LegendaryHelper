package com.thequietcroc.legendary.database.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.thequietcroc.legendary.database.entities.Mastermind;

import java.util.List;
@Dao
public interface MastermindDao extends CardDao<Mastermind> {

    // findByName
    @Override
    @Query("SELECT * FROM tblMasterminds WHERE name LIKE :name LIMIT 1")
    LiveData<Mastermind> findByNameAsync(final String name);

    @Override
    @Query("SELECT * FROM tblMasterminds WHERE name LIKE :name LIMIT 1")
    Mastermind findByNameSync(final String name);

    // findById
    @Override
    @Query("SELECT * FROM tblMasterminds WHERE id = :id LIMIT 1")
    LiveData<Mastermind> findByIdAsync(final int id);

    @Override
    @Query("SELECT * FROM tblMasterminds WHERE id = :id LIMIT 1")
    Mastermind findByIdSync(final int id);

    // getAll
    @Override
    @Query("SELECT * FROM tblMasterminds ORDER BY id ASC")
    LiveData<List<Mastermind>> getAllAsync();

    @Override
    @Query("SELECT * FROM tblMasterminds ORDER BY id ASC")
    List<Mastermind> getAllSync();

    // getAllEnabled
    @Override
    @Query("SELECT * FROM tblMasterminds WHERE isEnabled = 1 ORDER BY id ASC")
    LiveData<List<Mastermind>> getAllEnabledAsync();

    @Override
    @Query("SELECT * FROM tblMasterminds WHERE isEnabled = 1 ORDER BY id ASC")
    List<Mastermind> getAllEnabledSync();

    // getAllBySetId
    @Override
    @Query("SELECT * FROM tblMasterminds WHERE setId = :setId ORDER BY id ASC")
    LiveData<List<Mastermind>> getAllBySetIdAsync(final int setId);

    @Override
    @Query("SELECT * FROM tblMasterminds WHERE setId = :setId ORDER BY id ASC")
    List<Mastermind> getAllBySetIdSync(final int setId);
}
