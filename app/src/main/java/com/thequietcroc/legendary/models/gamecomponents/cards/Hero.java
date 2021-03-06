package com.thequietcroc.legendary.models.gamecomponents.cards;

import com.thequietcroc.legendary.database.LegendaryDatabase;
import com.thequietcroc.legendary.database.entities.gamecomponents.cards.HeroEntity;

public class Hero extends BaseCard {

    public static Hero NONE;

    static {
        NONE = new Hero("None");
        NONE.setId(0);
    }

    private boolean hasCovert = false;
    private boolean hasInstinct = false;
    private boolean hasRanged = false;
    private boolean hasStrength = false;
    private boolean hasTech = false;
    private boolean hasGun = false;
    private boolean hasFlavorText = false;

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

    public void dbSave() {
        dbSave(LegendaryDatabase.getInstance().heroDao(), toEntity());
    }

    public void dbDelete() {
        dbDelete(LegendaryDatabase.getInstance().heroDao(), toEntity());
    }

    @Override
    public boolean equals(final Object o) {

        if (!(o instanceof Hero)) {
            return false;
        }

        return super.equals(o);
    }
}
