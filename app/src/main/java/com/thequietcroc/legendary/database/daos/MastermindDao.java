package com.thequietcroc.legendary.database.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.thequietcroc.legendary.database.entities.Mastermind;

import java.util.List;
@Dao
public interface MastermindDao extends CardDao<Mastermind> {

    @Override
    @Query("SELECT * FROM tblMasterminds WHERE name LIKE :name LIMIT 1")
    LiveData<Mastermind> findByNameAsync(final String name);

    @Override
    @Query("SELECT * FROM tblMasterminds WHERE id = :id LIMIT 1")
    LiveData<Mastermind> findByIdAsync(final int id);

    @Override
    @Query("SELECT * FROM tblMasterminds ORDER BY name ASC")
    LiveData<List<Mastermind>> getAllUnfilteredAsync();

    @Override
    @Query("SELECT * FROM tblMasterminds WHERE isEnabled = 1 ORDER BY name ASC")
    LiveData<List<Mastermind>> getAllFilteredAsync();

    @Override
    @Query("SELECT * FROM tblMasterminds WHERE name LIKE :name LIMIT 1")
    Mastermind findByNameSync(final String name);

    @Override
    @Query("SELECT * FROM tblMasterminds WHERE id = :id LIMIT 1")
    Mastermind findByIdSync(final int id);

    @Override
    @Query("SELECT * FROM tblMasterminds ORDER BY name ASC")
    List<Mastermind> getAllUnfilteredSync();

    @Override
    @Query("SELECT * FROM tblMasterminds WHERE isEnabled = 1 ORDER BY name ASC")
    List<Mastermind> getAllFilteredSync();
}
