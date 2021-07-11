package com.thequietcroc.legendary.activities.info;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
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

public class VillainsInfoActivity extends CardInfoActivity {

    Spinner mastermindLeaderSpinner;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.villainsInfo));

        final View villainsInfoControls = LayoutInflater.from(componentControlsLayout.getContext())
                .inflate(R.layout.card_info_villain_henchmen_controls, componentControlsLayout, false);

        mastermindLeaderSpinner = villainsInfoControls.findViewById(R.id.cardInfoVHMastermindLeaderSpinner);

        componentControlsLayout.addView(villainsInfoControls);

        new DbAsyncTask(() -> {
            final Villains villains = (Villains) componentAtomicReference.get();
            final ArrayAdapter<Mastermind> adapter;

            if (villains.isCustom()) {

                final List<Mastermind> mastermindList = LegendaryDatabase.getInstance()
                        .mastermindDao()
                        .getAllBySetIdSync(villains.getGameSet().getId())
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

                mastermindLeaderSpinner.setAdapter(adapter);
                mastermindLeaderSpinner.setSelection(mastermindList.indexOf(villains.getMastermindLeader()));

                mastermindLeaderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(final AdapterView<?> parent,
                            final View view,
                            final int position,
                            final long id) {
                        mastermindList.get(position).setAlwaysLeadsVillains(villains);
                    }

                    @Override
                    public void onNothingSelected(final AdapterView<?> parent) {

                    }
                });
            } else {
                adapter = new ArrayAdapter<>(
                        mastermindLeaderSpinner.getContext(),
                        R.layout.spinner_layout,
                        Collections.singletonList(villains.getMastermindLeader())
                );

                adapter.setDropDownViewResource(R.layout.spinner_layout);

                mastermindLeaderSpinner.setAdapter(adapter);
                mastermindLeaderSpinner.setEnabled(false);
            }
        });
    }
}
