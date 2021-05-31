package com.thequietcroc.legendary.database.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

public interface BaseDao<T> {

    // findByName
    LiveData<T> findByNameAsync(final String name);

    T findByNameSync(final String name);

    // findById
    LiveData<T> findByIdAsync(final int id);

    T findByIdSync(final int id);

    // getAll
    LiveData<List<T>> getAllAsync();

    List<T> getAllSync();

    @Insert(onConflict = REPLACE)
    void insert(final T entry);

    @Insert(onConflict = REPLACE)
    void insert(final T[] entries);

    @Delete
    void delete(final T entry);

    @Delete
    void delete(final T[] entry);
}
