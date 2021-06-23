package com.thequietcroc.legendary.models.gamecomponents.cards;

import com.thequietcroc.legendary.database.entities.gamecomponents.cards.VillainsEntity;

public class Villains extends BaseCard {

    public Villains() {
        super();
    }

    public Villains(final VillainsEntity villainsEntity) {
        super(villainsEntity);
    }

    public Villains(final String name) {
        super(name);
    }

    @Override
    public VillainsEntity toEntity() {
        return new VillainsEntity(this);
    }

    @Override
    public boolean equals(final Object o) {

        if (!(o instanceof Villains)) {
            return false;
        }

        return super.equals(o);
    }
}
