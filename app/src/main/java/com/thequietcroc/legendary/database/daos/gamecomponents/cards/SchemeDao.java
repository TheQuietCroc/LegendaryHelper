package com.thequietcroc.legendary.database.daos.gamecomponents.cards;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.thequietcroc.legendary.database.entities.gamecomponents.cards.SchemeEntity;

import java.util.List;

@Dao
public interface SchemeDao extends BaseCardDao<SchemeEntity, SchemeEntity.Minimal> {

    // findByName
    @Override
    @Query("SELECT * FROM tblSchemes WHERE name LIKE :name LIMIT 1")
    LiveData<SchemeEntity> findByNameAsync(final String name);

    @Override
    @Query("SELECT * FROM tblSchemes WHERE name LIKE :name LIMIT 1")
    SchemeEntity findByNameSync(final String name);

    @Override
    @Query("SELECT id, name FROM tblSchemes WHERE name LIKE :name LIMIT 1")
    LiveData<SchemeEntity.Minimal> findByNameAsyncMinimal(final String name);

    @Override
    @Query("SELECT id, name FROM tblSchemes WHERE name LIKE :name LIMIT 1")
    SchemeEntity.Minimal findByNameSyncMinimal(final String name);

    // findById
    @Override
    @Query("SELECT * FROM tblSchemes WHERE id = :id LIMIT 1")
    LiveData<SchemeEntity> findByIdAsync(final int id);

    @Override
    @Query("SELECT * FROM tblSchemes WHERE id = :id LIMIT 1")
    SchemeEntity findByIdSync(final int id);

    @Override
    @Query("SELECT id, name FROM tblSchemes WHERE id = :id LIMIT 1")
    LiveData<SchemeEntity.Minimal> findByIdAsyncMinimal(final int id);

    @Override
    @Query("SELECT id, name FROM tblSchemes WHERE id = :id LIMIT 1")
    SchemeEntity.Minimal findByIdSyncMinimal(final int id);

    // getAll
    @Override
    @Query("SELECT * FROM tblSchemes ORDER BY id ASC")
    LiveData<List<SchemeEntity>> getAllAsync();

    @Override
    @Query("SELECT * FROM tblSchemes ORDER BY id ASC")
    List<SchemeEntity> getAllSync();

    @Override
    @Query("SELECT id, name FROM tblSchemes ORDER BY id ASC")
    LiveData<List<SchemeEntity.Minimal>> getAllAsyncMinimal();

    @Override
    @Query("SELECT id, name FROM tblSchemes ORDER BY id ASC")
    List<SchemeEntity.Minimal> getAllSyncMinimal();

    // getAllEnabled
    @Override
    @Query("SELECT * FROM tblSchemes WHERE isEnabled = 1 ORDER BY id ASC")
    LiveData<List<SchemeEntity>> getAllEnabledAsync();

    @Override
    @Query("SELECT * FROM tblSchemes WHERE isEnabled = 1 ORDER BY id ASC")
    List<SchemeEntity> getAllEnabledSync();

    @Override
    @Query("SELECT id, name FROM tblSchemes WHERE isEnabled = 1 ORDER BY id ASC")
    LiveData<List<SchemeEntity.Minimal>> getAllEnabledAsyncMinimal();

    @Override
    @Query("SELECT id, name FROM tblSchemes WHERE isEnabled = 1 ORDER BY id ASC")
    List<SchemeEntity.Minimal> getAllEnabledSyncMinimal();

    // getAllBySetId
    @Override
    @Query("SELECT * FROM tblSchemes WHERE setId = :setId ORDER BY id ASC")
    LiveData<List<SchemeEntity>> getAllBySetIdAsync(final int setId);

    @Override
    @Query("SELECT * FROM tblSchemes WHERE setId = :setId ORDER BY id ASC")
    List<SchemeEntity> getAllBySetIdSync(final int setId);

    @Override
    @Query("SELECT id, name FROM tblSchemes WHERE setId = :setId ORDER BY id ASC")
    LiveData<List<SchemeEntity.Minimal>> getAllBySetIdAsyncMinimal(final int setId);

    @Override
    @Query("SELECT id, name FROM tblSchemes WHERE setId = :setId ORDER BY id ASC")
    List<SchemeEntity.Minimal> getAllBySetIdSyncMinimal(final int setId);
}
