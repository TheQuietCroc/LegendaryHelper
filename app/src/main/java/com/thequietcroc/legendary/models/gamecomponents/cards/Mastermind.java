package com.thequietcroc.legendary.models.gamecomponents.cards;

import com.thequietcroc.legendary.database.LegendaryDatabase;
import com.thequietcroc.legendary.database.entities.gamecomponents.cards.MastermindEntity;
import com.thequietcroc.legendary.utilities.DbAsyncTask;

import java.util.concurrent.atomic.AtomicReference;

public class Mastermind extends BaseCard {

    public static Mastermind NONE;

    static {
        NONE = new Mastermind("None");
        NONE.setId(0);
    }

    private boolean isEpic = false;
    private final AtomicReference<Villains> alwaysLeadsVillains = new AtomicReference<>();
    private final AtomicReference<Henchmen> alwaysLeadsHenchmen = new AtomicReference<>();

    public Mastermind(final MastermindEntity mastermindEntity) {
        super(mastermindEntity);

        setIsEpic(mastermindEntity.isEpic());

        if (mastermindEntity.getVillainId() > 0) {
            new DbAsyncTask(() -> setAlwaysLeadsVillains(LegendaryDatabase.getInstance()
                    .villainsDao()
                    .findByIdSync(mastermindEntity.getVillainId())
                    .toModel()));
        } else {
            setAlwaysLeadsVillains(Villains.NONE);
        }

        if (mastermindEntity.getHenchmenId() > 0) {
            new DbAsyncTask(() -> setAlwaysLeadsHenchmen(LegendaryDatabase.getInstance()
                    .henchmenDao()
                    .findByIdSync(mastermindEntity.getHenchmenId())
                    .toModel()));
        } else {
            setAlwaysLeadsHenchmen(Henchmen.NONE);
        }
    }

    public Mastermind(final String name) {
        super(name);

        setAlwaysLeadsHenchmen(Henchmen.NONE);
        setAlwaysLeadsVillains(Villains.NONE);
    }
    
    public boolean isEpic() {
        return isEpic;
    }
    
    public void setIsEpic(final boolean isEpic) {
        this.isEpic = isEpic;
    }

    public Villains getAlwaysLeadsVillains() {
        return alwaysLeadsVillains.get();
    }

    public void setAlwaysLeadsVillains(final Villains alwaysLeadsVillains) {
        this.alwaysLeadsVillains.set(alwaysLeadsVillains);
    }

    public Henchmen getAlwaysLeadsHenchmen() {
        return alwaysLeadsHenchmen.get();
    }

    public void setAlwaysLeadsHenchmen(final Henchmen alwaysLeadsHenchmen) {
        this.alwaysLeadsHenchmen.set(alwaysLeadsHenchmen);
    }

    @Override
    public MastermindEntity toEntity() {
        return new MastermindEntity(this);
    }

    public void dbSave() {
        new DbAsyncTask(() -> {
            if (isEnabled()) {
                final Villains alwaysLeadsVillains = getAlwaysLeadsVillains();

                if (alwaysLeadsVillains.getId() != 0) {
                    alwaysLeadsVillains.setEnabled(isEnabled());
                    alwaysLeadsVillains.dbSave();
                }

                final Henchmen alwaysLeadsHenchmen = getAlwaysLeadsHenchmen();

                if (alwaysLeadsHenchmen.getId() != 0) {
                    alwaysLeadsHenchmen.setEnabled(isEnabled());
                    alwaysLeadsHenchmen.dbSave();
                }
            }

            dbSave(LegendaryDatabase.getInstance().mastermindDao(), toEntity());
        });
    }

    public void dbDelete() {
        dbDelete(LegendaryDatabase.getInstance().mastermindDao(), toEntity());
    }

    @Override
    public boolean equals(final Object o) {

        if (!(o instanceof Mastermind)) {
            return false;
        }

        return super.equals(o);
    }
}
