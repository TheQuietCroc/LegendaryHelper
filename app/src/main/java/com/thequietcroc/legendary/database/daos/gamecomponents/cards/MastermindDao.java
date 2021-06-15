package com.thequietcroc.legendary.database.daos.gamecomponents.cards;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.thequietcroc.legendary.database.entities.gamecomponents.cards.MastermindEntity;

import java.util.List;

@Dao
public interface MastermindDao extends BaseCardDao<MastermindEntity, MastermindEntity.Minimal> {

    // findByName
    @Override
    @Query("SELECT * FROM tblMasterminds WHERE name LIKE :name LIMIT 1")
    LiveData<MastermindEntity> findByNameAsync(final String name);

    @Override
    @Query("SELECT * FROM tblMasterminds WHERE name LIKE :name LIMIT 1")
    MastermindEntity findByNameSync(final String name);

    @Override
    @Query("SELECT id, name, villainId, henchmenId FROM tblMasterminds WHERE name LIKE :name "
            + "LIMIT 1")
    LiveData<MastermindEntity.Minimal> findByNameAsyncMinimal(final String name);

    @Override
    @Query("SELECT id, name, villainId, henchmenId FROM tblMasterminds WHERE name LIKE :name "
            + "LIMIT 1")
    MastermindEntity.Minimal findByNameSyncMinimal(final String name);

    // findById
    @Override
    @Query("SELECT * FROM tblMasterminds WHERE id = :id LIMIT 1")
    LiveData<MastermindEntity> findByIdAsync(final int id);

    @Override
    @Query("SELECT * FROM tblMasterminds WHERE id = :id LIMIT 1")
    MastermindEntity findByIdSync(final int id);

    @Override
    @Query("SELECT id, name, villainId, henchmenId FROM tblMasterminds WHERE id = :id LIMIT 1")
    LiveData<MastermindEntity.Minimal> findByIdAsyncMinimal(final int id);

    @Override
    @Query("SELECT id, name, villainId, henchmenId FROM tblMasterminds WHERE id = :id LIMIT 1")
    MastermindEntity.Minimal findByIdSyncMinimal(final int id);

    // getAll
    @Override
    @Query("SELECT * FROM tblMasterminds WHERE id > 0 ORDER BY id ASC")
    LiveData<List<MastermindEntity>> getAllAsync();

    @Override
    @Query("SELECT * FROM tblMasterminds WHERE id > 0 ORDER BY id ASC")
    List<MastermindEntity> getAllSync();

    @Override
    @Query("SELECT id, name, villainId, henchmenId FROM tblMasterminds WHERE id > 0 ORDER BY id "
            + "ASC")
    LiveData<List<MastermindEntity.Minimal>> getAllAsyncMinimal();

    @Override
    @Query("SELECT id, name, villainId, henchmenId FROM tblMasterminds WHERE id > 0 ORDER BY id "
            + "ASC")
    List<MastermindEntity.Minimal> getAllSyncMinimal();

    // getAllEnabled
    @Override
    @Query("SELECT * FROM tblMasterminds WHERE isEnabled = 1 AND id > 0 ORDER BY id ASC")
    LiveData<List<MastermindEntity>> getAllEnabledAsync();

    @Override
    @Query("SELECT * FROM tblMasterminds WHERE isEnabled = 1 AND id > 0 ORDER BY id ASC")
    List<MastermindEntity> getAllEnabledSync();

    @Override
    @Query("SELECT id, name, villainId, henchmenId FROM tblMasterminds WHERE isEnabled = 1 AND id"
            + " > 0 ORDER BY id ASC")
    LiveData<List<MastermindEntity.Minimal>> getAllEnabledAsyncMinimal();

    @Override
    @Query("SELECT id, name, villainId, henchmenId FROM tblMasterminds WHERE isEnabled = 1 AND id"
            + " > 0 ORDER BY id ASC")
    List<MastermindEntity.Minimal> getAllEnabledSyncMinimal();

    // getAllBySetId
    @Override
    @Query("SELECT * FROM tblMasterminds WHERE setId = :setId AND id > 0 ORDER BY id ASC")
    LiveData<List<MastermindEntity>> getAllBySetIdAsync(final int setId);

    @Override
    @Query("SELECT * FROM tblMasterminds WHERE setId = :setId AND id > 0 ORDER BY id ASC")
    List<MastermindEntity> getAllBySetIdSync(final int setId);

    @Override
    @Query("SELECT id, name, villainId, henchmenId FROM tblMasterminds WHERE setId = :setId AND "
            + "id > 0 ORDER BY id ASC")
    LiveData<List<MastermindEntity.Minimal>> getAllBySetIdAsyncMinimal(final int setId);

    @Override
    @Query("SELECT id, name, villainId, henchmenId FROM tblMasterminds WHERE setId = :setId AND "
            + "id > 0 ORDER BY id ASC")
    List<MastermindEntity.Minimal> getAllBySetIdSyncMinimal(final int setId);

    @Query("SELECT * FROM tblMasterminds WHERE henchmenId = :henchmenId")
    List<MastermindEntity> findAllByAlwaysLeadsHenchmenId(final int henchmenId);

    @Query("SELECT * FROM tblMasterminds WHERE villainId = :villainId")
    List<MastermindEntity> findAllByAlwaysLeadsVillainId(final int villainId);
}
