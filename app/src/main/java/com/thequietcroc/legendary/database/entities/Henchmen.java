package com.thequietcroc.legendary.database.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

import com.thequietcroc.legendary.database.LegendaryDatabase;
import com.thequietcroc.legendary.database.daos.HenchmenDao;

import static androidx.room.ForeignKey.CASCADE;

@Entity(
        tableName = "tblHenchmen",
        foreignKeys = {
                @ForeignKey(
                        entity = GameSet.class,
                        parentColumns = "id",
                        childColumns = "setId",
                        onUpdate = CASCADE,
                        onDelete = CASCADE
                )
        },
        indices = {
                @Index(
                        name = "henchmenIndex",
                        value = "name"
                )
        }
)
public class Henchmen extends BaseCard {

    @Override
    public HenchmenDao getDao(final LegendaryDatabase db) {
        return db.henchmenDao();
    }

    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof Henchmen)) {
            return false;
        }

        return super.equals(o);
    }
}
