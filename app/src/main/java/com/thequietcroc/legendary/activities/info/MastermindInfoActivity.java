package com.thequietcroc.legendary.activities.info;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.thequietcroc.legendary.R;
import com.thequietcroc.legendary.database.LegendaryDatabase;
import com.thequietcroc.legendary.database.entities.gamecomponents.cards.HenchmenEntity;
import com.thequietcroc.legendary.database.entities.gamecomponents.cards.VillainsEntity;
import com.thequietcroc.legendary.models.gamecomponents.cards.Henchmen;
import com.thequietcroc.legendary.models.gamecomponents.cards.Mastermind;
import com.thequietcroc.legendary.models.gamecomponents.cards.Villains;
import com.thequietcroc.legendary.utilities.DbAsyncTask;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class MastermindInfoActivity extends CardInfoActivity<Mastermind> {

    Mastermind mastermind;
    Spinner alwaysLeadsVillainsSpinner;
    Spinner alwaysLeadsHenchmenSpinner;
    ArrayAdapter<Villains> villainsArrayAdapter;
    ArrayAdapter<Henchmen> henchmenArrayAdapter;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.mastermindInfo));

        mastermind = componentAtomicReference.get();

        final View mastermindInfoControls = LayoutInflater.from(componentControlsLayout.getContext())
                .inflate(
                        R.layout.card_info_mastermind_controls,
                        componentControlsLayout,
                        false);

        componentControlsLayout.addView(mastermindInfoControls);

        alwaysLeadsVillainsSpinner = mastermindInfoControls
                .findViewById(R.id.cardInfoMastermindVillainsSpinner);
        alwaysLeadsHenchmenSpinner = mastermindInfoControls
                .findViewById(R.id.cardInfoMastermindHenchmenSpinner);

        new DbAsyncTask(() -> {

            if (mastermind.isCustom()) {

                final List<Villains> villainsList = getVillainsList();

                villainsArrayAdapter = new ArrayAdapter<>(
                        alwaysLeadsVillainsSpinner.getContext(),
                        R.layout.spinner_layout,
                        villainsList
                );

                villainsArrayAdapter.setDropDownViewResource(R.layout.spinner_layout);

                final List<Henchmen> henchmenList = getHenchmenList();

                henchmenArrayAdapter = new ArrayAdapter<>(
                        alwaysLeadsHenchmenSpinner.getContext(),
                        R.layout.spinner_layout,
                        henchmenList
                );

                henchmenArrayAdapter.setDropDownViewResource(R.layout.spinner_layout);

                new Handler(Looper.getMainLooper()).post(() -> {

                    alwaysLeadsVillainsSpinner.setAdapter(villainsArrayAdapter);
                    alwaysLeadsVillainsSpinner.setSelection(
                            villainsList.indexOf(mastermind.getAlwaysLeadsVillains()));

                    alwaysLeadsHenchmenSpinner.setAdapter(henchmenArrayAdapter);
                    alwaysLeadsHenchmenSpinner.setSelection(
                            henchmenList.indexOf(mastermind.getAlwaysLeadsHenchmen()));
                });
            } else {
                villainsArrayAdapter = new ArrayAdapter<>(
                        alwaysLeadsVillainsSpinner.getContext(),
                        R.layout.spinner_layout,
                        Collections.singletonList(mastermind.getAlwaysLeadsVillains())
                );

                villainsArrayAdapter.setDropDownViewResource(R.layout.spinner_layout);

                henchmenArrayAdapter = new ArrayAdapter<>(
                        alwaysLeadsHenchmenSpinner.getContext(),
                        R.layout.spinner_layout,
                        Collections.singletonList(mastermind.getAlwaysLeadsHenchmen())
                );

                henchmenArrayAdapter.setDropDownViewResource(R.layout.spinner_layout);

                new Handler(Looper.getMainLooper()).post(() -> {
                    alwaysLeadsVillainsSpinner.setAdapter(villainsArrayAdapter);
                    alwaysLeadsVillainsSpinner.setEnabled(false);

                    alwaysLeadsHenchmenSpinner.setAdapter(henchmenArrayAdapter);
                    alwaysLeadsHenchmenSpinner.setEnabled(false);
                });
            }
        });
    }

    @Override
    protected void saveComponent() {

        new DbAsyncTask(() -> {

            final Villains oldVillains = mastermind.getAlwaysLeadsVillains();
            final Villains selectedVillains = getSelectedVillains();

            if (!oldVillains.equals(selectedVillains)) {
                final Mastermind oldMastermindLeader = selectedVillains.getMastermindLeader();

                oldMastermindLeader.setAlwaysLeadsVillains(Villains.NONE);
                oldMastermindLeader.dbSave();

                mastermind.setAlwaysLeadsVillains(selectedVillains);
            }

            final Henchmen oldHenchmen = mastermind.getAlwaysLeadsHenchmen();
            final Henchmen selectedHenchmen = getSelectedHenchmen();

            if (!oldHenchmen.equals(selectedHenchmen)) {
                final Mastermind oldMastermindLeader = selectedHenchmen.getMastermindLeader();

                oldMastermindLeader.setAlwaysLeadsHenchmen(Henchmen.NONE);
                oldMastermindLeader.dbSave();

                mastermind.setAlwaysLeadsHenchmen(selectedHenchmen);
            }

            if (enabledSwitch.isChecked()) {
                mastermind.getAlwaysLeadsVillains().setEnabled(true);
                mastermind.getAlwaysLeadsHenchmen().setEnabled(true);

                mastermind.getAlwaysLeadsVillains().dbSave();
                mastermind.getAlwaysLeadsHenchmen().dbSave();
            }

            super.saveComponent();
        });
    }
    
    @Override
    public void onGameSetChanged() {
        
        new DbAsyncTask(() -> {
            
            final List<Villains> villainsList = getVillainsList();
            final List<Henchmen> henchmenList = getHenchmenList();
            
            new Handler(Looper.getMainLooper()).post(() -> {
                villainsArrayAdapter.clear();
                villainsArrayAdapter.addAll(villainsList);
                villainsArrayAdapter.notifyDataSetChanged();
                alwaysLeadsVillainsSpinner.setSelection(0);

                henchmenArrayAdapter.clear();
                henchmenArrayAdapter.addAll(henchmenList);
                henchmenArrayAdapter.notifyDataSetChanged();
                alwaysLeadsHenchmenSpinner.setSelection(0);
            });
        });
    }

    private List<Henchmen> getHenchmenList() {

        final List<Henchmen> henchmenList = new ArrayList<>();

        if (getSelectedGameSet() != null
                && getSelectedGameSet().getId() > 0) {
        henchmenList.addAll(LegendaryDatabase.getInstance()
                .henchmenDao()
                .getAllBySetId(getSelectedGameSet().getId())
                .stream()
                .map(HenchmenEntity::toModel)
                .collect(Collectors.toList()));
        }

        if (henchmenList.size() > 0) {
            henchmenList.add(0, Henchmen.NONE);
        } else {
            final Henchmen noHenchmenFound = new Henchmen(Henchmen.NONE.toEntity());
            noHenchmenFound.setName(getString(R.string.noHenchmenFound));

            henchmenList.add(noHenchmenFound);
        }
        
        return henchmenList;
    }

    private List<Villains> getVillainsList() {

        final List<Villains> villainsList = new ArrayList<>();

        if (getSelectedGameSet() != null
                && getSelectedGameSet().getId() > 0) {
            villainsList.addAll(LegendaryDatabase.getInstance()
                    .villainsDao()
                    .getAllBySetId(getSelectedGameSet().getId())
                    .stream()
                    .map(VillainsEntity::toModel)
                    .collect(Collectors.toList()));
        }

        if (villainsList.size() > 0) {
            villainsList.add(0, Villains.NONE);
        } else {
            final Villains noVillainsFound = new Villains(Villains.NONE.toEntity());
            noVillainsFound.setName(getString(R.string.noVillainsFound));

            villainsList.add(noVillainsFound);
        }

        
        return villainsList;
    }

    private Henchmen getSelectedHenchmen() {
        return (Henchmen) alwaysLeadsHenchmenSpinner.getSelectedItem();
    }

    private Villains getSelectedVillains() {
        return (Villains) alwaysLeadsVillainsSpinner.getSelectedItem();
    }
}
