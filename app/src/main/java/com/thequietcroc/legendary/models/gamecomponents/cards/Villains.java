package com.thequietcroc.legendary.models.gamecomponents.cards;

import com.thequietcroc.legendary.database.LegendaryDatabase;
import com.thequietcroc.legendary.database.daos.gamecomponents.cards.MastermindDao;
import com.thequietcroc.legendary.database.entities.gamecomponents.cards.MastermindEntity;
import com.thequietcroc.legendary.database.entities.gamecomponents.cards.VillainsEntity;
import com.thequietcroc.legendary.utilities.DbAsyncTask;

import java.util.concurrent.atomic.AtomicReference;

public class Villains extends BaseCard {

    public static Villains NONE;

    static {
        NONE = new Villains("None");
        NONE.setId(0);
    }

    private final AtomicReference<Mastermind> mastermindLeaderAtomicReference = new AtomicReference<>();

    public Villains(final VillainsEntity villainsEntity) {
        super(villainsEntity);
    }

    public Villains(final String name) {
        super(name);

        setMastermindLeader(Mastermind.NONE);
    }

    @Override
    public void setEnabled(final boolean isEnabled) {
        super.setEnabled(isEnabled);

        if (!isEnabled()) {
            getMastermindLeader().setEnabled(isEnabled());
        }
    }

    public Mastermind getMastermindLeader() {
        if (mastermindLeaderAtomicReference.get() == null) {

            new DbAsyncTask(() -> {
                final MastermindDao mastermindDao = LegendaryDatabase.getInstance().mastermindDao();
                final MastermindEntity mastermind = mastermindDao.findByAlwaysLeadsVillainsId(getId());

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
    public VillainsEntity toEntity() {
        return new VillainsEntity(this);
    }

    public void dbSave() {
        new DbAsyncTask(() -> {
            getMastermindLeader().dbSave();
            dbSave(LegendaryDatabase.getInstance().villainsDao(), toEntity());
        });
    }

    public void dbDelete() {
        dbDelete(LegendaryDatabase.getInstance().villainsDao(), toEntity());
    }

    @Override
    public boolean equals(final Object o) {

        if (!(o instanceof Villains)) {
            return false;
        }

        return super.equals(o);
    }
}
