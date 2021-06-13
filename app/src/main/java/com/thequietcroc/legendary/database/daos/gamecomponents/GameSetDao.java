package com.thequietcroc.legendary.database.daos.gamecomponents;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.thequietcroc.legendary.database.entities.gamecomponents.GameSetEntity;
import com.thequietcroc.legendary.database.entities.gamecomponents.cards.HenchmenEntity;
import com.thequietcroc.legendary.database.entities.gamecomponents.cards.HeroEntity;
import com.thequietcroc.legendary.database.entities.gamecomponents.cards.MastermindEntity;
import com.thequietcroc.legendary.database.entities.gamecomponents.cards.SchemeEntity;
import com.thequietcroc.legendary.database.entities.gamecomponents.cards.VillainsEntity;

import java.util.List;

@Dao
public interface GameSetDao extends BaseGameComponentDao<GameSetEntity, GameSetEntity.Minimal> {

    // findByName
    @Override
    @Query("SELECT * FROM tblGameSets WHERE name LIKE :name LIMIT 1")
    LiveData<GameSetEntity> findByNameAsync(final String name);

    @Override
    @Query("SELECT * FROM tblGameSets WHERE name LIKE :name LIMIT 1")
    GameSetEntity findByNameSync(final String name);

    @Override
    @Query("SELECT id, name FROM tblGameSets WHERE name = :name LIMIT 1")
    LiveData<GameSetEntity.Minimal> findByNameAsyncMinimal(final String name);

    @Override
    @Query("SELECT id, name FROM tblGameSets WHERE name = :name LIMIT 1")
    GameSetEntity.Minimal findByNameSyncMinimal(final String name);

    // findById
    @Override
    @Query("SELECT * FROM tblGameSets WHERE id = :id LIMIT 1")
    LiveData<GameSetEntity> findByIdAsync(final int id);

    @Override
    @Query("SELECT * FROM tblGameSets WHERE id = :id LIMIT 1")
    GameSetEntity findByIdSync(final int id);

    @Override
    @Query("SELECT id, name FROM tblGameSets WHERE id = :id LIMIT 1")
    LiveData<GameSetEntity.Minimal> findByIdAsyncMinimal(final int id);

    @Override
    @Query("SELECT id, name FROM tblGameSets WHERE id = :id LIMIT 1")
    GameSetEntity.Minimal findByIdSyncMinimal(final int id);

    // getAll
    @Override
    @Query("SELECT * FROM tblGameSets WHERE id > 0 ORDER BY id ASC")
    LiveData<List<GameSetEntity>> getAllAsync();

    @Override
    @Query("SELECT * FROM tblGameSets WHERE id > 0 ORDER BY id ASC")
    List<GameSetEntity> getAllSync();

    @Override
    @Query("SELECT id, name FROM tblGameSets WHERE id > 0 ORDER BY id DESC")
    LiveData<List<GameSetEntity.Minimal>> getAllAsyncMinimal();

    @Override
    @Query("SELECT id, name FROM tblGameSets WHERE id > 0 ORDER BY id DESC")
    List<GameSetEntity.Minimal> getAllSyncMinimal();

    // getAllEnabled
    @Override
    @Query("SELECT * FROM tblGameSets WHERE isEnabled = 1 AND id > 0 ORDER BY id ASC")
    LiveData<List<GameSetEntity>> getAllEnabledAsync();

    @Override
    @Query("SELECT * FROM tblGameSets WHERE isEnabled = 1 AND id > 0 ORDER BY id ASC")
    List<GameSetEntity> getAllEnabledSync();

    @Override
    @Query("SELECT id, name FROM tblGameSets WHERE isEnabled = 1 AND id > 0 ORDER BY id ASC")
    LiveData<List<GameSetEntity.Minimal>> getAllEnabledAsyncMinimal();

    @Override
    @Query("SELECT id, name FROM tblGameSets WHERE isEnabled = 1 AND id > 0 ORDER BY id ASC")
    List<GameSetEntity.Minimal> getAllEnabledSyncMinimal();

    @Query("SELECT * FROM tblMasterminds WHERE setId = :setId")
    List<MastermindEntity> getAllMastermindsInSet(final int setId);

    @Query("SELECT * FROM tblSchemes WHERE setId = :setId")
    List<SchemeEntity> getAllSchemesInSet(final int setId);

    @Query("SELECT * FROM tblVillains WHERE setId = :setId")
    List<VillainsEntity> getAllVillainsInSet(final int setId);

    @Query("SELECT * FROM tblHenchmen WHERE setId = :setId")
    List<HenchmenEntity> getAllHenchmenInSet(final int setId);

    @Query("SELECT * FROM tblHeroes WHERE setId = :setId")
    List<HeroEntity> getAllHeroesInSet(final int setId);
}
