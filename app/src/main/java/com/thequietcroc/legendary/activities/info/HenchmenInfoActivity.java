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
import com.thequietcroc.legendary.models.gamecomponents.cards.Henchmen;
import com.thequietcroc.legendary.models.gamecomponents.cards.Mastermind;
import com.thequietcroc.legendary.utilities.DbAsyncTask;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class HenchmenInfoActivity extends CardInfoActivity<Henchmen> {

    Spinner mastermindLeaderSpinner;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.henchmenInfo));

        final View henchmenInfoControls = LayoutInflater.from(componentControlsLayout.getContext())
                .inflate(R.layout.card_info_villain_henchmen_controls, componentControlsLayout, false);

        mastermindLeaderSpinner = henchmenInfoControls.findViewById(R.id.cardInfoVHMastermindLeaderSpinner);

        componentControlsLayout.addView(henchmenInfoControls);

        new DbAsyncTask(() -> {
            final Henchmen henchmen = componentAtomicReference.get();
            final ArrayAdapter<Mastermind> adapter;

            if (henchmen.isCustom()) {

                final List<Mastermind> mastermindList = LegendaryDatabase.getInstance()
                        .mastermindDao()
                        .getAllBySetId(henchmen.getGameSet().getId())
                        .stream()
                        .map(MastermindEntity::toModel)
                        .collect(Collectors.toList());

                mastermindList.add(0, Mastermind.NONE);

                adapter = new ArrayAdapter<>(
                        mastermindLeaderSpinner.getContext(),
                        R.layout.spinner_layout,
                        mastermindList
                );

                adapter.setDropDownViewResource(R.layout.spinner_layout);

                new Handler(Looper.getMainLooper()).post(() -> {
                    mastermindLeaderSpinner.setAdapter(adapter);
                    mastermindLeaderSpinner.setSelection(mastermindList.indexOf(henchmen.getMastermindLeader()));

                    mastermindLeaderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(final AdapterView<?> parent,
                                final View view,
                                final int position,
                                final long id) {
                            mastermindList.get(position).setAlwaysLeadsHenchmen(henchmen);
                        }

                        @Override
                        public void onNothingSelected(final AdapterView<?> parent) {

                        }
                    });
                });
            } else {
                adapter = new ArrayAdapter<>(
                        mastermindLeaderSpinner.getContext(),
                        R.layout.spinner_layout,
                        Collections.singletonList(henchmen.getMastermindLeader())
                );

                adapter.setDropDownViewResource(R.layout.spinner_layout);

                new Handler(Looper.getMainLooper()).post(() -> {
                    mastermindLeaderSpinner.setAdapter(adapter);
                    mastermindLeaderSpinner.setEnabled(false);
                });
            }
        });
    }
}
