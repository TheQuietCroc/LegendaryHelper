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
import com.thequietcroc.legendary.database.entities.gamecomponents.cards.MastermindEntity;
import com.thequietcroc.legendary.models.gamecomponents.cards.Villains;
import com.thequietcroc.legendary.models.gamecomponents.cards.Mastermind;
import com.thequietcroc.legendary.utilities.DbAsyncTask;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class VillainsInfoActivity extends CardInfoActivity<Villains> {

    Villains villains;
    Spinner mastermindLeaderSpinner;
    ArrayAdapter<Mastermind> mastermindArrayAdapter;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.villainsInfo));

        villains = componentAtomicReference.get();

        final View villainsInfoControls = LayoutInflater.from(componentControlsLayout.getContext())
                .inflate(
                        R.layout.card_info_villain_henchmen_controls,
                        componentControlsLayout,
                        false);

        componentControlsLayout.addView(villainsInfoControls);

        mastermindLeaderSpinner = villainsInfoControls.findViewById(R.id.cardInfoVHMastermindLeaderSpinner);

        new DbAsyncTask(() -> {

            final Mastermind mastermindLeader = villains.getMastermindLeader();

            if (villains.isCustom()) {

                final List<Mastermind> mastermindList = getMastermindList();

                mastermindArrayAdapter = new ArrayAdapter<>(
                        mastermindLeaderSpinner.getContext(),
                        R.layout.spinner_layout,
                        mastermindList
                );

                mastermindArrayAdapter.setDropDownViewResource(R.layout.spinner_layout);

                new Handler(Looper.getMainLooper()).post(() -> {

                    mastermindLeaderSpinner.setAdapter(mastermindArrayAdapter);
                    mastermindLeaderSpinner.setSelection(
                            mastermindList.indexOf(mastermindLeader));
                });
            } else {
                mastermindArrayAdapter = new ArrayAdapter<>(
                        mastermindLeaderSpinner.getContext(),
                        R.layout.spinner_layout,
                        Collections.singletonList(mastermindLeader)
                );

                mastermindArrayAdapter.setDropDownViewResource(R.layout.spinner_layout);

                new Handler(Looper.getMainLooper()).post(() -> {
                    mastermindLeaderSpinner.setAdapter(mastermindArrayAdapter);
                    mastermindLeaderSpinner.setEnabled(false);
                });
            }
        });
    }

    @Override
    protected void saveComponent() {

        new DbAsyncTask(() -> {

            final Mastermind oldMastermind = villains.getMastermindLeader();
            final Mastermind newMastermind = getSelectedMastermind();

            if (!oldMastermind.equals(newMastermind)) {

                oldMastermind.setAlwaysLeadsVillains(Villains.NONE);
                oldMastermind.dbSave();

                newMastermind.setAlwaysLeadsVillains(villains);
                newMastermind.dbSave();

                villains.setMastermindLeader(newMastermind);
            }

            super.saveComponent();
        });
    }

    @Override
    public void onGameSetChanged() {
        new DbAsyncTask(() -> {

            final List<Mastermind> mastermindList = getMastermindList();

            new Handler(Looper.getMainLooper()).post(() -> {
                mastermindArrayAdapter.clear();
                mastermindArrayAdapter.addAll(mastermindList);
                mastermindArrayAdapter.notifyDataSetChanged();
                mastermindLeaderSpinner.setSelection(0);
            });
        });
    }

    private List<Mastermind> getMastermindList() {
        final List<Mastermind> mastermindList = LegendaryDatabase.getInstance()
                .mastermindDao()
                .getAllBySetId(getSelectedGameSet().getId())
                .stream()
                .map(MastermindEntity::toModel)
                .collect(Collectors.toList());

        mastermindList.add(0, Mastermind.NONE);

        return mastermindList;
    }

    private Mastermind getSelectedMastermind() {
        return (Mastermind) mastermindLeaderSpinner.getSelectedItem();
    }
}
