package com.thequietcroc.legendary.database.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

import com.thequietcroc.legendary.database.entities.BaseEntity;

import java.util.List;

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

    // update
    @Update
    void update(final T entity);

    @Update
    void update(final T[] entities);

    @Update
    void update(final List<T> entities);

    // insert
    @Insert
    long insert(final T entity);

    @Insert
    long[] insert(final T[] entities);

    @Insert
    List<Long> insert(final List<T> entities);

    // delete
    @Delete
    void delete(final T entity);

    @Delete
    void delete(final T[] entities);

    @Delete
    void delete(final List<T> entities);
}
