package com.thequietcroc.legendary.database.daos.gamecomponents.cards;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.thequietcroc.legendary.database.entities.gamecomponents.cards.SchemeEntity;

import java.util.List;

@Dao
public interface SchemeDao extends BaseCardDao<SchemeEntity> {

    // findByName
    @Override
    @Query("SELECT * FROM tblSchemes WHERE name LIKE :name LIMIT 1")
    LiveData<SchemeEntity> findByNameAsync(final String name);

    @Override
    @Query("SELECT * FROM tblSchemes WHERE name LIKE :name LIMIT 1")
    SchemeEntity findByNameSync(final String name);

    // findById
    @Override
    @Query("SELECT * FROM tblSchemes WHERE id = :id LIMIT 1")
    LiveData<SchemeEntity> findByIdAsync(final int id);

    @Override
    @Query("SELECT * FROM tblSchemes WHERE id = :id LIMIT 1")
    SchemeEntity findByIdSync(final int id);

    // getAll
    @Override
    @Query("SELECT * FROM tblSchemes WHERE id > 0 ORDER BY id ASC")
    LiveData<List<SchemeEntity>> getAllAsync();

    @Override
    @Query("SELECT * FROM tblSchemes WHERE id > 0 ORDER BY id ASC")
    List<SchemeEntity> getAllSync();

    // getAllEnabled
    @Override
    @Query("SELECT * FROM tblSchemes WHERE isEnabled = 1 AND id > 0 ORDER BY id ASC")
    LiveData<List<SchemeEntity>> getAllEnabledAsync();

    @Override
    @Query("SELECT * FROM tblSchemes WHERE isEnabled = 1 AND id > 0 ORDER BY id ASC")
    List<SchemeEntity> getAllEnabledSync();

    // getAllBySetId
    @Override
    @Query("SELECT * FROM tblSchemes WHERE setId = :setId AND id > 0 ORDER BY id ASC")
    LiveData<List<SchemeEntity>> getAllBySetIdAsync(final int setId);

    @Override
    @Query("SELECT * FROM tblSchemes WHERE setId = :setId AND id > 0 ORDER BY id ASC")
    List<SchemeEntity> getAllBySetIdSync(final int setId);

    // getAllCustom
    @Override
    @Query("SELECT * FROM tblSchemes WHERE isCustom = 1 AND id > 0 ORDER BY id ASC")
    LiveData<List<SchemeEntity>> getAllCustomAsync();

    @Override
    @Query("SELECT * FROM tblSchemes WHERE isCustom = 1 AND id > 0 ORDER BY id ASC")
    List<SchemeEntity> getAllCustomSync();
}
