package com.thequietcroc.legendary.database.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

import com.thequietcroc.legendary.database.LegendaryDatabase;
import com.thequietcroc.legendary.database.daos.CardDao;

import static androidx.room.ForeignKey.CASCADE;

@Entity(
        tableName = "tblVillains",
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
                        name = "villainsIndex",
                        value = "name"
                )
        }
)
public class Villains extends BaseCard {

    @Override
    public CardDao<Villains> getDao(final LegendaryDatabase db) {
        return db.villainsDao();
    }
}
