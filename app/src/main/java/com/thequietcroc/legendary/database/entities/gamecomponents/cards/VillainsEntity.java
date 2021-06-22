package com.thequietcroc.legendary.database.entities.gamecomponents.cards;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

import com.thequietcroc.legendary.database.entities.gamecomponents.GameSetEntity;
import com.thequietcroc.legendary.models.gamecomponents.cards.Villains;

import static androidx.room.ForeignKey.CASCADE;
import static androidx.room.ForeignKey.SET_DEFAULT;

@Entity(
        tableName = "tblVillains",
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
                        name = "villainsIndex",
                        value = "name"
                )
        }
)
public class VillainsEntity extends BaseCardEntity {

    public VillainsEntity() {
        super();
    }

    public VillainsEntity(final Villains villains) {
        super(villains);
    }

    @Override
    public Villains toModel() {
        return new Villains(this);
    }

    @Override
    public boolean equals(final Object o) {

        if (!(o instanceof VillainsEntity)) {
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
