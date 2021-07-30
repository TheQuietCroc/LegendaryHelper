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

public abstract class CardInfoActivity<T extends BaseCard> extends InfoActivity<T> {

    T card;
    Spinner cardInfoGameSetSpinner;
    ArrayAdapter<GameSet> gameSetArrayAdapter;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        card = componentAtomicReference.get();

        final View cardInfoControls = LayoutInflater.from(componentControlsLayout.getContext())
                .inflate(
                        R.layout.card_info_controls,
                        componentControlsLayout,
                        false);

        componentControlsLayout.addView(cardInfoControls);

        cardInfoGameSetSpinner = cardInfoControls.findViewById(R.id.cardInfoGameSetSpinner);

        new DbAsyncTask(() -> {

            if (card.isCustom()) {
                final List<GameSet> gameSetList = getCustomGameSetList();

                gameSetArrayAdapter = new ArrayAdapter<>(
                        cardInfoGameSetSpinner.getContext(),
                        R.layout.spinner_layout,
                        gameSetList
                );

                gameSetArrayAdapter.setDropDownViewResource(R.layout.spinner_layout);

                new Handler(Looper.getMainLooper()).post(() -> {

                    cardInfoGameSetSpinner.setAdapter(gameSetArrayAdapter);
                    cardInfoGameSetSpinner.setSelection(gameSetList.indexOf(card.getGameSet()));

                    cardInfoGameSetSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(final AdapterView<?> parent,
                                final View view,
                                final int position,
                                final long id) {

                            final GameSet selectedGameSet = gameSetArrayAdapter.getItem(position);

                            if (!card.getGameSet().equals(selectedGameSet)) {
                                onGameSetChanged();
                            }
                        }

                        @Override
                        public void onNothingSelected(final AdapterView<?> parent) {

                        }
                    });
                });
            } else {
                gameSetArrayAdapter = new ArrayAdapter<>(
                        cardInfoGameSetSpinner.getContext(),
                        R.layout.spinner_layout,
                        Collections.singletonList(card.getGameSet())
                );

                gameSetArrayAdapter.setDropDownViewResource(R.layout.spinner_layout);

                new Handler(Looper.getMainLooper()).post(() -> {
                    cardInfoGameSetSpinner.setAdapter(gameSetArrayAdapter);
                    cardInfoGameSetSpinner.setEnabled(false);
                });
            }
        });
    }

    @Override
    protected void saveComponent() {

        new DbAsyncTask(() -> {

            card.setGameSet(getSelectedGameSet());

            super.saveComponent();
        });

    }

    protected final List<GameSet> getCustomGameSetList() {

        final List<GameSet> gameSetList = LegendaryDatabase.getInstance()
                .gameSetDao()
                .getAllCustom()
                .stream()
                .map(GameSetEntity::toModel)
                .collect(Collectors.toList());

        gameSetList.add(0, GameSet.NONE);

        return gameSetList;
    }

    protected final GameSet getSelectedGameSet() {
        if (cardInfoGameSetSpinner.getSelectedItem() == null) {
            return card.getGameSet();
        }

        return (GameSet) cardInfoGameSetSpinner.getSelectedItem();
    }

    protected abstract void onGameSetChanged();
}
