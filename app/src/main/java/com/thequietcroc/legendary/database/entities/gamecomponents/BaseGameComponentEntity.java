package com.thequietcroc.legendary.database.entities.gamecomponents;

import androidx.room.ColumnInfo;

import com.thequietcroc.legendary.database.entities.BaseEntity;
import com.thequietcroc.legendary.models.gamecomponents.BaseGameComponent;

public abstract class BaseGameComponentEntity extends BaseEntity {

    @ColumnInfo(defaultValue = "0")
    private boolean isCustom;

    @ColumnInfo(defaultValue = "1")
    private boolean isEnabled;

    public BaseGameComponentEntity() {
        super();
    }

    public BaseGameComponentEntity(final BaseGameComponent baseGameComponent) {
        super(baseGameComponent);

        this.isCustom = baseGameComponent.isCustom();
        this.isEnabled = baseGameComponent.isEnabled();
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

    public static class Minimal extends BaseEntity.Minimal {
    }
}
