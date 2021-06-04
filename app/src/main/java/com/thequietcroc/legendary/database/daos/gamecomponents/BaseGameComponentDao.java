package com.thequietcroc.legendary.database.daos.gamecomponents;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;

import com.thequietcroc.legendary.database.daos.BaseDao;
import com.thequietcroc.legendary.database.entities.gamecomponents.BaseGameComponentEntity;

import java.util.List;

@Dao
public interface BaseGameComponentDao<T extends BaseGameComponentEntity,
        M extends BaseGameComponentEntity.Minimal> extends BaseDao<T, M> {

    // findByName
    @Override
    LiveData<T> findByNameAsync(final String name);

    @Override
    T findByNameSync(final String name);

    @Override
    LiveData<M> findByNameAsyncMinimal(final String name);

    @Override
    M findByNameSyncMinimal(final String name);

    // findById
    @Override
    LiveData<T> findByIdAsync(final int id);

    @Override
    T findByIdSync(final int id);

    @Override
    LiveData<M> findByIdAsyncMinimal(final int id);

    @Override
    M findByIdSyncMinimal(final int id);

    // getAll
    @Override
    LiveData<List<T>> getAllAsync();

    @Override
    List<T> getAllSync();

    @Override
    LiveData<List<M>> getAllAsyncMinimal();

    @Override
    List<M> getAllSyncMinimal();

    // getAllEnabled
    LiveData<List<T>> getAllEnabledAsync();

    List<T> getAllEnabledSync();

    LiveData<List<M>> getAllEnabledAsyncMinimal();

    List<M> getAllEnabledSyncMinimal();
}
