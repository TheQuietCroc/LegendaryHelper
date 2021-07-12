package com.thequietcroc.legendary.database.daos.gamecomponents;

import androidx.room.Dao;

import com.thequietcroc.legendary.database.daos.BaseDao;
import com.thequietcroc.legendary.database.entities.gamecomponents.BaseGameComponentEntity;

import java.util.List;

@Dao
public interface BaseGameComponentDao<T extends BaseGameComponentEntity> extends BaseDao<T> {

    List<T> getAllEnabled();

    List<T> getAllCustom();
}
