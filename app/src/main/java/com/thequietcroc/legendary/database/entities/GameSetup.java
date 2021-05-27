package com.thequietcroc.legendary.database.entities;

import android.view.View;

import com.thequietcroc.legendary.custom.views.CardControl;
import com.thequietcroc.legendary.database.LegendaryDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class GameSetup {
    private Mastermind selectedMastermind;
    private Scheme selectedScheme;
    private List<Villains> selectedVillainsList = new ArrayList<>(Arrays.asList(null, null, null, null));
    private List<Henchmen> selectedHenchmenList = new ArrayList<>(Arrays.asList(null, null));
    private List<Hero> selectedHeroList = new ArrayList<>(Arrays.asList(null, null, null, null, null, null));

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

//        initializeControls();
    }

//    private void setSelectedMastermind(final Mastermind selectedMastermind) {
//        this.selectedMastermind = selectedMastermind;
//    }

//    private void initializeControls() {
//
//    }
//
//    private <T> void setOnItemSelectListener(final CardControl cardControl,
//                                             final List<T> selectedList,
//                                             final int selectedIndex) {
//        cardControl.getSpinner().setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                cardControl.getToggleLock().setEnabled(position > 0);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//    }

    public void newSetup() {

        selectedMastermind = setupHelper(mastermindControl, selectedMastermind, filteredMastermindList);
        selectedScheme = setupHelper(schemeControl, selectedScheme, filteredSchemeList);
        setupVillains();
        setupHenchmen();
        setupHeroes();
    }

    private void setupVillains() {

        setupHelper(villainsControlList, filteredVillainsList, selectedVillainsList);
        selectAlwaysLeadsVillains();
    }

    private void setupHenchmen() {

        setupHelper(henchmenControlList, filteredHenchmenList, selectedHenchmenList);
        selectAlwaysLeadsHenchmen();
    }

    private void setupHeroes() {

        setupHelper(heroControlList, filteredHeroList, selectedHeroList);
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

    private void selectAlwaysLeadsHenchmen() {

        final int alwaysLeadsHenchmenId = selectedMastermind.getHenchmenId();

        if (alwaysLeadsHenchmenId > 0) {

            selectAlwaysLeadsHelper(db.henchmenDao().findByIdSync(alwaysLeadsHenchmenId),
                    selectedHenchmenList,
                    filteredHenchmenList,
                    henchmenControlList);
        }
    }

    private <T extends BaseCard> T setupHelper(final CardControl control,
                                               final T selectedCard,
                                               final List<T> filteredList) {
        final T newSelectedCard;

        if (!control.getToggleLock().isChecked()) {

            newSelectedCard = (T) selectRandomlyFromList(filteredList);
            control.getSpinner().setSelection(filteredList.indexOf(newSelectedCard));
        } else {
            newSelectedCard = selectedCard;
        }

        return newSelectedCard;
    }

    private <T extends BaseCard> void setupHelper(final List<CardControl> controlList,
                                 final List<T> filteredList,
                                 final List<T> selectedList) {

        for (int i = 0; i < controlList.size(); ++i) {
            final CardControl control = controlList.get(i);

            if (control.getToggleLock().getVisibility() == View.INVISIBLE) {
                toggleControlLock(false, control);
            }

            if (!control.getToggleLock().isChecked()) {
                T randomSelection;

                do {
                    randomSelection = (T) selectRandomlyFromList(filteredList);
                } while (selectedList.contains(randomSelection));

                selectedList.add(i, randomSelection);
                selectedList.remove(i + 1);
            }
        }

        for (int i = 0; i < controlList.size(); ++i) {
            final CardControl control = controlList.get(i);
            final int selectedIndex = filteredList.indexOf(selectedList.get(i));

            control.getSpinner().setSelection(selectedIndex);
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
