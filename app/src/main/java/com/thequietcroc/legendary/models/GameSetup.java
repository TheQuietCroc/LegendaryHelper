package com.thequietcroc.legendary.models;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.ViewCompat;
import androidx.lifecycle.LifecycleOwner;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.thequietcroc.legendary.R;
import com.thequietcroc.legendary.custom.views.CardControl;
import com.thequietcroc.legendary.database.LegendaryDatabase;
import com.thequietcroc.legendary.database.entities.GameSetupEntity;
import com.thequietcroc.legendary.database.entities.gamecomponents.cards.BaseCardEntity;
import com.thequietcroc.legendary.database.entities.gamecomponents.cards.HenchmenEntity;
import com.thequietcroc.legendary.database.entities.gamecomponents.cards.HeroEntity;
import com.thequietcroc.legendary.database.entities.gamecomponents.cards.MastermindEntity;
import com.thequietcroc.legendary.database.entities.gamecomponents.cards.SchemeEntity;
import com.thequietcroc.legendary.database.entities.gamecomponents.cards.VillainsEntity;
import com.thequietcroc.legendary.enums.ItemType;
import com.thequietcroc.legendary.utilities.MinimalComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.thequietcroc.legendary.enums.ItemType.HENCHMEN;
import static com.thequietcroc.legendary.enums.ItemType.HERO;
import static com.thequietcroc.legendary.enums.ItemType.VILLAINS;
import static com.thequietcroc.legendary.utilities.LiveDataUtil.observeOnce;

public class GameSetup extends BaseItem {

    private int numPlayers;

    private MastermindEntity.Minimal selectedMastermind;
    private SchemeEntity.Minimal selectedScheme;
    private /*final*/ List<VillainsEntity.Minimal> selectedVillainsList;
    private /*final*/ List<HenchmenEntity.Minimal> selectedHenchmenList;
    private /*final*/ List<HeroEntity.Minimal> selectedHeroList;

    private /*final*/ Map<Integer, MastermindEntity.Minimal> filteredMastermindMap = new HashMap<>();
    private /*final*/ Map<Integer, SchemeEntity.Minimal> filteredSchemeMap = new HashMap<>();
    private /*final*/ Map<Integer, VillainsEntity.Minimal> filteredVillainsMap = new HashMap<>();
    private /*final*/ Map<Integer, HenchmenEntity.Minimal> filteredHenchmenMap = new HashMap<>();
    private /*final*/ Map<Integer, HeroEntity.Minimal> filteredHeroMap = new HashMap<>();

    private /*final*/ ConstraintLayout containerVillains;
    private /*final*/ ConstraintLayout containerHenchmen;
    private /*final*/ ConstraintLayout containerHero;

    private /*final*/ CardControl mastermindControl;
    private /*final*/ CardControl schemeControl;
    private /*final*/ List<CardControl> villainsControlList = new ArrayList<>();
    private /*final*/ List<CardControl> henchmenControlList = new ArrayList<>();
    private /*final*/ List<CardControl> heroControlList = new ArrayList<>();

    private /*final*/ MaterialButtonToggleGroup buttonGroupPlayers;

    private /*final*/ AtomicInteger numQueriesCompleted = new AtomicInteger(0);

    public GameSetup(final GameSetupEntity gameSetupEntity) {
        super(gameSetupEntity);

        // TODO: implement this
    }

    public GameSetup(final LifecycleOwner owner,
            final LegendaryDatabase db,
            final MaterialButtonToggleGroup buttonGroupPlayers,
            final CardControl mastermindControl,
            final CardControl schemeControl,
            final ConstraintLayout containerVillains,
            final ConstraintLayout containerHenchmen,
            final ConstraintLayout containerHero) {
        super("New Setup");

        this.buttonGroupPlayers = buttonGroupPlayers;

        this.buttonGroupPlayers.check(this.buttonGroupPlayers.getChildAt(0).getId());
        this.numPlayers = 1;

        this.mastermindControl = mastermindControl;
        this.schemeControl = schemeControl;

        this.containerVillains = containerVillains;
        this.containerHenchmen = containerHenchmen;
        this.containerHero = containerHero;

        this.selectedMastermind = new MastermindEntity.Minimal();
        this.selectedScheme = new SchemeEntity.Minimal();
        this.selectedVillainsList =
                new ArrayList<>(Collections.nCopies(containerVillains.getChildCount(),
                        new VillainsEntity.Minimal()));
        this.selectedHenchmenList =
                new ArrayList<>(Collections.nCopies(containerHenchmen.getChildCount(),
                        new HenchmenEntity.Minimal()));
        this.selectedHeroList = new ArrayList<>(Collections.nCopies(containerHero.getChildCount(),
                new HeroEntity.Minimal()));

        observeOnce(owner,
                db.mastermindDao().getAllEnabledAsyncMinimal(),
                filteredResults -> populateFilteredMap(filteredResults, filteredMastermindMap));
        observeOnce(owner,
                db.schemeDao().getAllEnabledAsyncMinimal(),
                filteredResults -> populateFilteredMap(filteredResults, filteredSchemeMap));
        observeOnce(owner,
                db.villainsDao().getAllEnabledAsyncMinimal(),
                filteredResults -> populateFilteredMap(filteredResults, filteredVillainsMap));
        observeOnce(owner,
                db.henchmenDao().getAllEnabledAsyncMinimal(),
                filteredResults -> populateFilteredMap(filteredResults, filteredHenchmenMap));
        observeOnce(owner,
                db.heroDao().getAllEnabledAsyncMinimal(),
                filteredResults -> populateFilteredMap(filteredResults, filteredHeroMap));


    }

    @Override
    public GameSetupEntity toEntity() {
        return new GameSetupEntity(this);
    }

    private <T extends BaseCardEntity.Minimal> void populateFilteredMap(final List<T> cardMap,
            final Map<Integer, T> filteredMap) {

        filteredMap.putAll(cardMap.stream()
                .collect(Collectors.toMap(T::getId, Function.identity())));


        if (numQueriesCompleted.incrementAndGet() == 5) {
            initializeControls();
        }
    }

    private void setNumPlayers(final int numPlayers) {
        this.numPlayers = numPlayers;
    }

    private void setSelectedMastermind(final MastermindEntity.Minimal selectedMastermind) {
        this.selectedMastermind = selectedMastermind;
    }

    private void setSelectedScheme(final SchemeEntity.Minimal selectedScheme) {
        this.selectedScheme = selectedScheme;
    }

    private <T> void setSelectedCard(final T selectedCard) {
        if (selectedCard instanceof MastermindEntity.Minimal) {
            setSelectedMastermind((MastermindEntity.Minimal) selectedCard);
        } else if (selectedCard instanceof SchemeEntity.Minimal) {
            setSelectedScheme((SchemeEntity.Minimal) selectedCard);
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

        initializeSpinner(new MastermindEntity.Minimal(),
                mastermindControl.getSpinner(),
                filteredMastermindMap);
        initializeSpinner(new SchemeEntity.Minimal(),
                schemeControl.getSpinner(),
                filteredSchemeMap);

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

                if (parent.getSelectedItem() instanceof MastermindEntity.Minimal) {
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

                ((CardControl) parent.getParent()).getToggleLock()
                        .setEnabled(selectedItemPosition > 0);
                parent.setTag(selectedItemPosition);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void newSetup() {

        selectedMastermind = setupHelper(mastermindControl,
                selectedMastermind,
                filteredMastermindMap);
        selectedScheme = setupHelper(schemeControl, selectedScheme, filteredSchemeMap);
        setupHelper(villainsControlList, filteredVillainsMap, selectedVillainsList);
        selectAlwaysLeadsVillains();
        setupHelper(henchmenControlList, filteredHenchmenMap, selectedHenchmenList);
        selectAlwaysLeadsHenchmen();
        setupHelper(heroControlList, filteredHeroMap, selectedHeroList);
    }

    private void selectAlwaysLeadsVillains() {
        if (numPlayers > 1) {
            final int alwaysLeadsVillainsId = selectedMastermind.getVillainId();

            if (alwaysLeadsVillainsId > 0) {

                selectAlwaysLeadsHelper(filteredVillainsMap.get(alwaysLeadsVillainsId),
                        selectedVillainsList,
                        filteredVillainsMap,
                        villainsControlList);
            }
        }
    }

    private void selectAlwaysLeadsHenchmen() {
        if (numPlayers > 1) {
            final int alwaysLeadsHenchmenId = selectedMastermind.getHenchmenId();

            if (alwaysLeadsHenchmenId > 0) {

                selectAlwaysLeadsHelper(filteredHenchmenMap.get(alwaysLeadsHenchmenId),
                        selectedHenchmenList,
                        filteredHenchmenMap,
                        henchmenControlList);
            }
        }
    }

    private <T extends BaseCardEntity.Minimal> T setupHelper(final CardControl control,
            final T selectedCard,
            final Map<Integer, T> filteredMap) {
        final T newSelectedCard;

        if (!control.getToggleLock().isChecked()) {

            newSelectedCard = (T) selectRandomlyFromMap(filteredMap);

            final int filteredIndex = ((ArrayAdapter<T>) control.getSpinner()
                    .getAdapter()).getPosition(newSelectedCard);

            control.getSpinner().setTag(filteredIndex);
            control.getSpinner().setSelection(filteredIndex);
        } else {
            newSelectedCard = selectedCard;
        }

        return newSelectedCard;
    }

    private <T extends BaseCardEntity.Minimal> void setupHelper(final List<CardControl> controlList,
            final Map<Integer, T> filteredMap,
            final List<T> selectedList) {

        for (int i = 0; i < controlList.size(); ++i) {
            final CardControl control = controlList.get(i);

            if (control.getToggleLock().getVisibility() == View.INVISIBLE) {
                toggleControlLock(false, control);
            }

            if (!control.getToggleLock().isChecked()) {
                T randomSelection;

                do {
                    randomSelection = (T) selectRandomlyFromMap(filteredMap);
                }
                while (selectedList.contains(randomSelection));

                selectedList.add(i, randomSelection);
                selectedList.remove(i + 1);
            }
        }

        for (int i = 0; i < controlList.size(); ++i) {
            final CardControl control = controlList.get(i);
            final int selectedIndex = ((ArrayAdapter<T>) control.getSpinner()
                    .getAdapter()).getPosition(selectedList.get(i));

            control.getSpinner().setTag(selectedIndex);
            control.getSpinner().setSelection(selectedIndex);
        }
    }

    private <T> void selectAlwaysLeadsHelper(final T alwaysLeads,
            final List<T> selectedList,
            final Map<Integer, T> filteredMap,
            final List<CardControl> cardControlList) {
        for (final CardControl control : cardControlList) {
            if (control.getToggleLock().getVisibility() == View.INVISIBLE) {
                toggleControlLock(false, control);
            }
        }

        final int filteredIndex = ((ArrayAdapter<T>) cardControlList.get(0)
                .getSpinner()
                .getAdapter()).getPosition(alwaysLeads);
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

    private BaseCardEntity.Minimal selectRandomlyFromMap(final Map<Integer, ?
            extends BaseCardEntity.Minimal> cardList) {
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
                adjustContainedControlsHelper(new HeroEntity.Minimal(),
                        numVisible,
                        heroControlList,
                        containerHero,
                        filteredHeroMap,
                        selectedHeroList);

            }
            break;
            case VILLAINS: {
                adjustContainedControlsHelper(new VillainsEntity.Minimal(),
                        numVisible,
                        villainsControlList,
                        containerVillains,
                        filteredVillainsMap,
                        selectedVillainsList);
            }
            break;
            case HENCHMEN: {
                adjustContainedControlsHelper(new HenchmenEntity.Minimal(),
                        numVisible,
                        henchmenControlList,
                        containerHenchmen,
                        filteredHenchmenMap,
                        selectedHenchmenList);
            }
            break;
        }
    }

    private <T extends BaseCardEntity.Minimal> void adjustContainedControlsHelper(final T noneCard,
            final int numVisible,
            final List<CardControl> controlList,
            final ConstraintLayout container,
            final Map<Integer, T> filteredMap,
            final List<T> selectedList) {

        if (controlList.size() < numVisible) {
            final int numToAdd = numVisible - controlList.size();

            for (int i = 0; i < numToAdd; ++i) {
                final CardControl control = createCardControl(container);
                initializeSpinner(noneCard,
                        control.getSpinner(),
                        filteredMap,
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
            final int selectedIndex =
                    ((ArrayAdapter<T>) spinner.getAdapter()).getPosition(selected);

            spinner.setTag(selectedIndex);
            spinner.setSelection(selectedIndex);
        }
    }

    private CardControl createCardControl(final ConstraintLayout parent) {

        final CardControl currentBottomControl =
                (CardControl) parent.getChildAt(parent.getChildCount()
                        - 1);
        final CardControl cardControl = new CardControl(parent.getContext());

        cardControl.setId(ViewCompat.generateViewId());

        final ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(0,
                ViewGroup.LayoutParams.WRAP_CONTENT);

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

    private <T extends BaseCardEntity.Minimal> void initializeSpinner(final T noneCard,
            final Spinner spinner,
            final Map<Integer, T> filteredCardMap) {
        final List<T> filteredCardList = new ArrayList<>();
        filteredCardList.addAll(filteredCardMap.values());
        Collections.sort(filteredCardList, new MinimalComparator<>());
        filteredCardList.add(0, noneCard);

        final ArrayAdapter<T> adapter = new ArrayAdapter<T>(
                spinner.getContext(),
                R.layout.spinner_layout,
                filteredCardList);

        adapter.setDropDownViewResource(R.layout.spinner_layout);
        spinner.setAdapter(adapter);

        setOnItemSelectedListener(spinner);
    }

    private <T extends BaseCardEntity.Minimal> void initializeSpinner(final T noneCard,
            final Spinner spinner,
            final Map<Integer, T> filteredCardMap,
            final List<T> selectedList,
            final int selectedIndex) {
        final List<T> filteredCardList = new ArrayList<>();
        filteredCardList.addAll(filteredCardMap.values());
        Collections.sort(filteredCardList, new MinimalComparator<>());
        filteredCardList.add(0, noneCard);

        final ArrayAdapter<T> adapter = new ArrayAdapter<T>(
                spinner.getContext(),
                R.layout.spinner_layout,
                filteredCardList);

        adapter.setDropDownViewResource(R.layout.spinner_layout);
        spinner.setAdapter(adapter);

        setOnItemSelectedListener(spinner, selectedList, selectedIndex);
    }
}
