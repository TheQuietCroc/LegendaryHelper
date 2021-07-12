package com.thequietcroc.legendary.database.daos.gamecomponents.cards;

import androidx.room.Dao;
import androidx.room.Query;

import com.thequietcroc.legendary.database.entities.gamecomponents.cards.HenchmenEntity;

import java.util.List;

@Dao
public interface HenchmenDao extends BaseCardDao<HenchmenEntity> {

    @Override
    @Query("SELECT * FROM tblHenchmen WHERE name LIKE :name LIMIT 1")
    HenchmenEntity findByName(final String name);

    @Override
    @Query("SELECT * FROM tblHenchmen WHERE id = :id LIMIT 1")
    HenchmenEntity findById(final int id);

    @Override
    @Query("SELECT * FROM tblHenchmen WHERE id > 0 ORDER BY id ASC")
    List<HenchmenEntity> getAll();

    @Override
    @Query("SELECT * FROM tblHenchmen WHERE isEnabled = 1 AND id > 0 ORDER BY id ASC")
    List<HenchmenEntity> getAllEnabled();

    @Override
    @Query("SELECT * FROM tblHenchmen WHERE setId = :setId AND id > 0 ORDER BY id ASC")
    List<HenchmenEntity> getAllBySetId(final int setId);

    @Override
    @Query("SELECT * FROM tblHenchmen WHERE isCustom = 1 AND id > 0 ORDER BY id ASC")
    List<HenchmenEntity> getAllCustom();
}
