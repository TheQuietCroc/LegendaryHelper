package com.thequietcroc.legendary.models.gamecomponents;

import com.thequietcroc.legendary.database.LegendaryDatabase;
import com.thequietcroc.legendary.database.daos.gamecomponents.cards.HenchmenDao;
import com.thequietcroc.legendary.database.daos.gamecomponents.cards.HeroDao;
import com.thequietcroc.legendary.database.daos.gamecomponents.cards.MastermindDao;
import com.thequietcroc.legendary.database.daos.gamecomponents.cards.SchemeDao;
import com.thequietcroc.legendary.database.daos.gamecomponents.cards.VillainsDao;
import com.thequietcroc.legendary.database.entities.gamecomponents.GameSetEntity;
import com.thequietcroc.legendary.models.gamecomponents.cards.Henchmen;
import com.thequietcroc.legendary.models.gamecomponents.cards.Hero;
import com.thequietcroc.legendary.models.gamecomponents.cards.Mastermind;
import com.thequietcroc.legendary.models.gamecomponents.cards.Scheme;
import com.thequietcroc.legendary.models.gamecomponents.cards.Villains;
import com.thequietcroc.legendary.utilities.DbAsyncTask;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class GameSet extends BaseGameComponent {

    public static GameSet NONE;

    static {
        NONE = new GameSet("None");
        NONE.setId(0);
    }

    private final List<Mastermind> mastermindList = Collections.synchronizedList(new ArrayList<>());
    private final List<Scheme> schemeList = Collections.synchronizedList(new ArrayList<>());
    private final List<Villains> villainsList = Collections.synchronizedList(new ArrayList<>());
    private final List<Henchmen> henchmenList = Collections.synchronizedList(new ArrayList<>());
    private final List<Hero> heroList = Collections.synchronizedList(new ArrayList<>());

    public GameSet(final GameSetEntity gameSetEntity) {
        super(gameSetEntity);
    }

    public GameSet(final String name) {
        super(name);
    }

    @Override
    public GameSetEntity toEntity() {
        return new GameSetEntity(this);
    }

    public List<Mastermind> getMastermindList() {
        if (mastermindList.isEmpty()) {

            final MastermindDao mastermindDao = LegendaryDatabase.getInstance().mastermindDao();

            new DbAsyncTask(() -> mastermindList.addAll(mastermindDao
                    .getAllBySetIdSync(getId())
                    .stream()
                    .map(Mastermind::new)
                    .collect(Collectors.toList())));
        }

        return mastermindList;
    }

    public List<Scheme> getSchemeList() {
        if (schemeList.isEmpty()) {

            final SchemeDao schemeDao = LegendaryDatabase.getInstance().schemeDao();

            new DbAsyncTask(() -> schemeList.addAll(schemeDao
                    .getAllBySetIdSync(getId())
                    .stream()
                    .map(Scheme::new)
                    .collect(Collectors.toList())));
        }

        return schemeList;
    }

    public List<Villains> getVillainsList() {
        if (villainsList.isEmpty()) {

            final VillainsDao villainsDao = LegendaryDatabase.getInstance().villainsDao();

            new DbAsyncTask(() -> villainsList.addAll(villainsDao
                    .getAllBySetIdSync(getId())
                    .stream()
                    .map(Villains::new)
                    .collect(Collectors.toList())));
        }

        return villainsList;
    }

    public List<Henchmen> getHenchmenList() {
        if (henchmenList.isEmpty()) {

            final HenchmenDao henchmenDao = LegendaryDatabase.getInstance().henchmenDao();

            new DbAsyncTask(() -> henchmenList.addAll(henchmenDao
                    .getAllBySetIdSync(getId())
                    .stream()
                    .map(Henchmen::new)
                    .collect(Collectors.toList())));
        }

        return henchmenList;
    }

    public List<Hero> getHeroList() {
        if (heroList.isEmpty()) {

            final HeroDao heroDao = LegendaryDatabase.getInstance().heroDao();

            new DbAsyncTask(() -> heroList.addAll(heroDao
                    .getAllBySetIdSync(getId())
                    .stream()
                    .map(Hero::new)
                    .collect(Collectors.toList())));
        }

        return heroList;
    }

    public void dbSave() {
        new DbAsyncTask(() -> {

            final List<Mastermind> mastermindsInSet = getMastermindList();
            final List<Scheme> schemesInSet = getSchemeList();
            final List<Villains> villainsInSet = getVillainsList();
            final List<Henchmen> henchmenInSet = getHenchmenList();
            final List<Hero> heroesInSet = getHeroList();

            mastermindsInSet.stream().forEach(mastermind -> mastermind.setEnabled(isEnabled()));
            schemesInSet.stream().forEach(scheme -> scheme.setEnabled(isEnabled()));
            villainsInSet.stream().forEach(villains -> villains.setEnabled(isEnabled()));
            henchmenInSet.stream().forEach(henchmen -> henchmen.setEnabled(isEnabled()));
            heroesInSet.stream().forEach(heroes -> heroes.setEnabled(isEnabled()));

            final LegendaryDatabase db = LegendaryDatabase.getInstance();

            db.mastermindDao().update(mastermindsInSet
                    .stream()
                    .map(Mastermind::toEntity)
                    .collect(Collectors.toList()));
            db.schemeDao().update(schemesInSet
                    .stream()
                    .map(Scheme::toEntity)
                    .collect(Collectors.toList()));
            db.villainsDao().update(villainsInSet
                    .stream()
                    .map(Villains::toEntity)
                    .collect(Collectors.toList()));
            db.henchmenDao().update(henchmenInSet
                    .stream()
                    .map(Henchmen::toEntity)
                    .collect(Collectors.toList()));
            db.heroDao().update(heroesInSet
                    .stream()
                    .map(Hero::toEntity)
                    .collect(Collectors.toList()));

            dbSave(LegendaryDatabase.getInstance().gameSetDao(), toEntity());
        });
    }

    public void dbDelete() {
        dbDelete(LegendaryDatabase.getInstance().gameSetDao(), toEntity());
    }

    @Override
    public boolean equals(final Object o) {

        if (!(o instanceof GameSet)) {
            return false;
        }

        return super.equals(o);
    }
}
