package com.thequietcroc.legendary.database.entities.gamecomponents;

import androidx.room.Entity;
import androidx.room.Index;

import com.thequietcroc.legendary.models.gamecomponents.GameSet;

@Entity(
        tableName = "tblGameSets",
        indices = {
                @Index(
                        name = "gameSetIndex",
                        value = "name"
                )
        }
)
public class GameSetEntity extends BaseGameComponentEntity {

    public GameSetEntity() {
        super();
    }

    public GameSetEntity(final GameSet gameSet) {
        super(gameSet);
    }

    @Override
    public GameSet toModel() {
        return new GameSet(this);
    }

    @Override
    public boolean equals(final Object o) {

        if (!(o instanceof GameSetEntity)) {
            return false;
        }

        return super.equals(o);
    }

    public static class Minimal extends BaseGameComponentEntity.Minimal {

        @Override
        public boolean equals(final Object o) {
            if (o == this) {
                return true;
            }

            if (!(o instanceof Minimal)) {
                return false;
            }

            return super.equals(o);
        }
    }
}
