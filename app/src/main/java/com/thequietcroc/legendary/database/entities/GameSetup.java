package com.thequietcroc.legendary.database.entities;

import android.view.View;

import com.thequietcroc.legendary.custom.views.CardControl;
import com.thequietcroc.legendary.database.LegendaryDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class GameSetup {
    private Mastermind selectedMastermind;
    private Scheme selectedScheme;
    private List<Villains> selectedVillainsList = new ArrayList<>(Arrays.asList(null, null, null, null));
    private List<Henchmen> selectedHenchmenList = new ArrayList<>(Arrays.asList(null, null));
    private Set<Hero> selectedHeroSet = new LinkedHashSet<>();

    private final LegendaryDatabase db;

    private final List<Mastermind> filteredMastermindList;
    private final List<Scheme> filteredSchemeList;
    private final List<Villains> filteredVillainsList;
    private final List<Henchmen> filteredHenchmenList;
    private final List<Hero> filteredHeroList;

    private final CardControl mastermindControl;
    private final CardControl schemeControl;
    private final List<CardControl> villainsControlList;
    private final List<CardControl> henchmenControlList;
    private final List<CardControl> heroControlList;

    public GameSetup(final LegendaryDatabase db,
                     final List<Mastermind> filteredMastermindList,
                     final List<Scheme> filteredSchemeList,
                     final List<Villains> filteredVillainList,
                     final List<Henchmen> filteredHenchmenList,
                     final List<Hero> filteredHeroList,
                     final CardControl mastermindControl,
                     final CardControl schemeControl,
                     final List<CardControl> villainsControlList,
                     final List<CardControl> henchmenControlList,
                     final List<CardControl> heroControlList) {
        this.db = db;

        this.filteredMastermindList = filteredMastermindList;
        this.filteredSchemeList = filteredSchemeList;
        this.filteredVillainsList = filteredVillainList;
        this.filteredHenchmenList = filteredHenchmenList;
        this.filteredHeroList = filteredHeroList;

        this.mastermindControl = mastermindControl;
        this.schemeControl = schemeControl;
        this.villainsControlList = villainsControlList;
        this.henchmenControlList = henchmenControlList;
        this.heroControlList = heroControlList;
    }

    public void newSetup() {

        setupMastermind();
        setupScheme();
        setupVillains();
        setupHenchmen();
        setupHeroes();
    }

    private void setupMastermind() {
        if (!mastermindControl.getToggleLock().isChecked()) {

            selectedMastermind = (Mastermind) selectRandomlyFromList(filteredMastermindList);
            mastermindControl.getSpinner().setSelection(filteredMastermindList.indexOf(selectedMastermind));
        }
    }

    private void setupScheme() {
        if (!schemeControl.getToggleLock().isChecked()) {

            selectedScheme = (Scheme) selectRandomlyFromList(filteredSchemeList);
            schemeControl.getSpinner().setSelection(filteredSchemeList.indexOf(selectedScheme));
        }
    }

    private void setupVillains() {

        for (int i = 0; i < villainsControlList.size(); ++i) {
            final CardControl villainControl = villainsControlList.get(i);

            if (villainControl.getToggleLock().getVisibility() == View.INVISIBLE) {
                toggleControlLock(false, villainControl);
            }

            if (!villainControl.getToggleLock().isChecked()) {
                Villains randomVillain;

                do {
                    randomVillain = (Villains) selectRandomlyFromList(filteredVillainsList);
                } while (selectedVillainsList.contains(randomVillain));

                selectedVillainsList.add(i, randomVillain);
                selectedVillainsList.remove(i + 1);
            }
        }

        for (int i = 0; i < villainsControlList.size(); ++i) {
            final CardControl villainControl = villainsControlList.get(i);
            final int selectedVillainsIndex = filteredVillainsList.indexOf(selectedVillainsList.get(i));

            villainControl.getSpinner().setSelection(selectedVillainsIndex);
        }

        selectAlwaysLeadsVillains();
    }

    private void selectAlwaysLeadsVillains() {

        final int alwaysLeadsVillainsId = selectedMastermind.getVillainId();

        if (alwaysLeadsVillainsId > 0) {

            selectAlwaysLeadsHelper(db.villainsDao().findByIdSync(alwaysLeadsVillainsId),
                    selectedVillainsList,
                    filteredVillainsList,
                    villainsControlList);
        }
    }

    private void setupHenchmen() {

        for (int i = 0; i < henchmenControlList.size(); ++i) {
            final CardControl henchmenControl = henchmenControlList.get(i);

            if (henchmenControl.getToggleLock().getVisibility() == View.INVISIBLE) {
                toggleControlLock(false, henchmenControl);
            }

            if (!henchmenControl.getToggleLock().isChecked()) {
                Henchmen randomHenchmen;

                do {
                    randomHenchmen = (Henchmen) selectRandomlyFromList(filteredHenchmenList);
                } while (selectedHenchmenList.contains(randomHenchmen));

                selectedHenchmenList.add(i, randomHenchmen);
                selectedHenchmenList.remove(i + 1);
            }
        }

        for (int i = 0; i < henchmenControlList.size(); ++i) {
            final CardControl henchmenControl = henchmenControlList.get(i);
            final int selectedHenchmenIndex = filteredHenchmenList.indexOf(selectedHenchmenList.get(i));

            henchmenControl.getSpinner().setSelection(selectedHenchmenIndex);
        }

        selectAlwaysLeadsHenchmen();
    }

    private void selectAlwaysLeadsHenchmen() {

        final int alwaysLeadsHenchmenId = selectedMastermind.getHenchmenId();

        if (alwaysLeadsHenchmenId > 0) {

            selectAlwaysLeadsHelper(db.henchmenDao().findByIdSync(alwaysLeadsHenchmenId),
                    selectedHenchmenList,
                    filteredHenchmenList,
                    henchmenControlList);
        }
    }

    private <T> void selectAlwaysLeadsHelper(final T alwaysLeads,
                                             final List<T> selectedList,
                                             final List<T> filteredList,
                                             final List<CardControl> cardControlList) {
        final int filteredIndex = filteredList.indexOf(alwaysLeads);
        int alwaysLeadsIndex = selectedList.indexOf(alwaysLeads);

        if (alwaysLeadsIndex == -1) {

            for (int i = 0; i < cardControlList.size(); ++i) {

                final CardControl control = cardControlList.get(i);

                if (!control.getToggleLock().isChecked()) {

                    selectedList.add(i, alwaysLeads);
                    selectedList.remove(i + 1);

                    alwaysLeadsIndex = i;

                    break;
                }
            }

            if (alwaysLeadsIndex == -1) {
                final CardControl control = cardControlList.get(0);

                selectedList.add(0, alwaysLeads);
                selectedList.remove(1);

                alwaysLeadsIndex = 0;
            }
        }

        final CardControl control = cardControlList.get(alwaysLeadsIndex);

        control.getSpinner().setSelection(filteredIndex);
        toggleControlLock(true, control);
    }

    private void setupHeroes() {
        selectedHeroSet.clear();

        for (final CardControl heroControl : heroControlList) {
            if (heroControl.getToggleLock().isChecked()) {
                selectedHeroSet.add((Hero) heroControl.getSpinner().getSelectedItem());
            } else {
                while (!selectedHeroSet.add((Hero) selectRandomlyFromList(filteredHeroList))) ;
            }
        }

        for (int i = 0; i < heroControlList.size(); ++i) {
            final CardControl heroControl = heroControlList.get(i);
            final Hero hero = (Hero) selectedHeroSet.toArray()[i];

            heroControl.getSpinner().setSelection(filteredHeroList.indexOf(hero));
        }
    }

    private BaseCard selectRandomlyFromList(final List<? extends BaseCard> cardList) {
        return cardList.get(new Random().nextInt(cardList.size() - 1) + 1);
    }

    private void toggleControlLock(final boolean isLocked, final CardControl control) {
        if (isLocked) {
            control.getToggleLock().setChecked(true);
            control.getToggleLock().setVisibility(View.INVISIBLE);
        } else if (control.getToggleLock().getVisibility() == View.INVISIBLE) {
            control.getToggleLock().setChecked(false);
            control.getToggleLock().setVisibility(View.VISIBLE);
        }
    }
}
