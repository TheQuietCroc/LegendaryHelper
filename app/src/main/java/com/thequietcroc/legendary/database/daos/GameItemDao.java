package com.thequietcroc.legendary.database.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;

import java.util.List;

@Dao
public interface GameItemDao<T> extends BaseDao<T> {

    // findByName
    @Override
    LiveData<T> findByNameAsync(final String name);

    @Override
    T findByNameSync(final String name);

    // findById
    @Override
    LiveData<T> findByIdAsync(final int id);

    @Override
    T findByIdSync(final int id);

    // getAll
    @Override
    LiveData<List<T>> getAllAsync();

    @Override
    List<T> getAllSync();

    // getAllEnabled
    LiveData<List<T>> getAllEnabledAsync();

    List<T> getAllEnabledSync();
}
