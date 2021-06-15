package com.thequietcroc.legendary.database.entities.gamecomponents.cards;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

import com.thequietcroc.legendary.database.entities.gamecomponents.GameSetEntity;

import static androidx.room.ForeignKey.CASCADE;
import static androidx.room.ForeignKey.SET_DEFAULT;

@Entity(
        tableName = "tblMasterminds",
        foreignKeys = {
                @ForeignKey(
                        entity = GameSetEntity.class,
                        parentColumns = "id",
                        childColumns = "setId",
                        onUpdate = CASCADE,
                        onDelete = SET_DEFAULT
                ),
                @ForeignKey(
                        entity = VillainsEntity.class,
                        parentColumns = "id",
                        childColumns = "villainId",
                        onUpdate = CASCADE,
                        onDelete = SET_DEFAULT
                ),
                @ForeignKey(
                        entity = HenchmenEntity.class,
                        parentColumns = "id",
                        childColumns = "henchmenId",
                        onUpdate = CASCADE,
                        onDelete = SET_DEFAULT
                )
        },
        indices = {
                @Index(
                        name = "mastermindIndex",
                        value = "name"
                )
        }
)
public class MastermindEntity extends BaseCardEntity {

    @ColumnInfo(defaultValue = "0")
    private boolean isEpic;

    @ColumnInfo(defaultValue = "0")
    private int villainId;

    @ColumnInfo(defaultValue = "0")
    private int henchmenId;

    public boolean isEpic() {
        return isEpic;
    }

    public void setEpic(boolean isEpic) {
        this.isEpic = isEpic;
    }

    public int getVillainId() {
        return villainId;
    }

    public void setVillainId(int villainId) {
        this.villainId = villainId;
    }

    public int getHenchmenId() {
        return henchmenId;
    }

    public void setHenchmenId(int henchmenId) {
        this.henchmenId = henchmenId;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof MastermindEntity)) {
            return false;
        }

        return super.equals(o);
    }

    public static class Minimal extends BaseCardEntity.Minimal {

        public int villainId;

        public int henchmenId;

        public int getVillainId() {
            return villainId;
        }

        public void setVillainId(int villainId) {
            this.villainId = villainId;
        }

        public int getHenchmenId() {
            return henchmenId;
        }

        public void setHenchmenId(int henchmenId) {
            this.henchmenId = henchmenId;
        }

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

