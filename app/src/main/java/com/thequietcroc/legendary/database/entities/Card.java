package com.thequietcroc.legendary.database.entities;

import com.thequietcroc.legendary.database.LegendaryDatabase;
import com.thequietcroc.legendary.database.daos.CardDao;

public abstract class Card extends BaseItem {

    public int setId;

    public int getSetId() {
        return setId;
    }

    public void setSetId(int setId) {
        this.setId = setId;
    }

    @Override
    public abstract CardDao<? extends Card> getDao(final LegendaryDatabase db);

    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof Card)) {
            return false;
        }

        final Card b = (Card) o;

        return super.equals(o)
                && setId == b.setId;
    }
}
