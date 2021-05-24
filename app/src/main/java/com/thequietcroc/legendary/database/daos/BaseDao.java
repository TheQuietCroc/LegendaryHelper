package com.thequietcroc.legendary.database.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

public interface BaseDao<T> {

    LiveData<T> findByName(final String name);

    LiveData<T> findById(final int id);

    LiveData<List<T>> getAllUnfiltered();

    LiveData<List<T>> getAllFiltered();

    LiveData<List<String>> getAllNames();

    LiveData<List<String>> getAllFilteredNames();

    @Insert(onConflict = REPLACE)
    void insert(final T entry);

    @Insert(onConflict = REPLACE)
    void insert(final T[] entries);

    @Delete
    void delete(final T entry);

    @Delete
    void delete(final T[] entry);
}
