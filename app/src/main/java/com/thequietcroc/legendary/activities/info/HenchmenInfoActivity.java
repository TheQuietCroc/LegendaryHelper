package com.thequietcroc.legendary.activities.info;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.thequietcroc.legendary.R;
import com.thequietcroc.legendary.database.LegendaryDatabase;
import com.thequietcroc.legendary.database.entities.gamecomponents.cards.MastermindEntity;
import com.thequietcroc.legendary.models.gamecomponents.cards.Mastermind;
import com.thequietcroc.legendary.models.gamecomponents.cards.Henchmen;
import com.thequietcroc.legendary.utilities.DbAsyncTask;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class HenchmenInfoActivity extends CardInfoActivity<Henchmen> {

    Spinner mastermindLeaderSpinner;
    ArrayAdapter<Mastermind> mastermindArrayAdapter;
    Henchmen henchmen;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.henchmenInfo));

        henchmen = componentAtomicReference.get();

        final View henchmenInfoControls = LayoutInflater.from(componentControlsLayout.getContext())
                .inflate(R.layout.card_info_villain_henchmen_controls, componentControlsLayout, false);

        mastermindLeaderSpinner = henchmenInfoControls.findViewById(R.id.cardInfoVHMastermindLeaderSpinner);

        componentControlsLayout.addView(henchmenInfoControls);

        new DbAsyncTask(() -> {

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
                    mastermindLeaderSpinner.setSelection(mastermindList.indexOf(henchmen.getMastermindLeader()));

                    mastermindLeaderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(final AdapterView<?> parent,
                                final View view,
                                final int position,
                                final long id) {
                            henchmen.setMastermindLeader(mastermindList.get(position));
                            henchmen.getMastermindLeader().setAlwaysLeadsHenchmen(henchmen);
                        }

                        @Override
                        public void onNothingSelected(final AdapterView<?> parent) {

                        }
                    });
                });
            } else {
                mastermindArrayAdapter = new ArrayAdapter<>(
                        mastermindLeaderSpinner.getContext(),
                        R.layout.spinner_layout,
                        Collections.singletonList(henchmen.getMastermindLeader())
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
    public void saveComponent() {
        super.saveComponent();

        henchmen.getMastermindLeader().dbSave();
    }

    @Override
    public void onItemSelected(final AdapterView<?> parent,
            final View view,
            final int position,
            final long id) {
        super.onItemSelected(parent, view, position, id);

        new DbAsyncTask(() -> {

            final List<Mastermind> mastermindList = getMastermindList();

            new Handler(Looper.getMainLooper()).post(() -> {
                mastermindArrayAdapter.clear();
                mastermindArrayAdapter.addAll(mastermindList);
                mastermindArrayAdapter.notifyDataSetChanged();
                mastermindLeaderSpinner.setSelection(0);
            });

            henchmen.getMastermindLeader().setAlwaysLeadsHenchmen(Henchmen.NONE);
            henchmen.setMastermindLeader(Mastermind.NONE);
        });
    }

    private List<Mastermind> getMastermindList() {
        final List<Mastermind> mastermindList = LegendaryDatabase.getInstance()
                .mastermindDao()
                .getAllBySetId(componentAtomicReference.get().getGameSet().getId())
                .stream()
                .map(MastermindEntity::toModel)
                .collect(Collectors.toList());

        mastermindList.add(0, Mastermind.NONE);

        return mastermindList;
    }
}
