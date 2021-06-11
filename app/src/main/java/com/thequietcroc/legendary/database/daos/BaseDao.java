package com.thequietcroc.legendary.database.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;

import com.thequietcroc.legendary.database.entities.BaseEntity;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

public interface BaseDao<T extends BaseEntity, M extends BaseEntity.Minimal> {

    // findByName
    LiveData<T> findByNameAsync(final String name);

    T findByNameSync(final String name);

    LiveData<M> findByNameAsyncMinimal(final String name);

    M findByNameSyncMinimal(final String name);

    // findById
    LiveData<T> findByIdAsync(final int id);

    T findByIdSync(final int id);

    LiveData<M> findByIdAsyncMinimal(final int id);

    M findByIdSyncMinimal(final int id);

    // getAll
    LiveData<List<T>> getAllAsync();

    List<T> getAllSync();

    LiveData<List<M>> getAllAsyncMinimal();

    List<M> getAllSyncMinimal();

    @Insert(onConflict = REPLACE)
    void insert(final T entry);

    @Insert(onConflict = REPLACE)
    void insert(final T[] entries);

    @Insert(onConflict = REPLACE)
    void insert(final List<T> entries);

    @Delete
    void delete(final T entry);

    @Delete
    void delete(final T[] entries);

    @Delete
    void delete(final List<T> entries);
}
