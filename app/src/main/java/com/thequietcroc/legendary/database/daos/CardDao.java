package com.thequietcroc.legendary.database.daos;

import androidx.lifecycle.LiveData;

import java.util.List;

public interface CardDao<T> extends BaseDao<T> {

    LiveData<T> findByName(final String name);

    LiveData<T> findById(final int id);

    LiveData<List<T>> getAllUnfiltered();

    LiveData<List<T>> getAllFiltered();

    LiveData<List<String>> getAllNames();

    LiveData<List<String>> getAllFilteredNames();
}
