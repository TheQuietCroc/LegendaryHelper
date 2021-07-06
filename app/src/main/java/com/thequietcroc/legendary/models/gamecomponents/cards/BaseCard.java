package com.thequietcroc.legendary.models.gamecomponents.cards;

import com.thequietcroc.legendary.database.LegendaryDatabase;
import com.thequietcroc.legendary.database.entities.gamecomponents.cards.BaseCardEntity;
import com.thequietcroc.legendary.models.gamecomponents.BaseGameComponent;
import com.thequietcroc.legendary.models.gamecomponents.GameSet;
import com.thequietcroc.legendary.utilities.DbAsyncTask;

import java.util.concurrent.atomic.AtomicReference;

public abstract class BaseCard extends BaseGameComponent {

    volatile AtomicReference<GameSet> gameSet = new AtomicReference<>(new GameSet());

    public BaseCard() {
        super();
    }

    public BaseCard(final BaseCardEntity baseCardEntity) {
        super(baseCardEntity);

        new DbAsyncTask(() -> setGameSet(LegendaryDatabase.getInstance()
                .gameSetDao()
                .findByIdSync(baseCardEntity.getGameSetId())
                .toModel()));
    }

    public BaseCard(final String name) {
        super(name);
    }

    public GameSet getGameSet() {
        return gameSet.get();
    }

    public void setGameSet(final GameSet gameSet) {
        this.gameSet.set(gameSet);
    }
}
