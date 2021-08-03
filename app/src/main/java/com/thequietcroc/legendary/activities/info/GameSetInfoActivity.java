package com.thequietcroc.legendary.activities.info;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.thequietcroc.legendary.R;
import com.thequietcroc.legendary.custom.adapters.GameComponentRecyclerAdapter;
import com.thequietcroc.legendary.database.LegendaryDatabase;
import com.thequietcroc.legendary.models.gamecomponents.BaseGameComponent;
import com.thequietcroc.legendary.models.gamecomponents.GameSet;
import com.thequietcroc.legendary.models.gamecomponents.cards.Henchmen;
import com.thequietcroc.legendary.models.gamecomponents.cards.Hero;
import com.thequietcroc.legendary.models.gamecomponents.cards.Mastermind;
import com.thequietcroc.legendary.models.gamecomponents.cards.Scheme;
import com.thequietcroc.legendary.models.gamecomponents.cards.Villains;
import com.thequietcroc.legendary.utilities.DbAsyncTask;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GameSetInfoActivity extends InfoActivity<GameSet> {

    GameComponentRecyclerAdapter<Mastermind> mastermindsAdapter;
    GameComponentRecyclerAdapter<Scheme> schemesAdapter;
    GameComponentRecyclerAdapter<Villains> villainsAdapter;
    GameComponentRecyclerAdapter<Henchmen> henchmenAdapter;
    GameComponentRecyclerAdapter<Hero> heroesAdapter;

    RecyclerView mastermindsRecyclerView;
    RecyclerView schemesRecyclerView;
    RecyclerView villainsRecyclerView;
    RecyclerView henchmenRecyclerView;
    RecyclerView heroesRecyclerView;

    SwitchCompat mastermindsEnabledSwitch;
    SwitchCompat schemesEnabledSwitch;
    SwitchCompat villainsEnabledSwitch;
    SwitchCompat henchmenEnabledSwitch;
    SwitchCompat heroesEnabledSwitch;

    GameSet gameSet;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.gameSetInfo));

        final View gameSetInfoControls = LayoutInflater.from(componentControlsLayout.getContext())
                .inflate(R.layout.card_info_game_set_controls, componentControlsLayout, false);

        mastermindsRecyclerView = gameSetInfoControls.findViewById(R.id.gameSetInfoMastermindsRecycler);
        schemesRecyclerView = gameSetInfoControls.findViewById(R.id.gameSetInfoSchemesRecycler);
        villainsRecyclerView = gameSetInfoControls.findViewById(R.id.gameSetInfoVillainsRecycler);
        henchmenRecyclerView = gameSetInfoControls.findViewById(R.id.gameSetInfoHenchmenRecycler);
        heroesRecyclerView = gameSetInfoControls.findViewById(R.id.gameSetInfoHeroesRecycler);

        mastermindsEnabledSwitch = gameSetInfoControls.findViewById(R.id.gameSetInfoMastermindsSwitch);
        schemesEnabledSwitch = gameSetInfoControls.findViewById(R.id.gameSetInfoSchemesSwitch);
        villainsEnabledSwitch = gameSetInfoControls.findViewById(R.id.gameSetInfoVillainsSwitch);
        henchmenEnabledSwitch = gameSetInfoControls.findViewById(R.id.gameSetInfoHenchmenSwitch);
        heroesEnabledSwitch = gameSetInfoControls.findViewById(R.id.gameSetInfoHeroesSwitch);

        mastermindsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        schemesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        villainsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        henchmenRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        heroesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        componentControlsLayout.addView(gameSetInfoControls);

        new DbAsyncTask(() -> {

            gameSet = componentAtomicReference.get();

            final List<Mastermind> mastermindList = gameSet.getMastermindList();
            final List<Scheme> schemeList = gameSet.getSchemeList();
            final List<Villains> villainsList = gameSet.getVillainsList();
            final List<Henchmen> henchmenList = gameSet.getHenchmenList();
            final List<Hero> heroList = gameSet.getHeroList();
            
            mastermindList.stream().forEach(mastermind -> {
                final Henchmen alwaysLeadsHenchmen = mastermind.getAlwaysLeadsHenchmen();

                if (alwaysLeadsHenchmen.getId() > 0) {
                    mastermind.setAlwaysLeadsHenchmen(henchmenList.get(henchmenList.indexOf(alwaysLeadsHenchmen)));
                }
                
                final Villains alwaysLeadsVillains = mastermind.getAlwaysLeadsVillains();

                if (alwaysLeadsVillains.getId() > 0) {
                    mastermind.setAlwaysLeadsVillains(villainsList.get(villainsList.indexOf(alwaysLeadsVillains)));
                }
            });

            villainsList.stream().forEach(villains -> {
                final Mastermind mastermindLeader = villains.getMastermindLeader();

                if (mastermindLeader.getId() > 0) {
                    villains.setMastermindLeader(mastermindList.get(mastermindList.indexOf(mastermindLeader)));
                }
            });

            henchmenList.stream().forEach(henchmen -> {
                final Mastermind mastermindLeader = henchmen.getMastermindLeader();

                if (mastermindLeader.getId() > 0) {
                    henchmen.setMastermindLeader(mastermindList.get(mastermindList.indexOf(mastermindLeader)));
                }
            });

            mastermindsAdapter = new GameComponentRecyclerAdapter<>(mastermindList, false);
            schemesAdapter = new GameComponentRecyclerAdapter<>(schemeList, false);
            villainsAdapter = new GameComponentRecyclerAdapter<>(villainsList, false);
            henchmenAdapter = new GameComponentRecyclerAdapter<>(henchmenList, false);
            heroesAdapter = new GameComponentRecyclerAdapter<>(heroList, false);

            mastermindsAdapter.setCheckboxOnClickCosumer(viewHolder -> {
                final boolean isChecked = viewHolder.getGameComponentEnabledCheckbox().isChecked();
                final Mastermind mastermind = mastermindList.get(viewHolder.getAdapterPosition());
                
                if (isChecked) {
                    if (villainsAdapter.getCheckboxMap()
                            .containsKey(mastermind.getAlwaysLeadsVillains())) {
                        villainsAdapter.getCheckboxMap()
                                .get(mastermind.getAlwaysLeadsVillains())
                                .setChecked(true);
                        
                        doCheckboxBehavior(villainsEnabledSwitch, villainsAdapter);
                    }
                    
                    if (henchmenAdapter.getCheckboxMap()
                            .containsKey(mastermind.getAlwaysLeadsHenchmen())) {
                        henchmenAdapter.getCheckboxMap()
                                .get(mastermind.getAlwaysLeadsHenchmen())
                                .setChecked(true);
                        
                        doCheckboxBehavior(henchmenEnabledSwitch, henchmenAdapter);
                    }
                }
                
                doCheckboxBehavior(mastermindsEnabledSwitch, mastermindsAdapter);
            });
            schemesAdapter.setCheckboxOnClickCosumer(viewHolder -> 
                    doCheckboxBehavior(schemesEnabledSwitch, schemesAdapter));
            villainsAdapter.setCheckboxOnClickCosumer(viewHolder -> {
                final boolean isChecked = viewHolder.getGameComponentEnabledCheckbox().isChecked();
                final Villains villains = villainsList.get(viewHolder.getAdapterPosition());
                
                if (!isChecked) {
                    if (mastermindsAdapter.getCheckboxMap()
                            .containsKey(villains.getMastermindLeader())) {
                        mastermindsAdapter.getCheckboxMap()
                                .get(villains.getMastermindLeader())
                                .setChecked(false);
                        
                        doCheckboxBehavior(mastermindsEnabledSwitch, mastermindsAdapter);
                    }
                }
                
                doCheckboxBehavior(villainsEnabledSwitch, villainsAdapter);
            });
            henchmenAdapter.setCheckboxOnClickCosumer(viewHolder -> {
                final boolean isChecked = viewHolder.getGameComponentEnabledCheckbox().isChecked();
                final Henchmen henchmen = henchmenList.get(viewHolder.getAdapterPosition());

                if (!isChecked) {
                    if (mastermindsAdapter.getCheckboxMap()
                            .containsKey(henchmen.getMastermindLeader())) {
                        mastermindsAdapter.getCheckboxMap()
                                .get(henchmen.getMastermindLeader())
                                .setChecked(false);

                        doCheckboxBehavior(mastermindsEnabledSwitch, mastermindsAdapter);
                    }
                }

                doCheckboxBehavior(henchmenEnabledSwitch, henchmenAdapter);
            });
            heroesAdapter.setCheckboxOnClickCosumer(viewHolder -> 
                    doCheckboxBehavior(heroesEnabledSwitch, heroesAdapter));

            new Handler(Looper.getMainLooper()).post(() -> {
                mastermindsRecyclerView.setAdapter(mastermindsAdapter);
                schemesRecyclerView.setAdapter(schemesAdapter);
                villainsRecyclerView.setAdapter(villainsAdapter);
                henchmenRecyclerView.setAdapter(henchmenAdapter);
                heroesRecyclerView.setAdapter(heroesAdapter);

                mastermindsEnabledSwitch.setChecked(
                        mastermindList.stream().anyMatch(BaseGameComponent::isEnabled));
                schemesEnabledSwitch.setChecked(
                        schemeList.stream().anyMatch(BaseGameComponent::isEnabled));
                villainsEnabledSwitch.setChecked(
                        villainsList.stream().anyMatch(BaseGameComponent::isEnabled));
                henchmenEnabledSwitch.setChecked(
                        henchmenList.stream().anyMatch(BaseGameComponent::isEnabled));
                heroesEnabledSwitch.setChecked(
                        heroList.stream().anyMatch(BaseGameComponent::isEnabled));
            });

            mastermindsEnabledSwitch.setOnClickListener(v
                    -> doEnabledSwitchBehavior(((SwitchCompat) v).isChecked(), mastermindsAdapter));
            schemesEnabledSwitch.setOnClickListener(v
                    -> doEnabledSwitchBehavior(((SwitchCompat) v).isChecked(), schemesAdapter));
            villainsEnabledSwitch.setOnClickListener(v
                    -> doEnabledSwitchBehavior(((SwitchCompat) v).isChecked(), villainsAdapter));
            henchmenEnabledSwitch.setOnClickListener(v
                    -> doEnabledSwitchBehavior(((SwitchCompat) v).isChecked(), henchmenAdapter));
            heroesEnabledSwitch.setOnClickListener(v
                    -> doEnabledSwitchBehavior(((SwitchCompat) v).isChecked(), heroesAdapter));
        });
    }

    private <T extends BaseGameComponent> void doCheckboxBehavior(final SwitchCompat sectionSwitch,
            final GameComponentRecyclerAdapter<T> adapter) {
        sectionSwitch.setChecked(adapter.getCheckboxMap()
                .values()
                .stream()
                .anyMatch(CompoundButton::isChecked));
        enabledSwitch.setChecked(isAnySectionEnabled());
    }

    private <T extends BaseGameComponent> void doEnabledSwitchBehavior(final boolean isChecked,
            final GameComponentRecyclerAdapter<T> adapter) {
        enabledSwitch.setChecked(isAnySectionEnabled());

        adapter.getCheckboxMap().values().stream().forEach(c -> c.setChecked(isChecked));
    }

    private boolean isAnySectionEnabled() {
        return mastermindsEnabledSwitch.isChecked()
                || schemesEnabledSwitch.isChecked()
                || villainsEnabledSwitch.isChecked()
                || henchmenEnabledSwitch.isChecked()
                || heroesEnabledSwitch.isChecked();
    }

    @Override
    public boolean onPrepareOptionsMenu(final Menu menu) {
        super.onPrepareOptionsMenu(menu);

        enabledSwitch.setOnClickListener(v -> {
            final boolean isEnabled = ((SwitchCompat) v).isChecked();

            mastermindsEnabledSwitch.setChecked(isEnabled);
            mastermindsAdapter.getCheckboxMap().values().stream().forEach(c -> c.setChecked(isEnabled));

            schemesEnabledSwitch.setChecked(isEnabled);
            schemesAdapter.getCheckboxMap().values().stream().forEach(c -> c.setChecked(isEnabled));

            villainsEnabledSwitch.setChecked(isEnabled);
            villainsAdapter.getCheckboxMap().values().stream().forEach(c -> c.setChecked(isEnabled));

            henchmenEnabledSwitch.setChecked(isEnabled);
            henchmenAdapter.getCheckboxMap().values().stream().forEach(c -> c.setChecked(isEnabled));

            heroesEnabledSwitch.setChecked(isEnabled);
            heroesAdapter.getCheckboxMap().values().stream().forEach(c -> c.setChecked(isEnabled));
        });

        return true;
    }

    @Override
    protected void saveComponent() {
        new DbAsyncTask(() -> {

            updateComponents(mastermindsAdapter);
            updateComponents(schemesAdapter);
            updateComponents(villainsAdapter);
            updateComponents(henchmenAdapter);
            updateComponents(heroesAdapter);

            final LegendaryDatabase db = LegendaryDatabase.getInstance();

            db.mastermindDao().update(gameSet.getMastermindList()
                    .stream()
                    .map(Mastermind::toEntity)
                    .collect(Collectors.toList()));
            db.schemeDao().update(gameSet.getSchemeList()
                    .stream()
                    .map(Scheme::toEntity)
                    .collect(Collectors.toList()));
            db.villainsDao().update(gameSet.getVillainsList()
                    .stream()
                    .map(Villains::toEntity)
                    .collect(Collectors.toList()));
            db.henchmenDao().update(gameSet.getHenchmenList()
                    .stream()
                    .map(Henchmen::toEntity)
                    .collect(Collectors.toList()));
            db.heroDao().update(gameSet.getHeroList()
                    .stream()
                    .map(Hero::toEntity)
                    .collect(Collectors.toList()));

            super.saveComponent();
        });
    }

    private <T extends BaseGameComponent> void updateComponents(final GameComponentRecyclerAdapter<T> adapter) {
        final Map<T, CheckBox> checkboxMap = adapter.getCheckboxMap();

        for(final Map.Entry<T, CheckBox> entry : checkboxMap.entrySet()) {
            entry.getKey().setEnabled(entry.getValue().isChecked());
        }
    }
}
