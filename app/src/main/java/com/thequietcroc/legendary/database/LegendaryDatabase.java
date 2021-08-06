package com.thequietcroc.legendary.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.thequietcroc.legendary.database.daos.gamecomponents.GameSetDao;
import com.thequietcroc.legendary.database.daos.gamecomponents.KeywordDao;
import com.thequietcroc.legendary.database.daos.gamecomponents.cards.HenchmenDao;
import com.thequietcroc.legendary.database.daos.gamecomponents.cards.HeroDao;
import com.thequietcroc.legendary.database.daos.gamecomponents.cards.MastermindDao;
import com.thequietcroc.legendary.database.daos.gamecomponents.cards.SchemeDao;
import com.thequietcroc.legendary.database.daos.gamecomponents.cards.VillainsDao;
import com.thequietcroc.legendary.database.entities.gamecomponents.GameSetEntity;
import com.thequietcroc.legendary.database.entities.GameSetupEntity;
import com.thequietcroc.legendary.database.entities.gamecomponents.KeywordEntity;
import com.thequietcroc.legendary.database.entities.gamecomponents.cards.HenchmenEntity;
import com.thequietcroc.legendary.database.entities.gamecomponents.cards.HeroEntity;
import com.thequietcroc.legendary.database.entities.gamecomponents.cards.MastermindEntity;
import com.thequietcroc.legendary.database.entities.gamecomponents.cards.SchemeEntity;
import com.thequietcroc.legendary.database.entities.gamecomponents.cards.VillainsEntity;

@Database(
        entities = {
                GameSetupEntity.class,
                GameSetEntity.class,
                HenchmenEntity.class,
                HeroEntity.class,
                MastermindEntity.class,
                SchemeEntity.class,
                VillainsEntity.class,
                KeywordEntity.class
        },
        version = 1
)
@TypeConverters({Converters.class})
public abstract class LegendaryDatabase extends RoomDatabase {

    private final static String DB_NAME = "legendary.db";

    private static volatile LegendaryDatabase INSTANCE;

    public static LegendaryDatabase getInstance() {
        return INSTANCE;
    }

    public static void setInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (LegendaryDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            LegendaryDatabase.class,
                            DB_NAME)
                            .createFromAsset(String.format("database/%s", DB_NAME))
                            .build();
                }
            }
        }
    }

    public abstract GameSetDao gameSetDao();

    public abstract HenchmenDao henchmenDao();

    public abstract HeroDao heroDao();

    public abstract MastermindDao mastermindDao();

    public abstract SchemeDao schemeDao();

    public abstract VillainsDao villainsDao();

    public abstract KeywordDao keywordDao();
}
