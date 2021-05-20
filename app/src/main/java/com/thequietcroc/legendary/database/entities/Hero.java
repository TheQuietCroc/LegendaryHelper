package com.thequietcroc.legendary.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

import static androidx.room.ForeignKey.CASCADE;

@Entity(
        tableName = "tblHeroes",
        foreignKeys = {
                @ForeignKey(
                        entity = GameSet.class,
                        parentColumns = "id",
                        childColumns = "setId",
                        onUpdate = CASCADE,
                        onDelete = CASCADE
                )
        },
        indices = {
                @Index(
                        name = "heroIndex",
                        value = "name"
                )
        }
)
public class Hero extends BaseCard {

    @ColumnInfo(name = "covert", defaultValue = "0")
    public boolean hasCovert;

    @ColumnInfo(name = "instinct", defaultValue = "0")
    public boolean hasInstinct;

    @ColumnInfo(name = "ranged", defaultValue = "0")
    public boolean hasRanged;

    @ColumnInfo(name = "strength", defaultValue = "0")
    public boolean hasStrength;

    @ColumnInfo(name = "tech", defaultValue = "0")
    public boolean hasTech;

    @ColumnInfo(name = "gun", defaultValue = "0")
    public boolean hasGun;

    @ColumnInfo(name = "flavor", defaultValue = "0")
    public boolean hasFlavorText;
}
