package com.thequietcroc.legendary.database.daos;

import androidx.lifecycle.LiveData;

import java.util.List;

public interface CardDao<T> extends BaseDao<T> {

    @Override
    LiveData<T> findByNameAsync(final String name);

    @Override
    LiveData<T> findByIdAsync(final int id);

    @Override
    LiveData<List<T>> getAllUnfilteredAsync();

    @Override
    LiveData<List<T>> getAllFilteredAsync();

    @Override
    T findByNameSync(final String name);

    @Override
    T findByIdSync(final int id);

    @Override
    List<T> getAllUnfilteredSync();

    @Override
    List<T> getAllFilteredSync();
}
