package com.thequietcroc.legendary.database.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Date;

import static androidx.room.ForeignKey.CASCADE;
import static androidx.room.ForeignKey.SET_DEFAULT;

@Entity(
        tableName = "tblGameSetups",
        foreignKeys = {
                @ForeignKey(
                        entity = Mastermind.class,
                        parentColumns = "id",
                        childColumns = "mastermindId",
                        onUpdate = CASCADE,
                        onDelete = SET_DEFAULT
                ),
                @ForeignKey(
                        entity = Scheme.class,
                        parentColumns = "id",
                        childColumns = "schemeId",
                        onUpdate = CASCADE,
                        onDelete = SET_DEFAULT
                )
        }
)
public class GameSetupEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    private String name;

    @ColumnInfo(defaultValue = "1")
    private int numPlayers;

    @ColumnInfo(defaultValue = "0")
    private int mastermindId;

    @ColumnInfo(defaultValue = "0")
    private int schemeId;

    @NonNull
    private Date dateLastPlayed;

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull final  String name) {
        this.name = name;
    }

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
}
