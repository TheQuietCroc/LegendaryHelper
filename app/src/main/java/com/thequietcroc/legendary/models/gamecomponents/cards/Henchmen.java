package com.thequietcroc.legendary.models.gamecomponents.cards;

import com.thequietcroc.legendary.database.LegendaryDatabase;
import com.thequietcroc.legendary.database.daos.gamecomponents.cards.MastermindDao;
import com.thequietcroc.legendary.database.entities.gamecomponents.cards.HenchmenEntity;
import com.thequietcroc.legendary.database.entities.gamecomponents.cards.MastermindEntity;
import com.thequietcroc.legendary.utilities.DbAsyncTask;

import java.util.concurrent.atomic.AtomicReference;

public class Henchmen extends BaseCard {

    public static Henchmen NONE;

    static {
        NONE = new Henchmen("None");
        NONE.setId(0);
    }

    private final AtomicReference<Mastermind> mastermindLeaderAtomicReference = new AtomicReference<>();

    public Henchmen(final HenchmenEntity henchmenEntity) {
        super(henchmenEntity);
    }

    public Henchmen(final String name) {
        super(name);

        setMastermindLeader(Mastermind.NONE);
    }

    public Mastermind getMastermindLeader() {
        if (mastermindLeaderAtomicReference.get() == null) {

            new DbAsyncTask(() -> {
                final MastermindDao mastermindDao = LegendaryDatabase.getInstance().mastermindDao();
                final MastermindEntity mastermind = mastermindDao.findByAlwaysLeadsHenchmenId(getId());

                mastermindLeaderAtomicReference.set(mastermind == null
                        ? Mastermind.NONE
                        : mastermind.toModel());
            });
        }

        return mastermindLeaderAtomicReference.get();
    }

    public void setMastermindLeader(final Mastermind mastermindLeader) {
        this.mastermindLeaderAtomicReference.set(mastermindLeader);
    }

    @Override
    public HenchmenEntity toEntity() {
        return new HenchmenEntity(this);
    }

    public void dbSave() {
        new DbAsyncTask(() -> {
            if (!isEnabled()) {
                final Mastermind mastermindLeader = getMastermindLeader();

                mastermindLeader.setEnabled(isEnabled());
                mastermindLeader.dbSave();
            }

            dbSave(LegendaryDatabase.getInstance().henchmenDao(), toEntity());
        });
    }

    public void dbDelete() {
        dbDelete(LegendaryDatabase.getInstance().henchmenDao(), toEntity());
    }

    @Override
    public boolean equals(final Object o) {

        if (!(o instanceof Henchmen)) {
            return false;
        }

        return super.equals(o);
    }
}
