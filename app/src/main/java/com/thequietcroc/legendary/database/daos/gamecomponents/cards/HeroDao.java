package com.thequietcroc.legendary.database.daos.gamecomponents.cards;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.thequietcroc.legendary.database.entities.gamecomponents.cards.HeroEntity;

import java.util.List;

@Dao
public interface HeroDao extends BaseCardDao<HeroEntity> {

    // findByName
    @Override
    @Query("SELECT * FROM tblHeroes WHERE name LIKE :name LIMIT 1")
    LiveData<HeroEntity> findByNameAsync(final String name);

    @Override
    @Query("SELECT * FROM tblHeroes WHERE name LIKE :name LIMIT 1")
    HeroEntity findByNameSync(final String name);

    // findById
    @Override
    @Query("SELECT * FROM tblHeroes WHERE id = :id LIMIT 1")
    LiveData<HeroEntity> findByIdAsync(final int id);

    @Override
    @Query("SELECT * FROM tblHeroes WHERE id = :id LIMIT 1")
    HeroEntity findByIdSync(final int id);

    // getAll
    @Override
    @Query("SELECT * FROM tblHeroes WHERE id > 0 ORDER BY id ASC")
    LiveData<List<HeroEntity>> getAllAsync();

    @Override
    @Query("SELECT * FROM tblHeroes WHERE id > 0 ORDER BY id ASC")
    List<HeroEntity> getAllSync();

    // getAllEnabled
    @Override
    @Query("SELECT * FROM tblHeroes WHERE isEnabled = 1 AND id > 0 ORDER BY id ASC")
    LiveData<List<HeroEntity>> getAllEnabledAsync();

    @Override
    @Query("SELECT * FROM tblHeroes WHERE isEnabled = 1 AND id > 0 ORDER BY id ASC")
    List<HeroEntity> getAllEnabledSync();

    // getAllBySetId
    @Override
    @Query("SELECT * FROM tblHeroes WHERE setId = :setId AND id > 0 ORDER BY id ASC")
    LiveData<List<HeroEntity>> getAllBySetIdAsync(final int setId);

    @Override
    @Query("SELECT * FROM tblHeroes WHERE setId = :setId AND id > 0 ORDER BY id ASC")
    List<HeroEntity> getAllBySetIdSync(final int setId);

    // getAllCustom
    @Override
    @Query("SELECT * FROM tblHeroes WHERE isCustom = 1 AND id > 0 ORDER BY id ASC")
    LiveData<List<HeroEntity>> getAllCustomAsync();

    @Override
    @Query("SELECT * FROM tblHeroes WHERE isCustom = 1 AND id > 0 ORDER BY id ASC")
    List<HeroEntity> getAllCustomSync();
}
