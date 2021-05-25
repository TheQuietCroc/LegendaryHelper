package com.thequietcroc.legendary.database.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

public interface BaseDao<T> {

    LiveData<T> findByNameAsync(final String name);

    LiveData<T> findByIdAsync(final int id);

    LiveData<List<T>> getAllUnfilteredAsync();

    LiveData<List<T>> getAllFilteredAsync();

    T findByNameSync(final String name);

    T findByIdSync(final int id);

    List<T> getAllUnfilteredSync();

    List<T> getAllFilteredSync();

    @Insert(onConflict = REPLACE)
    void insert(final T entry);

    @Insert(onConflict = REPLACE)
    void insert(final T[] entries);

    @Delete
    void delete(final T entry);

    @Delete
    void delete(final T[] entry);
}
