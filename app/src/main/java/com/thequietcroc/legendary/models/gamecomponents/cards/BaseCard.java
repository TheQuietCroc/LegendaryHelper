package com.thequietcroc.legendary.models.gamecomponents.cards;

import android.os.AsyncTask;

import com.thequietcroc.legendary.database.LegendaryDatabase;
import com.thequietcroc.legendary.database.entities.gamecomponents.GameSetEntity;
import com.thequietcroc.legendary.database.entities.gamecomponents.cards.BaseCardEntity;
import com.thequietcroc.legendary.models.gamecomponents.BaseGameComponent;
import com.thequietcroc.legendary.models.gamecomponents.GameSet;

import java.util.concurrent.atomic.AtomicReference;

public abstract class BaseCard extends BaseGameComponent {

    volatile AtomicReference<GameSet> gameSet = new AtomicReference<>(new GameSet());

    public BaseCard() {
        super();
    }

    public BaseCard(final BaseCardEntity baseCardEntity) {
        super(baseCardEntity);

        AsyncTask.execute(() -> setGameSet(LegendaryDatabase.getInstance()
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
