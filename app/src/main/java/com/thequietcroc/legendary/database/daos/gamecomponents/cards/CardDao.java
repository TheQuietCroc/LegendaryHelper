package com.thequietcroc.legendary.database.daos;

import androidx.lifecycle.LiveData;

import java.util.List;

public interface CardDao<T> extends GameItemDao<T> {

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
    @Override
    LiveData<List<T>> getAllEnabledAsync();

    @Override
    List<T> getAllEnabledSync();

    // getAllBySetId
    LiveData<List<T>> getAllBySetIdAsync(final int setId);

    List<T> getAllBySetIdSync(final int setId);
}
