package com.thequietcroc.legendary.database.entities.gamecomponents.cards;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

import com.thequietcroc.legendary.database.entities.gamecomponents.GameSetEntity;
import com.thequietcroc.legendary.models.gamecomponents.cards.Hero;

import static androidx.room.ForeignKey.CASCADE;
import static androidx.room.ForeignKey.SET_DEFAULT;

@Entity(
        tableName = "tblHeroes",
        foreignKeys = {
                @ForeignKey(
                        entity = GameSetEntity.class,
                        parentColumns = "id",
                        childColumns = "setId",
                        onUpdate = CASCADE,
                        onDelete = SET_DEFAULT
                )
        },
        indices = {
                @Index(
                        name = "heroIndex",
                        value = "name"
                )
        }
)
public class HeroEntity extends BaseCardEntity {

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
    
    public HeroEntity() {
        super();
    }
    
    public HeroEntity(final Hero hero) {
        super(hero);
        
        setHasCovert(hero.hasCovert());
        setHasInstinct(hero.hasInstinct());
        setHasRanged(hero.hasRanged());
        setHasStrength(hero.hasStrength());
        setHasTech(hero.hasTech());
        setHasGun(hero.hasGun());
        setHasFlavorText(hero.hasFlavorText());
    }

    public boolean hasCovert() {
        return hasCovert;
    }

    public void setHasCovert(boolean hasCovert) {
        this.hasCovert = hasCovert;
    }

    public boolean hasInstinct() {
        return hasInstinct;
    }

    public void setHasInstinct(boolean hasInstinct) {
        this.hasInstinct = hasInstinct;
    }

    public boolean hasRanged() {
        return hasRanged;
    }

    public void setHasRanged(boolean hasRanged) {
        this.hasRanged = hasRanged;
    }

    public boolean hasStrength() {
        return hasStrength;
    }

    public void setHasStrength(boolean hasStrength) {
        this.hasStrength = hasStrength;
    }

    public boolean hasTech() {
        return hasTech;
    }

    public void setHasTech(boolean hasTech) {
        this.hasTech = hasTech;
    }

    public boolean hasGun() {
        return hasGun;
    }

    public void setHasGun(boolean hasGun) {
        this.hasGun = hasGun;
    }

    public boolean hasFlavorText() {
        return hasFlavorText;
    }

    public void setHasFlavorText(boolean hasFlavorText) {
        this.hasFlavorText = hasFlavorText;
    }
    
    @Override
    public Hero toModel() {
        return new Hero(this);
    }

    @Override
    public boolean equals(final Object o) {

        if (!(o instanceof HeroEntity)) {
            return false;
        }

        return super.equals(o);
    }

    public static class Minimal extends BaseCardEntity.Minimal {

        @Override
        public boolean equals(final Object o) {
            if (o == this) {
                return true;
            }

            if (!(o instanceof Minimal)) {
                return false;
            }

            return super.equals(o);
        }
    }
}
