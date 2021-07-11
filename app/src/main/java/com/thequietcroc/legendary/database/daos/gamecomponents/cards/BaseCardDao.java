package com.thequietcroc.legendary.database.daos.gamecomponents.cards;

import androidx.lifecycle.LiveData;

import com.thequietcroc.legendary.database.daos.gamecomponents.BaseGameComponentDao;
import com.thequietcroc.legendary.database.entities.gamecomponents.cards.BaseCardEntity;

import java.util.List;

public interface BaseCardDao<T extends BaseCardEntity> extends BaseGameComponentDao<T> {

    // getAllBySetId
    LiveData<List<T>> getAllBySetIdAsync(final int setId);

    List<T> getAllBySetIdSync(final int setId);
}
