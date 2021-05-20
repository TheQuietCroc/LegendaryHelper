package com.thequietcroc.legendary.database.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.thequietcroc.legendary.database.entities.Hero;

import java.util.List;

@Dao
public interface HeroDao {

    @Query("SELECT * FROM tblHeroes")
    LiveData<List<Hero>> getAll();

    @Query("SELECT * FROM tblHeroes WHERE name LIKE :name LIMIT 1")
    LiveData<Hero> findByName(final String name);

    @Query("SELECT * FROM tblHeroes WHERE setId = :setId")
    LiveData<List<Hero>> findAllInSet(final int setId);

    @Query("SELECT * FROM tblHeroes WHERE isCustom = 1")
    LiveData<List<Hero>> getAllCustom();

    @Query("SELECT * FROM tblHeroes WHERE isCustom = 0")
    LiveData<List<Hero>> getAllStock();


}
