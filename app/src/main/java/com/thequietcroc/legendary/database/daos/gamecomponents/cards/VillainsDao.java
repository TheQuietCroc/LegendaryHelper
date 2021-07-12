package com.thequietcroc.legendary.database.daos.gamecomponents.cards;

import androidx.room.Dao;
import androidx.room.Query;

import com.thequietcroc.legendary.database.entities.gamecomponents.cards.VillainsEntity;

import java.util.List;

@Dao
public interface VillainsDao extends BaseCardDao<VillainsEntity> {

    @Override
    @Query("SELECT * FROM tblVillains WHERE name LIKE :name LIMIT 1")
    VillainsEntity findByName(final String name);

    @Override
    @Query("SELECT * FROM tblVillains WHERE id = :id LIMIT 1")
    VillainsEntity findById(final int id);

    @Override
    @Query("SELECT * FROM tblVillains WHERE id > 0 ORDER BY id ASC")
    List<VillainsEntity> getAll();

    @Override
    @Query("SELECT * FROM tblVillains WHERE isEnabled = 1 AND id > 0 ORDER BY id ASC")
    List<VillainsEntity> getAllEnabled();

    @Override
    @Query("SELECT * FROM tblVillains WHERE setId = :setId AND id > 0 ORDER BY id ASC")
    List<VillainsEntity> getAllBySetId(final int setId);

    @Override
    @Query("SELECT * FROM tblVillains WHERE isCustom = 1 AND id > 0 ORDER BY id ASC")
    List<VillainsEntity> getAllCustom();
}
