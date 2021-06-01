package com.thequietcroc.legendary.database.daos.gamecomponents.cards;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.thequietcroc.legendary.database.entities.gamecomponents.cards.VillainsEntity;

import java.util.List;

@Dao
public interface VillainsDao extends CardDao<VillainsEntity> {

    // findByName
    @Override
    @Query("SELECT * FROM tblVillains WHERE name LIKE :name LIMIT 1")
    LiveData<VillainsEntity> findByNameAsync(final String name);

    @Override
    @Query("SELECT * FROM tblVillains WHERE name LIKE :name LIMIT 1")
    VillainsEntity findByNameSync(final String name);

    // findById
    @Override
    @Query("SELECT * FROM tblVillains WHERE id = :id LIMIT 1")
    LiveData<VillainsEntity> findByIdAsync(final int id);

    @Override
    @Query("SELECT * FROM tblVillains WHERE id = :id LIMIT 1")
    VillainsEntity findByIdSync(final int id);

    // getAll
    @Override
    @Query("SELECT * FROM tblVillains ORDER BY id ASC")
    LiveData<List<VillainsEntity>> getAllAsync();

    @Override
    @Query("SELECT * FROM tblVillains ORDER BY id ASC")
    List<VillainsEntity> getAllSync();

    // getAllEnabled
    @Override
    @Query("SELECT * FROM tblVillains WHERE isEnabled = 1 ORDER BY id ASC")
    LiveData<List<VillainsEntity>> getAllEnabledAsync();

    @Override
    @Query("SELECT * FROM tblVillains WHERE isEnabled = 1 ORDER BY id ASC")
    List<VillainsEntity> getAllEnabledSync();

    // getAllBySetId
    @Override
    @Query("SELECT * FROM tblVillains WHERE setId = :setId ORDER BY id ASC")
    LiveData<List<VillainsEntity>> getAllBySetIdAsync(final int setId);

    @Override
    @Query("SELECT * FROM tblVillains WHERE setId = :setId ORDER BY id ASC")
    List<VillainsEntity> getAllBySetIdSync(final int setId);
}
