package com.thequietcroc.legendary.models.gamecomponents.cards;

import com.thequietcroc.legendary.database.LegendaryDatabase;
import com.thequietcroc.legendary.database.entities.gamecomponents.cards.SchemeEntity;

public class Scheme extends BaseCard {

    public static Scheme NONE;

    static {
        NONE = new Scheme("None");
        NONE.setId(0);
    }

    public Scheme(final SchemeEntity schemeEntity) {
        super(schemeEntity);
    }

    public Scheme(final String name) {
        super(name);
    }

    @Override
    public SchemeEntity toEntity() {
        return new SchemeEntity(this);
    }

    public void dbSave() {
        dbSave(LegendaryDatabase.getInstance().schemeDao(), toEntity());
    }

    public void dbDelete() {
        dbDelete(LegendaryDatabase.getInstance().schemeDao(), toEntity());
    }

    @Override
    public boolean equals(final Object o) {

        if (!(o instanceof Scheme)) {
            return false;
        }

        return super.equals(o);
    }
}
