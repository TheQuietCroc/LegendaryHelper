package com.thequietcroc.legendary.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.thequietcroc.legendary.database.daos.GameSetDao;
import com.thequietcroc.legendary.database.daos.HenchmenDao;
import com.thequietcroc.legendary.database.daos.HeroDao;
import com.thequietcroc.legendary.database.daos.MastermindDao;
import com.thequietcroc.legendary.database.daos.SchemeDao;
import com.thequietcroc.legendary.database.daos.VillainsDao;
import com.thequietcroc.legendary.database.entities.GameSet;
import com.thequietcroc.legendary.database.entities.Henchmen;
import com.thequietcroc.legendary.database.entities.Hero;
import com.thequietcroc.legendary.database.entities.Mastermind;
import com.thequietcroc.legendary.database.entities.Scheme;
import com.thequietcroc.legendary.database.entities.Villains;

@Database(
        entities = {
                GameSet.class,
                Henchmen.class,
                Hero.class,
                Mastermind.class,
                Scheme.class,
                Villains.class
        },
        version = 1
)
public abstract class LegendaryDatabase extends RoomDatabase {

    private final static String DB_NAME = "legendary.db";

    private static volatile LegendaryDatabase INSTANCE;

    public static LegendaryDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (LegendaryDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            LegendaryDatabase.class,
                            DB_NAME)
                            .allowMainThreadQueries()
                            .createFromAsset(String.format("database/%s", DB_NAME))
                            .build();
                }
            }
        }

        return INSTANCE;
    }

    public abstract GameSetDao gameSetDao();

    public abstract HenchmenDao henchmenDao();

    public abstract HeroDao heroDao();

    public abstract MastermindDao mastermindDao();

    public abstract SchemeDao schemeDao();

    public abstract VillainsDao villainsDao();
}
