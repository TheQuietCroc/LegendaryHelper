package com.thequietcroc.legendary.database.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;

public abstract class BaseItem {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    private String name;

    @ColumnInfo(defaultValue = "0")
    private boolean isCustom;

    @ColumnInfo(defaultValue = "1")
    private boolean isEnabled;

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

    public boolean isCustom() {
        return isCustom;
    }

    public void setCustom(boolean custom) {
        isCustom = custom;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof BaseItem)) {
            return false;
        }

        final BaseItem b = (BaseItem) o;

        return id == b.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return name;
    }
}
