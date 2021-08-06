package com.thequietcroc.legendary.database.daos.gamecomponents;

import androidx.room.Dao;
import androidx.room.Query;

import com.thequietcroc.legendary.database.entities.gamecomponents.KeywordEntity;

import java.util.List;

@Dao
public interface KeywordDao extends BaseGameComponentDao<KeywordEntity> {

    @Override
    @Query("SELECT * FROM tblKeywords WHERE name LIKE :name LIMIT 1")
    KeywordEntity findByName(final String name);

    @Override
    @Query("SELECT * FROM tblKeywords WHERE id = :id LIMIT 1")
    KeywordEntity findById(final int id);

    @Override
    @Query("SELECT * FROM tblKeywords WHERE id > 0 ORDER BY name ASC")
    List<KeywordEntity> getAll();

    @Override
    @Query("SELECT * FROM tblKeywords WHERE isEnabled = 1 ORDER BY name ASC")
    List<KeywordEntity> getAllEnabled();

    @Override
    @Query("SELECT * FROM tblKeywords WHERE isCustom = 1 ORDER BY name ASC")
    List<KeywordEntity> getAllCustom();
}
