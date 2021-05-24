package com.thequietcroc.legendary.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

import com.thequietcroc.legendary.database.LegendaryDatabase;
import com.thequietcroc.legendary.database.daos.CardDao;

import static androidx.room.ForeignKey.CASCADE;
import static androidx.room.ForeignKey.SET_NULL;

@Entity(
        tableName = "tblMasterminds",
        foreignKeys = {
                @ForeignKey(
                        entity = GameSet.class,
                        parentColumns = "id",
                        childColumns = "setId",
                        onUpdate = CASCADE,
                        onDelete = CASCADE
                ),
                @ForeignKey(
                        entity = Villains.class,
                        parentColumns = "id",
                        childColumns = "villainId",
                        onUpdate = CASCADE,
                        onDelete = SET_NULL
                ),
                @ForeignKey(
                        entity = Henchmen.class,
                        parentColumns = "id",
                        childColumns = "henchmenId",
                        onUpdate = CASCADE,
                        onDelete = SET_NULL
                )
        },
        indices = {
                @Index(
                        name = "mastermindIndex",
                        value = "name"
                )
        }
)
public class Mastermind extends BaseCard {

    @ColumnInfo(defaultValue = "0")
    public boolean isEpic;

    public Integer villainId;

    public Integer henchmenId;

    public boolean isEpic() {
        return isEpic;
    }

    public void setEpic(boolean epic) {
        isEpic = epic;
    }

    public Integer getVillainId() {
        return villainId;
    }

    public void setVillainId(Integer villainId) {
        this.villainId = villainId;
    }

    public Integer getHenchmenId() {
        return henchmenId;
    }

    public void setHenchmenId(Integer henchmenId) {
        this.henchmenId = henchmenId;
    }

    @Override
    public CardDao<Mastermind> getDao(final LegendaryDatabase db) {
        return db.mastermindDao();
    }

    @Override
    public boolean equals(final Object o) {
        if (super.equals(o)) {

            final Mastermind m = (Mastermind) o;

            return isEpic == m.isEpic
                    && villainId.intValue() == m.villainId.intValue()
                    && henchmenId.intValue() == m.henchmenId.intValue()
                    && isEpic == m.isEpic;
        }

        return false;
    }
}
