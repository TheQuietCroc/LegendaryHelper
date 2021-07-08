package com.thequietcroc.legendary.database.entities.gamecomponents.cards;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

import com.thequietcroc.legendary.database.entities.gamecomponents.GameSetEntity;
import com.thequietcroc.legendary.models.gamecomponents.cards.Scheme;

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

    public SchemeEntity() {
        super();
    }

    public SchemeEntity(final Scheme scheme) {
        super(scheme);
    }

    @Override
    public Scheme toModel() {
        return new Scheme(this);
    }

    @Override
    public boolean equals(final Object o) {

        if (!(o instanceof SchemeEntity)) {
            return false;
        }

        return super.equals(o);
    }
}
