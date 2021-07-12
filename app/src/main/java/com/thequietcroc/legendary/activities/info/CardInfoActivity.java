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
import com.thequietcroc.legendary.database.entities.gamecomponents.GameSetEntity;
import com.thequietcroc.legendary.models.gamecomponents.GameSet;
import com.thequietcroc.legendary.models.gamecomponents.cards.BaseCard;
import com.thequietcroc.legendary.utilities.DbAsyncTask;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public abstract class CardInfoActivity extends InfoActivity {

    Spinner cardInfoGameSetSpinner;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final View cardInfoControls = LayoutInflater.from(componentControlsLayout.getContext())
                .inflate(R.layout.card_info_controls, componentControlsLayout, false);

        cardInfoGameSetSpinner = cardInfoControls.findViewById(R.id.cardInfoGameSetSpinner);

        componentControlsLayout.addView(cardInfoControls);

        new DbAsyncTask(() -> {
            final BaseCard card = (BaseCard) componentAtomicReference.get();
            final ArrayAdapter<GameSet> adapter;

            if (card.isCustom()) {
                final List<GameSet> gameSetList = LegendaryDatabase.getInstance()
                        .gameSetDao()
                        .getAllCustom()
                        .stream()
                        .map(GameSetEntity::toModel)
                        .collect(Collectors.toList());

                gameSetList.add(0, GameSet.NONE);
                adapter = new ArrayAdapter<>(
                        cardInfoGameSetSpinner.getContext(),
                        R.layout.spinner_layout,
                        gameSetList
                );

                adapter.setDropDownViewResource(R.layout.spinner_layout);

                new Handler(Looper.getMainLooper()).post(() -> {

                    cardInfoGameSetSpinner.setAdapter(adapter);
                    cardInfoGameSetSpinner.setSelection(gameSetList.indexOf(card.getGameSet()));

                    cardInfoGameSetSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(final AdapterView<?> parent,
                                final View view,
                                final int position,
                                final long id) {
                            card.setGameSet(gameSetList.get(position));
                        }

                        @Override
                        public void onNothingSelected(final AdapterView<?> parent) {

                        }
                    });
                });
            } else {
                adapter = new ArrayAdapter<>(
                        cardInfoGameSetSpinner.getContext(),
                        R.layout.spinner_layout,
                        Collections.singletonList(card.getGameSet())
                );

                adapter.setDropDownViewResource(R.layout.spinner_layout);

                new Handler(Looper.getMainLooper()).post(() -> {
                    cardInfoGameSetSpinner.setAdapter(adapter);
                    cardInfoGameSetSpinner.setEnabled(false);
                });
            }
        });
    }

    @Override
    protected void saveComponent() {
        final BaseCard card = (BaseCard) componentAtomicReference.get();

        card.setGameSet((GameSet) cardInfoGameSetSpinner.getSelectedItem());

        super.saveComponent();
    }
}
