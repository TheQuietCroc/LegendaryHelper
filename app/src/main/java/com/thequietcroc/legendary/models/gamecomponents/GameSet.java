package com.thequietcroc.legendary.models.gamecomponents;

import android.os.AsyncTask;

import com.thequietcroc.legendary.database.LegendaryDatabase;
import com.thequietcroc.legendary.database.entities.gamecomponents.GameSetEntity;
import com.thequietcroc.legendary.models.gamecomponents.cards.Henchmen;
import com.thequietcroc.legendary.models.gamecomponents.cards.Hero;
import com.thequietcroc.legendary.models.gamecomponents.cards.Mastermind;
import com.thequietcroc.legendary.models.gamecomponents.cards.Scheme;
import com.thequietcroc.legendary.models.gamecomponents.cards.Villains;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class GameSet extends BaseGameComponent {

    private final List<Mastermind> mastermindList = Collections.synchronizedList(new ArrayList<>());
    private final List<Scheme> schemeList = Collections.synchronizedList(new ArrayList<>());
    private final List<Villains> villainsList = Collections.synchronizedList(new ArrayList<>());
    private final List<Henchmen> henchmenList = Collections.synchronizedList(new ArrayList<>());
    private final List<Hero> heroList = Collections.synchronizedList(new ArrayList<>());

    public GameSet() {
        super();
    }

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
            AsyncTask.execute(() -> mastermindList.addAll(
                    LegendaryDatabase.getInstance()
                            .mastermindDao()
                            .getAllBySetIdSync(getId())
                            .stream()
                            .map(Mastermind::new)
                            .collect(Collectors.toList())));
        }

        return mastermindList;
    }

    public List<Scheme> getSchemeList() {
        if (schemeList.isEmpty()) {
            AsyncTask.execute(() -> schemeList.addAll(
                    LegendaryDatabase.getInstance()
                            .schemeDao()
                            .getAllBySetIdSync(getId())
                            .stream()
                            .map(Scheme::new)
                            .collect(Collectors.toList())));
        }

        return schemeList;
    }

    public List<Villains> getVillainsList() {
        if (villainsList.isEmpty()) {
            AsyncTask.execute(() -> villainsList.addAll(
                    LegendaryDatabase.getInstance()
                            .villainsDao()
                            .getAllBySetIdSync(getId())
                            .stream()
                            .map(Villains::new)
                            .collect(Collectors.toList())));
        }

        return villainsList;
    }

    public List<Henchmen> getHenchmenList() {
        if (henchmenList.isEmpty()) {
            AsyncTask.execute(() -> henchmenList.addAll(
                    LegendaryDatabase.getInstance()
                            .henchmenDao()
                            .getAllBySetIdSync(getId())
                            .stream()
                            .map(Henchmen::new)
                            .collect(Collectors.toList())));
        }

        return henchmenList;
    }

    public List<Hero> getHeroList() {
        if (heroList.isEmpty()) {
            AsyncTask.execute(() -> heroList.addAll(
                    LegendaryDatabase.getInstance()
                            .heroDao()
                            .getAllBySetIdSync(getId())
                            .stream()
                            .map(Hero::new)
                            .collect(Collectors.toList())));
        }

        return heroList;
    }

    @Override
    public boolean equals(final Object o) {

        if (!(o instanceof GameSet)) {
            return false;
        }

        return super.equals(o);
    }
}
