package com.thequietcroc.legendary.models.gamecomponents.cards;

import com.thequietcroc.legendary.database.entities.gamecomponents.cards.HenchmenEntity;

public class Henchmen extends BaseCard {

    public Henchmen() {
        super();
    }

    public Henchmen(final HenchmenEntity henchmenEntity) {
        super(henchmenEntity);
    }

    public Henchmen(final String name) {
        super(name);
    }

    @Override
    public HenchmenEntity toEntity() {
        return new HenchmenEntity(this);
    }

    @Override
    public boolean equals(final Object o) {

        if (!(o instanceof Henchmen)) {
            return false;
        }

        return super.equals(o);
    }
}
