package com.thequietcroc.legendary.database.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

import com.thequietcroc.legendary.database.entities.gamecomponents.cards.MastermindEntity;
import com.thequietcroc.legendary.database.entities.gamecomponents.cards.SchemeEntity;

import java.util.Date;

import static androidx.room.ForeignKey.CASCADE;
import static androidx.room.ForeignKey.SET_DEFAULT;

@Entity(
        tableName = "tblGameSetups",
        foreignKeys = {
                @ForeignKey(
                        entity = MastermindEntity.class,
                        parentColumns = "id",
                        childColumns = "mastermindId",
                        onUpdate = CASCADE,
                        onDelete = SET_DEFAULT
                ),
                @ForeignKey(
                        entity = SchemeEntity.class,
                        parentColumns = "id",
                        childColumns = "schemeId",
                        onUpdate = CASCADE,
                        onDelete = SET_DEFAULT
                )
        }
)
public class GameSetupEntity extends BaseEntity {

    @ColumnInfo(defaultValue = "1")
    private int numPlayers;

    @ColumnInfo(defaultValue = "0")
    private int mastermindId;

    @ColumnInfo(defaultValue = "0")
    private int schemeId;

    @NonNull
    private Date dateLastPlayed;

    public int getNumPlayers() {
        return numPlayers;
    }

    public void setNumPlayers(final int numPlayers) {
        this.numPlayers = numPlayers;
    }

    public int getMastermindId() {
        return mastermindId;
    }

    public void setMastermindId(final int mastermindId) {
        this.mastermindId = mastermindId;
    }

    public int getSchemeId() {
        return schemeId;
    }

    public void setSchemeId(final int schemeId) {
        this.schemeId = schemeId;
    }

    public Date getDateLastPlayed() {
        return dateLastPlayed;
    }

    public void setDateLastPlayed(final Date dateLastPlayed) {
        this.dateLastPlayed = dateLastPlayed;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof GameSetupEntity)) {
            return false;
        }

        return super.equals(o);
    }

    public static class Minimal extends BaseEntity.Minimal {

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
