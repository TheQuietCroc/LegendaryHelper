package com.thequietcroc.legendary.database.daos.gamecomponents.cards;

import androidx.room.Dao;
import androidx.room.Query;

import com.thequietcroc.legendary.database.entities.gamecomponents.cards.SchemeEntity;

import java.util.List;

@Dao
public interface SchemeDao extends BaseCardDao<SchemeEntity> {

    @Override
    @Query("SELECT * FROM tblSchemes WHERE name LIKE :name LIMIT 1")
    SchemeEntity findByName(final String name);

    @Override
    @Query("SELECT * FROM tblSchemes WHERE id = :id LIMIT 1")
    SchemeEntity findById(final int id);

    @Override
    @Query("SELECT * FROM tblSchemes WHERE id > 0 ORDER BY id ASC")
    List<SchemeEntity> getAll();

    @Override
    @Query("SELECT * FROM tblSchemes WHERE isEnabled = 1 AND id > 0 ORDER BY id ASC")
    List<SchemeEntity> getAllEnabled();

    @Override
    @Query("SELECT * FROM tblSchemes WHERE setId = :setId AND id > 0 ORDER BY id ASC")
    List<SchemeEntity> getAllBySetId(final int setId);

    @Override
    @Query("SELECT * FROM tblSchemes WHERE isCustom = 1 AND id > 0 ORDER BY id ASC")
    List<SchemeEntity> getAllCustom();
}
