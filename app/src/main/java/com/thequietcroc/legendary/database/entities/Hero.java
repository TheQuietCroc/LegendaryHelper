package com.thequietcroc.legendary.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

import com.thequietcroc.legendary.database.LegendaryDatabase;
import com.thequietcroc.legendary.database.daos.CardDao;

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

    public boolean isHasCovert() {
        return hasCovert;
    }

    public void setHasCovert(boolean hasCovert) {
        this.hasCovert = hasCovert;
    }

    public boolean isHasInstinct() {
        return hasInstinct;
    }

    public void setHasInstinct(boolean hasInstinct) {
        this.hasInstinct = hasInstinct;
    }

    public boolean isHasRanged() {
        return hasRanged;
    }

    public void setHasRanged(boolean hasRanged) {
        this.hasRanged = hasRanged;
    }

    public boolean isHasStrength() {
        return hasStrength;
    }

    public void setHasStrength(boolean hasStrength) {
        this.hasStrength = hasStrength;
    }

    public boolean isHasTech() {
        return hasTech;
    }

    public void setHasTech(boolean hasTech) {
        this.hasTech = hasTech;
    }

    public boolean isHasGun() {
        return hasGun;
    }

    public void setHasGun(boolean hasGun) {
        this.hasGun = hasGun;
    }

    public boolean isHasFlavorText() {
        return hasFlavorText;
    }

    public void setHasFlavorText(boolean hasFlavorText) {
        this.hasFlavorText = hasFlavorText;
    }

    @Override
    public CardDao<Hero> getDao(final LegendaryDatabase db) {
        return db.heroDao();
    }

    @Override
    public boolean equals(final Object o) {
        if (super.equals(o)) {

            final Hero h = (Hero) o;

            return hasCovert == h.hasCovert
                    && hasInstinct == h.hasInstinct
                    && hasRanged == h.hasRanged
                    && hasStrength == h.hasStrength
                    && hasTech == h.hasTech
                    && hasGun == h.hasGun
                    && hasFlavorText == h.hasFlavorText;
        }

        return false;
    }
}
