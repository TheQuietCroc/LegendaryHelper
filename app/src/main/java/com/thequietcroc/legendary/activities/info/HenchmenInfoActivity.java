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
import com.thequietcroc.legendary.models.gamecomponents.cards.Henchmen;
import com.thequietcroc.legendary.models.gamecomponents.cards.Mastermind;
import com.thequietcroc.legendary.utilities.DbAsyncTask;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class HenchmenInfoActivity extends CardInfoActivity<Henchmen> {

    Henchmen henchmen;
    Spinner mastermindLeaderSpinner;
    ArrayAdapter<Mastermind> mastermindArrayAdapter;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.henchmenInfo));

        henchmen = componentAtomicReference.get();

        final View henchmenInfoControls = LayoutInflater.from(componentControlsLayout.getContext())
                .inflate(
                        R.layout.card_info_villain_henchmen_controls,
                        componentControlsLayout,
                        false);

        componentControlsLayout.addView(henchmenInfoControls);

        mastermindLeaderSpinner = henchmenInfoControls.findViewById(R.id.cardInfoVHMastermindLeaderSpinner);

        new DbAsyncTask(() -> {

            final Mastermind mastermindLeader = henchmen.getMastermindLeader();

            if (henchmen.isCustom()) {

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

            final Mastermind oldMastermind = henchmen.getMastermindLeader();
            final Mastermind newMastermind = getSelectedMastermind();

            if (!oldMastermind.equals(newMastermind)) {

                oldMastermind.setAlwaysLeadsHenchmen(Henchmen.NONE);
                oldMastermind.dbSave();

                newMastermind.setAlwaysLeadsHenchmen(henchmen);
                newMastermind.dbSave();

                henchmen.setMastermindLeader(newMastermind);
            }

            if (!enabledSwitch.isChecked()) {
                henchmen.getMastermindLeader().setEnabled(false);
                henchmen.getMastermindLeader().dbSave();
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

        final List<Mastermind> mastermindList = new ArrayList<>();

        if (getSelectedGameSet() != null
                && getSelectedGameSet().getId() > 0) {
            mastermindList.addAll(LegendaryDatabase.getInstance()
                    .mastermindDao()
                    .getAllBySetId(getSelectedGameSet().getId())
                    .stream()
                    .map(MastermindEntity::toModel)
                    .collect(Collectors.toList()));
        }

        if (mastermindList.size() > 0) {
            mastermindList.add(0, Mastermind.NONE);
        } else {
            final Mastermind noMastermindsFound = new Mastermind(Mastermind.NONE.toEntity());
            noMastermindsFound.setName(getString(R.string.noMastermindsFound));

            mastermindList.add(noMastermindsFound);
        }

        return mastermindList;
    }

    private Mastermind getSelectedMastermind() {
        return (Mastermind) mastermindLeaderSpinner.getSelectedItem();
    }
}
