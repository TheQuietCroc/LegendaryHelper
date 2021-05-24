package com.thequietcroc.legendary.database.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;

import com.thequietcroc.legendary.database.LegendaryDatabase;
import com.thequietcroc.legendary.database.daos.CardDao;

public abstract class BaseCard {

    @PrimaryKey
    public int id;

    @NonNull
    public String name;

    public int setId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public int getSetId() {
        return setId;
    }

    public void setSetId(int setId) {
        this.setId = setId;
    }

    public boolean isCustom() {
        return isCustom;
    }

    public void setCustom(boolean custom) {
        isCustom = custom;
    }

    public boolean isExcluded() {
        return isExcluded;
    }

    public void setExcluded(boolean excluded) {
        isExcluded = excluded;
    }

    @ColumnInfo(defaultValue = "0")
    public boolean isCustom;

    @ColumnInfo(defaultValue = "0")
    public boolean isExcluded;

    public abstract CardDao<? extends BaseCard> getDao(final LegendaryDatabase db);

    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof BaseCard)) {
            return false;
        }

        final BaseCard b = (BaseCard) o;

        return id == b.id
                && name.contentEquals(b.name)
                && isCustom == isCustom()
                && isExcluded == isExcluded();
    }

    @Override
    public int hashCode() {
        return id;
    }
}
