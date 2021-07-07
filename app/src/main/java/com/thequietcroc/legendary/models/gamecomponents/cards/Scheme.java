package com.thequietcroc.legendary.models.gamecomponents.cards;

import com.thequietcroc.legendary.database.LegendaryDatabase;
import com.thequietcroc.legendary.database.daos.gamecomponents.cards.SchemeDao;
import com.thequietcroc.legendary.database.entities.gamecomponents.cards.SchemeEntity;

public class Scheme extends BaseCard {

    public Scheme() {
        super();
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

    @Override
    public void dbSave() {
        final SchemeDao schemeDao = LegendaryDatabase.getInstance().schemeDao();

        if (0 == getId()) {
            setId((int) schemeDao.insert(toEntity()));
        } else {
            schemeDao.update(toEntity());
        }
    }

    @Override
    public void dbDelete() {
        if (0 < getId()) {
            final SchemeDao schemeDao = LegendaryDatabase.getInstance().schemeDao();

            schemeDao.delete(toEntity());
        }
    }

    @Override
    public boolean equals(final Object o) {

        if (!(o instanceof Scheme)) {
            return false;
        }

        return super.equals(o);
    }
}
