package com.thequietcroc.legendary.database.daos.gamecomponents.cards;

import androidx.lifecycle.LiveData;

import com.thequietcroc.legendary.database.daos.gamecomponents.BaseGameComponentDao;
import com.thequietcroc.legendary.database.entities.gamecomponents.cards.BaseCardEntity;

import java.util.List;

public interface BaseCardDao<T extends BaseCardEntity, M extends BaseCardEntity.Minimal> extends BaseGameComponentDao<T, M> {

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
    @Override
    LiveData<List<T>> getAllEnabledAsync();

    @Override
    List<T> getAllEnabledSync();

    LiveData<List<M>> getAllEnabledAsyncMinimal();

    List<M> getAllEnabledSyncMinimal();

    // getAllBySetId
    LiveData<List<T>> getAllBySetIdAsync(final int setId);

    List<T> getAllBySetIdSync(final int setId);

    LiveData<List<M>> getAllBySetIdAsyncMinimal(final int setID);

    List<M> getAllBySetIdSyncMinimal(final int setId);
}
