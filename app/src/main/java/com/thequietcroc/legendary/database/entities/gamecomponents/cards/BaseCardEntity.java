package com.thequietcroc.legendary.database.entities.gamecomponents.cards;

import androidx.room.ColumnInfo;

import com.thequietcroc.legendary.database.entities.gamecomponents.BaseGameComponentEntity;

public abstract class BaseCardEntity extends BaseGameComponentEntity {

    @ColumnInfo(name = "setId", defaultValue = "0")
    private int gameSetId;

    public int getGameSetId() {
        return gameSetId;
    }

    public void setGameSetId(int gameSetId) {
        this.gameSetId = gameSetId;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof BaseCardEntity)) {
            return false;
        }

        return super.equals(o);
    }
}
