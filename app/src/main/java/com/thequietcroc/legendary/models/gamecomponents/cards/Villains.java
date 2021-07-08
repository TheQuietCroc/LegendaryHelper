package com.thequietcroc.legendary.models.gamecomponents.cards;

import com.thequietcroc.legendary.database.LegendaryDatabase;
import com.thequietcroc.legendary.database.daos.gamecomponents.cards.MastermindDao;
import com.thequietcroc.legendary.database.entities.gamecomponents.cards.VillainsEntity;
import com.thequietcroc.legendary.utilities.DbAsyncTask;

import java.util.concurrent.atomic.AtomicReference;

public class Villains extends BaseCard {

    private final AtomicReference<Mastermind> mastermindLeaderAtomicReference = new AtomicReference<>();

    public Villains() {
        super();
    }

    public Villains(final VillainsEntity villainsEntity) {
        super(villainsEntity);
    }

    public Villains(final String name) {
        super(name);
    }

    public Mastermind getMastermindLeader() {
        if (mastermindLeaderAtomicReference.get() == null) {

            final MastermindDao mastermindDao = LegendaryDatabase.getInstance().mastermindDao();

            new DbAsyncTask(() -> {
                mastermindLeaderAtomicReference.set(mastermindDao.findByAlwaysLeadsVillainsId(getId()).toModel());
            });
        }

        return mastermindLeaderAtomicReference.get();
    }

    @Override
    public VillainsEntity toEntity() {
        return new VillainsEntity(this);
    }

    public void dbSave() {
        if (!isEnabled()) {
            final Mastermind mastermindLeader = getMastermindLeader();

            mastermindLeader.setEnabled(isEnabled());
            mastermindLeader.dbSave();
        }

        super.dbSave(LegendaryDatabase.getInstance().villainsDao(), toEntity());
    }

    public void dbDelete() {
        super.dbDelete(LegendaryDatabase.getInstance().villainsDao(), toEntity());
    }

    @Override
    public boolean equals(final Object o) {

        if (!(o instanceof Villains)) {
            return false;
        }

        return super.equals(o);
    }
}
