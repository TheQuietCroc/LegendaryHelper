package com.thequietcroc.legendary.models.gamecomponents.cards;

import com.thequietcroc.legendary.database.entities.gamecomponents.cards.HeroEntity;

public class Hero extends BaseCard {

    public boolean hasCovert = false;
    public boolean hasInstinct = false;
    public boolean hasRanged = false;
    public boolean hasStrength = false;
    public boolean hasTech = false;
    public boolean hasGun = false;
    public boolean hasFlavorText = false;

    public Hero() {
        super();
    }

    public Hero(final HeroEntity heroEntity) {
        super(heroEntity);

        setHasCovert(heroEntity.hasCovert());
        setHasInstinct(heroEntity.hasInstinct());
        setHasRanged(heroEntity.hasRanged());
        setHasStrength(heroEntity.hasStrength());
        setHasTech(heroEntity.hasTech());
        setHasGun(heroEntity.hasGun());
        setHasFlavorText(heroEntity.hasFlavorText());
    }

    public Hero(final String name) {
        super(name);
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
    public HeroEntity toEntity() {
        return new HeroEntity(this);
    }

    @Override
    public boolean equals(final Object o) {

        if (!(o instanceof Hero)) {
            return false;
        }

        return super.equals(o);
    }
}
