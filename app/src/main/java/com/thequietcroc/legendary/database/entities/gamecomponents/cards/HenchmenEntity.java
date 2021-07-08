package com.thequietcroc.legendary.database.entities.gamecomponents.cards;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

import com.thequietcroc.legendary.database.entities.gamecomponents.GameSetEntity;
import com.thequietcroc.legendary.models.gamecomponents.cards.Henchmen;

import static androidx.room.ForeignKey.CASCADE;
import static androidx.room.ForeignKey.SET_DEFAULT;

@Entity(
        tableName = "tblHenchmen",
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
                        name = "henchmenIndex",
                        value = "name"
                )
        }
)
public class HenchmenEntity extends BaseCardEntity {

    public HenchmenEntity() {
        super();
    }

    public HenchmenEntity(final Henchmen henchmen) {
        super(henchmen);
    }

    @Override
    public Henchmen toModel() {
        return new Henchmen(this);
    }

    @Override
    public boolean equals(final Object o) {

        if (!(o instanceof HenchmenEntity)) {
            return false;
        }

        return super.equals(o);
    }
}
