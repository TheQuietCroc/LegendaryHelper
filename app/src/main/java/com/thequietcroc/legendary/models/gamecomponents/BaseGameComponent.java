package com.thequietcroc.legendary.models.gamecomponents;

import com.thequietcroc.legendary.database.entities.gamecomponents.BaseGameComponentEntity;
import com.thequietcroc.legendary.models.BaseItem;

public abstract class BaseGameComponent extends BaseItem {

    private boolean isCustom = false;
    private boolean isEnabled = true;

    public BaseGameComponent() {
        super();
    }

    public BaseGameComponent(final BaseGameComponentEntity baseGameComponentEntity) {
        super(baseGameComponentEntity);

        this.isCustom = baseGameComponentEntity.isCustom();
        this.isEnabled = baseGameComponentEntity.isEnabled();
    }

    public BaseGameComponent(final String name) {
        super(name);
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
}
