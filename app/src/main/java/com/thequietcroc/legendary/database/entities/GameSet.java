package com.thequietcroc.legendary.database.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "tblGameSets",
        indices = {
                @Index(
                        name = "gameSetIndex",
                        value = "name"
                )
        }
)
public class GameSet {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @NonNull
    public String name;

    @ColumnInfo(defaultValue = "0")
    public boolean isCustom;

    @ColumnInfo(defaultValue = "0")
    public boolean isExcluded;
}
