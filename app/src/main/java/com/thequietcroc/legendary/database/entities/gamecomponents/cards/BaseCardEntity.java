package com.thequietcroc.legendary.database.entities.gamecomponents.cards;

import androidx.room.ColumnInfo;

import com.thequietcroc.legendary.database.entities.gamecomponents.BaseGameComponentEntity;
import com.thequietcroc.legendary.models.gamecomponents.cards.BaseCard;

public abstract class BaseCardEntity extends BaseGameComponentEntity {

    @ColumnInfo(name = "setId", defaultValue = "0")
    private int gameSetId;

    public BaseCardEntity() {
        super();
    }

    public BaseCardEntity(final BaseCard baseCard) {
        super(baseCard);

        this.gameSetId = baseCard.getGameSet().getId();
    }

    public int getGameSetId() {
        return gameSetId;
    }

    public void setGameSetId(int gameSetId) {
        this.gameSetId = gameSetId;
    }

    @Override
    public abstract BaseCard toModel();

    @Override
    public boolean equals(final Object o) {

        if (!(o instanceof BaseCardEntity)) {
            return false;
        }

        return super.equals(o);
    }

    public static class Minimal extends BaseGameComponentEntity.Minimal {
    }
}
