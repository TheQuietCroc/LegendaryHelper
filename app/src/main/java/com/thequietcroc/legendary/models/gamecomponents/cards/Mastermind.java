package com.thequietcroc.legendary.models.gamecomponents.cards;

import android.os.AsyncTask;

import com.thequietcroc.legendary.database.LegendaryDatabase;
import com.thequietcroc.legendary.database.entities.gamecomponents.cards.HenchmenEntity;
import com.thequietcroc.legendary.database.entities.gamecomponents.cards.MastermindEntity;
import com.thequietcroc.legendary.database.entities.gamecomponents.cards.VillainsEntity;

import java.util.concurrent.atomic.AtomicReference;

public class Mastermind extends BaseCard {

    private boolean isEpic = false;
    private final AtomicReference<Villains> alwaysLeadsVillains = new AtomicReference<>(new VillainsEntity().toModel());
    private final AtomicReference<Henchmen> alwaysLeadsHenchmen = new AtomicReference<>(new HenchmenEntity().toModel());

    public Mastermind() {
        super();
    }

    public Mastermind(final MastermindEntity mastermindEntity) {
        super(mastermindEntity);

        setIsEpic(mastermindEntity.isEpic());

        if (mastermindEntity.getVillainId() > 0) {
            AsyncTask.execute(() -> setAlwaysLeadsVillains(LegendaryDatabase.getInstance()
                    .villainsDao()
                    .findByIdSync(mastermindEntity.getVillainId())
                    .toModel()));
        }

        if (mastermindEntity.getHenchmenId() > 0) {
            AsyncTask.execute(() -> setAlwaysLeadsHenchmen(LegendaryDatabase.getInstance()
                    .henchmenDao()
                    .findByIdSync(mastermindEntity.getHenchmenId())
                    .toModel()));
        }
    }

    public Mastermind(final String name) {
        super(name);
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

    @Override
    public boolean equals(final Object o) {

        if (!(o instanceof Mastermind)) {
            return false;
        }

        return super.equals(o);
    }
}
