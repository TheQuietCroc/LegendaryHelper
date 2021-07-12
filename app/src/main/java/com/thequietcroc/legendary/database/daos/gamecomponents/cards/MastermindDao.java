package com.thequietcroc.legendary.database.daos.gamecomponents.cards;

import androidx.room.Dao;
import androidx.room.Query;

import com.thequietcroc.legendary.database.entities.gamecomponents.cards.MastermindEntity;

import java.util.List;

@Dao
public interface MastermindDao extends BaseCardDao<MastermindEntity> {

    @Override
    @Query("SELECT * FROM tblMasterminds WHERE name LIKE :name LIMIT 1")
    MastermindEntity findByName(final String name);

    @Override
    @Query("SELECT * FROM tblMasterminds WHERE id = :id LIMIT 1")
    MastermindEntity findById(final int id);

    @Override
    @Query("SELECT * FROM tblMasterminds WHERE id > 0 ORDER BY id ASC")
    List<MastermindEntity> getAll();

    @Override
    @Query("SELECT * FROM tblMasterminds WHERE isEnabled = 1 AND id > 0 ORDER BY id ASC")
    List<MastermindEntity> getAllEnabled();

    @Override
    @Query("SELECT * FROM tblMasterminds WHERE setId = :setId AND id > 0 ORDER BY id ASC")
    List<MastermindEntity> getAllBySetId(final int setId);

    @Query("SELECT * FROM tblMasterminds WHERE henchmenId = :henchmenId LIMIT 1")
    MastermindEntity findByAlwaysLeadsHenchmenId(final int henchmenId);

    @Query("SELECT * FROM tblMasterminds WHERE villainId = :villainId LIMIT 1")
    MastermindEntity findByAlwaysLeadsVillainsId(final int villainId);

    @Override
    @Query("SELECT * FROM tblMasterminds WHERE isCustom = 1 AND id > 0 ORDER BY id ASC")
    List<MastermindEntity> getAllCustom();
}
