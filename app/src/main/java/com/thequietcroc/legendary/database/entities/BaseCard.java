package com.thequietcroc.legendary.database.entities;

import androidx.room.ColumnInfo;

public abstract class BaseCard extends BaseItem {

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

        if (!(o instanceof BaseCard)) {
            return false;
        }

        return super.equals(o);
    }
}
