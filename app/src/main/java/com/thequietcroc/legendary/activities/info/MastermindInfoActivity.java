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
import com.thequietcroc.legendary.database.entities.gamecomponents.cards.HenchmenEntity;
import com.thequietcroc.legendary.database.entities.gamecomponents.cards.VillainsEntity;
import com.thequietcroc.legendary.models.gamecomponents.cards.Henchmen;
import com.thequietcroc.legendary.models.gamecomponents.cards.Mastermind;
import com.thequietcroc.legendary.models.gamecomponents.cards.Villains;
import com.thequietcroc.legendary.utilities.DbAsyncTask;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class MastermindInfoActivity extends CardInfoActivity<Mastermind> {

    Spinner alwaysLeadsVillainsSpinner;
    Spinner alwaysLeadsHenchmenSpinner;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.mastermindInfo));

        final View mastermindInfoControls = LayoutInflater.from(componentControlsLayout.getContext())
                .inflate(R.layout.card_info_mastermind_controls, componentControlsLayout, false);

        alwaysLeadsVillainsSpinner = mastermindInfoControls.findViewById(R.id.cardInfoMastermindVillainsSpinner);
        alwaysLeadsHenchmenSpinner = mastermindInfoControls.findViewById(R.id.cardInfoMastermindHenchmenSpinner);

        componentControlsLayout.addView(mastermindInfoControls);

        new DbAsyncTask(() -> {
            final Mastermind mastermind = componentAtomicReference.get();
            final ArrayAdapter<Villains> villainsAdapter;
            final ArrayAdapter<Henchmen> henchmenAdapter;

            if (mastermind.isCustom()) {

                final List<Villains> villainsList = LegendaryDatabase.getInstance()
                        .villainsDao()
                        .getAllBySetId(mastermind.getGameSet().getId())
                        .stream()
                        .map(VillainsEntity::toModel)
                        .collect(Collectors.toList());

                villainsList.add(0, Villains.NONE);

                villainsAdapter = new ArrayAdapter<>(
                        alwaysLeadsVillainsSpinner.getContext(),
                        R.layout.spinner_layout,
                        villainsList
                );

                villainsAdapter.setDropDownViewResource(R.layout.spinner_layout);

                final List<Henchmen> henchmenList = LegendaryDatabase.getInstance()
                        .henchmenDao()
                        .getAllBySetId(mastermind.getGameSet().getId())
                        .stream()
                        .map(HenchmenEntity::toModel)
                        .collect(Collectors.toList());

                henchmenList.add(0, Henchmen.NONE);

                henchmenAdapter = new ArrayAdapter<>(
                        alwaysLeadsHenchmenSpinner.getContext(),
                        R.layout.spinner_layout,
                        henchmenList
                );

                henchmenAdapter.setDropDownViewResource(R.layout.spinner_layout);

                new Handler(Looper.getMainLooper()).post(() -> {
                    alwaysLeadsVillainsSpinner.setAdapter(villainsAdapter);
                    alwaysLeadsVillainsSpinner.setSelection(villainsList.indexOf(mastermind.getAlwaysLeadsVillains()));

                    alwaysLeadsVillainsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(final AdapterView<?> parent,
                                final View view,
                                final int position,
                                final long id) {
                            mastermind.setAlwaysLeadsVillains(villainsList.get(position));
                        }

                        @Override
                        public void onNothingSelected(final AdapterView<?> parent) {

                        }
                    });

                    alwaysLeadsHenchmenSpinner.setAdapter(henchmenAdapter);
                    alwaysLeadsHenchmenSpinner.setSelection(henchmenList.indexOf(mastermind.getAlwaysLeadsHenchmen()));

                    alwaysLeadsHenchmenSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(final AdapterView<?> parent,
                                final View view,
                                final int position,
                                final long id) {
                            mastermind.setAlwaysLeadsHenchmen(henchmenList.get(position));
                        }

                        @Override
                        public void onNothingSelected(final AdapterView<?> parent) {

                        }
                    });
                });
            } else {
                villainsAdapter = new ArrayAdapter<>(
                        alwaysLeadsVillainsSpinner.getContext(),
                        R.layout.spinner_layout,
                        Collections.singletonList(mastermind.getAlwaysLeadsVillains())
                );

                villainsAdapter.setDropDownViewResource(R.layout.spinner_layout);

                henchmenAdapter = new ArrayAdapter<>(
                        alwaysLeadsHenchmenSpinner.getContext(),
                        R.layout.spinner_layout,
                        Collections.singletonList(mastermind.getAlwaysLeadsHenchmen())
                );

                henchmenAdapter.setDropDownViewResource(R.layout.spinner_layout);

                new Handler(Looper.getMainLooper()).post(() -> {
                    alwaysLeadsVillainsSpinner.setAdapter(villainsAdapter);
                    alwaysLeadsVillainsSpinner.setEnabled(false);

                    alwaysLeadsHenchmenSpinner.setAdapter(henchmenAdapter);
                    alwaysLeadsHenchmenSpinner.setEnabled(false);
                });
            }
        });
    }
}
