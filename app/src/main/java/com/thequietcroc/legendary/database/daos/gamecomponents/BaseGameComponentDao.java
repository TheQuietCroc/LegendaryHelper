package com.thequietcroc.legendary.database.daos.gamecomponents;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;

import com.thequietcroc.legendary.database.daos.BaseDao;
import com.thequietcroc.legendary.database.entities.gamecomponents.BaseGameComponentEntity;

import java.util.List;

@Dao
public interface BaseGameComponentDao<T extends BaseGameComponentEntity> extends BaseDao<T> {

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

    // getAllCustom
    LiveData<List<T>> getAllCustomAsync();

    List<T> getAllCustomSync();
}
