package com.thequietcroc.legendary.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;

import com.thequietcroc.legendary.R;
import com.thequietcroc.legendary.custom.adapters.GameComponentRecyclerAdapter;
import com.thequietcroc.legendary.database.entities.gamecomponents.cards.MastermindEntity;
import com.thequietcroc.legendary.models.gamecomponents.cards.Henchmen;
import com.thequietcroc.legendary.models.gamecomponents.cards.Mastermind;
import com.thequietcroc.legendary.models.gamecomponents.cards.Villains;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.thequietcroc.legendary.utilities.LiveDataUtil.observeOnce;

public class FilterMastermindActivity extends FilterActivity {

    final GameComponentRecyclerAdapter<Mastermind> gameComponentRecyclerAdapter = new GameComponentRecyclerAdapter<>(new ArrayList<>());

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (null != getActionBar()) {
            getActionBar().setTitle(R.string.activityFilterMastermind);
        } else if (null != getSupportActionBar()) {
            getSupportActionBar().setTitle(R.string.activityFilterMastermind);
        }

        filterRecyclerView.setAdapter(gameComponentRecyclerAdapter);

        gameComponentRecyclerAdapter.setDbUpdateConsumer(mastermind ->
                AsyncTask.execute(() -> {
                    if (mastermind.isEnabled()) {
                        final Villains alwaysLeadsVillains = mastermind.getAlwaysLeadsVillains();
                        final Henchmen alwaysLeadsHenchmen = mastermind.getAlwaysLeadsHenchmen();

                        if (alwaysLeadsVillains.getId() != 0) {
                            alwaysLeadsVillains.setEnabled(mastermind.isEnabled());

                            db.villainsDao().update(alwaysLeadsVillains.toEntity());
                        }

                        if (alwaysLeadsHenchmen.getId() != 0) {
                            alwaysLeadsHenchmen.setEnabled(mastermind.isEnabled());

                            db.henchmenDao().update(alwaysLeadsHenchmen.toEntity());
                        }
                    }

                    db.mastermindDao().update(mastermind.toEntity());
                })
        );

        gameComponentRecyclerAdapter.setDbDeleteConsumer(mastermind ->
                AsyncTask.execute(() ->
                        db.mastermindDao().delete(mastermind.toEntity()))
        );

        observeOnce(this,
                db.mastermindDao().getAllAsync(),
                this::observerActions);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuActionAddNew: {

            }
            break;
            default:
                super.onOptionsItemSelected(item);
                break;
        }

        return true;
    }

    private void observerActions(final List<MastermindEntity> results) {
        gameComponentRecyclerAdapter.getComponentEntityList().addAll(results
                .stream()
                .map(MastermindEntity::toModel)
                .collect(Collectors.toList()));
        gameComponentRecyclerAdapter.notifyDataSetChanged();
    }

}
