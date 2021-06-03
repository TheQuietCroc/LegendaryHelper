package com.thequietcroc.legendary.database.entities.gamecomponents.cards;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

import com.thequietcroc.legendary.database.entities.gamecomponents.GameSetEntity;

import static androidx.room.ForeignKey.CASCADE;
import static androidx.room.ForeignKey.SET_DEFAULT;

@Entity(
        tableName = "tblSchemes",
        foreignKeys = {
                @ForeignKey(
                        entity = GameSetEntity.class,
                        parentColumns = "id",
                        childColumns = "setId",
                        onUpdate = CASCADE,
                        onDelete = SET_DEFAULT
                )
        },
        indices = {
                @Index(
                        name = "schemeIndex",
                        value = "name"
                )
        }
)
public class SchemeEntity extends BaseCardEntity {

    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof SchemeEntity)) {
            return false;
        }

        return super.equals(o);
    }

    public static class Minimal extends BaseCardEntity.Minimal {

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
