package com.thequietcroc.legendary.database.entities;

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
public class GameSet extends BaseItem {

    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof GameSet)) {
            return false;
        }

        return super.equals(o);
    }
}
