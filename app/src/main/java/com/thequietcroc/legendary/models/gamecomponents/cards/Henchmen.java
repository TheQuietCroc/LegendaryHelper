package com.thequietcroc.legendary.models.gamecomponents.cards;

import com.thequietcroc.legendary.database.LegendaryDatabase;
import com.thequietcroc.legendary.database.daos.gamecomponents.cards.MastermindDao;
import com.thequietcroc.legendary.database.entities.gamecomponents.cards.HenchmenEntity;
import com.thequietcroc.legendary.utilities.DbAsyncTask;

import java.util.concurrent.atomic.AtomicReference;

public class Henchmen extends BaseCard {

    private final AtomicReference<Mastermind> mastermindLeaderAtomicReference = new AtomicReference<>();

    public Henchmen() {
        super();
    }

    public Henchmen(final HenchmenEntity henchmenEntity) {
        super(henchmenEntity);
    }

    public Henchmen(final String name) {
        super(name);
    }

    public Mastermind getMastermindLeader() {
        if (mastermindLeaderAtomicReference.get() == null) {

            final MastermindDao mastermindDao = LegendaryDatabase.getInstance().mastermindDao();

            new DbAsyncTask(() -> {
                mastermindLeaderAtomicReference.set(mastermindDao.findByAlwaysLeadsHenchmenId(getId()).toModel());
            });
        }

        return mastermindLeaderAtomicReference.get();
    }

    @Override
    public HenchmenEntity toEntity() {
        return new HenchmenEntity(this);
    }

    public void dbSave() {

        if (!isEnabled()) {
            final Mastermind mastermindLeader = getMastermindLeader();

            mastermindLeader.setEnabled(isEnabled());
            mastermindLeader.dbSave();
        }

        super.dbSave(LegendaryDatabase.getInstance().henchmenDao(), toEntity());
    }

    public void dbDelete() {
        super.dbDelete(LegendaryDatabase.getInstance().henchmenDao(), toEntity());
    }

    @Override
    public boolean equals(final Object o) {

        if (!(o instanceof Henchmen)) {
            return false;
        }

        return super.equals(o);
    }
}
