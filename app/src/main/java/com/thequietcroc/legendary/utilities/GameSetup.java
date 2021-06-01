package com.thequietcroc.legendary.utilities;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.ViewCompat;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.thequietcroc.legendary.R;
import com.thequietcroc.legendary.custom.views.CardControl;
import com.thequietcroc.legendary.database.LegendaryDatabase;
import com.thequietcroc.legendary.database.entities.gamecomponents.cards.BaseCardEntity;
import com.thequietcroc.legendary.database.entities.gamecomponents.cards.HenchmenEntity;
import com.thequietcroc.legendary.database.entities.gamecomponents.cards.HeroEntity;
import com.thequietcroc.legendary.database.entities.gamecomponents.cards.MastermindEntity;
import com.thequietcroc.legendary.database.entities.gamecomponents.cards.SchemeEntity;
import com.thequietcroc.legendary.database.entities.gamecomponents.cards.VillainsEntity;
import com.thequietcroc.legendary.enums.ItemType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static com.thequietcroc.legendary.enums.ItemType.HENCHMEN;
import static com.thequietcroc.legendary.enums.ItemType.HERO;
import static com.thequietcroc.legendary.enums.ItemType.VILLAINS;

public class GameSetup {

    private int numPlayers;

    private MastermindEntity selectedMastermind;
    private SchemeEntity selectedScheme;
    private final List<VillainsEntity> selectedVillainsList;
    private final List<HenchmenEntity> selectedHenchmenList;
    private final List<HeroEntity> selectedHeroList;

    private final LegendaryDatabase db;

    private final List<MastermindEntity> filteredMastermindList;
    private final List<SchemeEntity> filteredSchemeList;
    private final List<VillainsEntity> filteredVillainsList;
    private final List<HenchmenEntity> filteredHenchmenList;
    private final List<HeroEntity> filteredHeroList;

    final ConstraintLayout containerVillains;
    final ConstraintLayout containerHenchmen;
    final ConstraintLayout containerHero;

    private final CardControl mastermindControl;
    private final CardControl schemeControl;
    private final List<CardControl> villainsControlList = new ArrayList<>();
    private final List<CardControl> henchmenControlList = new ArrayList<>();
    private final List<CardControl> heroControlList = new ArrayList<>();

    private final MaterialButtonToggleGroup buttonGroupPlayers;

    public GameSetup(final int numPlayers,
                     final MaterialButtonToggleGroup buttonGroupPlayers,
                     final LegendaryDatabase db,
                     final List<MastermindEntity> filteredMastermindList,
                     final List<SchemeEntity> filteredSchemeList,
                     final List<VillainsEntity> filteredVillainsList,
                     final List<HenchmenEntity> filteredHenchmenList,
                     final List<HeroEntity> filteredHeroList,
                     final CardControl mastermindControl,
                     final CardControl schemeControl,
                     final ConstraintLayout containerVillains,
                     final ConstraintLayout containerHenchmen,
                     final ConstraintLayout containerHero) {
        this.numPlayers = numPlayers;

        this.buttonGroupPlayers = buttonGroupPlayers;

        this.db = db;

        this.filteredMastermindList = filteredMastermindList;
        this.filteredSchemeList = filteredSchemeList;
        this.filteredVillainsList = filteredVillainsList;
        this.filteredHenchmenList = filteredHenchmenList;
        this.filteredHeroList = filteredHeroList;

        this.mastermindControl = mastermindControl;
        this.schemeControl = schemeControl;

        this.containerVillains = containerVillains;
        this.containerHenchmen = containerHenchmen;
        this.containerHero = containerHero;

        selectedMastermind = filteredMastermindList.get(0);
        selectedScheme = filteredSchemeList.get(0);
        selectedVillainsList = new ArrayList<>(Collections.nCopies(villainsControlList.size(), filteredVillainsList.get(0)));
        selectedHenchmenList = new ArrayList<>(Collections.nCopies(henchmenControlList.size(), filteredHenchmenList.get(0)));
        selectedHeroList = new ArrayList<>(Collections.nCopies(heroControlList.size(), filteredHeroList.get(0)));

        initializeControls();
    }

    private void setNumPlayers(final int numPlayers) {
        this.numPlayers = numPlayers;
    }

    private void setSelectedMastermind(final MastermindEntity selectedMastermind) {
        this.selectedMastermind = selectedMastermind;
    }

    private void setSelectedScheme(final SchemeEntity selectedScheme) {
        this.selectedScheme = selectedScheme;
    }

    private <T> void setSelectedCard(final T selectedCard) {
        if (selectedCard instanceof MastermindEntity) {
            setSelectedMastermind((MastermindEntity) selectedCard);
        } else if (selectedCard instanceof SchemeEntity) {
            setSelectedScheme((SchemeEntity) selectedCard);
        }
    }

    private void initializeControls() {

        for (int i = 0; i < buttonGroupPlayers.getChildCount(); ++i) {

            final MaterialButton button = (MaterialButton) buttonGroupPlayers.getChildAt(i);

            button.setOnClickListener(v -> {

                setNumPlayers(Integer.parseInt((String) ((MaterialButton) v).getText()));

                adjustContainedControls();
            });
        }

        initializeSpinner(mastermindControl.getSpinner(), filteredMastermindList);
        initializeSpinner(schemeControl.getSpinner(), filteredSchemeList);

        adjustContainedControls();
    }

    private void setOnItemSelectedListener(final Spinner spinner) {

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                final int prevPosition = (Integer) parent.getTag();

                if (prevPosition != position) {

                    setSelectedCard(parent.getSelectedItem());

                    ((CardControl) parent.getParent()).getToggleLock().setEnabled(position > 0);
                }

                parent.setTag(position);

                if (parent.getSelectedItem() instanceof MastermindEntity) {
                    selectAlwaysLeadsVillains();
                    selectAlwaysLeadsHenchmen();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private <T> void setOnItemSelectedListener(final Spinner spinner,
                                               final List<T> selectedList,
                                               final int selectedIndex) {

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                final int prevPosition = (Integer) parent.getTag();

                if (prevPosition != position) {

                    final T selectedCard = (T) parent.getSelectedItem();

                    if (position > 0
                            && selectedList.contains(selectedCard)) {

                        parent.setSelection(prevPosition);
                    } else {

                        selectedList.add(selectedIndex, (T) parent.getSelectedItem());
                        selectedList.remove(selectedIndex + 1);
                    }
                }

                final int selectedItemPosition = parent.getSelectedItemPosition();

                ((CardControl) parent.getParent()).getToggleLock().setEnabled(selectedItemPosition > 0);
                parent.setTag(selectedItemPosition);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

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
        if (numPlayers > 1) {
            final int alwaysLeadsVillainsId = selectedMastermind.getVillainId();

            if (alwaysLeadsVillainsId > 0) {

                selectAlwaysLeadsHelper(db.villainsDao().findByIdSync(alwaysLeadsVillainsId),
                        selectedVillainsList,
                        filteredVillainsList,
                        villainsControlList);
            }
        }
    }

    private void selectAlwaysLeadsHenchmen() {
        if (numPlayers > 1) {
            final int alwaysLeadsHenchmenId = selectedMastermind.getHenchmenId();

            if (alwaysLeadsHenchmenId > 0) {

                selectAlwaysLeadsHelper(db.henchmenDao().findByIdSync(alwaysLeadsHenchmenId),
                        selectedHenchmenList,
                        filteredHenchmenList,
                        henchmenControlList);
            }
        }
    }

    private <T extends BaseCardEntity> T setupHelper(final CardControl control,
                                                     final T selectedCard,
                                                     final List<T> filteredList) {
        final T newSelectedCard;

        if (!control.getToggleLock().isChecked()) {

            newSelectedCard = (T) selectRandomlyFromList(filteredList);

            final int filteredIndex = filteredList.indexOf(newSelectedCard);

            control.getSpinner().setTag(filteredIndex);
            control.getSpinner().setSelection(filteredIndex);
        } else {
            newSelectedCard = selectedCard;
        }

        return newSelectedCard;
    }

    private <T extends BaseCardEntity> void setupHelper(final List<CardControl> controlList,
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

            control.getSpinner().setTag(selectedIndex);
            control.getSpinner().setSelection(selectedIndex);
        }
    }

    private <T> void selectAlwaysLeadsHelper(final T alwaysLeads,
                                             final List<T> selectedList,
                                             final List<T> filteredList,
                                             final List<CardControl> cardControlList) {
        for (final CardControl control : cardControlList) {
            if (control.getToggleLock().getVisibility() == View.INVISIBLE) {
                toggleControlLock(false, control);
            }
        }

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

                selectedList.add(0, alwaysLeads);
                selectedList.remove(1);

                alwaysLeadsIndex = 0;
            }
        }

        final CardControl control = cardControlList.get(alwaysLeadsIndex);

        control.getSpinner().setTag(filteredIndex);
        control.getSpinner().setSelection(filteredIndex);
        toggleControlLock(true, control);
    }

    private BaseCardEntity selectRandomlyFromList(final List<? extends BaseCardEntity> cardList) {
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

    private void adjustContainedControls() {

        switch (numPlayers) {
            case 5: {
                // 4 villain groups
                // 2 henchmen groups
                // 6 hero groups
                adjustContainedControls(4, VILLAINS);
                adjustContainedControls(2, HENCHMEN);
                adjustContainedControls(6, HERO);
            }
            break;
            case 4: {
                // 3 villain groups
                // 2 henchmen groups
                // 5 heroes
                adjustContainedControls(3, VILLAINS);
                adjustContainedControls(2, HENCHMEN);
                adjustContainedControls(5, HERO);
            }
            break;
            case 3: {
                // 3 villain groups
                // 1 henchmen group
                // 5 heroes
                adjustContainedControls(3, VILLAINS);
                adjustContainedControls(1, HENCHMEN);
                adjustContainedControls(5, HERO);
            }
            break;
            case 2: {
                // 2 villain groups
                // 1 henchmen group
                // 5 heroes
                adjustContainedControls(2, VILLAINS);
                adjustContainedControls(1, HENCHMEN);
                adjustContainedControls(5, HERO);
            }
            break;
            case 1: {
                // 1 villain group
                // 1 henchmen group
                // 3 heroes
                adjustContainedControls(1, VILLAINS);
                adjustContainedControls(1, HENCHMEN);
                adjustContainedControls(3, HERO);
            }
            break;
            default:
                break;
        }
    }

    private void adjustContainedControls(final int numVisible,
                                         final ItemType type) {
        switch (type) {
            case HERO: {
                adjustContainedControlsHelper(numVisible,
                        heroControlList,
                        containerHero,
                        filteredHeroList,
                        selectedHeroList);

            }
            break;
            case VILLAINS: {
                adjustContainedControlsHelper(numVisible,
                        villainsControlList,
                        containerVillains,
                        filteredVillainsList,
                        selectedVillainsList);
            }
            break;
            case HENCHMEN: {
                adjustContainedControlsHelper(numVisible,
                        henchmenControlList,
                        containerHenchmen,
                        filteredHenchmenList,
                        selectedHenchmenList);
            }
            break;
        }
    }

    private <T> void adjustContainedControlsHelper(final int numVisible,
                                                   final List<CardControl> controlList,
                                                   final ConstraintLayout container,
                                                   final List<T> filteredList,
                                                   final List<T> selectedList) {

        if (controlList.size() < numVisible) {
            final int numToAdd = numVisible - controlList.size();

            for (int i = 0; i < numToAdd; ++i) {
                final CardControl control = createCardControl(container);
                initializeSpinner(control.getSpinner(),
                        filteredList,
                        selectedList,
                        i + controlList.size());

                container.addView(control);
                controlList.add(control);

                if (selectedList.size() < controlList.size()) {
                    selectedList.add((T) control.getSpinner().getSelectedItem());
                }
            }
        } else if (controlList.size() > numVisible) {
            final int numToRemove = controlList.size() - numVisible;

            container.removeViews(numVisible, numToRemove);
            controlList.removeAll(controlList.subList(numVisible, controlList.size()));
        }

        for (int i = 0; i < controlList.size(); ++i) {
            final T selected = selectedList.get(i);
            final Spinner spinner = controlList.get(i).getSpinner();
            final int selectedIndex = filteredList.indexOf(selected);

            spinner.setTag(selectedIndex);
            spinner.setSelection(selectedIndex);
        }
    }

    private CardControl createCardControl(final ConstraintLayout parent) {

        final CardControl currentBottomControl = (CardControl) parent.getChildAt(parent.getChildCount() - 1);
        final CardControl cardControl = new CardControl(parent.getContext());

        cardControl.setId(ViewCompat.generateViewId());

        final ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);

        layoutParams.topMargin = 16;

        layoutParams.endToEnd = parent.getId();
        layoutParams.startToStart = parent.getId();

        if (null == currentBottomControl) {
            layoutParams.topToTop = parent.getId();
        } else {
            layoutParams.topToBottom = currentBottomControl.getId();
        }

        cardControl.setLayoutParams(layoutParams);

        return cardControl;
    }

    private <T> void initializeSpinner(final Spinner spinner,
                                       final List<T> filteredCardList) {
        final ArrayAdapter<T> adapter = new ArrayAdapter<T>(
                spinner.getContext(),
                R.layout.spinner_layout,
                filteredCardList);

        adapter.setDropDownViewResource(R.layout.spinner_layout);
        spinner.setAdapter(adapter);

        setOnItemSelectedListener(spinner);
    }

    private <T> void initializeSpinner(final Spinner spinner,
                                       final List<T> filteredCardList,
                                       final List<T> selectedList,
                                       final int selectedIndex) {
        final ArrayAdapter<T> adapter = new ArrayAdapter<T>(
                spinner.getContext(),
                R.layout.spinner_layout,
                filteredCardList);

        adapter.setDropDownViewResource(R.layout.spinner_layout);
        spinner.setAdapter(adapter);

        setOnItemSelectedListener(spinner, selectedList, selectedIndex);
    }
}
