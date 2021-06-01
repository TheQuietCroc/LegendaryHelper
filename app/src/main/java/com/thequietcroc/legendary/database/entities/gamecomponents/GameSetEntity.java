package com.thequietcroc.legendary.database.entities.gamecomponents;

import androidx.room.Entity;
import androidx.room.Index;

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

    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof GameSetEntity)) {
            return false;
        }

        return super.equals(o);
    }
}
