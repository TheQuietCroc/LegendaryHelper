package com.thequietcroc.legendary.database.daos;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

import com.thequietcroc.legendary.database.entities.BaseEntity;

import java.util.List;

public interface BaseDao<T extends BaseEntity> {

    T findByName(final String name);

    T findById(final int id);

    List<T> getAll();

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
