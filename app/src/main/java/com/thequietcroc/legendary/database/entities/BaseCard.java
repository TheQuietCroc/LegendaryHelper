package com.thequietcroc.legendary.database.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;

public abstract class BaseCard {
    @PrimaryKey
    public int id;

    @NonNull
    public String name;

    public int setId;

    @ColumnInfo(defaultValue = "0")
    public boolean isCustom;

    @ColumnInfo(defaultValue = "0")
    public boolean isExcluded;
}
