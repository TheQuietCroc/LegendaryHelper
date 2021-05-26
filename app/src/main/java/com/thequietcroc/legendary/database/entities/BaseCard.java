package com.thequietcroc.legendary.database.entities;

import androidx.room.ColumnInfo;

import com.thequietcroc.legendary.database.LegendaryDatabase;
import com.thequietcroc.legendary.database.daos.CardDao;

public abstract class BaseCard extends BaseItem {

    @ColumnInfo(defaultValue = "0")
    public int setId;

    public int getSetId() {
        return setId;
    }

    public void setSetId(int setId) {
        this.setId = setId;
    }

    public abstract CardDao<? extends BaseCard> getDao(final LegendaryDatabase db);

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
