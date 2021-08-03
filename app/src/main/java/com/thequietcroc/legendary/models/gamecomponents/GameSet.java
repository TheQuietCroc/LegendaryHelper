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
        if (getId() != null && mastermindList.isEmpty()) {

            final MastermindDao mastermindDao = LegendaryDatabase.getInstance().mastermindDao();

            new DbAsyncTask(() -> mastermindList.addAll(mastermindDao
                    .getAllBySetId(getId())
                    .stream()
                    .map(Mastermind::new)
                    .collect(Collectors.toList())));
        }

        return mastermindList;
    }

    public List<Scheme> getSchemeList() {
        if (getId() != null && schemeList.isEmpty()) {

            final SchemeDao schemeDao = LegendaryDatabase.getInstance().schemeDao();

            new DbAsyncTask(() -> schemeList.addAll(schemeDao
                    .getAllBySetId(getId())
                    .stream()
                    .map(Scheme::new)
                    .collect(Collectors.toList())));
        }

        return schemeList;
    }

    public List<Villains> getVillainsList() {
        if (getId() != null && villainsList.isEmpty()) {

            final VillainsDao villainsDao = LegendaryDatabase.getInstance().villainsDao();

            new DbAsyncTask(() -> villainsList.addAll(villainsDao
                    .getAllBySetId(getId())
                    .stream()
                    .map(Villains::new)
                    .collect(Collectors.toList())));
        }

        return villainsList;
    }

    public List<Henchmen> getHenchmenList() {
        if (getId() != null && henchmenList.isEmpty()) {

            final HenchmenDao henchmenDao = LegendaryDatabase.getInstance().henchmenDao();

            new DbAsyncTask(() -> henchmenList.addAll(henchmenDao
                    .getAllBySetId(getId())
                    .stream()
                    .map(Henchmen::new)
                    .collect(Collectors.toList())));
        }

        return henchmenList;
    }

    public List<Hero> getHeroList() {
        if (getId() != null && heroList.isEmpty()) {

            final HeroDao heroDao = LegendaryDatabase.getInstance().heroDao();

            new DbAsyncTask(() -> heroList.addAll(heroDao
                    .getAllBySetId(getId())
                    .stream()
                    .map(Hero::new)
                    .collect(Collectors.toList())));
        }

        return heroList;
    }

    public void dbSave() {
        dbSave(LegendaryDatabase.getInstance().gameSetDao(), toEntity());
    }

    public void dbDelete() {
        dbDelete(LegendaryDatabase.getInstance().gameSetDao(), toEntity());
    }

    public boolean areAllItemsEnabled() {
        return getMastermindList().stream().anyMatch(BaseGameComponent::isEnabled)
                || getSchemeList().stream().anyMatch(BaseGameComponent::isEnabled)
                || getVillainsList().stream().anyMatch(BaseGameComponent::isEnabled)
                || getHenchmenList().stream().anyMatch(BaseGameComponent::isEnabled)
                || getHeroList().stream().anyMatch(BaseGameComponent::isEnabled);
    }

    @Override
    public boolean equals(final Object o) {

        if (!(o instanceof GameSet)) {
            return false;
        }

        return super.equals(o);
    }
}
