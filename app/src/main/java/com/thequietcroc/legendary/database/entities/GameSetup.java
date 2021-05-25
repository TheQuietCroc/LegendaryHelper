package com.thequietcroc.legendary.database.entities;

import com.thequietcroc.legendary.custom.views.CardControl;
import com.thequietcroc.legendary.database.LegendaryDatabase;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class GameSetup {
    private Mastermind mastermind;
    private Scheme scheme;
    private Villains villains1;
    private Villains villains2;
    private Villains villains3;
    private Villains villains4;
    private Henchmen henchmen1;
    private Henchmen henchmen2;
    private Hero hero1;
    private Hero hero2;
    private Hero hero3;
    private Hero hero4;
    private Hero hero5;
    private Hero hero6;

    private final LegendaryDatabase db;

    private final List<Mastermind> mastermindList;
    private final List<Scheme> schemeList;
    private final List<Villains> villainsList;
    private final List<Henchmen> henchmenList;
    private final List<Hero> heroList;

    private final CardControl mastermindControl;
    private final CardControl schemeControl;
    private final CardControl[] villainsControls;
    private final CardControl[] henchmenControls;
    private final CardControl[] heroControls;

    public GameSetup(final LegendaryDatabase db,
                     final List<Mastermind> mastermindList,
                     final List<Scheme> schemeList,
                     final List<Villains> villainsList,
                     final List<Henchmen> henchmenList,
                     final List<Hero> heroList,
                     final CardControl mastermindControl,
                     final CardControl schemeControl,
                     final CardControl[] villainsControls,
                     final CardControl[] henchmenControls,
                     final CardControl[] heroControls) {
        this.db = db;
        this.mastermindList = mastermindList;
        this.schemeList = schemeList;
        this.villainsList = villainsList;
        this.henchmenList = henchmenList;
        this.heroList = heroList;
        this.mastermindControl = mastermindControl;
        this.schemeControl = schemeControl;
        this.villainsControls = villainsControls;
        this.henchmenControls = henchmenControls;
        this.heroControls = heroControls;
    }

    public void setMastermind(final Mastermind mastermind) {
        this.mastermind = mastermind;
    }

    public void setScheme(final Scheme scheme) {
        this.scheme = scheme;
    }

    public void setVillains1(final Villains villains1) {
        this.villains1 = villains1;
    }

    public void setVillains2(final Villains villains2) {
        this.villains2 = villains2;
    }

    public void setVillains3(final Villains villains3) {
        this.villains3 = villains3;
    }

    public void setVillains4(final Villains villains4) {
        this.villains4 = villains4;
    }

    public void setHenchmen1(final Henchmen henchmen1) {
        this.henchmen1 = henchmen1;
    }

    public void setHenchmen2(final Henchmen henchmen2) {
        this.henchmen2 = henchmen2;
    }

    public void setHero1(final Hero hero1) {
        this.hero1 = hero1;
    }

    public void setHero2(final Hero hero2) {
        this.hero2 = hero2;
    }

    public void setHero3(final Hero hero3) {
        this.hero3 = hero3;
    }

    public void setHero4(final Hero hero4) {
        this.hero4 = hero4;
    }

    public void setHero5(final Hero hero5) {
        this.hero5 = hero5;
    }

    public void setHero6(final Hero hero6) {
        this.hero6 = hero6;
    }

    public void newSetup() {
        setMastermind((Mastermind) selectRandomlyFromList(mastermindList));
        mastermindControl.getSpinner().setSelection(mastermindList.indexOf(mastermind));

        setScheme((Scheme) selectRandomlyFromList(schemeList));
        schemeControl.getSpinner().setSelection(schemeList.indexOf(scheme));

        final Set<Villains> selectedVillains = new LinkedHashSet<>();

        if (mastermind.getVillainId() != null && mastermind.getVillainId() > 0) {
            selectedVillains.add(db.villainsDao().findByIdSync(mastermind.getVillainId()));
        }

        for (int i = selectedVillains.size(); i < 4; ++i) {
            while (!selectedVillains.add((Villains) selectRandomlyFromList(villainsList)));
        }

        setVillains1((Villains) selectedVillains.toArray()[0]);
        villainsControls[0].getSpinner().setSelection(villainsList.indexOf(villains1));

        setVillains2((Villains) selectedVillains.toArray()[1]);
        villainsControls[1].getSpinner().setSelection(villainsList.indexOf(villains2));

        setVillains3((Villains) selectedVillains.toArray()[2]);
        villainsControls[2].getSpinner().setSelection(villainsList.indexOf(villains3));

        setVillains4((Villains) selectedVillains.toArray()[3]);
        villainsControls[3].getSpinner().setSelection(villainsList.indexOf(villains4));

        final Set<Henchmen> selectedHenchmen = new LinkedHashSet<>();

        if (mastermind.getHenchmenId() != null && mastermind.getHenchmenId() > 0) {
            selectedHenchmen.add(db.henchmenDao().findByIdSync(mastermind.getHenchmenId()));
        }

        for (int i = selectedHenchmen.size(); i < 2; ++i) {
            while (!selectedHenchmen.add((Henchmen) selectRandomlyFromList(henchmenList)));
        }

        setHenchmen1((Henchmen) selectedHenchmen.toArray()[0]);
        henchmenControls[0].getSpinner().setSelection(henchmenList.indexOf(henchmen1));

        setHenchmen2((Henchmen) selectedHenchmen.toArray()[1]);
        henchmenControls[1].getSpinner().setSelection(henchmenList.indexOf(henchmen2));

        setHero1((Hero) selectRandomlyFromList(heroList));
        heroControls[0].getSpinner().setSelection(heroList.indexOf(hero1));

        setHero2((Hero) selectRandomlyFromList(heroList));
        heroControls[1].getSpinner().setSelection(heroList.indexOf(hero2));

        setHero3((Hero) selectRandomlyFromList(heroList));
        heroControls[2].getSpinner().setSelection(heroList.indexOf(hero3));

        setHero4((Hero) selectRandomlyFromList(heroList));
        heroControls[3].getSpinner().setSelection(heroList.indexOf(hero4));

        setHero5((Hero) selectRandomlyFromList(heroList));
        heroControls[4].getSpinner().setSelection(heroList.indexOf(hero5));

        setHero6((Hero) selectRandomlyFromList(heroList));
        heroControls[5].getSpinner().setSelection(heroList.indexOf(hero6));
    }

    private BaseCard selectRandomlyFromList(final List<? extends BaseCard> cardList) {
        return cardList.get(new Random().nextInt(cardList.size()));
    }
}
