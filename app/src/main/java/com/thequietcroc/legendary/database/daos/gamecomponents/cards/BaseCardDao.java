package com.thequietcroc.legendary.database.daos.gamecomponents.cards;

import com.thequietcroc.legendary.database.daos.gamecomponents.BaseGameComponentDao;
import com.thequietcroc.legendary.database.entities.gamecomponents.cards.BaseCardEntity;

import java.util.List;

public interface BaseCardDao<T extends BaseCardEntity> extends BaseGameComponentDao<T> {

    List<T> getAllBySetId(final int setId);
}
