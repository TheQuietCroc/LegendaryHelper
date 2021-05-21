package com.thequietcroc.legendary.database.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface BaseDao<T> {

    @Insert(onConflict = REPLACE)
    void insert(final T entry);

    @Insert(onConflict = REPLACE)
    void insert(final T[] entries);

    @Delete
    void delete(final T entry);

    @Delete
    void delete(final T[] entry);
}
