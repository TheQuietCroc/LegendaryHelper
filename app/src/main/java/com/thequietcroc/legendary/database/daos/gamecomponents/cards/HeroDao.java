package com.thequietcroc.legendary.database.daos.gamecomponents.cards;

import androidx.room.Dao;
import androidx.room.Query;

import com.thequietcroc.legendary.database.entities.gamecomponents.cards.HeroEntity;

import java.util.List;

@Dao
public interface HeroDao extends BaseCardDao<HeroEntity> {

    @Override
    @Query("SELECT * FROM tblHeroes WHERE name LIKE :name LIMIT 1")
    HeroEntity findByName(final String name);

    @Override
    @Query("SELECT * FROM tblHeroes WHERE id = :id LIMIT 1")
    HeroEntity findById(final int id);

    @Override
    @Query("SELECT * FROM tblHeroes WHERE id > 0 ORDER BY id ASC")
    List<HeroEntity> getAll();

    @Override
    @Query("SELECT * FROM tblHeroes WHERE isEnabled = 1 AND id > 0 ORDER BY id ASC")
    List<HeroEntity> getAllEnabled();

    @Override
    @Query("SELECT * FROM tblHeroes WHERE setId = :setId AND id > 0 ORDER BY id ASC")
    List<HeroEntity> getAllBySetId(final int setId);

    @Override
    @Query("SELECT * FROM tblHeroes WHERE isCustom = 1 AND id > 0 ORDER BY id ASC")
    List<HeroEntity> getAllCustom();
}
