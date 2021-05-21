package com.thequietcroc.legendary.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

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

    public Integer getVillainId() {
        return villainId;
    }

    public Integer getHenchmenId() {
        return henchmenId;
    }
}
